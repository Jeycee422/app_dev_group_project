package com.example.grocery_list_app_dev;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.Random;

import room.Category;
import room.User;

public class CategoryList extends AppCompatActivity {

    private int avatarRandom;
    private Dialog dialog;
    private FloatingActionButton fab;
    private CategoryViewModel categoryViewModel;
    private MainViewModel mainViewModel;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);

        categoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        TextView userText = findViewById(R.id.fullname);
        TextView emailText = findViewById(R.id.email_address);
        TextView profile_pic = findViewById(R.id.profile_avatar);

        mainViewModel.getUser().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                userText.setText(users.get(0).getUsername());
                emailText.setText(users.get(0).getEmail());
                profile_pic.setBackgroundResource(users.get(0).getAvatar());
            }
        });


        dialog = new Dialog(CategoryList.this);
        dialog.setContentView(R.layout.add_list_dialog);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(AppCompatResources.getDrawable(getApplicationContext(),R.drawable.custom_dialog_bg));
        dialog.setCancelable(false);

        fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(v -> {
            dialog.show();
        });

        RelativeLayout exit_btn = dialog.findViewById(R.id.exit_btn);
        exit_btn.setOnClickListener(v -> {
            dialog.dismiss();
        });

        TextView listName = dialog.findViewById(R.id.list_name);
        TextView listDesc = dialog.findViewById(R.id.list_desc);

        TextView addListBtn = dialog.findViewById(R.id.add_list_btn);
        addListBtn.setOnClickListener(v -> {
            if (listName.getText().toString().equals("")) {
                Toast.makeText(this, "Enter name", Toast.LENGTH_SHORT).show();
            }else {
                categoryViewModel.insertCategoryAsync(new Category(capitalizeWords(listName.getText().toString()), listDesc.getText().toString().trim().equals("") ?"No Description":listDesc.getText().toString().trim()));
                listName.setText("");
                listDesc.setText("");
                dialog.dismiss();
            }
        });

        LinearLayout clearAll = findViewById(R.id.clear_all);
        clearAll.setOnClickListener(v -> {
            categoryViewModel.deleteAllCategoryAsync();
        });

        recyclerView = findViewById(R.id.category_container);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        recyclerView.addItemDecoration(new SpacesItemDecoration(16));
        recyclerView.setHasFixedSize(true);
        final CategoryAdapter adapter = new CategoryAdapter(getApplication(),categoryViewModel);
        recyclerView.setAdapter(adapter);

        LinearLayout linearLayout = findViewById(R.id.list_splash);

        categoryViewModel.getAllCategory().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categories) {
                if(!categories.isEmpty()) {
                    linearLayout.setVisibility(View.GONE);
                }else {
                    linearLayout.setVisibility(View.VISIBLE);
                }
                adapter.setCategories(categories);
            }
        });


    }
    @Override
    public void onBackPressed() {

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