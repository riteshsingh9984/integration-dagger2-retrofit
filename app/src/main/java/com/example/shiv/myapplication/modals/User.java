package com.example.shiv.myapplication.modals;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

public class User {

    private static final long serialVersionUID = -8283916825882093630L;

    private String id;
    private String username;
    private String password;
    private List<Role> authorities;
    private String emailId;
    private String parentId;
    private String crn;
    private String hashedUserImage;
    private String branchCode;
    private String departmentCode;

    public User(String email, String username) {
        this.username = username;
        this.emailId = email;
    }

    public String getCrn() {
        return crn;
    }

    public void setCrn(String crn) {
        this.crn = crn;
    }

    public String getHashedUserImage() {
        return hashedUserImage;
    }

    public void setHashedUserImage(String hashedUserImage) {
        this.hashedUserImage = hashedUserImage;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    public List<Role> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<Role> authorities) {
        this.authorities = authorities;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    @Inject
    public User(){}

    public User(String id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String code) {
        this.password = password;
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
                ", password='" + password + '\'' +
                '}';
    }
}
