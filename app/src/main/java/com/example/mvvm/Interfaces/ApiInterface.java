package com.example.mvvm.Interfaces;

import com.example.mvvm.models.CategoryList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {
    @GET("/v2/5ec39cba300000720039c1f6")
    Call<CategoryList> getAllProducts();
}
