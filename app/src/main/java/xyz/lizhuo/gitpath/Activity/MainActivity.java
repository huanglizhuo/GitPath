package xyz.lizhuo.gitpath.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import it.sephiroth.android.library.bottomnavigation.BottomNavigation;
import xyz.lizhuo.gitpath.Frgments.AboutFragment;
import xyz.lizhuo.gitpath.Frgments.BaseFragment;
import xyz.lizhuo.gitpath.Frgments.EventFragment;
import xyz.lizhuo.gitpath.Frgments.RepoFragment;
import xyz.lizhuo.gitpath.Frgments.TrendingFragment;
import xyz.lizhuo.gitpath.GithubModel.GitHub;
import xyz.lizhuo.gitpath.GithubModel.Repo;
import xyz.lizhuo.gitpath.R;

public class MainActivity extends AppCompatActivity {

    BottomNavigation mBottomNavigation;
    FrameLayout mFragmentContainer;

    private List<String> fragmentTags;
    private FragmentManager mFragmentManager;
    private boolean isBackClicked = false;

    private long firstClick;
    private long lastClick;//use for double check inplement
    private int count;

    private String currentFramentTag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBottomNavigation = (BottomNavigation) findViewById(R.id.bottomNavigation);
        mFragmentContainer = (FrameLayout) findViewById(R.id.fragment_container);
        mFragmentManager = getSupportFragmentManager();
        fragmentTags = new ArrayList<>();
        setupBottomNavigation();
        initFraments();
    }

    // TODO: 16/5/8 try to fix restart the fragment overlaped
    // TODO: 16/5/13 add an custom framentmanager use map or something else to manage

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("ss","start");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("ss","resume");
        showThisFrament(currentFramentTag);
    }

    @Override
    protected void onPause() {
        Log.e("ss","pause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.e("ss","stop");
        super.onStop();
    }

    private void initFraments() {
        String name = GitHub.getInstance().getName();
        addFrament(EventFragment.newInstance(name), "event", true);
        Log.e("ss","initframent");
        // TODO: 16/5/13 ugly code try to make it simple and stupid
        String since = getResources().getStringArray(R.array.since)[GitHub.getInstance().getTrend_since()];
        String language = getResources().getStringArray(R.array.language)[GitHub.getInstance().getTrend_lanaguage()];

        addFrament(TrendingFragment.newInstance(language, since), "trending", false);

        addFrament(RepoFragment.newInstance(name, Repo.STARTEDREPO), "started", false);
        addFrament(AboutFragment.newInstance(name), "about", false);
    }

    private void setupBottomNavigation() {
        mBottomNavigation.setOnMenuItemClickListener(new BottomNavigation.OnMenuItemSelectionListener() {
            @Override
            public void onMenuItemSelect(@IdRes int i, int i1) {
                showThisFrament(fragmentTags.get(i1));
//                getWindow().setStatusBarColor();

            }

            @Override
            public void onMenuItemReselect(@IdRes int i, int i1) {
                if (firstClick != 0 && System.currentTimeMillis() - firstClick > 300) {
                    count = 0;
                }
                count++;
                if (count == 1) {
                    firstClick = System.currentTimeMillis();

                    if (fragmentTags.get(i1) == "about") {
                        return;
                    }
                    BaseFragment baseFragment = (BaseFragment) mFragmentManager.findFragmentByTag(fragmentTags.get(i1));
                    baseFragment.upToTop();

                } else if (count == 2) {
                    lastClick = System.currentTimeMillis();
                    if (lastClick - firstClick < 300) {
                        // TODO: 16/5/10 doubleclick to refrash list
                    }
                }

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // TODO: 16/5/13 modify this avoid overlap
    }

    @Override
    public void onBackPressed() {
        if (isBackClicked) {
            finish();
        } else {
            Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
            isBackClicked = true;
        }
    }

    private void addFrament(Fragment fragment, String tag, boolean showNow) {
        Log.e("ss","add frgment"+tag);
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.add(R.id.fragment_container, fragment, tag);
        fragmentTags.add(tag);
        if (showNow) {
            transaction.show(fragment);
            currentFramentTag = tag;
        } else {
            transaction.hide(fragment);
        }
        transaction.addToBackStack(tag);
        transaction.commit();
    }

    private void showThisFrament(String tag) {
        Log.e("ss","showframent");
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        for (String i : fragmentTags) {
            Fragment f = mFragmentManager.findFragmentByTag(i);
            //avoid Android Fragment null object mNextAnim Internal Crach
            if (f == null) {
                continue;
            } else {
                if (i == tag) {
                    transaction.show(f);
                    currentFramentTag = tag;
                } else {
                    transaction.hide(f);
                }
            }
        }
        transaction.commit();
    }

}
