package room.gzp.com.roomapplication.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import room.gzp.com.roomapplication.db.entity.User;

/**
 * author: Gzp
 * Create on 2018/3/13
 * Description:
 */
@Dao
public interface UserDao {

    @Insert
    void insertUser(User... user);

    @Update
    void updateUser(User... users);

    @Delete
    void deleteUser(User... users);

    @Query("SELECT * FROM user")
    List<User> getAllUser();

    @Query("SELECT * FROM user WHERE id=:id")
    User getUserByUid(int id);

    @Query("SELECT * FROM user WHERE name=:name")
    List<User> getUserByName(String name);

}
