package xyz.lizhuo.gitpath.Utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import xyz.lizhuo.gitpath.R;

/**
 * Created by lizhuo on 16/4/16.
 */
public class GlideManager {

    private GlideManager(){

    }
    private static class GlideControlHolder {
        private static GlideManager instance = new GlideManager();
    }

    public static GlideManager getInstance() {
        return GlideControlHolder.instance;
    }

    public void loadCircleImage(Context context, String url , ImageView imageView){
        // TODO: 16/4/16  change transform make it more smothe
        Glide.with(context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transform(new GlideCircleTransform(context))
                .placeholder(R.drawable.octorcat_black)
                .into(imageView);
    }

    public void loadRoundImage(Context context,String url,ImageView imageView){
        Glide.with(context)
                .load(url)
                .transform(new GlideRoundTransform(context))
                .placeholder(R.drawable.octorcat_black)
                .crossFade()
                .into(imageView);
    }
    public void loadImage(Context context,String url,ImageView imageView){
        Glide.with(context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .crossFade()
                .centerCrop()
                .into(imageView);
    }
}
