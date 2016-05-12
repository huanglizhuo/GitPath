package xyz.lizhuo.gitpath.Adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;

import java.util.List;

import xyz.lizhuo.gitpath.GitPathApplication;
import xyz.lizhuo.gitpath.GithubModel.Event;
import xyz.lizhuo.gitpath.R;
import xyz.lizhuo.gitpath.Utils.GlideManager;
import xyz.lizhuo.gitpath.Utils.Utils;

/**
 * Created by lizhuo on 16/4/6.
 */
public class EventAdapter extends BaseAdapter {

    public List<Event> list;

    public EventAdapter(Context context, List<Event> list) {
        super(context);
        this.list = list;
    }

    @Override
    public int onCreateViewLayout(int viewType) {
        return R.layout.item_event_layout;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onBindViewHolder(BaseViewHolder baseViewHolder, int position) {
        Event event = list.get(position);
        String type = event.getType();
        ImageView action_img = baseViewHolder.getImageView(R.id.action_img);
        if (type.equals("PullRequestEvent")) {
            baseViewHolder.getTextView(R.id.actor_tv).setText(event.getActor().getLogin() + " " + event.getPayload().getAction());
            action_img.setImageDrawable(ContextCompat.getDrawable(GitPathApplication.getContext(), R.drawable.repo_marge));
        } else if (type.equals("ForkEvent")) {
            baseViewHolder.getTextView(R.id.actor_tv).setText(event.getActor().getLogin() + " forked ");
            action_img.setImageDrawable(ContextCompat.getDrawable(GitPathApplication.getContext(), R.drawable.repo_fork));
        } else if (type.equals("MemberEvent")) {
            list.remove(position); //I do not care about the memberEvent
            return;
        } else if (type.equals("CreateEvent")) {
            baseViewHolder.getTextView(R.id.actor_tv).setText(event.getActor().getLogin() + " create ");
            action_img.setImageDrawable(ContextCompat.getDrawable(GitPathApplication.getContext(), R.drawable.repo_create));
        } else if (type.equals("IssuesEvent")) {
            // TODO: 16/5/7 add issue comment ... event
            baseViewHolder.getTextView(R.id.actor_tv).setText(event.getActor().getLogin() + " " + event.getPayload().getAction());
            action_img.setImageDrawable(ContextCompat.getDrawable(GitPathApplication.getContext(), R.drawable.comment));
        } else {
            baseViewHolder.getTextView(R.id.actor_tv).setText(event.getActor().getLogin() + " " + event.getPayload().getAction());
            action_img.setImageDrawable(ContextCompat.getDrawable(GitPathApplication.getContext(), R.drawable.star_black));
        }
        baseViewHolder.getTextView(R.id.repo_name_tv).setText(event.getRepo().getName());
        baseViewHolder.getTextView(R.id.past_time_tv).setText(Utils.getBetweenTime(event.getCreated_at()));

        ImageView avatar = baseViewHolder.getImageView(R.id.avatar_spv);
        GlideManager.getInstance().loadCircleImage(context, event.getActor().getAvatar_url(), avatar);

    }

    public void refresh(List<Event> events) {
        list = null;
        list = events;
        notifyDataSetChanged();
    }

    public void update(List<Event> events) {
        list.addAll(events);
//        notifyDataSetChanged();
        if (events != null && events.size() > 0) {
            int startPosition = list.size();
            list.addAll(events);
            notifyItemRangeInserted(startPosition, events.size());
        }
    }
}
