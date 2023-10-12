package room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CategoryDao {
    @Insert
    void insert(Category category);
    @Update
    void update(Category category);
    @Delete
    void delete(Category category);

    @Query("DELETE FROM category")
    void deleteAllCategory();

    @Query("SELECT * FROM category ORDER BY category_id ASC")
    LiveData<List<Category>> getALlCategory();

    @Query("SELECT Category.*, COUNT(Items.item_id) AS item_count " +
            "FROM Category " +
            "LEFT JOIN Items ON Category.category_id = Items.category_id " +
            "GROUP BY Category.category_id")
    LiveData<List<Category>> getCategoriesWithItemCount();
}
