package com.example.mvvm.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.mvvm.models.Category;
import com.example.mvvm.models.CategoryList;

@Database(entities = CategoryList.class, version = 2)
public abstract class CategoryDb extends RoomDatabase {
    private static CategoryDb instance;

    public abstract CategoryDao categoryDao();

    public static synchronized CategoryDb getInstance(Context context) {
        if (instance == null) {

            instance = Room.databaseBuilder(context.getApplicationContext(), CategoryDb.class, "category_database")

                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallBack)
                    .build();

        }
        return instance;

    }

    private static RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback(){

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
        }


    };

}