package room;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.Executors;

@Database(entities = {Items.class,Category.class,User.class},version = 6git status,exportSchema = false)
public abstract class RoomDB extends RoomDatabase {
    private static RoomDB instance;

    public abstract ItemsDao itemsDao();
    public abstract CategoryDao categoryDao();
    public abstract UserDao userDao();

    public static synchronized RoomDB getInstance(Context context) {
        if(instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),RoomDB.class,"app_database")
                    .fallbackToDestructiveMigration().addCallback(roomCallback).build();
        }
        return instance;
    }

    private static final RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            Executors.newSingleThreadExecutor().execute(() -> {
                ItemsDao itemsDao = RoomDB.instance.itemsDao();
            });
        }
    };
}
