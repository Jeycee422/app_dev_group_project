package com.example.grocery_list_app_dev;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import room.Category;
import room.CategoryRepository;

public class CategoryViewModel extends AndroidViewModel {
    private final CategoryRepository categoryRepository;
    public CategoryViewModel(@NonNull Application application) {
        super(application);
        categoryRepository = new CategoryRepository(application);
    }
    public void insertCategoryAsync(Category category) {
        categoryRepository.insert(category);
    }
    public void updateCategoryAsync(Category category) {
        categoryRepository.update(category);
    }
    public void deleteCategoryAsync(Category category) {
        categoryRepository.delete(category);
    }
    public void deleteAllCategoryAsync() {
        categoryRepository.deleteAllCategory();
    }
    public LiveData<List<Category>> getAllCategory() {
        return categoryRepository.getAllCategory();
    }

}
