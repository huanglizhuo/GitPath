package xyz.lizhuo.gitpath.HttpMethods;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.List;

import retrofit2.Response;
import retrofit2.Retrofit;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import xyz.lizhuo.gitpath.GithubModel.Event;
import xyz.lizhuo.gitpath.GithubModel.Notification;
import xyz.lizhuo.gitpath.GithubModel.Repo;
import xyz.lizhuo.gitpath.GithubModel.RepoContent;
import xyz.lizhuo.gitpath.GithubModel.User;
import xyz.lizhuo.gitpath.Utils.RetrofitUtils;
import xyz.lizhuo.gitpath.Utils.Utils;

/**
 * Created by lizhuo on 16/4/2.
 */
public class RetrofitMethods {

    private Retrofit retrofit;
    private GithubServices services;

    private RetrofitMethods() {
        retrofit = RetrofitUtils.getRetrofitWithToken();
        services = retrofit.create(GithubServices.class);
    }

    private static class SingoletonHolder {
        private static final RetrofitMethods INSTANCE = new RetrofitMethods();
    }

    public static RetrofitMethods getInstances() {
        return SingoletonHolder.INSTANCE;
    }

    public void getUserEvent(Subscriber<List<Event>> subscriber, String username, int page) {
        Observable event = services.userRecivedEvent(username, page);
        toSubscribe(event, subscriber);
    }

    public void getRepoDetail(Subscriber<Repo> subscriber, String owner, String reponame) {
        Observable repo = services.repoByName(owner, reponame);
        toSubscribe(repo, subscriber);
    }

    public void getReadme(Subscriber<String> subscriber, String owner, String reponame, final String cssfile) {
        final Observable readme = services.getReadme(owner, reponame)
                .flatMap(new Func1<RepoContent, Observable<String>>() {
                    @Override
                    public Observable<String> call(RepoContent repoContent) {
                        try {
                            Document document = Jsoup.connect(repoContent.getHtml_url()).get();
                            Element readme = document.getElementById("readme");
                            return Observable.just(Utils.loadMarkdownToHtml(readme.outerHtml(), cssfile));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return Observable.just("");
                    }
                });
        toSubscribe(readme, subscriber);
    }

    public void ifStarted(Subscriber<Boolean> subscriber, String owner, final String repo) {
        Observable star = services.ifStarted(owner, repo);
        toSubscribe(statucheck(star), subscriber);
    }

    public void starAction(Subscriber<Boolean> subscriber, String owner, final String repo, boolean isstar) {
        Observable star = null;
        if (isstar) {
            star = services.staraRepo(owner, repo);
        } else {
            star = services.unstaraRepo(owner, repo);
        }
        toSubscribe(statucheck(star), subscriber);
    }

//    public void unstaraRepo(Subscriber<Boolean> subscriber, String owner, final String repo) {
//        Observable star = services.unstaraRepo(owner, repo);
//        toSubscribe(statucheck(star), subscriber);
//    }

    public void ifFollowing(Subscriber<Boolean> subscriber, String username) {
        Observable follow = services.ifFollowing(username);
        toSubscribe(statucheck(follow), subscriber);
    }

    public void followAction(Subscriber<Boolean> subscriber, String username, boolean tofollow) {
        Observable follow = null;
        if (tofollow) {
            follow = services.followaUser(username);
        }else {
            follow = services.unfollowaUser(username);
        }
        toSubscribe(statucheck(follow), subscriber);
    }

    private Observable<Boolean> statucheck(Observable<Response<String>> observable) {
        return observable.flatMap(new Func1<Response<String>, Observable<Boolean>>() {
            @Override
            public Observable<Boolean> call(Response<String> stringResponse) {
                if (stringResponse.code() == 204) {
                    return Observable.just(true);
                } else {
                    return Observable.just(false); //if (response.header("Status")=="404 Not Found")
                }
            }
        });
    }

    public void getUserRepos(Subscriber<List<Repo>> subscriber, String username, int page) {
        Observable repo = services.userRepos(username, page);
        toSubscribe(repo, subscriber);
    }

    public void getUserInfo(Subscriber<User> subscriber, String username) {
        Observable user = services.userInfo(username);
        toSubscribe(user, subscriber);
    }

    public void getFollowers(Subscriber<List<User>> subscriber, String username, int page) {
        Observable user = services.followers(username, page);
        toSubscribe(user, subscriber);
    }

    public void getFollowing(Subscriber<List<User>> subscriber, String username, int page) {
        Observable follower = services.following(username, page);
        toSubscribe(follower, subscriber);
    }

    public void getStarted(Subscriber<List<Repo>> subscriber, String username, int page) {
        Observable startedRepo = services.starredRepos(username, page);
        toSubscribe(startedRepo, subscriber);
    }

    public void getNotifications(Subscriber<List<Notification>> subscriber) {
        Observable noti = services.getNotifications();
        toSubscribe(noti, subscriber);
    }

    public void markAsRead(Subscriber<List<Notification>> subscriber, String lastReadAt) {
        Observable noti = services.markAsRead(lastReadAt);
        toSubscribe(noti, subscriber);
    }

    //    public void getConttents(Subscriber<RepoContent> subscriber, String owner, String reponame, String contents) {
//        Observable co = services.contentsByPath(owner, reponame, contents);
//        toSubscribe(co, subscriber);
//    }
//
//    public void getRepoContent(Subscriber<List<RepoContent>> subscriber, String owner, String reponame) {
//        Observable content = services.contents(owner, reponame);
//        toSubscribe(content, subscriber);
//    }

    // TODO: 16/5/7 add error handleing like token invalide or network Unavailable ...
    private void toSubscribe(Observable observable, Subscriber subscriber) {
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
