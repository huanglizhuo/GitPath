package xyz.lizhuo.gitpath.View;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import xyz.lizhuo.gitpath.R;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.viewpager)
    ViewPager viewpager;
    @Bind(R.id.tabs)
    TabLayout tabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        getSupportActionBar().hide();
        setupViewPager(viewpager);
        tabs.setupWithViewPager(viewpager);
    }

    public void setupViewPager(ViewPager upViewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new EventFragment(), "Event");
        adapter.addFragment(new BlankFragment(),"Started");
//        adapter.addFragment(new BlankFragment(),"HotRepo");
//        adapter.addFragment(new BlankFragment(),"HotUser");
        upViewPager.setAdapter(adapter);
    }

}
