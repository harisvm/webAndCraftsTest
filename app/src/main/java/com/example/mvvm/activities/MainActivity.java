package com.example.mvvm.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.RetrofitService;
import com.example.mvvm.Interfaces.ApiInterface;
import com.example.mvvm.R;
import com.example.mvvm.adapters.ProductAdapter;
import com.example.mvvm.adapters.RecyclerAdapter;
import com.example.mvvm.models.Category;
import com.example.mvvm.models.CategoryList;
import com.example.mvvm.viewmodels.MainActivityViewModel;
import com.github.florent37.expansionpanel.ExpansionLayout;
import com.github.florent37.expansionpanel.viewgroup.ExpansionLayoutCollection;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.core.content.ContextCompat.getSystemService;

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
        if (isNetworkConnected()) {

            mainActivityViewModel.loadCategoriesFromApiAndStore().observe(MainActivity.this, categoryResponse -> {
                initRecyclerView(categoryResponse);
                adapter.notifyDataSetChanged();
                if(mDialog.isShowing()){

                    mDialog.dismiss();
                }


            });

        } else {
            if(mDialog.isShowing()){

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


    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    private void initRecyclerView(CategoryList categoryList) {
        adapter.setItems(categoryList.getCategories());

        LinearLayoutManager llm = new LinearLayoutManager(MainActivity.this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(adapter);


    }


}