package com.example.shiv.myapplication.modals;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Objects;

import javax.inject.Inject;

public class User implements Serializable {

    private static final long serialVersionUID = -8283916825882093630L;

    private String id;
    private String username;

    private String code;

    @Inject
    public User(){}

    public User(String id, String username, String code) {
        this.id = id;
        this.username = username;
        this.code = code;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return this.username != null && user.username != null &&
                this.username.equals(user.username);
    }

    @Override
    public int hashCode() {

        if(this.username != null ){
            return this.username.hashCode();
        }
        return 0;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
