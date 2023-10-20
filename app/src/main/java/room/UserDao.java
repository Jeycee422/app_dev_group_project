package room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao {
    @Insert
    void insert(User user);
    @Update
    void update(User user);
    @Delete
    void delete(User user);
    @Query("SELECT * FROM user")
    LiveData<List<User>> getUser();

    @Query("SELECT * FROM user WHERE user_id = :user_id")
    LiveData<User> getUserById(int user_id);

    @Query("DELETE FROM user")
    void deleteUser();
}
