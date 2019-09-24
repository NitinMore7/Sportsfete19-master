package spider.app.sportsfete19.API.SearchUserByRollNo;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Achievements {

    @SerializedName("winners")
    @Expose
    private List<Winner> winners = null;
    @SerializedName("mvps")
    @Expose
    private List<Mvp> mvps = null;

    public List<Winner> getWinners() {
        return winners;
    }

    public void setWinners(List<Winner> winners) {
        this.winners = winners;
    }

    public List<Mvp> getMvps() {
        return mvps;
    }

    public void setMvps(List<Mvp> mvps) {
        this.mvps = mvps;
    }

}