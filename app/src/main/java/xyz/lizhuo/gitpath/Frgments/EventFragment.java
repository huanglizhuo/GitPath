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
import xyz.lizhuo.gitpath.Adapter.EventAdapter;
import xyz.lizhuo.gitpath.GithubModel.Event;
import xyz.lizhuo.gitpath.R;
import xyz.lizhuo.gitpath.Utils.RxBus;
import xyz.lizhuo.gitpath.View.RepoDetailActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventFragment extends BaseFragment {
    private EventAdapter adapter;
    private String TYPE = "username";

    public EventFragment() {
    }

    public static EventFragment newInstance(String arg) {
        EventFragment fragment = new EventFragment();
        Bundle bundle = new Bundle();
        bundle.putString("username", arg);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void initViews() {
        username = getArguments().getString(TYPE);
        recyclerView.addOnScrollListener(new EndlessScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                setRefreshing(true);
                isRefresh = false;
                getUserEvent(username, currentPage);
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isRefresh = true;
                getUserEvent(username, 1);
            }
        });
        getUserEvent(username, 1);
    }

    private void getUserEvent(String username, int page) {
        Subscriber<List<Event>> subscriber = new Subscriber<List<Event>>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                String s = e.getMessage();
                Toast.makeText(context, s, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNext(List<Event> events) {
                setAdapter(events);
            }
        };
        retrofitMethods.getUserEvent(subscriber, username, page);
    }

    public void setAdapter(final List<Event> events) {
        mProgressBar.setVisibility(View.GONE);
        if (adapter == null) {
            // TODO: 16/4/11  adaptar add refrash() upadte()
            adapter = new EventAdapter(context, events);
            adapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(context, RepoDetailActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Event event = events.get(position);
                    intent.putExtra("reponame", event.getRepo().getName());
                    intent.putExtra("avatar_url", event.getActor().getAvatar_url());
                    ImageView share = (ImageView) view.findViewById(R.id.avatar_spv);

                    RxBus.getDefault().send(event);

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
                adapter.refresh(events);
            } else {
                adapter.update(events);
            }
        }
        setRefreshing(false);
    }

}
