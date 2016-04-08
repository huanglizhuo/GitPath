package xyz.lizhuo.gitpath.Services;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;
import xyz.lizhuo.gitpath.GithubModel.Event;
import xyz.lizhuo.gitpath.GithubModel.Repo;
import xyz.lizhuo.gitpath.GithubModel.RepoContent;
import xyz.lizhuo.gitpath.GithubModel.RepoReadme;
import xyz.lizhuo.gitpath.GithubModel.Token;
import xyz.lizhuo.gitpath.GithubModel.User;

/**
 * Created by lizhuo on 16/3/24.
 */
public interface GithubServices {

    //token

    @POST("authorizations")
    Call<Token> createToken(@Body Token token, @Header("Authorization") String authorization);

//    @DELETE("authorizations")
//    Call<String> deleToken(@Header("Authorization") String authorization);
    //events

    @GET("/users/{username}/received_events")
    Observable<List<Event>> userRecivedEvent(@Path("username") String username, @Query("page") int page);

    //repos

    @GET("/repos/{owner}/{name}")
    Observable<Repo> repoByName(@Path("owner") String owner, @Path("name") String repo);

    @GET("/repos/{owner}/{repo}/contents")
    Observable<List<RepoContent>> contents(@Path("owner") String owner, @Path("repo") String repo);

    @GET("/repos/{owner}/{repo}/contents/{path}")
    Observable<List<RepoContent>> contentsByPath(@Path("owner") String owner, @Path("repo") String repo, @Path("path") String path);

    @GET("/repos/{owner}/{repo}/readme")
    Observable<RepoReadme> readme(@Path("owner") String owner, @Path("repo") String repo);

    @GET("/repos/{owner}/{repo}/stargazers")
    Observable<List<User>> stargazers(@Path("owner") String owner, @Path("repo") String repo,@Query("page") int page);

    //user

    @GET("/users/{username}")
    Observable<User> userInfo(@Path("username") String username);

    @GET("/users/{username}/followers")
    Observable<List<User>> followers(@Path("username") String username,@Query("page") int page);

    @GET("/users/:username/following")
    Observable<List<User>> following(@Path("username") String username,@Query("page") int page);

    @GET("/users/{username}/repos")
    Observable<List<Repo>> userRepos(@Path("username") String username,@Query("page") int page);

    @GET("/users/{username}/starred")
    Observable<List<Repo>> starred(@Path("username") String username,@Query("page") int page);

}
