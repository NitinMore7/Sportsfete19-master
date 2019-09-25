package spider.app.sportsfete19.API.SearchUserByRollNo;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Achievements {

    @SerializedName("winners")
    @Expose
    private List<Winner> winners;
    @SerializedName("mvps")
    @Expose
    private List<Mvp> mvps = null;

    public String getWinners() {
        String info = "";
        int i =1;
        for (Winner winner : winners){
            info += i + ". Sport Name : " + winner.getSportName()  + "\n    " + "Position : " + winner.getPosition() + "\n\n";
        }

        return info;
    }



    public String getMvps() {
        String info = "";
        int i =1;
        for (Mvp mvp : mvps){
            info += i + ".  Match : " + mvp.getDescription()  + "\n    " + "Sport Name : " + mvp.getSportName() + "\n\n";
        }

        return info;
    }



}