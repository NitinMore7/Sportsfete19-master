package org.spider.sportsfete.API;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by AVINASH on 3/4/2018.
 */

public class FixturePOJO {

    @SerializedName("dept1")
    @Expose
    private String dept1;
    @SerializedName("dept2")
    @Expose
    private String dept2;
    @SerializedName("winner")
    @Expose
    private String winner;
    @SerializedName("fixture_index")
    @Expose
    private Integer fixtureIndex;
    @SerializedName("fixture")
    @Expose
    private String fixture;
    @SerializedName("status")
    @Expose
    private String status;

    public String getDept1() {
        return dept1;
    }

    public void setDept1(String dept1) {
        this.dept1 = dept1;
    }

    public String getDept2() {
        return dept2;
    }

    public void setDept2(String dept2) {
        this.dept2 = dept2;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public Integer getFixtureIndex() {
        return fixtureIndex;
    }

    public void setFixtureIndex(Integer fixtureIndex) {
        this.fixtureIndex = fixtureIndex;
    }

    public String getFixture() {
        return fixture;
    }

    public void setFixture(String fixture) {
        this.fixture = fixture;
    }

    public String getStatus(){ return status;}

    public void setStatus(String status){ this.status = status; }

}
