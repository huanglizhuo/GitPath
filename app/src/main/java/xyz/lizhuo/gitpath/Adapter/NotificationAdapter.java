package xyz.lizhuo.gitpath.Adapter;

import android.content.Context;

import java.util.List;

import xyz.lizhuo.gitpath.GithubModel.Notification;
import xyz.lizhuo.gitpath.R;

/**
 * Created by lizhuo on 16/5/11.
 */
public class NotificationAdapter extends BaseAdapter {
    public List<Notification> list;

    public NotificationAdapter(Context context, List<Notification> list) {
        super(context);
        this.list = list;
    }

    @Override
    public int onCreateViewLayout(int viewType) {
        return R.layout.item_notification_layout;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onBindViewHolder(BaseViewHolder baseViewHolder, int position) {
        Notification notification = list.get(position);

    }

    public void refresh(List<Notification> notifications) {
        list = null;
        list = notifications;
        notifyDataSetChanged();
    }

    public void update(List<Notification> notifications) {
        list.addAll(notifications);
        notifyDataSetChanged();
        if (notifications != null && notifications.size() > 0) {
            int startPosition = list.size();
            list.addAll(notifications);
            notifyItemRangeInserted(startPosition, notifications.size());
        }
    }
}
