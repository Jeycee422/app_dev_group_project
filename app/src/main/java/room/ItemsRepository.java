package room;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ItemsRepository {
    private final ItemsDao itemsDao;
    private final ExecutorService executorService;

    private LiveData<List<Items>> allItems;
    private List<Items> allItemsList;
    public ItemsRepository(Application application,int category_id) {
        RoomDB appDatabase = RoomDB.getInstance(application);
        itemsDao = appDatabase.itemsDao();
        executorService = Executors.newSingleThreadExecutor();
        allItems = itemsDao.getAllItems(category_id);
    }

    public void insert(Items items) {
        CompletableFuture.runAsync(() -> itemsDao.insert(items),executorService);
    }
    public void update(Items items) {
        CompletableFuture.runAsync(() -> itemsDao.update(items),executorService);
    }
    public void delete(Items items) {
        CompletableFuture.runAsync(() -> itemsDao.delete(items),executorService);
    }

    public void deleteAllItems(int category_id) {
        CompletableFuture.runAsync(() -> itemsDao.deleteAllItems(category_id),executorService);
    }

    public LiveData<List<Items>> getAllItems() {
        return allItems;
    }
}
