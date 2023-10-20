package room;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UserRepository {
    private final UserDao userDao;
    private final ExecutorService executorService;
    private LiveData<List<User>> user;
    public UserRepository(Application application) {
        RoomDB appDatabase = RoomDB.getInstance(application);
        userDao = appDatabase.userDao();
        executorService = Executors.newSingleThreadExecutor();
        user = userDao.getUser();
    }
    public void insert(User user) {
        CompletableFuture.runAsync(() -> userDao.insert(user),executorService);
    }
    public void update(User user) {
        CompletableFuture.runAsync(() -> userDao.update(user),executorService);
    }
    public void delete(User user) {
        CompletableFuture.runAsync(() -> userDao.delete(user),executorService);
    }
    public LiveData<List<User>> getUser() { return user;}
    public void deleteUser() {
        CompletableFuture.runAsync(userDao::deleteUser,executorService);
    }
}
