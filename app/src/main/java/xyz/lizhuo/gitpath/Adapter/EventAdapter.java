package xyz.lizhuo.gitpath.Adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import xyz.lizhuo.gitpath.GithubModel.Event;
import xyz.lizhuo.gitpath.R;
import xyz.lizhuo.gitpath.Utils.Utils;

/**
 * Created by lizhuo on 16/4/6.
 */
public class EventAdapter extends BaseAdapter{

    public EventAdapter(Context context, List<Event> list) {
        super(context, list);
    }

    @Override
    public int onCreateViewLayout(int viewType) {
        return R.layout.item_event_layout;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder baseViewHolder, int position) {
        Event event = (Event)list.get(position);
        baseViewHolder.getTextView(R.id.actor_tv).setText(event.getActor().getLogin()+" "+event.getPayload().getAction());
        baseViewHolder.getTextView(R.id.repo_name_tv).setText(event.getRepo().getName());
        
        // TODO: 16/4/7  更改时区
        baseViewHolder.getTextView(R.id.past_time_tv).setText(Utils.getHowTime(event.getCreated_at()));
        ImageView avatar = baseViewHolder.getImageView(R.id.avatar_spv);
        Glide.with(this.context)
                .load(event.getActor().getAvatar_url())
                .centerCrop()
                .placeholder(R.drawable.fork)
                .crossFade()
                .into(avatar);
    }
}
