package xyz.lizhuo.gitpath.HttpHandle;

import java.util.List;

import retrofit2.Retrofit;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import xyz.lizhuo.gitpath.GithubModel.Event;
import xyz.lizhuo.gitpath.Services.GithubServices;
import xyz.lizhuo.gitpath.Utils.RetrofitUtils;

/**
 * Created by lizhuo on 16/4/2.
 */
public class RetrofitMethods {

    private Retrofit retrofit;
    private GithubServices services;

    private RetrofitMethods(){
        retrofit = RetrofitUtils.getRetrofitWithToken();
        services = retrofit.create(GithubServices.class);
    }

    private static class SingoletonHolder{
        private static final RetrofitMethods INSTANCE = new RetrofitMethods();
    }

    public static RetrofitMethods getInstances(){
        return SingoletonHolder.INSTANCE;
    }

    public void getUserEvent(Subscriber<List<Event>> subscriber, String username, int page){
        services.userRecivedEvent(username,page)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
