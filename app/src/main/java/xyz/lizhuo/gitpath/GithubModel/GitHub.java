package xyz.lizhuo.gitpath.GithubModel;

import android.content.Context;
import android.content.SharedPreferences;

import xyz.lizhuo.gitpath.GitPathApplication;

/**
 * Created by lizhuo on 16/3/27.
 */
public class GitHub {
    public String NAME = "GitPath";
    public String TOKEN_KEY = "token";
    public String CODE_KEY = "code";

    private Context context;
    private String token;
    private String code;
    private String name;


    private int trend_since;
    private int trend_lanaguage;

    private GitHub(Context context) {
        this.context = context;
        SharedPreferences sharedPreferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        token = sharedPreferences.getString(TOKEN_KEY, "");
        code = sharedPreferences.getString(CODE_KEY, "");
        name = sharedPreferences.getString("name", "");
        trend_since = sharedPreferences.getInt("since", 0);
        trend_lanaguage = sharedPreferences.getInt("lanaguage", 0);
    }


    private static class SingleHolder {
        private static final GitHub github = new GitHub(GitPathApplication.getContext());
    }

    public static GitHub getInstance() {
        return SingleHolder.github;
    }

    public String getSavedToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
        SharedPreferences.Editor editor = context.getSharedPreferences(NAME, Context.MODE_PRIVATE).edit();
        editor.putString(TOKEN_KEY, token);
        editor.commit();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        SharedPreferences.Editor editor = context.getSharedPreferences(NAME, Context.MODE_PRIVATE).edit();
        editor.putString("name", name);
        editor.commit();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
        SharedPreferences.Editor editor = context.getSharedPreferences(NAME, Context.MODE_PRIVATE).edit();
        editor.putString(CODE_KEY, code);
        editor.commit();
    }

    public int getTrend_since() {
        return trend_since;
    }

    public void setTrend_since(int trend_since) {
        this.trend_since = trend_since;
        SharedPreferences.Editor editor = context.getSharedPreferences(NAME, Context.MODE_PRIVATE).edit();
        editor.putInt("since", trend_since);
        editor.commit();
    }

    public int getTrend_lanaguage() {
        return trend_lanaguage;
    }

    public void setTrend_lanaguage(int trend_lanaguage) {
        this.trend_lanaguage = trend_lanaguage;
        SharedPreferences.Editor editor = context.getSharedPreferences(NAME, Context.MODE_PRIVATE).edit();
        editor.putInt("lanaguage", trend_lanaguage);
        editor.commit();
    }


}
