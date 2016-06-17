package com.weather.web.domain;

/**
 *
 * @author ryagudin
 */
public class User {

    private String userName;
    private String password;
    private Boolean administrator;

    public String getPassword() {
        return this.password;
    }

    public String getUserName() {
        return this.userName;
    }

    public Boolean isAdministrator() {
        return this.administrator;
    }

    @Override
    public String toString() {
        return "User [userName=" + userName + ", password=" + password
                + ", isAdministrator=" + administrator + "]";
    }
}
