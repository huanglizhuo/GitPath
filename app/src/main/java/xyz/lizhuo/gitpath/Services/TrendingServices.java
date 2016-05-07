package xyz.lizhuo.gitpath.Services;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;
import xyz.lizhuo.gitpath.GithubModel.Repo;

/**
 * Created by lizhuo on 16/4/9.
 */
public interface TrendingServices {
    @GET("trending?")
    Observable<List<Repo>> trendingReposList(@Query("language") String language, @Query("since") String since);
}
