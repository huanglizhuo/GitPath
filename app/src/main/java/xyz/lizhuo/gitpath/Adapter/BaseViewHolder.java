package xyz.lizhuo.gitpath.Adapter;

import android.support.v7.widget.CardView;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * Created by lizhuo on 16/4/5.
 */
public class BaseViewHolder {
    private SparseArray<View> viewHolder;
    private View view;

    public static BaseViewHolder getViewHolder(View view){
        BaseViewHolder baseViewHolder = (BaseViewHolder)view.getTag();
        if (baseViewHolder ==null){
            baseViewHolder = new BaseViewHolder(view);
            view.setTag(baseViewHolder);
        }
        return baseViewHolder;
    }

    private BaseViewHolder(View view){
        this.view = view;
        viewHolder = new SparseArray<View>();
        view.setTag(viewHolder);
    }

    public <T extends View> T get(int id){
        View childView = viewHolder.get(id);
        if (childView == null){
            childView = view.findViewById(id);
            viewHolder.put(id,childView);
        }
        return (T) childView;
    }

    public EditText getEditText(int id){
        return get(id);
    }

    public View getConverView(){
        return view;
    }

    public TextView getTextView(int id){
        return get(id);
    }

    public Button getButton(int id){
        return get(id);
    }

    public CardView getCardView(int id){
        return get(id);
    }

    public ImageView getImageView(int id){
        return get(id);
    }


}
