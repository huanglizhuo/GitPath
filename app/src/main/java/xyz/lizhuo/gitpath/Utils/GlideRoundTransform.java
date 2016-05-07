package xyz.lizhuo.gitpath.Utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

/**
 * Created by lizhuo on 16/4/16.
 */
public class GlideRoundTransform extends BitmapTransformation {

    private static float radius = 0f;

    public GlideRoundTransform(Context context) {
        this(context,4);
    }

    public GlideRoundTransform(Context context,int dp) {
        super(context);
        this.radius = Resources.getSystem().getDisplayMetrics().density * dp;
    }
    @Override
    public String getId() {
        return getClass().getName();
    }

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        return roundCorp(pool,toTransform);
    }

    private Bitmap roundCorp(BitmapPool pool, Bitmap toTransform) {
        if (toTransform==null){
            return null;
        }
        int width = toTransform.getWidth();
        int height = toTransform.getHeight();
        Bitmap result = pool.get(width,height, Bitmap.Config.RGB_565);
        if (result==null){
            result = Bitmap.createBitmap(width,height, Bitmap.Config.RGB_565);
        }
        Canvas canvas = new Canvas(result);
        Paint paint = new Paint();
        paint.setShader(new BitmapShader(toTransform,BitmapShader.TileMode.CLAMP,BitmapShader.TileMode.CLAMP));
        paint.setAntiAlias(true);
        RectF rectF = new RectF(0f,0f,width,height);
        canvas.drawRoundRect(rectF,radius,radius,paint);
        return result;

    }
}
