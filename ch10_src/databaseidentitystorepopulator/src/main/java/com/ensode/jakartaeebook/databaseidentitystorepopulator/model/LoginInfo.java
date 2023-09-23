package com.ensode.jakartaeebook.databaseidentitystorepopulator.model;

import jakarta.enterprise.inject.Model;
import java.util.List;

@Model
public class LoginInfo {

    private String userName;
    private String clearTextPassword;
    private List<String> roleList;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getClearTextPassword() {
        return clearTextPassword;
    }

    public void setClearTextPassword(String clearTextPassword) {
        this.clearTextPassword = clearTextPassword;
    }

    public List<String> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<String> roleList) {
        this.roleList = roleList;
    }

    @Override
    public String toString() {
        return "LoginInfo{" + "userName=" + userName + ", clearTextPassword=" + clearTextPassword + ", roleList=" + roleList + '}';
    }

}
