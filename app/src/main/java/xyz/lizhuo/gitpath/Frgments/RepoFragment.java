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

import java.util.List;

import rx.Subscriber;
import xyz.lizhuo.gitpath.Adapter.EndlessScrollListener;
import xyz.lizhuo.gitpath.Adapter.RepoAdapter;
import xyz.lizhuo.gitpath.GithubModel.Repo;
import xyz.lizhuo.gitpath.R;
import xyz.lizhuo.gitpath.Activity.RepoDetailActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class RepoFragment extends BaseFragment {

    private RepoAdapter adapter;
    private String username = "huanglizhuo";
    private int type = 1;
    public RepoFragment() {
    }

    public static RepoFragment newInstance(String name,int type) {
        RepoFragment fragment = new RepoFragment();
        Bundle bundle = new Bundle();
        bundle.putString("username", name);
        bundle.putInt("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void initViews() {
        username = getArguments().getString("username");
        type = getArguments().getInt("type");
        swipeRefreshLayout.setColorSchemeColors(R.color.blue, R.color.green, R.color.red);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isRefresh = true;
                getRepo(username,1,type);
            }
        });
        recyclerView.addOnScrollListener(new EndlessScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                isRefresh = false;
                getRepo(username,currentPage,type);
            }
        });
        getRepo(username,1,type);
    }

    private void getRepo(String username,int page,int type){
        Subscriber<List<Repo>> subscriber = new Subscriber<List<Repo>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(final List<Repo> repos) {
                setAdapter(repos);
            }
        };

        if (type==Repo.STARTEDREPO){
            retrofitMethods.getStarted(subscriber,username,page);
        }else if (type==Repo.OWNREPO){
            retrofitMethods.getUserRepos(subscriber, username, page);
        }
    }

    public void setAdapter(final List<Repo> repos) {
        setRefreshing(false);
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
            if (isRefresh){
                adapter.refresh(repos);
            }else {
                adapter.update(repos);
            }
        }
    }
}
