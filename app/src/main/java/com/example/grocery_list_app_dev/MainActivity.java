package com.example.grocery_list_app_dev;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.javafaker.Faker;

import java.util.List;

import room.User;

public class MainActivity extends AppCompatActivity {

    private EditText username;
    private TextView login_button;
    private EditText email;
    private int selected_avatar_id = R.drawable.einstein;
    private MainViewModel mainViewModel;
    private SessionManagement sessionManagement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sessionManagement = new SessionManagement(this);
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        login_button = findViewById(R.id.login_button);
        username = findViewById(R.id.username_input);
        email = findViewById(R.id.email_input);


        login_button.setOnClickListener(v -> {
            if(username.getText().toString().equals("")) {
                Toast.makeText(this, "Enter your username", Toast.LENGTH_SHORT).show();
            }else if(email.getText().toString().equals("")){
                Toast.makeText(this, "Enter your email", Toast.LENGTH_SHORT).show();
            }else {
                mainViewModel.insertUserAsync(new User(capitalizeWords(username.getText().toString()),email.getText().toString().trim(),selected_avatar_id));
                sessionManagement.saveSession(new User(capitalizeWords(username.getText().toString()),email.getText().toString().trim(),selected_avatar_id));
                startActivity(new Intent(MainActivity.this,CategoryList.class));
            }
        });
        RadioGroup avatar_group = findViewById(R.id.avatar_group);
        avatar_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.radio_1) {
                    selected_avatar_id = R.drawable.einstein;
                }else if(checkedId == R.id.radio_2) {
                    selected_avatar_id = R.drawable.afro;
                }
                else if(checkedId == R.id.radio_3) {
                    selected_avatar_id = R.drawable.marilyn;
                }
                else if(checkedId == R.id.radio_4) {
                    selected_avatar_id = R.drawable.builder;
                }
                else if(checkedId == R.id.radio_5) {
                    selected_avatar_id = R.drawable.avocado;
                }
                else if(checkedId == R.id.radio_6) {
                    selected_avatar_id = R.drawable.female_one;
                }
                else if(checkedId == R.id.radio_7) {
                    selected_avatar_id = R.drawable.muslim;
                }
                else if(checkedId == R.id.radio_8) {
                    selected_avatar_id = R.drawable.away_face;
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        sessionManagement = new SessionManagement(this);
        int ID = sessionManagement.getSession();

        if(ID != -1) {
            startActivity(new Intent(MainActivity.this,CategoryList.class));
        }
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