package com.example.mvvm.repositories;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.RetrofitService;
import com.example.mvvm.Interfaces.ApiInterface;
import com.example.mvvm.R;
import com.example.mvvm.activities.MainActivity;
import com.example.mvvm.adapters.ProductAdapter;
import com.example.mvvm.db.CategoryDao;
import com.example.mvvm.db.CategoryDb;
import com.example.mvvm.models.Category;
import com.example.mvvm.models.CategoryList;
import com.github.florent37.expansionpanel.ExpansionLayout;
import com.github.florent37.expansionpanel.viewgroup.ExpansionLayoutCollection;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductRepository {

    private MutableLiveData<CategoryList> productList = new MutableLiveData<>();
    private LiveData<CategoryList> productListFromDb = new LiveData<CategoryList>() {
    };

    private static ProductRepository productRepository;
    CategoryDao categoryDao;
    CategoryDb categoryDb;

    public ProductRepository(Application application) {
        categoryDb = CategoryDb.getInstance(application);
        categoryDao = categoryDb.categoryDao();

    }

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

