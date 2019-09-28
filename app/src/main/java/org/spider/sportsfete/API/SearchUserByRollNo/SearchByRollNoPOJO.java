package org.spider.sportsfete.API.SearchUserByRollNo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchByRollNoPOJO {

    @SerializedName("dept")
    @Expose
    private String dept;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("achievements")
    @Expose
    private Achievements achievements;

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getAchievements() {
        String[] arr= new String[2];
        String info1 = "";
        String info2 = "";

        info1 +=  achievements.getWinners();
        info2 +=  achievements.getMvps();

        arr[0] = info1;
        arr[1] = info2;
        return arr;
    }

//    public void setAchievements(Achievements achievements) {
//        this.achievements = achievements;
//    }

}