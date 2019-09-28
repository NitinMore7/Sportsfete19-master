package org.spider.sportsfete.FireBaseServices;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;

/**
 * Created by praba1110 on 2/2/17.
 */

public class Score {

    @DatabaseField
    @SerializedName("day")
    @Expose
    private Integer day;
    @DatabaseField
    @SerializedName("dept1")
    @Expose
    private String dept1;
    @DatabaseField
    @SerializedName("dept1_score")
    @Expose
    private String dept1_score;
    @DatabaseField
    @SerializedName("dept2")
    @Expose
    private String dept2;
    @DatabaseField
    @SerializedName("dept2_score")
    @Expose
    private String dept2_score;
    @SerializedName("hint")
    @Expose
    private String hint;
    @DatabaseField
    @SerializedName("score_timestamp")
    @Expose
    private Long score_timestamp;
    @DatabaseField
    @SerializedName("start_time")
    @Expose
    private Long start_time;
    @DatabaseField
    @SerializedName("status")
    @Expose
    private String status;

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public String getDept1() {
        return dept1;
    }

    public void setDept1(String dept1) {
        this.dept1 = dept1;
    }

    public String getDept1_score() {
        return dept1_score;
    }

    public void setDept1_score(String dept1_score) {
        this.dept1_score = dept1_score;
    }

    public String getDept2() {
        return dept2;
    }

    public void setDept2(String dept2) {
        this.dept2 = dept2;
    }

    public String getDept2_score() {
        return dept2_score;
    }

    public void setDept2_score(String dept2Score) {
        this.dept2_score = dept2Score;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public Long getScore_timestamp(){ return score_timestamp; }

    public void setScore_timestamp(Long score_timestamp){
        this.score_timestamp = score_timestamp;
    }

    public Long getStart_time() {
        return start_time;
    }

    public void setStart_time(Long startTime) {
        this.start_time = startTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
