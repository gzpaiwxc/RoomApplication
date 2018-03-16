package room.gzp.com.roomapplication.db.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import room.gzp.com.roomapplication.db.dao.UserDao;
import room.gzp.com.roomapplication.db.entity.User;

/**
 * author: Gzp
 * Create on 2018/3/14
 * Description:
 */

@Database(entities = User.class, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "room_database";
    private static volatile AppDatabase Instance;

    public static AppDatabase getInstance(Context context) {
        if (Instance == null) {
            Instance = Room.databaseBuilder(context, AppDatabase.class, DATABASE_NAME)
                    .build();
        }
        return Instance;
    }

    public abstract UserDao userDao();

}
