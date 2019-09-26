package spider.app.sportsfete19;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class predict {

    @SerializedName("event")
    @Expose
    private String event;
    @SerializedName("votedteam")
    @Expose
    private String votedteam;

    public predict(String event,String votedteam){
        this.event=event;
        this.votedteam=votedteam;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getVotedteam() {
        return votedteam;
    }

    public void setVotedteam(String votedteam) {
        this.votedteam = votedteam;
    }

}