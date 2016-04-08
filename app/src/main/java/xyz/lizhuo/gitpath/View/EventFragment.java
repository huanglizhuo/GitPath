package xyz.lizhuo.gitpath.View;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import xyz.lizhuo.gitpath.Adapter.EndlessScrollListener;
import xyz.lizhuo.gitpath.Adapter.EventAdapter;
import xyz.lizhuo.gitpath.GithubModel.Event;
import xyz.lizhuo.gitpath.HttpHandle.RetrofitMethods;
import xyz.lizhuo.gitpath.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventFragment extends Fragment {

    private Context context;
    private RetrofitMethods retrofitMethods;
    private EventAdapter adapter;
    private Subscriber<List<Event>> eventSubscriber;
    @Bind(R.id.refrash_ly)
    SwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.event_rcv)
    RecyclerView recyclerView;
    private boolean isUpdate;
    public EventFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.context = getActivity();
        View root = inflater.inflate(R.layout.fragment_event2,container,false);
        ButterKnife.bind(this,root);
        eventSubscriber = new Subscriber<List<Event>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(List<Event> events) {
                if (!isUpdate){
                    adapter = new EventAdapter(context,events);
                    recyclerView.setAdapter(adapter);
                }else {
                    adapter.list.addAll(events);
                    adapter.notifyDataSetChanged();
                }
            }
        };
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init(){
        retrofitMethods = RetrofitMethods.getInstances();
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this.context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addOnScrollListener(new EndlessScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                isUpdate=true;
                retrofitMethods.getUserEvent(eventSubscriber, "huanglizhuo", currentPage);
            }
        });
        swipeRefreshLayout.setColorSchemeColors(R.color.blue,
                R.color.green,
                R.color.red
        );
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Observable
                        .timer(2, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                        .map(new Func1<Long, Object>() {
                            @Override
                            public Object call(Long aLong) {
                                fetchDate();
                                swipeRefreshLayout.setRefreshing(false);
                                adapter.notifyDataSetChanged();
                                return null;
                            }
                        }).subscribe();
            }
        });
        fetchDate();
    }

    private void fetchDate(){
        isUpdate=false;
        retrofitMethods.getUserEvent(eventSubscriber, "huanglizhuo",1);
    }

}
