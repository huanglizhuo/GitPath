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
    private GitHub(Context context) {
        this.context = context;
        SharedPreferences sharedPreferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        token = sharedPreferences.getString(TOKEN_KEY, "");
        code = sharedPreferences.getString(CODE_KEY, "");
        name = sharedPreferences.getString("name", "");
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
}
