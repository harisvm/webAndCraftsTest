package com.example.mvvm.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mvvm.R;
import com.example.mvvm.adapters.RecyclerAdapter;
import com.example.mvvm.models.CategoryList;
import com.example.mvvm.viewmodels.MainActivityViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.recycler)
    RecyclerView recyclerView;

    MainActivityViewModel mainActivityViewModel;
    RecyclerAdapter adapter = new RecyclerAdapter(MainActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        getSupportActionBar().setTitle("Categories");
        mainActivityViewModel = ViewModelProviders.of(MainActivity.this).get(MainActivityViewModel.class);
        Log.v("CONNECTED", String.valueOf(isNetworkConnected()));
        ProgressDialog mDialog = new ProgressDialog(MainActivity.this);
        mDialog.setTitle("Please wait");
        mDialog.show();

        //if connected to network - showing data from api - also storing a fresh copy ito local db
        if (isNetworkConnected()) {

            mainActivityViewModel.loadCategoriesFromApiAndStore().observe(MainActivity.this, categoryResponse -> {
                initRecyclerView(categoryResponse);
                adapter.notifyDataSetChanged();
                if (mDialog.isShowing()) {

                    mDialog.dismiss();
                }


            });

        }


        //if not connected to network - showing data from local db
        else {
            if (mDialog.isShowing()) {

                mDialog.dismiss();
            }

            Toast.makeText(this, "Showing data from local cache", Toast.LENGTH_SHORT).show();
            mainActivityViewModel.loadCategoriesFromDb().observe(MainActivity.this, categoryList -> {


                initRecyclerView(categoryList);
                Log.v("from db", categoryList.toString());
                adapter.notifyDataSetChanged();


            });
        }

    }

    //checking network connection state
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }


    //passing data in to recyclerview and attaching adapter
    private void initRecyclerView(CategoryList categoryList) {
        adapter.setItems(categoryList.getCategories());

        LinearLayoutManager llm = new LinearLayoutManager(MainActivity.this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(adapter);


    }


}