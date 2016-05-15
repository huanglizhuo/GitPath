package xyz.lizhuo.gitpath.Frgments;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import xyz.lizhuo.gitpath.Activity.OneFragmentActivity;
import xyz.lizhuo.gitpath.Activity.UserDetailActivity;
import xyz.lizhuo.gitpath.Adapter.NotificationAdapter;
import xyz.lizhuo.gitpath.GithubModel.GitHub;
import xyz.lizhuo.gitpath.GithubModel.Notification;
import xyz.lizhuo.gitpath.GithubModel.Repo;
import xyz.lizhuo.gitpath.GithubModel.User;
import xyz.lizhuo.gitpath.HttpMethods.RetrofitMethods;
import xyz.lizhuo.gitpath.R;
import xyz.lizhuo.gitpath.Utils.GlideManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class AboutFragment extends Fragment {

    @Bind(R.id.avatar_img)
    ImageView mAvatarImg;
    @Bind(R.id.user_name)
    EditText mUserName;
    @Bind(R.id.user_followers)
    TextView mUserFollowers;

    @Bind(R.id.user_following)
    TextView mUserFollowing;
    @Bind(R.id.user_repos_count)
    TextView mUserReposCount;

    @Bind(R.id.notification_rcl)
    RecyclerView mNotificationRcl;

    @Bind(R.id.notification_tell_tv)
    TextView mNotification;
    @Bind(R.id.foww)
    LinearLayout mFoww;
    @Bind(R.id.company)
    EditText mCompany;
    @Bind(R.id.location)
    EditText mLocation;
    @Bind(R.id.e_mail)
    EditText mEMail;
    @Bind(R.id.join_time)
    EditText mJoinTime;
    @Bind(R.id.info)
    LinearLayout mInfo;
    @Bind(R.id.trend_setting_tv)
    TextView mTrendSettingTv;
    @Bind(R.id.since_spinner)
    AppCompatSpinner mSinceSpinner;
    @Bind(R.id.since_lin)
    LinearLayout mSinceLin;
    @Bind(R.id.lanaguage_spinner)
    AppCompatSpinner mLanaguageSpinner;
    @Bind(R.id.language_lin)
    LinearLayout mLanguageLin;



    private String username;
    private String avatar_url;
    private Context context;

    public AboutFragment() {
    }

    public static AboutFragment newInstance(String arg) {
        AboutFragment fragment = new AboutFragment();
        Bundle bundle = new Bundle();
        bundle.putString("username", arg);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about, container, false);
        ButterKnife.bind(this, view);
        username = getArguments().getString("username");

        initSpinner();

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

        RetrofitMethods.getInstances().getNotifications(new Subscriber<List<Notification>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(List<Notification> notifications) {
                setNotification(notifications);
            }
        });
        return view;
    }

    private void initSpinner() {
// TODO: 16/5/14 add save data mode by disable the user avatar
        mSinceSpinner.setSelection(GitHub.getInstance().getTrend_since());

        mSinceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                GitHub.getInstance().setTrend_since(position);
//                RxBus.getDefault().send();
                // TODO: 16/5/13 try to use rxbus to update TendingFrament 's content
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mLanaguageSpinner.setSelection(GitHub.getInstance().getTrend_lanaguage());
        mLanaguageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                GitHub.getInstance().setTrend_lanaguage(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void updadeInfo(User user) {
        avatar_url = user.getAvatar_url();
        GlideManager.getInstance().loadCircleImage(context, user.getAvatar_url(), mAvatarImg);
        mUserName.setText(user.getName());
        mUserFollowers.setText(user.getFollowers() + "");
        mUserFollowing.setText(user.getFollowing() + "");
        mUserReposCount.setText(user.getPublic_repos() + "");

//        mJoinTime.setText("Joined on " + user.getCreated_at().substring(0, 10));
//        mEMail.setText((user.getEmail() == null) ? "N/A" : user.getEmail());
//        mCompany.setText((user.getCompany() == null) ? "N/A" : user.getCompany().toString());
//        mLocation.setText((user.getLocation() == null) ? "N/A" : user.getLocation().toString());
    }

    private void setNotification(List<Notification> notifications) {

        // TODO: 16/5/14 add notification detail like this https://github.com/Meetic/MaryPopup

        if (notifications.size() == 0) {
            mNotification.setTextSize(26);
            mNotification.setText("No Unread Notification");
        } else {
            mNotificationRcl.setLayoutManager(new LinearLayoutManager(context));
            mNotificationRcl.setHasFixedSize(true);
            mNotificationRcl.setAdapter(new NotificationAdapter(context, notifications));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.user_followers_ll, R.id.user_following_ll, R.id.user_repos_count_ll,R.id.give_comment_tv})
    public void onClick(View view) {
        // TODO: 16/5/14 maybe use implicit intent is better
        Intent intent = new Intent(context, OneFragmentActivity.class);
        intent.putExtra("userLogin", username);
        intent.putExtra("avatar_url", avatar_url);
        switch (view.getId()) {
            case R.id.user_followers_ll:
                intent.putExtra("type", User.FOLLOWER);
                context.startActivity(intent);
                break;
            case R.id.user_following_ll:
                intent.putExtra("type", User.FOLLOWING);
                context.startActivity(intent);
                break;
            case R.id.user_repos_count_ll:
                intent.putExtra("type", Repo.OWNREPO);
                context.startActivity(intent);
                break;
            case R.id.app_author_detail:
                Intent detailIntent = new Intent(context, UserDetailActivity.class);
                detailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                detailIntent.putExtra("userlogin", username);
                detailIntent.putExtra("avatar_url", avatar_url);
                context.startActivity(detailIntent);
                break;
            case R.id.give_comment_tv:
                Intent feedbackIntent = new Intent();
                feedbackIntent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse("https://github.com/huanglizhuo/GitPath/issues/new");
                feedbackIntent.setData(content_url);
                context.startActivity(feedbackIntent);

                break;
            default:
                break;
        }
    }
}
