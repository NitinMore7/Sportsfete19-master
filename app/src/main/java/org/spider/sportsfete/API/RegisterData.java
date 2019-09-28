package org.spider.sportsfete.API;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegisterData {
    @SerializedName("gender")
    @Expose
    private String gender;

    public RegisterData(String gender) {
        this.gender = gender;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String roll_number) {
        this.gender = gender;
    }
}
