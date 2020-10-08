package com.example.mvvm.db;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.mvvm.models.Category;
import com.example.mvvm.models.CategoryList;

import java.util.List;

@Dao
public
interface CategoryDao {

    @Query("DELETE FROM CATEGORY_TABLE")
    void deleteAll();

    @Query("SELECT * FROM CATEGORY_TABLE")
    LiveData<CategoryList> getAllCategories();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllCategories(CategoryList categoryList);

}
