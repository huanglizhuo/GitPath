package xyz.lizhuo.gitpath.Frgments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

    private String username;
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

    private void updadeInfo(User user) {
        GlideManager.getInstance().loadCircleImage(context, user.getAvatar_url(), mAvatarImg);
        mUserName.setText(user.getName());
        mUserFollowers.setText(user.getFollowers() + "");
        mUserFollowing.setText(user.getFollowing() + "");
        mUserReposCount.setText(user.getPublic_repos() + "");

        mJoinTime.setText("Joined on " + user.getCreated_at().substring(0, 10));
        mEMail.setText((user.getEmail() == null) ? "N/A" : user.getEmail());
        mCompany.setText((user.getCompany() == null) ? "N/A" : user.getCompany().toString());
        mLocation.setText((user.getLocation() == null) ? "N/A" : user.getLocation().toString());
    }

}
