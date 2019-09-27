package spider.app.sportsfete19.API;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetVotes {
    @GET("get_vote_count")
    Call <String> getvotes(@Query("event") String eventname);



}
