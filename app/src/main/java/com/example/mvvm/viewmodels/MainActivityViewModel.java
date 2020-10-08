package com.example.mvvm.viewmodels;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mvvm.activities.MainActivity;
import com.example.mvvm.models.CategoryList;
import com.example.mvvm.repositories.ProductRepository;

import static androidx.core.content.ContextCompat.getSystemService;

public class MainActivityViewModel extends AndroidViewModel {

    public ProductRepository productRepository;


    public MainActivityViewModel(@NonNull Application application) {

        super(application);
        this.productRepository = new ProductRepository(application);

    }



    public MutableLiveData<CategoryList> loadCategoriesFromApiAndStore() {

        return productRepository.getProductsFromApi();
    }

    public LiveData<CategoryList> loadCategoriesFromDb() {

        return productRepository.getProductListFromDb();
    }


}
