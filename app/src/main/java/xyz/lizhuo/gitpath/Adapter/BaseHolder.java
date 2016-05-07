package xyz.lizhuo.gitpath.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by lizhuo on 16/4/5.
 */
public class BaseHolder extends RecyclerView.ViewHolder{
    private BaseViewHolder baseViewHolder;

    public BaseHolder(View itemView,int viewType) {
        super(itemView);
        baseViewHolder = BaseViewHolder.getViewHolder(itemView);
    }

    public BaseViewHolder getBaseViewHolder(){
        return baseViewHolder;
    }

}
