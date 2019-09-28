package org.spider.sportsfete.API;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TeamSheet {

    @SerializedName("team1Names")
    @Expose
    private List<String> team1Names = null;
    @SerializedName("team2Names")
    @Expose
    private List<String> team2Names = null;

    public List<String> getTeam1Names() {
        return team1Names;
    }

    public void setTeam1Names(List<String> team1Names) {
        this.team1Names = team1Names;
    }

    public List<String> getTeam2Names() {
        return team2Names;
    }

    public void setTeam2Names(List<String> team2Names) {
        this.team2Names = team2Names;
    }

}
