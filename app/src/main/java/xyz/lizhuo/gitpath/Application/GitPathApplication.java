package xyz.lizhuo.gitpath.Application;

import android.app.Application;
import android.content.Context;

/**
 * Created by lizhuo on 16/3/25.
 */
public class GitPathApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }
}
