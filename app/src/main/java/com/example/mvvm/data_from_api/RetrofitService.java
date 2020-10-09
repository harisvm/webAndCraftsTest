package com.example.mvvm.data_from_api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
//initialising retofit service
public class RetrofitService {
    private static Retrofit retrofit;
    private static final String BASE_URL = "https://www.mocky.io";

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
