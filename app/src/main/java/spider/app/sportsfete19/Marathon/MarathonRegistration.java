package spider.app.sportsfete19.Marathon;

import android.app.ProgressDialog;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import spider.app.sportsfete19.API.ApiInterface;
import spider.app.sportsfete19.R;

/**
 * Edited by Vaishnavi on 8/29/2018.
 */

public class MarathonRegistration extends android.support.v4.app.Fragment {

    EditText reg_user_name;
    EditText reg_user_rollno;
    EditText user_password;
    Spinner department_spinner;
    Button male,female;
    int selected_gender;
    Button register;
    ViewGroup viewGroup;
   /* RadioGroup radioGroup;
    RadioButton radio_male;
    RadioButton radio_female;*/

    int selectedPosition=0;

    String[] dept = {
            "Select Department",
            "CSE",
            "ECE",
            "Eee",
            "Mech",
            "Prod",
            "Ice",
            "Chem",
            "Civil",
            "Meta",
            "Archi",
            "PhD_MSC_MS",
            "DoMS",
            "CA",
            "M_tech"};

    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewGroup = container;

        final View view = inflater.inflate(R.layout.marathon_regis_layout, container, false);
        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                //r will be populated with the coordinates of your view that area still visible.
                view.getWindowVisibleDisplayFrame(r);

                int heightDiff = view.getRootView().getHeight() - (r.bottom - r.top);
                Log.d("height",""+heightDiff);
                if (heightDiff > 300) { // if more than 100 pixels, its probably a keyboard...

                }else{
                    Log.d("close","");
                    reg_user_name.clearFocus();
                    reg_user_rollno.clearFocus();
                    user_password.clearFocus();
                }
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://sportsfete-732bf.firebaseapp.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        progressDialog  = new ProgressDialog(getActivity());
        progressDialog.setMessage("Registering user");

        reg_user_name = (EditText) getActivity().findViewById(R.id.user_name);
        user_password = (EditText) getActivity().findViewById(R.id.user_password);
        reg_user_rollno = (EditText) getActivity().findViewById(R.id.user_rollno);
      //  department_spinner = (Spinner) getActivity().findViewById(R.id.department);
        register = (Button) getActivity().findViewById(R.id.register);
/*        radioGroup = (RadioGroup) getActivity().findViewById(R.id.radio_group);
        radio_female = (RadioButton) getActivity().findViewById(R.id.female_radio);
        radio_male = (RadioButton) getActivity().findViewById(R.id.male_radio);*/
/*

        radio_female.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),  "fonts/HammersmithOneRegular.ttf"));
        radio_male.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),  "fonts/HammersmithOneRegular.ttf"));
        reg_user_rollno.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),  "fonts/HammersmithOneRegular.ttf"));
        reg_user_name.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),  "fonts/HammersmithOneRegular.ttf"));
        register.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),  "fonts/HammersmithOneRegular.ttf"));
        user_password.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),  "fonts/HammersmithOneRegular.ttf"));
*/

