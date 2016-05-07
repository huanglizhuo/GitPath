package xyz.lizhuo.gitpath;

import android.app.Application;
import android.content.Context;

import com.facebook.stetho.Stetho;

/**
 * Created by lizhuo on 16/3/25.
 */
public class GitPathApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }

}
