package spider.app.sportsfete19.API;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;

/**
 * Created by AVINASH on 2/24/2018.
 */
@DatabaseTable(tableName = "StatusEventsDetailTable")
public class StatusEventDetailsPOJO {
    @DatabaseField(generatedId = true)
    long localId;
    @DatabaseField
    @SerializedName("id")
    @Expose
    private String id;
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
    private String dept1Score;
    @DatabaseField
    @SerializedName("dept2")
    @Expose
    private String dept2;
    @DatabaseField
    @SerializedName("dept2_score")
    @Expose
    private String dept2Score;
    @DatabaseField
    @SerializedName("elimination_type")
    @Expose
    private String eliminationType;
    @DatabaseField
    @SerializedName("fixture")
    @Expose
    private String fixture;
    @DatabaseField
    @SerializedName("fixture_index")
    @Expose
    private Integer fixtureIndex;
    @DatabaseField
    @SerializedName("fixture_type")
    @Expose
    private Integer fixtureType;
    @DatabaseField
    @SerializedName("group")
    @Expose
    private String group;
    @DatabaseField
    @SerializedName("hint")
    @Expose
    private String hint;
  /*  @DatabaseField
    @SerializedName("loser_dept_next")
    @Expose
    private String loserDeptNext;
    @DatabaseField
    @SerializedName("loser_next_match")
    @Expose
    private String loserNextMatch;
  */  @DatabaseField
    @SerializedName("name")
    @Expose
    private String name;
    @DatabaseField(dataType = DataType.SERIALIZABLE)
    @SerializedName("participating_teams")
    @Expose
    private ArrayList<String> participatingTeams = null;
    @DatabaseField
    @SerializedName("round")
    @Expose
    private String round;
    @DatabaseField
    @SerializedName("start_time")
    @Expose
    private Long startTime;
    @DatabaseField
    @SerializedName("status")
    @Expose
    private String status;
    @DatabaseField
    @SerializedName("venue")
    @Expose
    private String venue;
    @DatabaseField
    @SerializedName("winner")
    @Expose
    private String winner;
 /*   @DatabaseField
    @SerializedName("winner_dept_next")
    @Expose
    private String winnerDeptNext;
    @DatabaseField
    @SerializedName("winner_next_match")
    @Expose
    private String winnerNextMatch;
*/
    public String getId(){ return id; }

    public void setId(String id){  this.id = id; }

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

    public String getDept1Score() {
        return dept1Score;
    }

    public void setDept1Score(String dept1Score) {
        this.dept1Score = dept1Score;
    }

    public String getDept2() {
        return dept2;
    }

    public void setDept2(String dept2) {
        this.dept2 = dept2;
    }

    public String getDept2Score() {
        return dept2Score;
    }

    public void setDept2Score(String dept2Score) {
        this.dept2Score = dept2Score;
    }

    public String getEliminationType() {
        return eliminationType;
    }

    public void setEliminationType(String eliminationType) {
        this.eliminationType = eliminationType;
    }

    public String getFixture() {
        return fixture;
    }

    public void setFixture(String fixture) {
        this.fixture = fixture;
    }

    public Integer getFixtureIndex() {
        return fixtureIndex;
    }

    public void setFixtureIndex(Integer fixtureIndex) {
        this.fixtureIndex = fixtureIndex;
    }

    public Integer getFixtureType() {
        return fixtureType;
    }

    public void setFixtureType(Integer fixtureType) {
        this.fixtureType = fixtureType;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }
/*
    public String getLoserDeptNext() {
        return loserDeptNext;
    }

    public void setLoserDeptNext(String loserDeptNext) {
        this.loserDeptNext = loserDeptNext;
    }

    public String getLoserNextMatch() {
        return loserNextMatch;
    }

    public void setLoserNextMatch(String loserNextMatch) {
        this.loserNextMatch = loserNextMatch;
    }
*/
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getParticipatingTeams() {
        return participatingTeams;
    }

    public void setParticipatingTeams(ArrayList<String> participatingTeams) {
        this.participatingTeams = participatingTeams;
    }

    public String getRound() {
        return round;
    }

    public void setRound(String round) {
        this.round = round;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }
/*
    public String getWinnerDeptNext() {
        return winnerDeptNext;
    }

    public void setWinnerDeptNext(String winnerDeptNext) {
        this.winnerDeptNext = winnerDeptNext;
    }

    public String getWinnerNextMatch() {
        return winnerNextMatch;
    }

    public void setWinnerNextMatch(String winnerNextMatch) {
        this.winnerNextMatch = winnerNextMatch;
    }
*/}
