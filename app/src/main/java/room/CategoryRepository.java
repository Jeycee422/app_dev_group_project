package room;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CategoryRepository {
    private final CategoryDao categoryDao;
    private final ExecutorService executorService;
    private LiveData<List<Category>> allCategory;

    public CategoryRepository(Application application) {
        RoomDB appDatabase = RoomDB.getInstance(application);
        categoryDao = appDatabase.categoryDao();
        executorService = Executors.newSingleThreadExecutor();
        allCategory = categoryDao.getCategoriesWithItemCount();
    }

    public void insert(Category category) {
        CompletableFuture.runAsync(() -> categoryDao.insert(category),executorService);
    }
    public void update(Category category) {
        CompletableFuture.runAsync(() -> categoryDao.update(category),executorService);
    }
    public void delete(Category category) {
        CompletableFuture.runAsync(() -> categoryDao.delete(category),executorService);
    }
    public void deleteAllCategory() {
        CompletableFuture.runAsync(categoryDao::deleteAllCategory,executorService);
    }
    public LiveData<List<Category>> getAllCategory() {
        return allCategory;
    }
}
