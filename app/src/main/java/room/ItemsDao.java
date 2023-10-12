package room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Dao
public interface ItemsDao {
    @Insert
    void insert(Items items);
    @Update
    void update(Items items);
    @Delete
    void delete(Items items);

    @Query("DELETE FROM items WHERE category_id = :category_id")
    void deleteAllItems(int category_id);

    @Query("SELECT * FROM items WHERE category_id = :category_id ORDER BY item_id ASC")
    LiveData<List<Items>> getAllItems(int category_id);
}
