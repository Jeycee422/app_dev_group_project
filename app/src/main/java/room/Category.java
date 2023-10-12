package room;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "category")
public class Category {
    @PrimaryKey(autoGenerate = true)
    private int category_id;
    private String name;
    private String description;
    private int item_count;

    public Category(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public int getItem_count() {
        return item_count;
    }

    public void setItem_count(int item_count) {
        this.item_count = item_count;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
