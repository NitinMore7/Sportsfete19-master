package spider.app.sportsfete19.API;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MvpLeaderBoard {
    @SerializedName("name")
    @Expose
    private String name;
@SerializedName("mvp_score")
@Expose
private String mvpScore;
public String getName()
{ return name;}
public String getMvpScore()
{return mvpScore;}
}
