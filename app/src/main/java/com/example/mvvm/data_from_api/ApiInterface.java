package com.example.mvvm.data_from_api;

import com.example.mvvm.models.CategoryList;

import retrofit2.Call;
import retrofit2.http.GET;

//api get method for getting all products
public interface ApiInterface {
    @GET("/v2/5ec39cba300000720039c1f6")
    Call<CategoryList> getAllProducts();
}
