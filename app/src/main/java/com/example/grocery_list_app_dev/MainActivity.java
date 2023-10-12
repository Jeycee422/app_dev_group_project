package com.example.grocery_list_app_dev;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.github.javafaker.Faker;

public class MainActivity extends AppCompatActivity {

    private TextView username;
    private TextView login_button;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Faker faker = new Faker();
        email = faker.internet().emailAddress();

        login_button = findViewById(R.id.login_button);
        username = findViewById(R.id.username_input);

        login_button.setOnClickListener(v -> {
            if(username.getText().toString().equals("")) {
                Toast.makeText(this, "Enter your username", Toast.LENGTH_SHORT).show();
            }else {
                startActivity(new Intent(MainActivity.this,CategoryList.class)
                        .putExtra("username",capitalizeWords(username.getText().toString()))
                        .putExtra("email",email)
                );
                username.setText("");
            }
        });
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