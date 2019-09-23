package spider.app.sportsfete19;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

//import com.tomlonghurst.expandablehinttext.ExpandableHintText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import spider.app.sportsfete19.API.LoginData;
import spider.app.sportsfete19.API.LoginInterface;
import spider.app.sportsfete19.API.LoginResponse;
import spider.app.sportsfete19.API.UserDetails;

public class LoginActivity extends Activity {

    //login page

    //private ExpandableHintText rollNo,password;
    private EditText rollNo,password;
    private MyDatabase myDatabase = new MyDatabase(this);
    private LoginResponse loginResponse;
    private LoginInterface loginInterface;
    private boolean clicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_temp);
        rollNo = findViewById(R.id.rollNoEditText);
        password = findViewById(R.id.passwordEditText);
    }

    private boolean entriesGiven(){
        if(rollNo.getText().toString().length()==0) {
            Toast.makeText(this, "Roll Number Required.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(password.getText().toString().length()==0){
            Toast.makeText(this, "Password Required.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public void signIn(View view) {
        if(!clicked){
            clicked = true;
            Toast.makeText(this, "Signing in", Toast.LENGTH_SHORT).show();
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
                                Log.i("jwt", loginResponse.getToken());
                                myDatabase.insert(userDetails);
                                Toast.makeText(LoginActivity.this, "Logged in!", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(i);
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), "Error logging in, Please enter correct Roll number and Password", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Error logging in, Please enter correct Roll number and Password", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Error logging in, Please check your Internet Connection", Toast.LENGTH_SHORT).show();
                        Log.d("login failure", t.getMessage());
                    }
                });
            }
        }

    }
}
