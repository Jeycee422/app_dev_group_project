package room;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user")
public class User {
    @PrimaryKey(autoGenerate = true)
    private int user_id;
    private String username;
    private String email;
    private int avatar;

    public User(String username, String email, int avatar) {
        this.username = username;
        this.email = email;
        this.avatar = avatar;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public int getAvatar() {
        return avatar;
    }
}
