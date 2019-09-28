package org.spider.sportsfete;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.spider.sportsfete.API.ApiInterface;
import org.spider.sportsfete.API.SearchUserByRollNo.SearchByRollNoPOJO;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import org.spider.sportsfete.R;

public class UserProfile extends AppCompatActivity {
    Call<SearchByRollNoPOJO> call;
    ApiInterface apiInterface;
    private static final String Key = "rollno";
    private TextView name, rollno, winnerText, mvpText;
    private CircleImageView imageView;
    private String query;
    private RelativeLayout relLayout;

    int[] dept_icon = {
            R.drawable.arch1,
            R.drawable.chem1,
            R.drawable.civil2,
            R.drawable.cse2,
            R.drawable.doms2,
            R.drawable.ece2,
            R.drawable.eee2,
            R.drawable.ice2,
            R.drawable.mca2,
            R.drawable.mech2,
            R.drawable.meta2,
            R.drawable.mtech2,
            R.drawable.phd2,
            R.drawable.prod2
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        Intent intent = getIntent();
        query = intent.getStringExtra(Key);

        name = findViewById(R.id.name);
        rollno = findViewById(R.id.rollno);
        winnerText = findViewById(R.id.winnerText);
        mvpText = findViewById(R.id.mvpText);
        imageView = findViewById(R.id.imgView);
        relLayout = findViewById(R.id.relLayout);


        getDetails(query);



    }

    private void getDetails(String roll) {
        Snackbar.make(relLayout,"Finding details...", Snackbar.LENGTH_SHORT).show();
        apiInterface = ApiInterface.retrofit.create(ApiInterface.class);
        call = apiInterface.getUserDetails(roll);
        call.enqueue(new Callback<SearchByRollNoPOJO>() {
            @Override
            public void onResponse(Call<SearchByRollNoPOJO> call, Response<SearchByRollNoPOJO> response) {
                Log.e("TAG", "pass");
                SearchByRollNoPOJO object = response.body();
                Log.e("TAG", object.getName());
                name.setText(object.getName());
                rollno.setText(query);
                Log.e("TAG", object.getDept());
                String dept = object.getDept();
                switch(dept){
                    case "ARCHI":
                        imageView.setImageResource(dept_icon[0]);
                        imageView.setFillColor(Color.WHITE);
                        break;
                    case "CHEM":
                        imageView.setImageResource(dept_icon[1]);
                        imageView.setFillColor(Color.WHITE);
                        break;
                    case "CIVIL":
                        imageView.setImageResource(dept_icon[2]);
                        imageView.setFillColor(Color.WHITE);
                        break;
                    case "CSE":imageView.setImageResource((dept_icon[3]));
                        imageView.setFillColor(Color.parseColor("#16282a"));
                        break;
                    case "DOMS":
                        imageView.setImageResource(dept_icon[4]);
                        imageView.setFillColor(Color.WHITE);
                        break;
                    case "ECE":
                        imageView.setImageResource(dept_icon[5]);
                        imageView.setFillColor(Color.WHITE);
                        break;
                    case "EEE":
                        imageView.setImageResource(dept_icon[6]);
                        imageView.setFillColor(Color.WHITE);
                        break;
                    case "ICE":
                        imageView.setImageResource(dept_icon[7]);
                        imageView.setFillColor(Color.parseColor("#16282a"));
                        break;
                    case "CA":
                        imageView.setImageResource(dept_icon[8]);
                        imageView.setFillColor(Color.WHITE);
                        break;
                    case "MECH":
                        imageView.setImageResource(dept_icon[9]);
                        imageView.setFillColor(Color.WHITE);
                        break;
                    case "META":

                        imageView.setImageResource(dept_icon[10]);
                        imageView.setFillColor(Color.WHITE);
                        break;
                    case "M_TECH":
                        imageView.setImageResource(dept_icon[11]);
                        imageView.setFillColor(Color.WHITE);
                        break;
                    case "PHD_MSC_MS":
                        imageView.setImageResource(dept_icon[12]);
                        imageView.setFillColor(Color.WHITE);
                        break;
                    case "PROD":
                        imageView.setImageResource(dept_icon[13]);
                        imageView.setFillColor(Color.WHITE);
                        break;
                }
//                    Log.e("TAG", object.getAchievements().getWinners().get(0).getSportName());
//                    Log.e("TAG",object.getAchievements().getWinners().get(0).getPosition().toString());
//                    String str = object.getAchievements().getWinners().get(0).getSportName() + " : " + object.getAchievements().getWinners().get(0).getPosition().toString();
                String[] achievement = new String[2];
                achievement = object.getAchievements();
                if (achievement[0].equals("")) {
                    winnerText.setText("No Data Available");
                    winnerText.setTypeface(null, Typeface.ITALIC);
                } else {
                    winnerText.setText(achievement[0]);
                }

                if (achievement[1].equals("")) {
                    mvpText.setText("No Data Available");
                    mvpText.setTypeface(null, Typeface.ITALIC);
                } else {
                    mvpText.setText(achievement[1]);
                }

//                    Log.e("TAG",object.getAchievements().getMvps().get(0).getDescription());
//                    Log.e("TAG",object.getAchievements().getMvps().get(0).getSportName());
//                    str = object.getAchievements().getMvps().get(0).getDescription() + " : " + object.getAchievements().getMvps().get(0).getSportName();
            }

            @Override
            public void onFailure(Call<SearchByRollNoPOJO> call, Throwable t) {
                Log.e("TAG", "fail");
            }
        });

    }


}