package com.example.grocery_list_app_dev;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class GLViewModelFactory implements ViewModelProvider.Factory {
    private final Application application;
    private final int category_id;

    public GLViewModelFactory(Application application, int category_id) {
        this.application = application;
        this.category_id = category_id;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(GLViewModel.class)) {
            return (T) new GLViewModel(application, category_id);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}