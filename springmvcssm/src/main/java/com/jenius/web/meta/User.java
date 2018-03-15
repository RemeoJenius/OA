package com.jenius.web.meta;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by liyongjun on 17/2/2.
 */
public class User {

    private Integer id;
    private String username;
    private String password;
    private String sex;
    private String phone;
    private String email;
    private String image;
    private Department department;
    private HashSet<Post> posts;
    private Set<Menu> menus;
    private User manager;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getManager() {
        return manager;
    }

    public void setManager(User manager) {
        this.manager = manager;
    }

    public Set<Menu> getMenus() {
        return menus;
    }

    public void setMenus(Set<Menu> menus) {
        this.menus = menus;
    }

    public User(Integer id, String username) {
        this.id = id;
        this.username = username;
    }
    public User(){

    }


    public User(Integer id, String username, String sex, String phone, String email,String image) {
        this.id = id;
        this.username = username;
        this.sex = sex;
        this.phone = phone;
        this.email = email;
        this.image = image;
    }

    public User(Integer id, String username, String sex, String phone, String email) {
        this.id = id;
        this.username = username;
        this.sex = sex;
        this.phone = phone;
        this.email = email;
    }

    public User(Integer id, String username, String sex, String phone) {
        this.id = id;
        this.username = username;
        this.sex = sex;
        this.phone = phone;
    }

    public User(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }


    public HashSet<Post> getPosts() {
        return posts;
    }

    public void setPosts(HashSet<Post> posts) {
        this.posts = posts;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
