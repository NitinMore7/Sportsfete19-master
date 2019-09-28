package org.spider.sportsfete.API;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchByNamePOJO {
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("rollno")
    @Expose
    private String rollno;

    public String getUserName(){
        return name;
    }

    public String getRollno(){
        return rollno;
    }
}
