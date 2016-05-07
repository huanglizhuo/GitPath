package xyz.lizhuo.gitpath.Adapter;

import android.content.Context;
import android.widget.ImageView;

import java.util.List;

import xyz.lizhuo.gitpath.GithubModel.User;
import xyz.lizhuo.gitpath.R;
import xyz.lizhuo.gitpath.Utils.GlideManager;

/**
 * Created by lizhuo on 16/4/8.
 */
public class UserAdapter extends BaseAdapter{
    public List<User> list;

    public UserAdapter(Context context,List<User> users) {
        super(context);
        this.list = users;
    }

    @Override
    public int onCreateViewLayout(int viewType) {
        return R.layout.item_user_layout;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onBindViewHolder(BaseViewHolder baseViewHolder, int position) {
        User user = list.get(position);
        baseViewHolder.getTextView(R.id.user_name).setText(user.getLogin());
        ImageView avatar = baseViewHolder.getImageView(R.id.avatar_spv);
        GlideManager.getInstance().loadCircleImage(context,user.getAvatar_url(),avatar);
    }

    public void refresh(List<User> users){
        list = null;
        list = users;
        notifyDataSetChanged();
    }

    public void update(List<User> users){
        list.addAll(users);
        notifyDataSetChanged();
        if(users != null && users.size() > 0) {
            int startPosition =list.size();
            list.addAll(users);
            notifyItemRangeInserted(startPosition,users.size());
        }
    }
}
