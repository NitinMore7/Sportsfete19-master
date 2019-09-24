package spider.app.sportsfete19;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.widget.VideoView;

public class HomeScreen extends AppCompatActivity {

    VideoView videoview;
    private MyDatabase myDatabase = new MyDatabase(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        setContentView(R.layout.activity_splash);

        videoview = (VideoView) findViewById(R.id.videoview);
    }


    @Override
    public void onPause(){
        super.onPause();
        if(videoview.isPlaying())
        videoview.pause();
    }

    @Override
    public void onResume(){
        super.onResume();

        Uri uri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.splash);
        videoview.setVideoURI(uri);
        videoview.setZOrderOnTop(true);
        videoview.start();
        Log.d("durtion",""+videoview.getDuration());

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(myDatabase.getDifferentItemsCount()>0){
                    Intent i = new Intent(HomeScreen.this, LoginActivity.class);
                    //CHANGE HERE TO MAINACTIVITY
                    startActivity(i);
                    finish();
                }
                else {
                    //this is login activity
                    startActivity(new Intent(HomeScreen.this, LoginActivity.class));
                    finish();
                }
            }
        },3000);

    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Runtime.getRuntime().gc();
    }
}