package xyz.lizhuo.gitpath.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import java.util.List;

import xyz.lizhuo.gitpath.GithubModel.Event;

/**
 * Created by lizhuo on 16/4/5.
 */
public abstract class BaseAdapter extends RecyclerView.Adapter<BaseHolder> {
    public List<Event> list;
    public Context context;
    private AdapterView.OnItemClickListener onItemClickListener;

    public BaseAdapter(Context context, List<Event> list){
        this.list = list;
        this.context = context;
    }

    @Override
    public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(onCreateViewLayout(viewType),null);
        return new BaseHolder(view);
    }

    public abstract int onCreateViewLayout(int viewType);

    @Override
    public void onViewRecycled(BaseHolder holder) {
        super.onViewRecycled(holder);
    }

    @Override
    public void onBindViewHolder(final BaseHolder holder, final int position) {
        onBindViewHolder(holder.getBaseViewHolder(),position);
        if (onItemClickListener!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(null,v,holder.getPosition(),holder.getItemId());
                }
            });
        }
    }

    public abstract void onBindViewHolder(BaseViewHolder baseViewHolder, int position);

    @Override
    public int getItemCount() {
        return list.size();
    }

    public AdapterView.OnItemClickListener getOnItemClickListener(){
        return onItemClickListener;
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
}
