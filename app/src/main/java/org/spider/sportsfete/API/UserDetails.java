package org.spider.sportsfete.API;

public class UserDetails {

    private String loginStatus;
    private String token;
    private String rollNumber;

    public String getLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(String loginStatus) {
        this.loginStatus = loginStatus;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String JWTToken) { this.token = JWTToken; }

    public String getRollNumber() { return rollNumber; }

    public void setRollNumber(String rollNumber) { this.rollNumber = rollNumber; }
}
