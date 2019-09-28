package org.spider.sportsfete.API.SearchUserByRollNo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Mvp {

    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("sport_name")
    @Expose
    private String sportName;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSportName() {
        return sportName;
    }

    public void setSportName(String sportName) {
        this.sportName = sportName;
    }

}