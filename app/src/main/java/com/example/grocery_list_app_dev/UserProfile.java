package com.example.grocery_list_app_dev;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import room.User;

public class UserProfile extends AppCompatActivity {

    private MainViewModel mainViewModel;
    private CategoryViewModel categoryViewModel;
    private Dialog dialog;
    private Dialog updateDialog;
    private SessionManagement sessionManagement;
    private int selected_avatar_id;
    private int radioIdChecked;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        categoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);

        sessionManagement = new SessionManagement(this);


        int[] drawables = {R.drawable.einstein,R.drawable.afro,R.drawable.marilyn,R.drawable.builder,R.drawable.avocado,R.drawable.female_one,R.drawable.muslim,R.drawable.away_face};
        HashMap<Integer, Integer> avatarMap = new HashMap<>();


        updateDialog = new Dialog(UserProfile.this);
        updateDialog.setContentView(R.layout.update_profile_dialog);
        updateDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        updateDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.custom_dialog_bg));
        updateDialog.setCancelable(false);

        EditText usernameUpdate = updateDialog.findViewById(R.id.username_input);
        EditText emailUpdate = updateDialog.findViewById(R.id.email_input);
        RadioGroup radioGroupUpdate = updateDialog.findViewById(R.id.avatar_group);

        for (int i = 0; i < radioGroupUpdate.getChildCount(); i++) {
            View child = radioGroupUpdate.getChildAt(i);

            if (child instanceof RadioButton) {
                RadioButton radioButton = (RadioButton) child;
                avatarMap.put(drawables[i],radioButton.getId());
            }
        }
        radioGroupUpdate.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.radio_1) {
                    selected_avatar_id = R.drawable.einstein;
                    Log.d(String.valueOf(checkedId),String.valueOf(selected_avatar_id));
                }else if(checkedId == R.id.radio_2) {
                    selected_avatar_id = R.drawable.afro;
                    Log.d(String.valueOf(checkedId),String.valueOf(selected_avatar_id));
                }
                else if(checkedId == R.id.radio_3) {
                    selected_avatar_id = R.drawable.marilyn;
                    Log.d(String.valueOf(checkedId),String.valueOf(selected_avatar_id));
                }
                else if(checkedId == R.id.radio_4) {
                    selected_avatar_id = R.drawable.builder;
                    Log.d(String.valueOf(checkedId),String.valueOf(selected_avatar_id));
                }
                else if(checkedId == R.id.radio_5) {
                    selected_avatar_id = R.drawable.avocado;
                    Log.d(String.valueOf(checkedId),String.valueOf(selected_avatar_id));
                }
                else if(checkedId == R.id.radio_6) {
                    selected_avatar_id = R.drawable.female_one;
                    Log.d(String.valueOf(checkedId),String.valueOf(selected_avatar_id));
                }
                else if(checkedId == R.id.radio_7) {
                    selected_avatar_id = R.drawable.muslim;
                    Log.d(String.valueOf(checkedId),String.valueOf(selected_avatar_id));
                }
                else if(checkedId == R.id.radio_8) {
                    selected_avatar_id = R.drawable.away_face;
                    Log.d(String.valueOf(checkedId),String.valueOf(selected_avatar_id));
                }
            }
        });

        LinearLayout updateProfileBtn = findViewById(R.id.update_profile_button);
        updateProfileBtn.setOnClickListener(v -> {
            updateDialog.show();
        });

        RelativeLayout exit_btn = updateDialog.findViewById(R.id.exit_btn);
        exit_btn.setOnClickListener(v -> {
            usernameUpdate.setText("");
            emailUpdate.setText("");

            for (int i = 0; i < radioGroupUpdate.getChildCount(); i++) {
                View child = radioGroupUpdate.getChildAt(i);

                if (child instanceof RadioButton) {
                    RadioButton radioButton = (RadioButton) child;
                    if(radioButton.getId() == radioIdChecked) {
                        radioButton.setChecked(true);
                    }
                }
            }

            for(Map.Entry<Integer,Integer> entry : avatarMap.entrySet()) {
                Integer key = entry.getKey();
                Integer value = entry.getValue();
//                Log.d("Key",String.valueOf(key));
//                Log.d("Value",String.valueOf(value));
            }
            updateDialog.dismiss();
        });

        TextView updateProfile = updateDialog.findViewById(R.id.update_profile);
        updateProfile.setOnClickListener(v -> {
            int id = sessionManagement.getSession();
            if(usernameUpdate.getText().toString().equals("")) {
                Toast.makeText(this, "Enter new username", Toast.LENGTH_SHORT).show();
            } else if (emailUpdate.getText().toString().equals("")) {
                Toast.makeText(this, "Enter new email", Toast.LENGTH_SHORT).show();
            }else {
                User user = new User(usernameUpdate.getText().toString(),emailUpdate.getText().toString(),selected_avatar_id);
                user.setUser_id(id);
                mainViewModel.updateUserAsync(user);
                updateDialog.dismiss();
            }
        });



        dialog = new Dialog(UserProfile.this);
        dialog.setContentView(R.layout.confirm_deletion);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.custom_dialog_bg));
        dialog.setCancelable(false);


        LinearLayout back_btn = findViewById(R.id.back_btn);
        back_btn.setOnClickListener(v -> {
            finish();
        });

        TextView delete_profile_btn = findViewById(R.id.delete_profile_btn);
        delete_profile_btn.setOnClickListener(v -> {
            dialog.show();
        });

        TextView cancel_delete = dialog.findViewById(R.id.cancel_delete_btn);
        cancel_delete.setOnClickListener(v -> {
            dialog.dismiss();
        });
        TextView confirm_delete = dialog.findViewById(R.id.confirm_delete_btn);
        confirm_delete.setOnClickListener(v -> {
            mainViewModel.deleteUser();
            categoryViewModel.deleteAllCategoryAsync();
            sessionManagement.removeSession();
            dialog.dismiss();
            finish();
            startActivity(new Intent(this, MainActivity.class));
        });

        TextView profile_image = findViewById(R.id.profile_image);
        TextView profile_username = findViewById(R.id.profile_username);
        TextView profile_email = findViewById(R.id.profile_email);

        mainViewModel.getUser().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                if(!users.isEmpty()) {
                    profile_image.setBackgroundResource(users.get(0).getAvatar());
                    profile_username.setText(users.get(0).getUsername());
                    profile_email.setText(users.get(0).getEmail());

                    usernameUpdate.setHint(users.get(0).getUsername());
                    emailUpdate.setHint(users.get(0).getEmail());
                    for (int i = 0; i < radioGroupUpdate.getChildCount(); i++) {
                        View child = radioGroupUpdate.getChildAt(i);

                        if (child instanceof RadioButton) {
                            RadioButton radioButton = (RadioButton) child;
                            if(avatarMap.get(users.get(0).getAvatar()) == radioButton.getId()) {
                                radioButton.setChecked(true);
                                radioIdChecked = radioButton.getId();
                            }
                        }
                    }
                }
            }
        });
    }
}