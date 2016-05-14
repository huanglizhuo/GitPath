package xyz.lizhuo.gitpath.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

/**
 * Created by lizhuo on 16/4/5.
 */
public abstract class BaseAdapter extends RecyclerView.Adapter<BaseHolder> {

    // TODO: 16/5/13 optimise this using https://github.com/CymChad/BaseRecyclerViewAdapterHelper
    // TODO: 16/4/11 add Header and Footer
    private static final int IS_NORMAL = 1;
    private static final int IS_HEADER = 2;
    private static final int IS_FOOTER = 3;

    public Context context;
    private AdapterView.OnItemClickListener onItemClickListener;

    public BaseAdapter(Context context) {
        this.context = context;
//        this.context = GitPathApplication.getContext();
    }

    @Override
    public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(onCreateViewLayout(viewType), null);
        return new BaseHolder(view, viewType);
    }

    public abstract int onCreateViewLayout(int viewType);

    @Override
    public void onViewRecycled(BaseHolder holder) {
        super.onViewRecycled(holder);
    }


    @Override
    public void onBindViewHolder(final BaseHolder holder, final int position) {
        onBindViewHolder(holder.getBaseViewHolder(), position);
        if (onItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(null, v, holder.getPosition(), holder.getItemId());
                }
            });
        }
    }

    public abstract void onBindViewHolder(BaseViewHolder baseViewHolder, int position);


    public AdapterView.OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

}
