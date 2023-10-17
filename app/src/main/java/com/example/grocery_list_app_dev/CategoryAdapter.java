package com.example.grocery_list_app_dev;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

import room.Category;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryHolder> {
    private List<Category> categories = new ArrayList<>();
    private Application application;
    private CategoryViewModel categoryViewModel;
    @NonNull
    @Override
    public CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_card,parent,false);

        return new CategoryHolder(view);
    }

    public CategoryAdapter(Application application,CategoryViewModel categoryViewModel) {
        this.application = application;
        this.categoryViewModel = categoryViewModel;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryHolder holder, int position) {
        Category currentCategory = categories.get(position);

        holder.itemsCount.setText(String.valueOf(currentCategory.getItem_count()));
        holder.categoryItem.setText(currentCategory.getItem_count() < 2 ?"Item":"Items");
        holder.categoryName.setText(currentCategory.getName());
        holder.categoryDesc.setText(currentCategory.getDescription());
        holder.categoryCard.setOnClickListener(v -> {
            application.startActivity(new Intent(v.getContext(),GroceryList.class).putExtra("list_name",currentCategory.getName()).putExtra("category_id",currentCategory.getCategory_id()).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
        notifyDataSetChanged();
    }

    class CategoryHolder extends RecyclerView.ViewHolder {

        private TextView categoryName;
        private TextView itemsCount;
        private TextView categoryDesc;
        private MaterialCardView categoryCard;
        private TextView categoryItem;
        private LinearLayout delete_list;
        public CategoryHolder(@NonNull View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.category_name);
            categoryItem = itemView.findViewById(R.id.category_item);
            categoryDesc = itemView.findViewById(R.id.category_desc);
            itemsCount = itemView.findViewById(R.id.category_item_count);
            categoryCard = itemView.findViewById(R.id.category_materialCard);
            delete_list = itemView.findViewById(R.id.delete_list);

            delete_list.setOnClickListener(v -> {
                categoryViewModel.deleteCategoryAsync(categories.get(getAdapterPosition()));
            });
        }
    }

}
