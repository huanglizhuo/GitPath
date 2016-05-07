package xyz.lizhuo.gitpath.Frgments;


import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscriber;
import xyz.lizhuo.gitpath.GithubModel.User;
import xyz.lizhuo.gitpath.HttpHandle.RetrofitMethods;
import xyz.lizhuo.gitpath.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AboutMe extends Fragment {

    @Bind(R.id.header_img)
    ImageView mHeaderImg;
    @Bind(R.id.appbar)
    AppBarLayout mAppbar;
    @Bind(R.id.user_name)
    EditText mUserName;
    @Bind(R.id.user_followers)
    TextView mUserFollowers;
    @Bind(R.id.user_following)
    TextView mUserFollowing;
    @Bind(R.id.foww)
    LinearLayout mFoww;
    @Bind(R.id.e_mail)
    EditText mEMail;
    @Bind(R.id.company)
    EditText mCompany;
    @Bind(R.id.location)
    EditText mLocation;
    @Bind(R.id.join_time)
    EditText mJoinTime;
    @Bind(R.id.info)
    LinearLayout mInfo;
    @Bind(R.id.user_repos_count)
    TextView mUserReposCount;
    @Bind(R.id.user_started_count)
    TextView mUserStartedCount;
    @Bind(R.id.repo_gist_place)
    LinearLayout mRepoGistPlace;
    @Bind(R.id.content_card)
    CardView mContentCard;

    private String username;

    public AboutMe() {
    }

    public static EventFragment newInstance(String arg) {
        EventFragment fragment = new EventFragment();
        Bundle bundle = new Bundle();
        bundle.putString("username", arg);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        ButterKnife.bind(this, view);
        username = getArguments().getString("username");
        RetrofitMethods.getInstances().getUserInfo(new Subscriber<User>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(User user) {
                updadeInfo(user);
            }
        }, username);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void updadeInfo(User user) {
        mUserName.setText(user.getName());
        mUserFollowers.setText(user.getFollowers() + "");
        mUserFollowing.setText(user.getFollowing() + "");
        mJoinTime.setText("Joined on " + user.getCreated_at().substring(0, 10));
        mEMail.setText((user.getEmail() == null) ? "N/A" : user.getEmail());
        mCompany.setText((user.getCompany() == null) ? "N/A" : user.getCompany().toString());
        mLocation.setText((user.getLocation() == null) ? "N/A" : user.getLocation().toString());
        mUserReposCount.setText(user.getPublic_repos() + "");
    }

}
