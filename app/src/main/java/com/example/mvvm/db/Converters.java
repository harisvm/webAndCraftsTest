package com.example.mvvm.db;

import androidx.room.TypeConverter;

import com.example.mvvm.models.Category;
import com.example.mvvm.models.Product;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Converters {

    @TypeConverter
    public String fromValuesToList(ArrayList<Category> value) {
        if (value== null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Category>>() {}.getType();
        return gson.toJson(value, type);
    }

    @TypeConverter
    public ArrayList<Category> toOptionValuesList(String value) {
        if (value== null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Category>>() {
        }.getType();
        return gson.fromJson(value, type);
    }
}
