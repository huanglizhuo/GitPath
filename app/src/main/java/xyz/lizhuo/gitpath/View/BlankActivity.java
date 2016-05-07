package xyz.lizhuo.gitpath.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import butterknife.Bind;
import butterknife.ButterKnife;
import xyz.lizhuo.gitpath.Frgments.RepoFragment;
import xyz.lizhuo.gitpath.Frgments.UsersFragment;
import xyz.lizhuo.gitpath.GithubModel.Repo;
import xyz.lizhuo.gitpath.GithubModel.User;
import xyz.lizhuo.gitpath.R;

public class BlankActivity extends AppCompatActivity {
    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    private String userLogin;
    private String avatar_url;
    private int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blank);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        userLogin = intent.getStringExtra("userLogin");
        avatar_url = intent.getStringExtra("avatar_url");
        type = intent.getIntExtra("type", 1);
        initContent();
    }

    private void initContent() {
        switch (type) {
            case User.FOLLOWER:
                mToolbar.setTitle(userLogin+"'s follower");
                addFrament(UsersFragment.newInstance(userLogin, User.FOLLOWER), "user");
                break;
            case User.FOLLOWING:
                mToolbar.setTitle(userLogin+"'s following");
                addFrament(UsersFragment.newInstance(userLogin, User.FOLLOWING), "user");
                break;
            case Repo.OWNREPO:
                mToolbar.setTitle(userLogin+"'s repo");
                addFrament(RepoFragment.newInstance(userLogin, Repo.OWNREPO), "repo");
                break;
            case Repo.STARTEDREPO:
                mToolbar.setTitle(userLogin+" started");
                addFrament(RepoFragment.newInstance(userLogin, Repo.STARTEDREPO), "repo");
                break;
        }
    }

    private void addFrament(Fragment fragment, String tag) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.frament2place, fragment, tag);
        transaction.addToBackStack(tag);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}
