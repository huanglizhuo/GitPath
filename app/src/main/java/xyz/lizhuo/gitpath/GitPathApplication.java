package xyz.lizhuo.gitpath;

import android.app.Application;
import android.content.Context;

import im.fir.sdk.FIR;

/**
 * Created by lizhuo on 16/3/25.
 */
public class GitPathApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
//        Stetho.initializeWithDefaults(this);
        FIR.init(this);
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }

}
