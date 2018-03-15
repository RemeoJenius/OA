package com.jenius.web.meta;

import java.util.Set;

/**
 * Created by liyongjun on 17/2/3.
 */
public class Department {

    private Integer id;
    private String dname;
    private String description;
    private Set<User> users;

    public Department(String id) {
        this.id = Integer.valueOf(id);
    }

    public Department() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDname() {
        return dname;
    }

    public void setDname(String dname) {
        this.dname = dname;
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
