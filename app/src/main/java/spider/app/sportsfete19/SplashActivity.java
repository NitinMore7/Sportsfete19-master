package spider.app.sportsfete19;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import spider.app.sportsfete19.Marathon.MarathonRegistration;


public class SplashActivity extends AppCompatActivity {
    private MyDatabase myDatabase = new MyDatabase(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(myDatabase.getDifferentItemsCount()>0){
                    Intent i = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }
                else {
                    //this is login activity
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    finish();
                }
            }
        },1000);

    }

    @Override
    public void onDestroy(){

        Runtime.getRuntime().gc();
        super.onDestroy();
    }
}
