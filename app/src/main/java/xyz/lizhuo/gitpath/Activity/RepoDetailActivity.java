package xyz.lizhuo.gitpath.Activity;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscriber;
import xyz.lizhuo.gitpath.GithubModel.Repo;
import xyz.lizhuo.gitpath.R;
import xyz.lizhuo.gitpath.Utils.Constant;

/**
 * Created by lizhuo on 16/4/11.
 */
public class RepoDetailActivity extends BaseActivity {
    @Bind(R.id.repo_description_tv)
    TextView mRepoDescriptionTv;
    @Bind(R.id.repo_stars)
    TextView mRepoStars;
    @Bind(R.id.repo_forks)
    TextView mRepoForks;

    @Bind(R.id.readme_webv)
    WebView mReadmeWebv;
    @Bind(R.id.progressBar)
    ProgressBar mProgressBar;

    public String owner;
    public String reponame;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repo_detail);
        ButterKnife.bind(this);
        // TODO: 16/4/11 replace with RxBus
        String repoFullName = getIntent().getStringExtra("reponame");
        owner = repoFullName.split("/")[0];
        reponame = repoFullName.split("/")[1];
        final String ownerAvatar = getIntent().getStringExtra("avatar_url");
        setHeader(reponame, ownerAvatar);
        getDetail(owner, reponame);

        mFloatingActionButton.setImageResource(R.drawable.star);
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEnable) {
                    mFloatingActionButton.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(RepoDetailActivity.this, R.color.floatenable)));
                } else {
                    mFloatingActionButton.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(RepoDetailActivity.this, R.color.floatdisable)));
                }
                mRetrofitMethods.starAction(new Subscriber<Boolean>() {
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
                            Toast.makeText(RepoDetailActivity.this, "Action Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, owner, reponame, !isEnable);
            }
        });

        mReadmeWebv.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mReadmeWebv.setVisibility(View.VISIBLE);
                mProgressBar.setVisibility(View.GONE);
            }
        });

    }

    private void getDetail(String owner, final String reponame) {
        mRetrofitMethods.getRepoDetail(new Subscriber<Repo>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Repo repo) {
                updateDetail(repo);
            }
        }, owner, reponame);

        mRetrofitMethods.getReadme(new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                mReadmeWebv.loadDataWithBaseURL(null, s, "text/html", "UTF-8", null);
            }
        }, owner, reponame, Constant.GITHUBCSSFILE);

        mRetrofitMethods.ifStarted(new Subscriber<Boolean>() {
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
        }, owner, reponame);
    }

    public void updateDetail(Repo repo) {
        mRepoStars.setText(repo.getStargazers_count() + "");
        mRepoForks.setText(repo.getForks_count() + "");
        mRepoDescriptionTv.setText(repo.getDescription());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
