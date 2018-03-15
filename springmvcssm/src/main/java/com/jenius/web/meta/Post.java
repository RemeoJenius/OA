package com.jenius.web.meta;

import java.util.Set;

/**
 * Created by liyongjun on 17/2/3.
 */
public class Post {

    private int id;
    private String pname;
    private String description;
    private Set<User> users;

    public Post(String id) {
        this.id = Integer.valueOf(id);
    }

    public Post() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
