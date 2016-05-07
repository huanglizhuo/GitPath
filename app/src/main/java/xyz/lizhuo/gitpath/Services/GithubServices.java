package xyz.lizhuo.gitpath.Services;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;
import xyz.lizhuo.gitpath.GithubModel.Event;
import xyz.lizhuo.gitpath.GithubModel.Notification;
import xyz.lizhuo.gitpath.GithubModel.Repo;
import xyz.lizhuo.gitpath.GithubModel.RepoContent;
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

    @GET("/repos/{owner}/{reponame}")
    Observable<Repo> repoByName(@Path("owner") String owner, @Path("reponame") String reponame);

    @GET("/repos/{owner}/{repo}/contents")
    Observable<List<RepoContent>> contents(@Path("owner") String owner, @Path("repo") String repo);

    @GET("/repos/{owner}/{repo}/contents/{path}")
    Observable<List<RepoContent>> contentsByPath(@Path("owner") String owner, @Path("repo") String repo, @Path("path") String path);

    @GET("repos/{owner}/{repo}/readme")
    Observable<RepoContent> getReadme(@Path("owner") String owner, @Path("repo") String repo);

//    @GET("/repos/{owner}/{repo}/contents/{path}")
//    Call<String> getRawContent(@Path("owner") String owner, @Path("repo") String repo,@Path("path") String path);

    @GET("/repos/{owner}/{repo}/stargazers")
    Observable<List<User>> stargazers(@Path("owner") String owner, @Path("repo") String repo, @Query("page") int page);


    @GET("/user/starred/{owner}/{repo}")
    Observable<Response<String>> ifStarted(@Path("owner") String owner, @Path("repo") String repo);

    @Headers("Content-Length: 0")
    @PUT("/user/starred/{owner}/{repo}")
    Observable<Response<String>> staraRepo(@Path("owner") String owner, @Path("repo") String repo);

    @Headers("Content-Length: 0")
    @DELETE("/user/starred/{owner}/{repo}")
    Observable<Response<String>> unstaraRepo(@Path("owner") String owner, @Path("repo") String repo);

    @GET("/user/following/{username}")
    Observable<Response<String>> ifFollowing(@Path("username") String username);

    @Headers("Content-Length: 0")
    @PUT("/user/following/{username}")
    Observable<Response<String>> followaUser(@Path("username") String username);

    @Headers("Content-Length: 0")
    @DELETE("/user/following/{username}")
    Observable<Response<String>> unfollowaUser(@Path("username") String username);

    @GET("/users/{username}")
    Observable<User> userInfo(@Path("username") String username);

    @GET("/users/{username}/followers")
    Observable<List<User>> followers(@Path("username") String username, @Query("page") int page);

    @GET("/users/{username}/following")
    Observable<List<User>> following(@Path("username") String username, @Query("page") int page);

    @GET("/users/{username}/repos")
    Observable<List<Repo>> userRepos(@Path("username") String username, @Query("page") int page);

    @GET("/users/{username}/starred")
    Observable<List<Repo>> starredRepos(@Path("username") String username, @Query("page") int page);

    @GET("/notifications")
    Observable<List<Notification>> getNotifications();

    @PUT("/notifications")
    Observable<Response<String>> markAsRead(@Body String lastReadAt);
}
