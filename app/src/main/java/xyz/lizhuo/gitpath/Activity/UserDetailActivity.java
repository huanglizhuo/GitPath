package xyz.lizhuo.gitpath.Activity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import xyz.lizhuo.gitpath.GithubModel.Repo;
import xyz.lizhuo.gitpath.GithubModel.User;
import xyz.lizhuo.gitpath.R;

/**
 * Created by lizhuo on 16/4/11.
 */
public class UserDetailActivity extends BaseActivity {
    @Bind(R.id.user_name)
    EditText mUserName;
    @Bind(R.id.user_followers)
    TextView mUserFollowers;
    @Bind(R.id.user_following)
    TextView mUserFollowing;

    @Bind(R.id.e_mail)
    EditText mEMail;
    @Bind(R.id.company)
    EditText mCompany;
    @Bind(R.id.location)
    EditText mLocation;
    @Bind(R.id.join_time)
    EditText mJoinTime;

    @Bind(R.id.user_repos_count)
    TextView mUserReposCount;
    @Bind(R.id.user_started_count)
    TextView mStartedCount;

    private String userLogin;
    private String avatar_url;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        ButterKnife.bind(this);
        // TODO: 16/4/11 try use RxBus
        Intent intent = getIntent();
        userLogin = intent.getStringExtra("userlogin");
        avatar_url = intent.getStringExtra("avatar_url");
        setHeader(userLogin, avatar_url);
        mFloatingActionButton.setImageResource(R.drawable.follow);
        mFloatingActionButton.setVisibility(View.GONE);
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEnable) {
                    mFloatingActionButton.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(UserDetailActivity.this, R.color.floatenable)));
                } else {
                    mFloatingActionButton.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(UserDetailActivity.this, R.color.floatdisable)));
                }
                mRetrofitMethods.followAction(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        if (isEnable && aBoolean) {
                            setFloatSatus(false);
                        } else if (!isEnable && aBoolean) {
                            setFloatSatus(true);
                        } else {
                            Toast.makeText(UserDetailActivity.this, "Action Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, userLogin, !isEnable);
            }
        });
        mRetrofitMethods.getUserInfo(new Subscriber<User>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(User user) {
                updadeInfo(user);
            }
        }, userLogin);
        mRetrofitMethods.ifFollowing(new Subscriber<Boolean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Boolean aBoolean) {
                setFloatSatus(aBoolean);
            }
        }, userLogin);
    }

    private void updadeInfo(User user) {
        mUserName.setText(user.getName());
        mUserFollowers.setText(user.getFollowers() + "");
        mUserFollowing.setText(user.getFollowing() + "");
        mJoinTime.setText("Joined on " + user.getCreated_at().substring(0, 10));
        mEMail.setText((user.getEmail() == null) ? "N/A" : user.getEmail());
        mCompany.setText((user.getCompany() == null) ? "N/A" : user.getCompany().toString());
        mLocation.setText((user.getLocation() == null) ? "N/A" : user.getLocation().toString());
        mUserReposCount.setText(user.getPublic_repos() + "");
        mStartedCount.setText(user.getPublic_gists() + "");
    }

    @OnClick({R.id.user_followers_ll, R.id.user_following_ll, R.id.user_repos_count_ll})
    public void onClick(View view) {
        Intent intent = new Intent(UserDetailActivity.this, OneFragmentActivity.class);
        intent.putExtra("userLogin", userLogin);
        intent.putExtra("avatar_url", avatar_url);
        switch (view.getId()) {
            case R.id.user_followers_ll:
                intent.putExtra("type", User.FOLLOWER);
                break;
            case R.id.user_following_ll:
                intent.putExtra("type", User.FOLLOWING);
                break;
            case R.id.user_repos_count_ll:
                intent.putExtra("type", Repo.OWNREPO);
                break;
            case R.id.user_started_count_ll:
                intent.putExtra("type", Repo.STARTEDREPO);
                break;
        }
        startActivity(intent);
    }
}
