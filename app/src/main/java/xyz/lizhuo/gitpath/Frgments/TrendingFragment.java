package xyz.lizhuo.gitpath.Frgments;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;

import rx.Subscriber;
import xyz.lizhuo.gitpath.Adapter.EndlessScrollListener;
import xyz.lizhuo.gitpath.Adapter.RepoAdapter;
import xyz.lizhuo.gitpath.GithubModel.Repo;
import xyz.lizhuo.gitpath.HttpHandle.TrendingMethods;
import xyz.lizhuo.gitpath.R;
import xyz.lizhuo.gitpath.View.RepoDetailActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class TrendingFragment extends BaseFragment {

    private RepoAdapter adapter;
    private TrendingMethods trendingMethods;
    private String language = "java";
    private String since = "weekly";

    public TrendingFragment() {
        trendingMethods = new TrendingMethods();
    }

    public static TrendingFragment newInstance(String language, String since) {
        TrendingFragment fragment = new TrendingFragment();
        Bundle bundle = new Bundle();
        bundle.putString("language", language);
        bundle.putString("since", since);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void initViews() {
        language = getArguments().getString("language");
        since = getArguments().getString("since");
        trendingMethods.getTrending(new Subscriber<List<Repo>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(List<Repo> repos) {
                setAdapter(repos);
            }
        }, language, since);
        recyclerView.addOnScrollListener(new EndlessScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                Toast.makeText(context, "就这么多啦", Toast.LENGTH_LONG).show();
            }
        });
        swipeRefreshLayout.setColorSchemeColors(R.color.blue, R.color.green, R.color.red);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                trendingMethods.getTrending(new Subscriber<List<Repo>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<Repo> repos) {
                        setAdapter(repos);
                    }
                }, language, since);
            }
        });

    }


    public void setAdapter(final List<Repo> repos) {
        mProgressBar.setVisibility(View.GONE);
        if (adapter == null) {
            // TODO: 16/4/11  adaptar add refrash() upadte()
            adapter = new RepoAdapter(context, repos);
            adapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(context, RepoDetailActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Repo repo = repos.get(position);
                    intent.putExtra("reponame", repo.getFull_name());
                    intent.putExtra("avatar_url", repo.getOwner().getAvatar_url());
                    ImageView share = (ImageView) view.findViewById(R.id.avatar_spv);
                    String transitionName = "avatar_img" + position;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        share.setTransitionName(transitionName);
                        intent.putExtra("transitionName", transitionName);
                    }
                    ActivityOptionsCompat options = ActivityOptionsCompat.
                            makeSceneTransitionAnimation(getActivity(), share, transitionName);
                    getActivity().startActivity(intent, options.toBundle());

                }
            });
            recyclerView.setAdapter(adapter);
        } else {
            adapter.refresh(repos);
        }
        swipeRefreshLayout.setRefreshing(false);
    }
}
