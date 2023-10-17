package com.example.grocery_list_app_dev;

import android.content.res.ColorStateList;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StrikethroughSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

import room.Items;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemsHolder> {
    private List<Items> items = new ArrayList<>();
    private GLViewModel glViewModel;
    @NonNull
    @Override
    public ItemsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_card,parent,false);
        return new ItemsHolder(itemView);
    }
    public ItemsAdapter(GLViewModel viewModel) {
        this.glViewModel = viewModel;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemsHolder holder, int position) {
        Items currentItem = items.get(position);

        String itemName = capitalizeWords(currentItem.getName());

        holder.checkBox.setText(itemName);
        holder.checkBox.setChecked(currentItem.isChecked());

        SpannableString spannableString = new SpannableString(itemName);
        spannableString.setSpan(new StrikethroughSpan(),0,spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        ColorStateList buttonTintPrimary = ColorStateList.valueOf(holder.checkBox.getResources().getColor(R.color.primary_600));
        ColorStateList buttonTintPrimary = ColorStateList.valueOf(ContextCompat.getColor(holder.checkBox.getContext(), R.color.primary_600));
        ColorStateList strokePrimary = ColorStateList.valueOf(holder.checkBox.getResources().getColor(R.color.primary_300));
        ColorStateList cardBgPrimary = ColorStateList.valueOf(holder.checkBox.getResources().getColor(R.color.primary_50));
        ColorStateList buttonTintGray = ColorStateList.valueOf(holder.checkBox.getResources().getColor(R.color.gray_200));
        ColorStateList strokeGray = ColorStateList.valueOf(holder.checkBox.getResources().getColor(R.color.gray_200));
        ColorStateList cardBgGray = ColorStateList.valueOf(holder.checkBox.getResources().getColor(R.color.gray_25));

        if(currentItem.isChecked()) {
            holder.checkBox.setText(spannableString);
            holder.checkBox.setTextColor(holder.checkBox.getResources().getColor(R.color.primary_600));
            holder.checkBox.setButtonTintList(buttonTintPrimary);
            holder.materialCardView.setStrokeColor(strokePrimary);
            holder.materialCardView.setCardBackgroundColor(cardBgPrimary);
        }else {
            holder.checkBox.setText(itemName);
            holder.checkBox.setButtonTintList(buttonTintGray);
            holder.materialCardView.setStrokeColor(strokeGray);
            holder.materialCardView.setCardBackgroundColor(cardBgGray);
            holder.checkBox.setTextColor(holder.checkBox.getResources().getColor(R.color.gray_700));
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static String capitalizeWords(String input) {
        // Split the input string into words based on spaces
        String[] words = input.split(" ");
        StringBuilder result = new StringBuilder();

        for (String word : words) {
            if (!word.isEmpty()) {
                // Capitalize the first letter of the word
                String firstLetter = word.substring(0, 1).toUpperCase();
                String restOfWord = word.substring(1).toLowerCase();
                result.append(firstLetter).append(restOfWord).append(" ");
            }
        }

        // Remove the trailing space and return the result
        return result.toString().trim();
    }

    public void setItems(List<Items> items) {
        this.items = items;
        notifyDataSetChanged();
    }
    public Items getItemAt(int position) {
        return items.get(position);
    }

    class ItemsHolder extends RecyclerView.ViewHolder {

        private CheckBox checkBox;
        private MaterialCardView materialCardView;
        public ItemsHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.checkBox);
            materialCardView = itemView.findViewById(R.id.item_materialCard);


            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean isChecked = ((CheckBox) v).isChecked();

                    if(isChecked) {
                        items.get(getAdapterPosition()).setChecked(true);
                        glViewModel.updateItemsAsync(items.get(getAdapterPosition()));

                    }else {
                        items.get(getAdapterPosition()).setChecked(false);
                        glViewModel.updateItemsAsync(items.get(getAdapterPosition()));

                    }
                    notifyDataSetChanged();
                }
            });

        }
    }
}
