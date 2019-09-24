package spider.app.sportsfete19.API.SearchUserByRollNo;

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

    public Achievements getAchievements() {
        return achievements;
    }

    public void setAchievements(Achievements achievements) {
        this.achievements = achievements;
    }

}