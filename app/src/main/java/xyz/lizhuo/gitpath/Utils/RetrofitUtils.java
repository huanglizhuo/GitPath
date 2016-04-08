package xyz.lizhuo.gitpath.Utils;

import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import xyz.lizhuo.gitpath.Application.GitPathApplication;
import xyz.lizhuo.gitpath.GithubModel.GitHub;

/**
 * Created by lizhuo on 16/3/27.
 */
public class RetrofitUtils {
    public static Retrofit getRetrofitWithToken() {
        Retrofit retrofit = null;
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        File cacheFile = new File(GitPathApplication.getContext().getCacheDir(), "[缓存目录]");
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 100); //100Mb
        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request.Builder builder = chain.request().newBuilder();
                builder.removeHeader("User-Agent");
                builder.addHeader("User-Agent", "GitPath");
                builder.addHeader("Accept", "application/vnd.github.v3.raw");
//                builder.addHeader("Time-Zone","Asia/Shanghai");
                builder.addHeader("Authorization", "token " + GitHub.getInstance().getSavedToken());
                Request request = builder.build();
                Log.i("Git", "Interceptor header = " + request.headers());
                Log.i("Git", "Interceptor method = " + request.method());
                Log.i("Git", "Interceptor urlString = " + request.url());
                return chain.proceed(request);
            }
        };
        httpClientBuilder.addInterceptor(interceptor);
        httpClientBuilder.connectTimeout(5, TimeUnit.SECONDS);
        httpClientBuilder.cache(cache);
        retrofit = new Retrofit.Builder()
                .client(httpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(Constant.BASE_URL)
                .build();
    return retrofit;
}

    public static Retrofit getRetrofitWithoutToken() {
        Retrofit retrofitWithoutToken = null;

        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();

        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {

                Request request = chain.request();
                request = request.newBuilder()
                        .removeHeader("User-Agent")
                        .addHeader("User-Agent", "GitPath")
                        .addHeader("Accept", "application/vnd.github.v3.raw")
                        .build();

                return chain.proceed(request);
            }
        };
        httpClientBuilder.addInterceptor(interceptor);

        File cacheFile = new File(GitPathApplication.getContext().getCacheDir(), "[缓存目录]");
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 100); //100Mb
        httpClientBuilder.cache(cache);

        retrofitWithoutToken = new Retrofit.Builder().baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClientBuilder.build())
                .build();


        return retrofitWithoutToken;
    }
}
