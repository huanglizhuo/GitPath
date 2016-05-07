package xyz.lizhuo.gitpath.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

/**
 * Created by lizhuo on 16/4/16.
 */
public class GlideCircleTransform extends BitmapTransformation {

    public GlideCircleTransform(Context context){
        super(context);
    }

    @Override
    public String getId() {
        return getClass().getName();
    }

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        return circleCorp(pool,toTransform);
    }

    public static Bitmap circleCorp(BitmapPool pool,Bitmap toTransform){
        if (toTransform==null){
            return null;
        }

        int width = toTransform.getWidth();
        int height = toTransform.getHeight();

        int size = Math.min(width,height);
        int x = (width - size)/2;
        int y = (height - size)/2;

        Bitmap squared = Bitmap.createBitmap(toTransform,x,y,size,size);
        Bitmap result = pool.get(size,size, Bitmap.Config.ARGB_4444);
        if (result == null){
            result = Bitmap.createBitmap(size,size, Bitmap.Config.ARGB_4444);
        }
        Canvas canvas = new Canvas(result);
        Paint paint = new Paint();
        paint.setShader(new BitmapShader(squared,BitmapShader.TileMode.CLAMP,BitmapShader.TileMode.CLAMP));
        paint.setAntiAlias(true);
        float r = size/2f;
        canvas.drawCircle(r,r,r,paint);
        return result;
    }

}
