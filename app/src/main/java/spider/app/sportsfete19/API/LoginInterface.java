package spider.app.sportsfete19.API;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LoginInterface {

    @POST("~praveen/auth_jwt.php")
    Call<LoginResponse> requestLogin(@Body LoginData body);

}