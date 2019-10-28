package com.zzkk.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author mojiayi
 */
public class User {
    @JsonProperty("user")
    private String user;
    @JsonProperty("pwd")
    private String password;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
