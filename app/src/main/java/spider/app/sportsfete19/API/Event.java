package spider.app.sportsfete19.API;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;

/**
 * Created by srikanth on 21/1/17.
 */
@DatabaseTable(tableName = "EventsTable")
public class Event {
    @DatabaseField(generatedId = true)
    long localId;
    @DatabaseField
    @SerializedName("id")
    @Expose
    private Integer id;
    @DatabaseField
    @SerializedName("day")
    @Expose
    private String day;
    @DatabaseField
    @SerializedName("name")
    @Expose
    private String name;
    @DatabaseField
    @SerializedName("venue")
    @Expose
    private String venue;
    @DatabaseField
    @SerializedName("start_time")
    @Expose
    private String startTime;
    @DatabaseField
    @SerializedName("round")
    @Expose
    private String round;
    @DatabaseField
    @SerializedName("status")
    @Expose
    private String status;
    @DatabaseField
    @SerializedName("winner")
    @Expose
    private String winner;
    @DatabaseField
    @SerializedName("teama")
    @Expose
    private String teamA;
    @DatabaseField
    @SerializedName("teamb")
    @Expose
    private String teamB;
    @DatabaseField
    @SerializedName("fixture")
    @Expose
    private String fixture;
    @DatabaseField(dataType = DataType.SERIALIZABLE)
    @SerializedName("participants")
    @Expose
    private ArrayList<String> participants = null;
    @DatabaseField
    @SerializedName("department")
    @Expose
    private String department;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public ArrayList<String> getParticipants() {
        return participants;
    }

    public void setParticipants(ArrayList<String> participants) {
        this.participants = participants;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getLocalId() {
        return localId;
    }

    public String getRound() {
        return round;
    }

    public void setRound(String round) {
        this.round = round;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public String getTeamA() {
        return teamA;
    }

    public void setTeamA(String teamA) {
        this.teamA = teamA;
    }

    public String getTeamB() {
        return teamB;
    }

    public void setTeamB(String teamB) {
        this.teamB = teamB;
    }

    public String getFixture() {
        return fixture;
    }

    public void setFixture(String fixture) {
        this.fixture = fixture;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setLocalId(long localId) {
        this.localId = localId;
    }

}
