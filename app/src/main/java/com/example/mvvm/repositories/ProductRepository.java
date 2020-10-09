package com.example.mvvm.repositories;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mvvm.data_from_api.ApiInterface;
import com.example.mvvm.data_from_api.RetrofitService;
import com.example.mvvm.db.CategoryDao;
import com.example.mvvm.db.CategoryDb;
import com.example.mvvm.models.CategoryList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//repository class - here data is fetched either from local db or from api depending up on network connection status
public class ProductRepository {

    private static ProductRepository productRepository;
    CategoryDao categoryDao;
    CategoryDb categoryDb;
    private MutableLiveData<CategoryList> productList = new MutableLiveData<>();
    private LiveData<CategoryList> productListFromDb = new LiveData<CategoryList>() {
    };

    public ProductRepository(Application application) {
        categoryDb = CategoryDb.getInstance(application);
        categoryDao = categoryDb.categoryDao();

    }

    //data from local db
    public LiveData<CategoryList> getProductListFromDb() {

        Thread thread = new Thread() {
            @Override
            public void run() {
                productListFromDb = categoryDb.categoryDao().getAllCategories();


            }
        };

        thread.start();


        return productListFromDb;

    }

    //data from api
    public MutableLiveData<CategoryList> getProductsFromApi() {
        ApiInterface retroInterface = RetrofitService.getRetrofitInstance().create(ApiInterface.class);

        Call<CategoryList> call = retroInterface.getAllProducts();

        call.enqueue(new Callback<CategoryList>() {
            @Override
            public void onResponse(Call<CategoryList> call, Response<CategoryList> response) {
                Log.v("response : ", response.body().toString());

                productList.setValue(response.body());


                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        if (categoryDb.categoryDao().getAllCategories() != null) {
                            categoryDb.categoryDao().deleteAll();

                            categoryDb.categoryDao().insertAllCategories(response.body());


                        } else {
                            categoryDb.categoryDao().insertAllCategories(response.body());
                        }
                    }
                };

                thread.start();


            }

            @Override
            public void onFailure(Call<CategoryList> call, Throwable t) {

                Log.v("response : ", t.toString());


            }
        });

        return productList;

    }


}

