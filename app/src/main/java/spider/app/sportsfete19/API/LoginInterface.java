package spider.app.sportsfete19.API;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import spider.app.sportsfete19.predict;

public interface LoginInterface {

    @POST("~praveen/auth_jwt.php")
    Call<LoginResponse> requestLogin(@Body LoginData body);

    @Headers("Content-Type: application/json")
    @POST("votepredictor")
    Call<String> votepredictor(@Header("Authorization") String jwt, @Body predict body);

}
