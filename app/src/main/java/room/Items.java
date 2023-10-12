package room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "items",foreignKeys = @ForeignKey(entity = Category.class,parentColumns = "category_id" ,childColumns = "category_id",onDelete = ForeignKey.CASCADE))
public class Items {
    @PrimaryKey(autoGenerate = true)
    private int item_id;

    @ColumnInfo(name = "category_id",index = true)
    private int category_id;
    private final String name;
    private boolean isChecked;

    public Items(String name,boolean isChecked,int category_id) {
        this.category_id = category_id;
        this.name = name;
        this.isChecked = isChecked;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public int getItem_id() {
        return item_id;
    }

    public String getName() {
        return name;
    }

    public int getCategory_id() {
        return category_id;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
