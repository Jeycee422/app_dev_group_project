package com.example.grocery_list_app_dev;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import room.Items;
import room.ItemsRepository;
import room.User;
import room.UserRepository;

public class GLViewModel extends AndroidViewModel {
    private final ItemsRepository itemsRepository;
    public GLViewModel(@NonNull Application application,int category_id) {
        super(application);
        itemsRepository = new ItemsRepository(application,category_id);
    }

    public void insertItemAsync(Items items) {
        itemsRepository.insert(items);
    }

    public LiveData<List<Items>> getAllItems() {
        return itemsRepository.getAllItems();
    }

    public void deleteItemAsync(Items items) {
        itemsRepository.delete(items);
    }

    public void updateItemsAsync(Items items) {
        itemsRepository.update(items);
    }

    public void deleteAllItemsAsync(int category_id) {
        itemsRepository.deleteAllItems(category_id);
    }
}
