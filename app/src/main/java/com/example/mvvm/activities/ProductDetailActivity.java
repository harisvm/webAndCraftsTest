package com.example.mvvm.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mvvm.R;
import com.example.mvvm.models.Product;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductDetailActivity extends AppCompatActivity {
    @BindView(R.id.imageDetailView)
    ImageView imageView;
    @BindView(R.id.productDetailName)
    TextView productName;
    @BindView(R.id.productDetailPrice)
    TextView productPrice;
    @BindView(R.id.productDetailDescription)
    TextView productDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        ButterKnife.bind(this);

        Product passedProduct = getIntent().getParcelableExtra("product");

        Picasso.get().load(passedProduct.getImageUrl()).memoryPolicy(MemoryPolicy.NO_STORE).into(imageView);

        productName.setText(passedProduct.getTitle());
        productPrice.setText(passedProduct.getPrice().toString());
        productDescription.setText(passedProduct.getDescription());




    }
}