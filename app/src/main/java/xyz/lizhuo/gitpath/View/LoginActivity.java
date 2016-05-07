package xyz.lizhuo.gitpath.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import xyz.lizhuo.gitpath.GithubModel.GitHub;
import xyz.lizhuo.gitpath.GithubModel.Token;
import xyz.lizhuo.gitpath.R;
import xyz.lizhuo.gitpath.Services.GithubServices;
import xyz.lizhuo.gitpath.Utils.Constant;
import xyz.lizhuo.gitpath.Utils.RetrofitUtils;

public class LoginActivity extends AppCompatActivity {

    @Bind(R.id.login_btn)
    Button loginBtn;
    @Bind(R.id.username_et)
    EditText usernameEt;
    @Bind(R.id.password_et)
    EditText passwordEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        if (GitHub.getInstance().getSavedToken() != "") {
            jumpMainActivity();
        }
    }

    private void jumpMainActivity() {
        Intent intent = new Intent(LoginActivity.this,UserDetailActivity.class);
        intent.putExtra("userlogin","huanglizhuo");
        intent.putExtra("avatar_url","https://avatars.githubusercontent.com/u/3874324?v=3");
        intent.putExtra("reponame","huanglizhuo/kotlin-in-chinese");
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
    }

    @OnClick(R.id.login_btn)
    public void onClick() {
        // TODO: 16/4/10 add loading animation 
        String userName = usernameEt.getText().toString();
        String passWord = passwordEt.getText().toString();
        GitHub.getInstance().setName(userName);
        if (userName != "" && passWord != "") {
            // TODO: 16/4/8 添加登出操作 删除 token 方法
            getToken(userName + ":" + passWord, new Callback<Token>() {
                @Override
                public void onResponse(Call<Token> call, Response<Token> response) {
                    String s = response.body().getToken();
                    GitHub.getInstance().setToken(s);
                    jumpMainActivity();
                }

                @Override
                public void onFailure(Call<Token> call, Throwable t) {
                }
            });
        }else {
            Toast.makeText(this,"用户名密码不可为空",Toast.LENGTH_LONG).show();
        }
    }


    public void getToken(String credentials, Callback<Token> callback) {
        GithubServices client = RetrofitUtils.getRetrofitWithoutToken().create(GithubServices.class);
        JSONObject json = new JSONObject();
        try {
            json.put("note", Constant.TOKEN_NOTE);
            JSONArray jsonArray = new JSONArray(Arrays.asList(Constant.SCOPES));
            json.put("scopes", jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Token token = new Token();
        token.setNote(Constant.TOKEN_NOTE);
        token.setScopes(Arrays.asList(Constant.SCOPES));
        final String basic =
                "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
        Call<Token> call = client.createToken(token, basic);
        call.enqueue(callback);
    }

}
