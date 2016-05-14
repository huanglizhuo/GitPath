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
import xyz.lizhuo.gitpath.Adapter.UserAdapter;
import xyz.lizhuo.gitpath.GithubModel.User;
import xyz.lizhuo.gitpath.R;
import xyz.lizhuo.gitpath.Activity.UserDetailActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class UsersFragment extends BaseFragment {
    private UserAdapter adapter;
    private int type;//
    public UsersFragment() {
    }

    public static UsersFragment newInstance(String name,int type) {
        UsersFragment fragment = new UsersFragment();
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
        recyclerView.addOnScrollListener(new EndlessScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                isRefresh = false;
                getUser(username,currentPage,type);
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isRefresh = true;
                getUser(username,1,type);
            }
        });
        getUser(username,1,type);
    }

    public void getUser(String username, int page, int type) {
        Subscriber subscriber = new Subscriber<List<User>>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(List<User> users) {
                setAdapter(users);
            }
        };
        if (type == User.FOLLOWER){
            retrofitMethods.getFollowers(subscriber, username, page);
        }else if (type == User.FOLLOWING){
            retrofitMethods.getFollowing(subscriber, username, page);
        }
    }

    public void setAdapter(final List<User> users) {
        mProgressBar.setVisibility(View.GONE);
        if (adapter == null) {
            // TODO: 16/4/11  adaptar add refrash() upadte()
            adapter = new UserAdapter(context, users);
            adapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(context, UserDetailActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    User user = users.get(position);
                    intent.putExtra("userlogin", user.getLogin());
                    intent.putExtra("avatar_url", user.getAvatar_url());

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
            if (isRefresh) {
                adapter.refresh(users);
            } else {
                adapter.update(users);
            }
        }
        swipeRefreshLayout.setRefreshing(false);
    }
}
