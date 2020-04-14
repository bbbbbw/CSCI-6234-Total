package project.main.model;

import java.io.Serializable;

public class UrlFilter implements Serializable {
    private String url;
    private int type; // 0: data api, 1: web page
    private int auth; // 0: any visitor, 1: need login

    public UrlFilter() {
    }

    public UrlFilter(String url, int type, int auth) {
        this.url = url;
        this.type = type;
        this.auth = auth;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getAuth() {
        return auth;
    }

    public void setAuth(int auth) {
        this.auth = auth;
    }
}
