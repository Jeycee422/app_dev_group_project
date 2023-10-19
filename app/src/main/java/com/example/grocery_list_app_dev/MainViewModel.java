package com.example.grocery_list_app_dev;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import room.User;
import room.UserRepository;

public class MainViewModel extends AndroidViewModel {
    private final UserRepository userRepository;
    public MainViewModel(@NonNull Application application) {
        super(application);
        userRepository = new UserRepository(application);
    }
    public void insertUserAsync(User user) { userRepository.insert(user); }
    public void deleteUserAsync(User user) {
        userRepository.delete(user);
    }
    public void updateUserAsync(User user) {
        userRepository.update(user);
    }

    public LiveData<List<User>> getUser() {return userRepository.getUser();}
}
