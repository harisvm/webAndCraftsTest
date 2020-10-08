package com.example.mvvm.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mvvm.R;
import com.example.mvvm.models.Category;
import com.github.florent37.expansionpanel.ExpansionLayout;
import com.github.florent37.expansionpanel.viewgroup.ExpansionLayoutCollection;

import java.util.ArrayList;
import java.util.List;

public final  class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerHolder> {
    Context context;

    private final ExpansionLayoutCollection expansionsCollection = new ExpansionLayoutCollection();
    private final List<Category> categoryList = new ArrayList();


    public RecyclerAdapter(Context context) {
        expansionsCollection.openOnlyOne(true);
        this.context = context;
    }

    @Override
    public RecyclerAdapter.RecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return RecyclerAdapter.RecyclerHolder.buildFor(parent);
    }


    @Override
    public void onBindViewHolder(RecyclerAdapter.RecyclerHolder holder, int position) {
        holder.bind(categoryList.get(position));


        holder.title.setText(categoryList.get(position).getTitle());

        if (categoryList.get(position).getProducts() != null) {
            for (int i = 0; i < categoryList.get(position).getProducts().size(); i++) {
                ProductAdapter productAdapter = new ProductAdapter(categoryList.get(position).getProducts(), context);
                GridLayoutManager gridLayoutManager = new GridLayoutManager(context,2, RecyclerView.HORIZONTAL,false);


                holder.gridView.setLayoutManager(gridLayoutManager);
                holder.gridView.setHasFixedSize(true);
                holder.gridView.setAdapter(productAdapter);



            }

        }
        expansionsCollection.add(holder.getExpansionLayout());


    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public void setItems(List<Category> items) {
        this.categoryList.addAll(items);
        notifyDataSetChanged();
    }

    public final static class RecyclerHolder extends RecyclerView.ViewHolder {

        private static final int LAYOUT = R.layout.individual_item;

        public static RecyclerAdapter.RecyclerHolder buildFor(ViewGroup viewGroup) {
            return new RecyclerAdapter.RecyclerHolder(LayoutInflater.from(viewGroup.getContext()).inflate(LAYOUT, viewGroup, false));
        }

        ExpansionLayout expansionLayout;
        TextView title;
        RecyclerView gridView;

        public RecyclerHolder(View itemView) {
            super(itemView);
            expansionLayout = itemView.findViewById(R.id.expansionLayout);

            title = itemView.findViewById(R.id.title_exp);
            gridView = itemView.findViewById(R.id.gridView);


        }

        public void bind(Object object) {
            expansionLayout.collapse(false);
        }

        public ExpansionLayout getExpansionLayout() {
            return expansionLayout;
        }
    }
}

