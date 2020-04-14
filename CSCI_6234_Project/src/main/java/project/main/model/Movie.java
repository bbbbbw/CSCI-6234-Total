package project.main.model;

import java.io.Serializable;

public class Movie implements Serializable {
    private int id;
    private String apiId;
    private String name;

    public Movie() {
    }

    public Movie(int id, String apiId, String name) {
        this.id = id;
        this.apiId = apiId;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getApiId() {
        return apiId;
    }

    public void setApiId(String apiId) {
        this.apiId = apiId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