       /* CustomAdapter adapter = new CustomAdapter(getActivity(),R.layout.dept_spinner_element,dept);
        department_spinner.setAdapter(adapter);*/

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reg_user_name.clearFocus();
                reg_user_rollno.clearFocus();
                user_password.clearFocus();
                if(validate()){
                    progressDialog.show();
                    HashMap<String, String> map = new HashMap<>();
                    map.put("roll",reg_user_rollno.getText().toString().trim());
                    map.put("name",reg_user_name.getText().toString().trim());
                    //NOTE: Department field is unused.
                    //map.put("dept",dept[selectedPosition].toUpperCase());
                    map.put("password",user_password.getText().toString().trim());

                    if(selected_gender==1){
                        map.put("gender","M");
                    }
                    if(selected_gender==2){
                        map.put("gender","F");
                    }

                    apiInterface.registerUserForMarathon(map).enqueue(new Callback<JsonObject>() {
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                            progressDialog.dismiss();
                            if(response.isSuccessful()){
                                Log.e("response",response.body().toString());
                                if(response.body().get("response code").getAsInt()==1){
                                    Snackbar.make(viewGroup,"Registered successfully-:)", Snackbar.LENGTH_SHORT).show();
                                }
                                if(response.body().get("response code").getAsInt()==-1){
                                    Snackbar.make(viewGroup,"User already registered!!", Snackbar.LENGTH_SHORT).show();
                                }
                                if(response.body().get("response code").getAsInt()==-2){
                                    Snackbar.make(viewGroup,"Invalid Roll Number or password!!", Snackbar.LENGTH_SHORT).show();
                                }
                                if(response.body().get("response code").getAsInt()==-3){
                                    Snackbar.make(viewGroup,"Missing/Wrong input data!!", Snackbar.LENGTH_SHORT).show();
                                }
                                if(response.body().get("response code").getAsInt()==-4){
                                    Snackbar.make(viewGroup,"Internal Server Error!!", Snackbar.LENGTH_SHORT).show();
                                }
                                if(response.body().get("response code").getAsInt()==2){
                                    Snackbar.make(viewGroup,"Marathon Registration is Closed-:(", Snackbar.LENGTH_SHORT).show();
                                }
                            }else
                                try {
                                    String error = response.errorBody().string();
                                    Log.e("error response",error+"");
                                    Snackbar.make(viewGroup,"Internal Server Error!!", Snackbar.LENGTH_SHORT).show();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                        }

                        @Override
                        public void onFailure(Call<JsonObject> call, Throwable t) {
                            progressDialog.dismiss();
                            t.printStackTrace();
                        }
                    });
                }
            }
        });

        selected_gender=0;
        Spinner spinner = (Spinner) getActivity().findViewById(R.id.dept_spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.departments_array, R.layout.spinner_layout);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(R.layout.spinner_droplayout);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedPosition = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                reg_user_name.clearFocus();
                reg_user_rollno.clearFocus();
                user_password.clearFocus();
                return false;
            }
        });

        male=(Button)getActivity().findViewById(R.id.male);
        female=(Button)getActivity().findViewById(R.id.female);


        male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                reg_user_name.clearFocus();
                reg_user_rollno.clearFocus();
                user_password.clearFocus();

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    male.setBackground(getActivity().getDrawable(R.drawable.gender_s));
                }
                if (selected_gender == 2) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        female.setBackground(getActivity().getDrawable(R.drawable.gender));
                    }
                }
                selected_gender = 1;
            }
        });

        female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                reg_user_name.clearFocus();
                reg_user_rollno.clearFocus();
                user_password.clearFocus();

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    female.setBackground(getActivity().getDrawable(R.drawable.gender_s));
                }

                if (selected_gender == 1) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        male.setBackground(getActivity().getDrawable(R.drawable.gender));
                    }

                }
                selected_gender=2;
            }
        });

    /*    radio_male.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                reg_user_name.clearFocus();
                reg_user_rollno.clearFocus();
                user_password.clearFocus();
                return false;
            }
        });

        radio_female.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                reg_user_name.clearFocus();
                reg_user_rollno.clearFocus();
                user_password.clearFocus();
                return false;
            }
        });

*/
        reg_user_rollno.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                Log.d("focus",""+b);
                if(b){
                    Animation anim = new ScaleAnimation(
                            1f, 1.07f, // Start and end values for the X axis scaling
                            1f, 1.1f, // Start and end values for the Y axis scaling
                            Animation.RELATIVE_TO_SELF, 0.5f, // Pivot point of X scaling
                            Animation.RELATIVE_TO_SELF, 0.5f); // Pivot point of Y scaling
                    anim.setFillAfter(true); // Needed to keep the result of the animation
                    anim.setDuration(200);
                    reg_user_rollno.startAnimation(anim);
                }else{
                    Animation anim = new ScaleAnimation(
                            1.07f, 1f, // Start and end values for the X axis scaling
                            1.1f, 1f, // Start and end values for the Y axis scaling
                            Animation.RELATIVE_TO_SELF, 0.5f, // Pivot point of X scaling
                            Animation.RELATIVE_TO_SELF, 0.5f); // Pivot point of Y scaling
                    anim.setFillAfter(true); // Needed to keep the result of the animation
                    anim.setDuration(200);
                    reg_user_rollno.startAnimation(anim);
                }
            }
        });

        user_password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){
                    Animation anim = new ScaleAnimation(
                            1f, 1.07f, // Start and end values for the X axis scaling
                            1f, 1.1f, // Start and end values for the Y axis scaling
                            Animation.RELATIVE_TO_SELF, 0.5f, // Pivot point of X scaling
                            Animation.RELATIVE_TO_SELF, 0.5f); // Pivot point of Y scaling
                    anim.setFillAfter(true); // Needed to keep the result of the animation
                    anim.setDuration(200);
                    user_password.startAnimation(anim);
                }else{
                    Animation anim = new ScaleAnimation(
                            1.07f, 1f, // Start and end values for the X axis scaling
                            1.1f, 1f, // Start and end values for the Y axis scaling
                            Animation.RELATIVE_TO_SELF, 0.5f, // Pivot point of X scaling
                            Animation.RELATIVE_TO_SELF, 0.5f); // Pivot point of Y scaling
                    anim.setFillAfter(true); // Needed to keep the result of the animation
                    anim.setDuration(200);
                    user_password.startAnimation(anim);
                }
            }
        });

        reg_user_name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){
                    Animation anim = new ScaleAnimation(
                            1f, 1.07f, // Start and end values for the X axis scaling
                            1f, 1.1f, // Start and end values for the Y axis scaling
                            Animation.RELATIVE_TO_SELF, 0.5f, // Pivot point of X scaling
                            Animation.RELATIVE_TO_SELF, 0.5f); // Pivot point of Y scaling
                    anim.setFillAfter(true); // Needed to keep the result of the animation
                    anim.setDuration(200);
                    reg_user_name.startAnimation(anim);
                }else{
                    Animation anim = new ScaleAnimation(
                            1.07f, 1f, // Start and end values for the X axis scaling
                            1.1f, 1f, // Start and end values for the Y axis scaling
                            Animation.RELATIVE_TO_SELF, 0.5f, // Pivot point of X scaling
                            Animation.RELATIVE_TO_SELF, 0.5f); // Pivot point of Y scaling
                    anim.setFillAfter(true); // Needed to keep the result of the animation
                    anim.setDuration(200);
                    reg_user_name.startAnimation(anim);
                }
            }
        });

    }

    public boolean validate(){
        if(reg_user_rollno.getText().toString().length()!=9){
            reg_user_rollno.setError(null);
            reg_user_rollno.setError("Enter 9 digit Roll No");
            return false;
        }

        if(reg_user_name.getText().toString().isEmpty()){
            reg_user_name.setError(null);
            reg_user_rollno.setError(null);
            reg_user_name.setError("Enter your Name");
            return false;
        }

        //NOTE: Department is unused
//        if(selectedPosition == 0){
//            Snackbar.make(viewGroup,"Select department", Snackbar.LENGTH_SHORT).show();
//            reg_user_rollno.setError(null);
//            reg_user_name.setError(null);
//            return false;
//        }

        if(selected_gender==0){
            Snackbar.make(viewGroup,"Choose Gender!!", Snackbar.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    @Override
    public void onDestroyView(){
        Runtime.getRuntime().gc();
        super.onDestroyView();
    }
}
