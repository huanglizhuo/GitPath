package xyz.lizhuo.gitpath.HttpMethods;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import xyz.lizhuo.gitpath.GithubModel.Repo;
import xyz.lizhuo.gitpath.Services.TrendingServices;

/**
 * Created by lizhuo on 16/4/9.
 */
public class TrendingMethods {
    private TrendingServices trendingServices;
    private Retrofit retrofit;

    public TrendingMethods() {
        retrofit = new Retrofit.Builder()
                .client(new OkHttpClient.Builder().build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl("http://trending.codehub-app.com/v2/")
                .build();
        trendingServices = retrofit.create(TrendingServices.class);
    }

    //since: daily  weekly monthly
    public void getTrending(Subscriber<List<Repo>> subscriber, String language, String since) {
        trendingServices.trendingReposList(language, since)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
