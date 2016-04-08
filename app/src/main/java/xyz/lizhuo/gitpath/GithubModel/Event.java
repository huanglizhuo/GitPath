package xyz.lizhuo.gitpath.GithubModel;

import com.google.gson.annotations.SerializedName;

/**
 * Created by lizhuo on 16/4/6.
 */
public class Event {

    /**
     * id : 3852168953
     * type : WatchEvent
     * actor : {"id":5214214,"login":"drakeet","gravatar_id":"","url":"https://api.github.com/users/drakeet","avatar_url":"https://avatars.githubusercontent.com/u/5214214?"}
     * repo : {"id":44513129,"name":"CtripMobile/DynamicAPK","url":"https://api.github.com/repos/CtripMobile/DynamicAPK"}
     * payload : {"action":"started"}
     * public : true
     * created_at : 2016-04-06T07:25:38Z
     * org : {"id":13991918,"login":"CtripMobile","gravatar_id":"","url":"https://api.github.com/orgs/CtripMobile","avatar_url":"https://avatars.githubusercontent.com/u/13991918?"}
     */

    private String id;
    private String type;
    /**
     * id : 5214214
     * login : drakeet
     * gravatar_id :
     * url : https://api.github.com/users/drakeet
     * avatar_url : https://avatars.githubusercontent.com/u/5214214?
     */

    private ActorBean actor;
    /**
     * id : 44513129
     * name : CtripMobile/DynamicAPK
     * url : https://api.github.com/repos/CtripMobile/DynamicAPK
     */

    private RepoBean repo;
    /**
     * action : started
     */

    private PayloadBean payload;
    @SerializedName("public")
    private boolean publicX;
    private String created_at;
    /**
     * id : 13991918
     * login : CtripMobile
     * gravatar_id :
     * url : https://api.github.com/orgs/CtripMobile
     * avatar_url : https://avatars.githubusercontent.com/u/13991918?
     */

    private OrgBean org;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ActorBean getActor() {
        return actor;
    }

    public void setActor(ActorBean actor) {
        this.actor = actor;
    }

    public RepoBean getRepo() {
        return repo;
    }

    public void setRepo(RepoBean repo) {
        this.repo = repo;
    }

    public PayloadBean getPayload() {
        return payload;
    }

    public void setPayload(PayloadBean payload) {
        this.payload = payload;
    }

    public boolean isPublicX() {
        return publicX;
    }

    public void setPublicX(boolean publicX) {
        this.publicX = publicX;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public OrgBean getOrg() {
        return org;
    }

    public void setOrg(OrgBean org) {
        this.org = org;
    }

    public static class ActorBean {
        private int id;
        private String login;
        private String gravatar_id;
        private String url;
        private String avatar_url;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getLogin() {
            return login;
        }

        public void setLogin(String login) {
            this.login = login;
        }

        public String getGravatar_id() {
            return gravatar_id;
        }

        public void setGravatar_id(String gravatar_id) {
            this.gravatar_id = gravatar_id;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getAvatar_url() {
            return avatar_url;
        }

        public void setAvatar_url(String avatar_url) {
            this.avatar_url = avatar_url;
        }
    }

    public static class RepoBean {
        private int id;
        private String name;
        private String url;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public static class PayloadBean {
        private String action;

        public String getAction() {
            return action;
        }

        public void setAction(String action) {
            this.action = action;
        }
    }

    public static class OrgBean {
        private int id;
        private String login;
        private String gravatar_id;
        private String url;
        private String avatar_url;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getLogin() {
            return login;
        }

        public void setLogin(String login) {
            this.login = login;
        }

        public String getGravatar_id() {
            return gravatar_id;
        }

        public void setGravatar_id(String gravatar_id) {
            this.gravatar_id = gravatar_id;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getAvatar_url() {
            return avatar_url;
        }

        public void setAvatar_url(String avatar_url) {
            this.avatar_url = avatar_url;
        }
    }
}
