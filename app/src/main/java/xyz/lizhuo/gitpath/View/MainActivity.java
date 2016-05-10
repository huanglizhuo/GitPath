package xyz.lizhuo.gitpath.View;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
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
    private long lastClick;
    private int count;

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
//    @Override
//    protected void onResume() {
//        super.onResume();
//        removeAllFragment();
//        initFraments();
//    }

    private void initFraments() {
        String name = GitHub.getInstance().getName();
        addFrament(EventFragment.newInstance(name), "event", true);
        addFrament(TrendingFragment.newInstance("", "daily"), "trending", false);
        addFrament(RepoFragment.newInstance(name, Repo.OWNREPO), "started", false);
        addFrament(AboutFragment.newInstance(name), "about", false);
    }

    private void setupBottomNavigation() {
        mBottomNavigation.setOnMenuItemClickListener(new BottomNavigation.OnMenuItemSelectionListener() {
            @Override
            public void onMenuItemSelect(@IdRes int i, int i1) {
                showThisFrament(fragmentTags.get(i1));
//                getWindow().setStatusBarColor();
                //stupid old way hahaha
//                switch (i) {
//                    case R.id.event_menu:
//                        break;
//                    case R.id.treding_menu:
//                        showThisFrament("trending");
//                        break;
//                    case R.id.started_menu:
//                        showThisFrament("started");
//                        break;
//                    case R.id.following_menu:
//                        showThisFrament("following");
//                        break;
//
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
    public void onBackPressed() {
        if (isBackClicked) {
            finish();
        } else {
            Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
            isBackClicked = true;
        }
    }

    private void addFrament(Fragment fragment, String tag, boolean showNow) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.add(R.id.fragment_container, fragment, tag);
        fragmentTags.add(tag);
        if (showNow) {
            transaction.show(fragment);
        } else {
            transaction.hide(fragment);
        }
        transaction.addToBackStack(tag);
        transaction.commit();
    }

    private void showThisFrament(String tag) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        for (String i : fragmentTags) {
            Fragment f = mFragmentManager.findFragmentByTag(i);
            //avoid Android Fragment null object mNextAnim Internal Crach
            if (f == null) {
                continue;
            } else {
                if (i == tag) {
                    transaction.show(f);
                } else {
                    transaction.hide(f);
                }
            }
        }
        transaction.commit();
    }

    private void removeAllFragment() {
        for (String i : fragmentTags) {
            Fragment f = mFragmentManager.findFragmentByTag(i);
            f = null;
        }
    }

}
