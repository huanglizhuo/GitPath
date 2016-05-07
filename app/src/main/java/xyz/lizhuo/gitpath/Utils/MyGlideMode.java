package xyz.lizhuo.gitpath.Utils;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.module.GlideModule;

/**
 * Created by lizhuo on 16/4/10.
 */
public class MyGlideMode implements GlideModule {
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        builder.setDiskCache(new InternalCacheDiskCacheFactory(context,1024*200));
        builder.setMemoryCache(new LruResourceCache(1024*100));
    }

    @Override
    public void registerComponents(Context context, Glide glide) {

    }
}
