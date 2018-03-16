package room.gzp.com.roomapplication;

import android.app.Application;
import android.content.Context;

/**
 * author: Gzp
 * Create on 2018/3/16
 * Description:
 */

public class MyApplication extends Application {

    private static Application INSTANCE;

    public static Application get() {
        return INSTANCE;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }
}
