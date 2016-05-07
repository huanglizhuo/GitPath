package xyz.lizhuo.gitpath.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Base64;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import xyz.lizhuo.gitpath.GitPathApplication;

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

    public static Date getDate(String date) {
        try {
            return format.parse(date);
        } catch (ParseException e) {
            return new Date();
        }
    }

    // TODO: 16/4/8 修复关于时区的问题
    public static String getHowTime(String time){
        Date from = getDate(time);
        Calendar cal = Calendar.getInstance();
        cal.setTime(from);
        long time1 = cal.getTimeInMillis();
        Date to = new Date();
        cal.setTime(to);
        long time2 = cal.getTimeInMillis();
        long betweenMinutes=(time2-time1)/(1000*60);

        int hours = (int)(betweenMinutes/60);
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
            return ((int)betweenMinutes + " minutes ago");
        }
    }

    public static final byte[] fromBase64(String content) {
        return Base64.decode(content, Base64.DEFAULT);
    }

    public static final String toBase64(final byte[] content) {
        return Base64.encodeToString(content, Base64.DEFAULT);
    }


    public static String loadMarkdownToHtml(final String txt, final String cssFile) {
        String html;
        if (null != cssFile) {
            html = "<link rel='stylesheet' type='text/css' href='" + cssFile + "' />" + txt;
            return html;
        }
        return null;
    }


}
