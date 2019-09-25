package spider.app.sportsfete19.API.SearchUserByRollNo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Winner {

    @SerializedName("sport_name")
    @Expose
    private String sportName;
    @SerializedName("position")
    @Expose
    private Integer position;

    public String getSportName() {
        return sportName;
    }

    public void setSportName(String sportName) {
        this.sportName = sportName;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

}