package room.gzp.com.roomapplication.db.manager;

import java.util.List;

import room.gzp.com.roomapplication.MyApplication;
import room.gzp.com.roomapplication.db.database.AppDatabase;
import room.gzp.com.roomapplication.db.entity.User;

/**
 * author: Gzp
 * Create on 2018/3/15
 * Description:
 */

public class AppDatabaseManager {
    private static AppDatabaseManager INSTANCE;

    private AppDatabase appDatabase;

    private AppDatabaseManager() {
        this.appDatabase = AppDatabase.getInstance(MyApplication.get());
    }

    public static AppDatabaseManager getInstance() {
        if (INSTANCE == null) {
            synchronized (AppDatabaseManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new AppDatabaseManager();
                }
            }
        }
        return INSTANCE;
    }

    /**插入一条用户数据*/
    public void insertUser(User user) {
        appDatabase.userDao().insertUser(user);
    }

    /**查询所有用户*/
    public List<User> getUsers() {
        return appDatabase.userDao().getAllUser();
    }

}
