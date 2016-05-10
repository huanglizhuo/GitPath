package xyz.lizhuo.gitpath.View;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Base64;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
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
    @Bind(R.id.octocat)
    ImageView mOctocat;
    @Bind(R.id.input_place)
    LinearLayout mInputPlace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        if (GitHub.getInstance().getSavedToken() != "") {
            jumpMainActivity();
        }
        passwordEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    loginBtn.setEnabled(false);
                    loginBtn.performClick();
                    return true;
                } else {
                    return false;
                }
            }
        });
    }

    private void jumpMainActivity() {
//        Intent intent = new Intent(LoginActivity.this, UserDetailActivity.class);
//        intent.putExtra("userlogin", "huanglizhuo");
//        intent.putExtra("avatar_url", "https://avatars.githubusercontent.com/u/3874324?v=3");
//        intent.putExtra("reponame", "huanglizhuo/kotlin-in-chinese");
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
    }

    @OnClick(R.id.login_btn)
    public void onClick() {
        // TODO: 16/4/10 add loading animation
        loginBtn.setEnabled(false);
        String userName = usernameEt.getText().toString();
        String passWord = passwordEt.getText().toString();
        GitHub.getInstance().setName(userName);
        if (!TextUtils.isEmpty(userName) && !TextUtils.isEmpty(passWord)) {
            getToken(userName + ":" + passWord, new Callback<Token>() {
                @Override
                public void onResponse(Call<Token> call, Response<Token> response) {
                    if (response.code() == 201) {
                        GitHub.getInstance()
                                .setToken(response.body().getToken());
                        sucessAnimation();
                        return;
                    } else if (response.code() == 401) {
                        Toast.makeText(getBaseContext(), "Username or password is wrong", Toast.LENGTH_LONG).show();
                    } else if (response.code() == 422) {
                        // TODO: 16/5/10 引导用户删除已存在的 token   或者改变当前登录方式,改为网页跳转式
                        Toast.makeText(getBaseContext(), "", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getBaseContext(), response.message(), Toast.LENGTH_LONG).show();
                    }
                    fail();
                }

                @Override
                public void onFailure(Call<Token> call, Throwable t) {
                    fail();
                    Toast.makeText(getBaseContext(), "Fail :( Check Your Network", Toast.LENGTH_LONG).show();
                }
            });
        } else {
            fail();
            Toast.makeText(this, "用户名密码不可为空", Toast.LENGTH_LONG).show();
        }
    }

    private void sucessAnimation() {
        final Animator fade = AnimatorInflater.loadAnimator(this, R.animator.fade_out);
        fade.setTarget(mInputPlace);
        fade.start();

        final Animator succes = AnimatorInflater.loadAnimator(this, R.animator.zoom_out_fade);
        succes.setTarget(mOctocat);
        succes.setInterpolator(new FastOutSlowInInterpolator());
        succes.start();
        succes.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                jumpMainActivity();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    private void fail() {

        loginBtn.setEnabled(true);
        int delta = mOctocat.getResources().getDimensionPixelOffset(R.dimen.spacing_medium);
        PropertyValuesHolder pvhTranslateX = PropertyValuesHolder.ofKeyframe(View.TRANSLATION_X,
                Keyframe.ofFloat(0f, 0),
                Keyframe.ofFloat(.10f, -delta),
                Keyframe.ofFloat(.26f, delta),
                Keyframe.ofFloat(.42f, -delta),
                Keyframe.ofFloat(.58f, delta),
                Keyframe.ofFloat(.74f, -delta),
                Keyframe.ofFloat(.90f, delta),
                Keyframe.ofFloat(1f, 0f)
        );
        ObjectAnimator.ofPropertyValuesHolder(mOctocat, pvhTranslateX)
                .setDuration(1000).start();
    }

    private void getToken(String credentials, Callback<Token> callback) {
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
