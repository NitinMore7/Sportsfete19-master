package org.spider.sportsfete;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.rengwuxian.materialedittext.MaterialEditText;

import org.spider.sportsfete.API.LoginData;
import org.spider.sportsfete.API.LoginInterface;
import org.spider.sportsfete.API.LoginResponse;
import org.spider.sportsfete.API.UserDetails;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import org.spider.sportsfete.R;
import tyrantgit.explosionfield.ExplosionField;

public class LoginActivity extends Activity {

    //login page

    //private ExpandableHintText rollNo,password;
    private MaterialEditText rollNo,password;
    private ExplosionField mExplosionField;
    private MyDatabase myDatabase = new MyDatabase(this);
    private LoginResponse loginResponse;
    private LoginInterface loginInterface;
    private ImageView logo,spiderlogo;
    private boolean clicked = false;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        rollNo = findViewById(R.id.rollNoEditText);
        password = findViewById(R.id.passwordEditText);
        logo = findViewById(R.id.logo);
        mExplosionField = ExplosionField.attach2Window(this);
        spiderlogo = findViewById(R.id.spiderlogo);
        progressBar = findViewById(R.id.progressBar_cyclic);
        logo.setClickable(true);
        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addListener(findViewById(R.id.root));
            }
        });
    }

    private void addListener(View root) {
        if (root instanceof ViewGroup) {
            ViewGroup parent = (ViewGroup) root;
            for (int i = 0; i < parent.getChildCount(); i++) {
                addListener(parent.getChildAt(i));
            }
        } else {
            root.setClickable(true);
            root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mExplosionField.explode(v);
                    if (v != null) {
                        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    }
                    if(v.getId()==R.id.passwordEditText||v.getId()==R.id.rollNoEditText){
                        v.setVisibility(View.GONE);
                    }
                    v.setOnClickListener(null);
                }
            });
        }
        findViewById(R.id.cv1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mExplosionField.explode(view);
            }
        });
        findViewById(R.id.cv2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mExplosionField.explode(view);
            }
        });
        spiderlogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });
    }

    private boolean entriesGiven(){
        if(rollNo.getText().toString().length()==0) {
            Toast.makeText(this, "Roll Number Required.", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.INVISIBLE);
            return false;
        }
        if(password.getText().toString().length()==0){
            Toast.makeText(this, "Password Required.", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.INVISIBLE);
            return false;
        }
        return true;
    }

    public void signIn(View view) {
        if(!clicked){
            clicked = true;
            //Toast.makeText(this, "Signing in", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.VISIBLE);
            if(entriesGiven()){
                Retrofit retrofit = new Retrofit.Builder().baseUrl("https://spider.nitt.edu/")
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                loginInterface = retrofit.create(LoginInterface.class);
                Call<LoginResponse> mCall = loginInterface.requestLogin(new LoginData(rollNo.getText().toString(), password.getText().toString()));
                mCall.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (response.body() != null) {
                            loginResponse = response.body();
                            if (loginResponse.getSuccess()) {
                                UserDetails userDetails = new UserDetails();
                                userDetails.setLoginStatus("TRUE");
                                userDetails.setToken(loginResponse.getToken());
                                userDetails.setRollNumber(rollNo.getText().toString());
//                                Log.i("jwt", loginResponse.getToken());
                                myDatabase.insert(userDetails);
                                //Toast.makeText(LoginActivity.this, "Logged in!", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(i);
                                finish();
                            } else {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(getApplicationContext(), "Error logging in, Please enter correct Roll number and Password", Toast.LENGTH_SHORT).show();
                                clicked=false;
                            }
                        }
                        else {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(getApplicationContext(), "Error logging in, Please enter correct Roll number and Password", Toast.LENGTH_SHORT).show();
                            clicked=false;
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), "Error logging in, Please check your Internet Connection", Toast.LENGTH_SHORT).show();
                        Log.d("login failure", t.getMessage());
                        clicked=false;
                    }
                });
            }
        }
    }
}
