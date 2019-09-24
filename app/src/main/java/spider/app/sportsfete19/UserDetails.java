package spider.app.sportsfete19;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import spider.app.sportsfete19.API.ApiInterface;
import spider.app.sportsfete19.API.SearchUserByRollNo.SearchByRollNoPOJO;

public class UserDetails extends AppCompatActivity {

    Call<SearchByRollNoPOJO> call;
    ApiInterface apiInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);


        apiInterface = ApiInterface.retrofit.create(ApiInterface.class);
        call = apiInterface.getUserDetails("106117063");
        call.enqueue(new Callback<SearchByRollNoPOJO>() {
            @Override
            public void onResponse(Call<SearchByRollNoPOJO> call, Response<SearchByRollNoPOJO> response) {
                Log.e("TAG","pass");
                SearchByRollNoPOJO object = response.body();
                Log.e("TAG",object.getName());
                Log.e("TAG",object.getDept());
                Log.e("TAG", object.getAchievements().getWinners().get(0).getSportName());
                Log.e("TAG",object.getAchievements().getWinners().get(0).getPosition().toString());
                Log.e("TAG",object.getAchievements().getMvps().get(0).getDescription());
                Log.e("TAG",object.getAchievements().getMvps().get(0).getSportName());
            }

            @Override
            public void onFailure(Call<SearchByRollNoPOJO> call, Throwable t) {
                Log.e("TAG","fail");
            }
        });
    }
}
