package com.example.grocery_list_app_dev;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.Random;

import room.Items;

public class GroceryList extends AppCompatActivity {
    private TextView add_item;
    private RecyclerView recyclerView;
    private GLViewModel glViewModel;
    private LinearLayout linearLayout;
    private Dialog dialog;
    private TextView add_btn;
    private RelativeLayout exit_btn;
    private EditText item_name;
    private EditText item_qty;
    private EditText item_unit;
    private EditText item_price;
    private EditText item_desc;
    private TextView deleteAll;
    private TextView itemChecked;
    private TextView itemSize;
    private int category_id;
    private TextView listTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery_list);

        Intent intent = getIntent();
        category_id = intent.getIntExtra("category_id",0);
        String categoryName = intent.getStringExtra("list_name");

        GLViewModelFactory factory = new GLViewModelFactory(getApplication(),category_id);
        glViewModel = new ViewModelProvider(this,factory).get(GLViewModel.class);


        linearLayout = findViewById(R.id.add_items_msg);
        itemChecked = findViewById(R.id.item_checked);
        itemSize = findViewById(R.id.item_size);
        listTitle = findViewById(R.id.list_title);
        listTitle.setText(capitalizeWords(categoryName));

        // add item dialog code -->
        dialog = new Dialog(GroceryList.this);
        dialog.setContentView(R.layout.add_item_dialog);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.custom_dialog_bg));
        dialog.setCancelable(false);

        exit_btn = dialog.findViewById(R.id.exit_btn);
        exit_btn.setOnClickListener(v -> {
            dialog.dismiss();
        });

        add_item = findViewById(R.id.add_item);
        add_item.setOnClickListener(v -> {
            dialog.show();
        });

        item_name = dialog.findViewById(R.id.item_name);
        item_qty = dialog.findViewById(R.id.item_qty);
        item_unit = dialog.findViewById(R.id.item_unit);
        item_price = dialog.findViewById(R.id.item_price);
        item_desc = dialog.findViewById(R.id.item_desc);

        deleteAll = findViewById(R.id.delete_all);
        deleteAll.setOnClickListener(v -> {
            glViewModel.deleteAllItemsAsync(category_id);
            linearLayout.setVisibility(View.VISIBLE);
        });

        add_btn = dialog.findViewById(R.id.add_item_btn);
        add_btn.setOnClickListener(v -> {
            if(item_name.getText().toString().equals("")) {
                Toast.makeText(this, "Add item name", Toast.LENGTH_SHORT).show();
            }else {
                glViewModel.insertItemAsync(new Items(item_name.getText().toString(),false,category_id));
                item_name.setText("");
                item_qty.setText("");
                item_unit.setText("");
                item_price.setText("");
                item_desc.setText("");
                dialog.dismiss();
            }
        });

        LinearLayout back_btn = findViewById(R.id.back_btn);
        back_btn.setOnClickListener(v -> {
            finish();
        });

        recyclerView = findViewById(R.id.itemsContainer);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setHasFixedSize(true);
        final ItemsAdapter adapter = new ItemsAdapter(glViewModel);
        recyclerView.setAdapter(adapter);

        glViewModel.getAllItems().observe(this, new Observer<List<Items>>() {
            @Override
            public void onChanged(List<Items> items) {
                if(!items.isEmpty()) {
                    linearLayout.setVisibility(View.GONE);
                }

                int itemCount = items.size();
                int itemCheck = countCheckedItems(items);
                itemChecked.setText(String.valueOf(itemCheck));
                itemSize.setText(String.valueOf(itemCount));
                adapter.setItems(items);
            }
        });

        // Add swipe delete function
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                glViewModel.deleteItemAsync(adapter.getItemAt(viewHolder.getAdapterPosition()));
            }
        }).attachToRecyclerView(recyclerView);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private int countCheckedItems(List<Items> items) {
        int count = 0;
        for (Items item : items) {
            if (item.isChecked()) {
                count++;
            }
        }
        return count;
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
}