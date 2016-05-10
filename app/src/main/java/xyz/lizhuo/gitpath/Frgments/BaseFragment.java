package xyz.lizhuo.gitpath.Frgments;


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
import android.widget.ProgressBar;

import xyz.lizhuo.gitpath.HttpHandle.RetrofitMethods;
import xyz.lizhuo.gitpath.R;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class BaseFragment extends Fragment {

    public Context context;
    public RetrofitMethods retrofitMethods;
    public SwipeRefreshLayout swipeRefreshLayout;
    public RecyclerView recyclerView;
    public ProgressBar mProgressBar;
    public boolean isRefresh = false;
    public String username = "huanglizhuo";

    public LinearLayoutManager layoutManager;

    public BaseFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_within_recylerview, container, false);
        swipeRefreshLayout = (SwipeRefreshLayout) root.findViewById(R.id.refrash_ly);
        mProgressBar = (ProgressBar) root.findViewById(R.id.progressBar);
        recyclerView = (RecyclerView) root.findViewById(R.id.event_rcv);
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        retrofitMethods = RetrofitMethods.getInstances();
        layoutManager = new LinearLayoutManager(this.context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        swipeRefreshLayout.setColorSchemeColors(R.color.blue,
                R.color.green,
                R.color.red
        );
        recyclerView.setHasFixedSize(true);
        initViews();
    }

    public abstract void initViews();

    public void setRefreshing(final boolean isRegreshing) {
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(isRegreshing);
            }
        });
    }

    public void upToTop() {
        if (recyclerView.getAdapter().getItemCount() > 0) {
            recyclerView.scrollToPosition(0);
            // TODO: 16/5/4 add smooth scroll2top
        }
    }

}
