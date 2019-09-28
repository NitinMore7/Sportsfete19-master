package org.spider.sportsfete.API;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginData {
    @SerializedName("rollno")
    @Expose
    private String roll_number;
    @SerializedName("password")
    @Expose
    private String password;

    public LoginData(String roll_number, String password) {
        this.roll_number = roll_number;
        this.password = password;
    }

    public String getRollNumber() {
        return roll_number;
    }

    public void setRollNumber(String roll_number) {
        this.roll_number = roll_number;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
