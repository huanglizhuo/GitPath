package xyz.lizhuo.gitpath.Adapter;

import android.content.Context;

import java.util.List;

import xyz.lizhuo.gitpath.GithubModel.Notification;
import xyz.lizhuo.gitpath.R;
import xyz.lizhuo.gitpath.Utils.Utils;

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
        baseViewHolder.getTextView(R.id.repo_name_tv).setText(notification.getRepository().getName());
        baseViewHolder.getTextView(R.id.notification_title_tv).setText(notification.getSubject().getTitle());
        baseViewHolder.getTextView(R.id.notification_since_tv).setText(Utils.getBetweenTime(notification.getUpdated_at()));
        baseViewHolder.getTextView(R.id.notification_reason_tv).setText(notification.getReason());
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
