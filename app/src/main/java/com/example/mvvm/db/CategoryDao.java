package com.example.mvvm.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.mvvm.models.CategoryList;

//database access object
@Dao
public
interface CategoryDao {
    //deleting all stored data
    @Query("DELETE FROM CATEGORY_TABLE")
    void deleteAll();

    //retrieving all stored data
    @Query("SELECT * FROM CATEGORY_TABLE")
    LiveData<CategoryList> getAllCategories();

    //inserting data in to db
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllCategories(CategoryList categoryList);

}
