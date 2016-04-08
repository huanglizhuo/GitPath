package xyz.lizhuo.gitpath.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import xyz.lizhuo.gitpath.Application.GitPathApplication;

/**
 * Created by lizhuo on 16/3/26.
 */
public class Utils {
    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

    public static boolean isNetworkConnected() {
        ConnectivityManager mConnectivityManager = (ConnectivityManager) GitPathApplication.getContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
        if (mNetworkInfo != null) {
            return mNetworkInfo.isAvailable();
        }
        return false;
    }

    // TODO: 16/4/7 修改关于时间的函数

    public static int fromNow(String date){
        Date from = getDate(date);
        Calendar cal = Calendar.getInstance();
        cal.setTime(from);
        long time1 = cal.getTimeInMillis();
        Date now = new Date();
        cal.setTime(now);
        cal.add(Calendar.HOUR,4);
        long time2 = cal.getTimeInMillis()+20*60*1000;
        long between_days=(time2-time1)/(1000*60);

        return Integer.parseInt(String.valueOf(between_days));
    }

    public static Date getDate(String date) {
        try {
            return format.parse(date);
        } catch (ParseException e) {
            return new Date();
        }
    }

    public static String getHowTime(String time){
        int minute = Utils.fromNow(time);
        int hours = minute/60;
        int days = hours/24;
        int months = days/30;
        int years = months/12;
        if(years == 1||years>1){
            return (years+" year ago");
        }
        else if(months == 1||months>1){
            return (months+" months ago");
        }
        else if(days == 1||days>1){
            return (days+" days ago");
        }
        else if(hours > 1||hours==1){
            return (hours + " hours ago");
        }
        else{
            return (minute + " minutes ago");
        }
    }




}
