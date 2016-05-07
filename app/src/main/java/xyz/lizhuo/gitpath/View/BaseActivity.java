package xyz.lizhuo.gitpath.View;

import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import xyz.lizhuo.gitpath.HttpHandle.RetrofitMethods;
import xyz.lizhuo.gitpath.R;
import xyz.lizhuo.gitpath.Utils.GlideManager;

/**
 * Created by lizhuo on 16/4/11.
 */
public class BaseActivity extends AppCompatActivity {

    public RetrofitMethods mRetrofitMethods;
    public Boolean isEnable;
    public FloatingActionButton mFloatingActionButton;
    private ImageView mHeaderImageView;
    private CardView mContentCard;
    private CollapsingToolbarLayout mCollapsingToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        mCollapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        mHeaderImageView = (ImageView) findViewById(R.id.header_img);
        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.floating_btn);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        assert mToolbar != null;
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        mRetrofitMethods = RetrofitMethods.getInstances();
        initTransition();
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        if (layoutResID == R.layout.activity_base) {
            super.setContentView(R.layout.activity_base);
            mContentCard = (CardView) findViewById(R.id.content_card);
            mContentCard.removeAllViews();
        } else {
            View addedView = LayoutInflater.from(this).inflate(layoutResID, null);
            mContentCard.addView(addedView);
        }
    }

    public void setHeader(String title, String headerimgUrl) {
        mCollapsingToolbar.setTitle(title);
//        Glide.with(GitPathApplication.getContext())
//                .load(headerimgUrl)
//                .centerCrop()
//                .crossFade()
//                .into(mHeaderImageView);
        GlideManager.getInstance().loadImage(this,headerimgUrl,mHeaderImageView);
    }

    private void initTransition() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // TODO: 16/4/11 replace with RxBus
            String transitionName = getIntent().getStringExtra("transitionName");
            if (transitionName != null) {
                mHeaderImageView.setTransitionName(transitionName);
            }
        }
    }

    public void setFloatSatus(Boolean isEnable) {
        this.isEnable = isEnable;
        if (isEnable) {
            mFloatingActionButton.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.floatenable)));
        } else {
            mFloatingActionButton.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.floatdisable)));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
