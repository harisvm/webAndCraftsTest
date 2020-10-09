package com.example.mvvm.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mvvm.R;
import com.example.mvvm.activities.ProductDetailActivity;
import com.example.mvvm.models.Product;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

//products are attached in to their categories using this adapter
public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    List<Product> productsList;
    Context context;

    public ProductAdapter(List<Product> productsList, Context context) {
        this.productsList = productsList;
        this.context = context;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Picasso.get().load(productsList.get(position).getImageUrl()).memoryPolicy(MemoryPolicy.NO_STORE).into(holder.imageView);

        holder.productName.setText(productsList.get(position).getTitle());
        holder.productPrice.setText("₹ " + productsList.get(position).getPrice().toString());
        holder.imageView.setOnClickListener(v -> {

            Product selectedProduct = productsList.get(position);

            context.startActivity(new Intent(context, ProductDetailActivity.class).putExtra("product", selectedProduct));


        });


    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView productName, productPrice;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.productImage);
            productName = itemView.findViewById(R.id.productName);
            productPrice = itemView.findViewById(R.id.productPrice);
        }


    }
}