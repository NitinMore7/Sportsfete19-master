package spider.app.sportsfete19.API;

/**
 * Created by Ayush on 26/9/19.
 */

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import spider.app.sportsfete19.API.SearchUserByRollNo.SearchByRollNoPOJO;

public interface ApiInterface {

    Gson gson = new GsonBuilder()
            .setLenient()
            .create();

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://us-central1-sportsfete19-f7729.cloudfunctions.net")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    @GET("/leaderboard")
    Call<List<Leaderboard>> getLeaderBoard();
    @GET("/day_events")
    Call<List<EventDetailsPOJO>> getSchedule2(@Query("day") int day);
    @GET("/status_events")
    Call<List<StatusEventDetailsPOJO>> getEventByStatus(@Query("status") String status);
    @FormUrlEncoded
    @POST("/marathon_register")
    Call<JsonObject> registerUserForMarathon(@FieldMap Map<String, String> params);
    @GET("/return_marathon_count")
    Call<Integer> getCount();
    @GET("/fixture")
    Call<List<FixturePOJO>> getfixture(@Query("sport") String sport);
    @GET("/mvpleader")
    Call<List<MvpLeaderBoard>> getMvpLeaderBoard();
    @GET("/positions")
    Call<List<String>> getStanding(@Query("sport") String sport);
    @GET("/positions")
    Call<List<List<String>>> getIndividualStanding(@Query("sport") String sport);

    @GET("/fetch_user")
    Call<SearchByRollNoPOJO> getUserDetails(@Query("roll") String roll_no);

    @GET("/autocomplete")
    Call<List<SearchByNamePOJO>> getRollNo(@Query("name") String name);

    @GET("/team_sheet_display")
    Call<TeamSheet> getteamsheet(@Query("event")String event);

    @GET("/initial_teams")
    Call<List<initialTeams>> getInitialTeams(@Query ("sport") String sport);
}
