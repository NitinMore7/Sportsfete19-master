package spider.app.sportsfete19.SportDetails;


import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.ScaleAnimation;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.twotoasters.jazzylistview.JazzyHelper;
import com.twotoasters.jazzylistview.recyclerview.JazzyRecyclerViewScrollListener;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import spider.app.sportsfete19.API.ApiInterface;
import spider.app.sportsfete19.API.Event;
import spider.app.sportsfete19.API.FixturePOJO;
import spider.app.sportsfete19.DatabaseHelper;
import spider.app.sportsfete19.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FixturesFragment extends Fragment {

    List<Event> eventList = new ArrayList<>();
    IndividualEventRecyclerAdapter individualEventRecyclerAdapter;
    JazzyRecyclerViewScrollListener jazzyRecyclerViewScrollListener;
    private int currentTransitionEffect = JazzyHelper.TILT;
    RecyclerView individual_recycler;
    DatabaseHelper helper;
    Context context;
    View view;
    String selectedSport, selectedSportName, selectedLayout = "";
    ArrayList<FixturePOJO> fixtureArrayList;
    List<List<String>> eventsStanding;

    public int fixtureType = 0;
    String[] sportNames;

    Integer[] fixture_type = {
            R.layout.fixture_layout_zero,
            R.layout.fixture_layout_one,
            R.layout.fixture_layout_two,
            R.layout.fixture_layout_three,
            R.layout.fixture_layout_four,
            R.layout.fixture_layout_five,
            R.layout.fixture_layout_six,
            R.layout.fixture_layout_seven,
            R.layout.fixture_layout_eight,
            R.layout.fixture_layout_nine,
            R.layout.fixture_layout_ten,
            R.layout.fixture_layout_eleven
    };

    Integer[] matchId = {
            0,
            R.id.match1,
            R.id.match2,
            R.id.match3,
            R.id.match4,
            R.id.match5,
            R.id.match6,
            R.id.match7,
            R.id.match8,
            R.id.match9,
            R.id.match10,
            R.id.match11,
            R.id.match12,
            R.id.match13,
            R.id.match14,
            R.id.match15,
            R.id.match16,
            R.id.match17,
            R.id.match18,
            R.id.match19,
            R.id.match20,
            R.id.match21,
            R.id.match22,
            R.id.match23,
            R.id.match24,
            R.id.match25,
            R.id.match26
    };

    Integer[] matchTeamsId = {
            0,
            R.id.match1teams,
            R.id.match2teams,
            R.id.match3teams,
            R.id.match4teams,
            R.id.match5teams,
            R.id.match6teams,
            R.id.match7teams,
            R.id.match8teams,
            R.id.match9teams,
            R.id.match10teams,
            R.id.match11teams,
            R.id.match12teams,
            R.id.match13teams,
            R.id.match14teams,
            R.id.match15teams,
            R.id.match16teams,
            R.id.match17teams,
            R.id.match18teams,
            R.id.match19teams,
            R.id.match20teams,
            R.id.match21teams,
            R.id.match22teams,
            R.id.match23teams,
            R.id.match24teams,
            R.id.match25teams,
            R.id.match26teams
    };

    TextView[] match;
    TextView[] matchTeams;

    private LinearLayout linearLayout, post_final_match;
    private HorizontalScrollView horizontalScrollView;
    private float mScale = 1f;
    private float prevScale;
    private ScaleGestureDetector mScaleDetector;
    private GestureDetector gestureDetector;

    private TextView match1teams, match2teams, match3teams, match4teams, match5teams, match6teams, match7teams, match8teams,
            match9teams, match10teams, match11teams, match12teams, match13teams, match14teams;
    private TextView match1, match2, match3, match4, match5, match6, match7, match8, match9, match10, match11, match12, match13, match14;
    private TextView knockout, quarterfinals, semifinals, bronze, finals, finalsII, standings;
    private TextView position1, position2, position3;
    private TextView grpAteam1, grpAteam2, grpAteam3, grpAteam4;
    private TextView grpBteam1, grpBteam2, grpBteam3, grpBteam4;
    private TextView grpCteam1, grpCteam2, grpCteam3, grpCteam4;
    private TextView grpDteam1, grpDteam2, grpDteam3, grpDteam4;
    private View arrow_next_match;
    private LinearLayout  finalsIIelement;

    public FixturesFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        sportNames = getActivity().getResources().getStringArray(R.array.sport_array);
        selectedSport = getActivity().getIntent().getStringExtra("SELECTED_SPORT");
        int selectedSportInt = Integer.parseInt(selectedSport);
        selectedSportName = sportNames[selectedSportInt];

        fixtureType = getActivity().getIntent().getIntExtra("FIXTURE_TYPE", 0);

        Log.d("FIXTURE TYPE", getActivity().getIntent().getIntExtra("FIXTURE_TYPE", 0) + "");
        if ((getActivity().getIntent().getIntExtra("FIXTURE_TYPE", 0)) == 0) {
            return inflater.inflate(R.layout.fixture_layout_zero, container, false);
        }

        return inflater.inflate(fixture_type[getActivity().getIntent().getIntExtra("FIXTURE_TYPE", 0)],
                container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
        context = getContext();

        fixtureArrayList = new ArrayList<>();
        eventsStanding = new ArrayList<>();

        match = new TextView[matchId.length];
        matchTeams = new TextView[matchTeamsId.length];

        position1 = (TextView) getActivity().findViewById(R.id.position1);
        position2 = (TextView) getActivity().findViewById(R.id.position2);
        position3 = (TextView) getActivity().findViewById(R.id.position3);

        position1.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/HammersmithOneRegular.ttf"));
        position2.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/HammersmithOneRegular.ttf"));
        position3.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/HammersmithOneRegular.ttf"));

        knockout = (TextView) getActivity().findViewById(R.id.knockout);
        quarterfinals = (TextView) getActivity().findViewById(R.id.quarterfinals);
        semifinals = (TextView) getActivity().findViewById(R.id.semifinals);
        bronze = (TextView) getActivity().findViewById(R.id.bronze);
        finals = (TextView) getActivity().findViewById(R.id.finals);
        standings = (TextView) getActivity().findViewById(R.id.Standings);

        if (knockout != null) {

            knockout.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/HammersmithOneRegular.ttf"));
            quarterfinals.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/HammersmithOneRegular.ttf"));
            semifinals.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/HammersmithOneRegular.ttf"));
            bronze.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/HammersmithOneRegular.ttf"));
            finals.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/HammersmithOneRegular.ttf"));
            standings.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/HammersmithOneRegular.ttf"));

        }

        if (getActivity().findViewById(R.id.grpAteam1) != null) {

            grpAteam1 = (TextView) getActivity().findViewById(R.id.grpAteam1);
            grpAteam2 = (TextView) getActivity().findViewById(R.id.grpAteam2);
            grpAteam3 = (TextView) getActivity().findViewById(R.id.grpAteam3);

            grpAteam1.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/HammersmithOneRegular.ttf"));
            grpAteam2.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/HammersmithOneRegular.ttf"));
            grpAteam3.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/HammersmithOneRegular.ttf"));

            grpBteam1 = (TextView) getActivity().findViewById(R.id.grpBteam1);
            grpBteam2 = (TextView) getActivity().findViewById(R.id.grpBteam2);
            grpBteam3 = (TextView) getActivity().findViewById(R.id.grpBteam3);

            grpBteam1.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/HammersmithOneRegular.ttf"));
            grpBteam2.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/HammersmithOneRegular.ttf"));
            grpBteam3.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/HammersmithOneRegular.ttf"));

            grpCteam1 = (TextView) getActivity().findViewById(R.id.grpCteam1);
            grpCteam2 = (TextView) getActivity().findViewById(R.id.grpCteam2);
            grpCteam3 = (TextView) getActivity().findViewById(R.id.grpCteam3);
            grpCteam4 = (TextView) getActivity().findViewById(R.id.grpCteam4);

            if (grpCteam1 != null) {

                grpCteam1.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/HammersmithOneRegular.ttf"));
                grpCteam2.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/HammersmithOneRegular.ttf"));
                grpCteam3.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/HammersmithOneRegular.ttf"));

                if (grpCteam4 != null) {
                    grpCteam4.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/HammersmithOneRegular.ttf"));
                }

                grpDteam1 = (TextView) getActivity().findViewById(R.id.grpDteam1);
                grpDteam2 = (TextView) getActivity().findViewById(R.id.grpDteam2);
                grpDteam3 = (TextView) getActivity().findViewById(R.id.grpDteam3);

                grpDteam1.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/HammersmithOneRegular.ttf"));
                grpDteam2.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/HammersmithOneRegular.ttf"));
                grpDteam3.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/HammersmithOneRegular.ttf"));
            }
        }

        for (int i = 0; i < matchId.length; i++) {
            //Log.d("position",""+i);
            match[i] = (TextView) getActivity().findViewById(matchId[i]);
            if (match[i] != null)
                match[i].setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/HammersmithOneRegular.ttf"));
            matchTeams[i] = (TextView) getActivity().findViewById(matchTeamsId[i]);
            if (matchTeams[i] != null)
                matchTeams[i].setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/HammersmithOneRegular.ttf"));
        }

        //for carrom

        if (selectedSportName.equalsIgnoreCase("CARROM")) {
            arrow_next_match = (View) getActivity().findViewById(R.id.hide_next_arrow);
            finalsII = (TextView) getActivity().findViewById(R.id.postfinals);
            finalsIIelement = (LinearLayout) getActivity().findViewById(R.id.postfinal_match);
            post_final_match = (LinearLayout) getActivity().findViewById(R.id.postfinal_match);
        }

        horizontalScrollView = (HorizontalScrollView) getActivity().findViewById(R.id.parent_view);
        linearLayout = (LinearLayout) getActivity().findViewById(R.id.parent_linearLayout);

        mScaleDetector = new ScaleGestureDetector(getActivity(), new ScaleGestureDetector.SimpleOnScaleGestureListener() {
            @Override
            public boolean onScale(ScaleGestureDetector detector) {
                float scale = 1 - detector.getScaleFactor();

                prevScale = mScale;
                mScale += scale;

                if (mScale < 0.1f) // Minimum scale condition:
                    mScale = 0.1f;

                if (mScale > 10f) // Maximum scale condition:
                    mScale = 10f;
                ScaleAnimation scaleAnimation = new ScaleAnimation(1f / prevScale, 1f / mScale, 1f / prevScale, 1f / mScale, detector.getFocusX(), detector.getFocusY());
                scaleAnimation.setDuration(0);
                scaleAnimation.setFillAfter(true);
                linearLayout.startAnimation(scaleAnimation);
                return true;
            }
        });

        SportDetailsActivity.MyOnTouchListener onTouchListener = new SportDetailsActivity.MyOnTouchListener() {
            @Override
            public boolean onTouch(MotionEvent ev) {
                return mScaleDetector.onTouchEvent(ev);
            }
        };

        ((SportDetailsActivity) getActivity()).registerMyOnTouchListener(onTouchListener);

        linearLayout.setOnTouchListener(new TouchListener() {
            @Override
            public void onSingleClick(View v) {

            }

            @Override
            public void onDoubleClick(View v) {
                Log.e("Double Tap Event", "Tapped at: ");
                if (linearLayout.getScaleX() == 1f || linearLayout.getScaleY() == 1f) {
                    ScaleAnimation scaleAnimation = new ScaleAnimation(1f / prevScale, 1f, 1f / prevScale, 1f);
                    scaleAnimation.setDuration(200);
                    scaleAnimation.setFillAfter(true);
                    linearLayout.startAnimation(scaleAnimation);
                    prevScale = 1;
                    mScale = 1;
                } else {
                    prevScale = 1;
                    mScale = 1;
                }
            }
        });


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (fixtureType == 0) {
                    Log.d("fixturetype", "0");
                    getStandingforIndividualGame(selectedSportName.toUpperCase());
                } else {
                    getFixtureForGame(selectedSportName.toUpperCase());
                    getStandingsForGame(selectedSportName.toUpperCase());
                }
            }
        },50);

              if (fixtureType == 0) {
                individual_recycler = (RecyclerView) getActivity().findViewById(R.id.individual_recycler);
                individual_recycler.setHasFixedSize(true);
                individual_recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
                jazzyRecyclerViewScrollListener = new JazzyRecyclerViewScrollListener();
                jazzyRecyclerViewScrollListener.setTransitionEffect(currentTransitionEffect);
                individual_recycler.setOnScrollListener(jazzyRecyclerViewScrollListener);
                individualEventRecyclerAdapter = new IndividualEventRecyclerAdapter(eventsStanding, getActivity(), new IndividualEventRecyclerAdapter.MyAdapterListener() {
                    @Override
                    public void onItemSelected(int position, ExpandableLayout expandableLayout) {
                        expandableLayout.toggle();
                    }
                });
                individual_recycler.setAdapter(individualEventRecyclerAdapter);
            }

    }

    public void getStandingforIndividualGame(String sport_name) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://us-central1-sportsfete-732bf.cloudfunctions.net")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        apiInterface.getIndividualStanding(sport_name).enqueue(new Callback<List<List<String>>>() {
            @Override
            public void onResponse(Call<List<List<String>>> call, Response<List<List<String>>> response) {
                if (response.isSuccessful()) {
                    Log.d("size", response.body().size() + "");
                    eventsStanding.clear();
                    eventsStanding.addAll(response.body());
                    individualEventRecyclerAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<List<String>>> call, Throwable t) {

            }
        });
    }

    public void getStandingsForGame(String sport_name) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://us-central1-sportsfete-732bf.cloudfunctions.net")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        apiInterface.getStanding(sport_name).enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if (response.isSuccessful()) {
                    position1.setText(response.body().get(0));
                    position2.setText(response.body().get(1));
                    position3.setText(response.body().get(2));
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {

            }
        });
    }


    public void getFixtureForGame(String sports_name) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://us-central1-sportsfete-732bf.cloudfunctions.net")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        apiInterface.getfixture(sports_name).enqueue(new Callback<List<FixturePOJO>>() {
            @Override
            public void onResponse(Call<List<FixturePOJO>> call, Response<List<FixturePOJO>> response) {
                if (response.isSuccessful()) {
                    Log.d("response", "successful" + " : " + response.body().size());
                    fixtureArrayList.clear();
                    fixtureArrayList.addAll(response.body());
                    if (fixtureArrayList.size() == 0) {
                        if (horizontalScrollView != null) {
                            //horizontalScrollView.setVisibility(View.INVISIBLE);
                        }
                    }

                    if (fixtureType != 0) {
                        if (fixtureType == 4) {
                            setTypeFOURGroupElements();
                        } else if (fixtureType == 6) {
                            setTypeSIXGroupElements();
                        } else if (fixtureType == 7) {
                            setTypeSEVENGroupElements();
                        } else if (fixtureType == 8) {
                            setTypeEIGHTGroupElements();
                        } else {
                            setElements();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<FixturePOJO>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    //four->football(boys)3,4,5,6
    //six->hockey(boys)6,7
    //seven->kho kho(boys)1,2,3,4
    //eight->kho kho(girls)1,2,3,4

    public void setElements() {
        for (FixturePOJO fixtureElement : fixtureArrayList) {

            Log.d("selectedSPort",""+selectedSport+" "+selectedSportName);
            if (selectedSportName.equalsIgnoreCase("CARROM")&&(!fixtureElement.getDept1().contains("WINNER OF"))) {
                if (fixtureElement.getFixtureIndex() == 21) {
                    Log.d("fixture_index",""+21);
                    finalsII.setVisibility(View.VISIBLE);
                    finalsIIelement.setVisibility(View.VISIBLE);
                    arrow_next_match.setVisibility(View.VISIBLE);
                    post_final_match.setVisibility(View.VISIBLE);
                }
            }

            Log.d("index", fixtureElement.getFixtureIndex() + "");

            String text = "";
            match[fixtureElement.getFixtureIndex()].setText("MATCH " + fixtureElement.getFixture());

            if (fixtureElement.getDept1().contains("WINNER OF")) {
                text = text + "W" + fixtureElement.getDept1().trim().split(" ")[2] + " Vs ";
            } else if (fixtureElement.getDept1().contains("LOSER OF")) {
                text = text + "L" + fixtureElement.getDept1().trim().split(" ")[2] + " Vs ";
            } else {
                text = text + fixtureElement.getDept1().trim() + " Vs ";
            }

            if (fixtureElement.getDept2().contains("WINNER OF")) {
                text = text + "W" + fixtureElement.getDept2().trim().split(" ")[2];
            } else if (fixtureElement.getDept2().contains("LOSER OF")) {
                text = text + "L" + fixtureElement.getDept2().trim().split(" ")[2];
            } else {
                text = text + fixtureElement.getDept2().trim();
            }

            text = text.replace("M_TECH","M.TECH");
            text = text.replace("PhD_MSc_MS","PhD/MSc/MS");
            if(text.length()>12){
                matchTeams[fixtureElement.getFixtureIndex()].setTextSize(TypedValue.COMPLEX_UNIT_DIP, 13);
            }
            if(text.length()>17){
                matchTeams[fixtureElement.getFixtureIndex()].setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
            }
            matchTeams[fixtureElement.getFixtureIndex()].setText(text);
            //matchTeams[fixtureElement.getFixtureIndex()].setSelected(true);

            Log.d("text", "" + text);
            if (fixtureElement.getWinner().trim().equalsIgnoreCase(fixtureElement.getDept1().trim())) {
                SpannableStringBuilder sb = new SpannableStringBuilder(text);
                ForegroundColorSpan fcs = new ForegroundColorSpan(Color.parseColor("#4ab556"));
                StyleSpan b = new StyleSpan(android.graphics.Typeface.BOLD);
                sb.setSpan(fcs, 0, text.split(" ")[0].length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                sb.setSpan(b, 0, text.split(" ")[0].length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                matchTeams[fixtureElement.getFixtureIndex()].setText(sb);
            } else if (fixtureElement.getWinner().equalsIgnoreCase(fixtureElement.getDept2().trim())) {


                SpannableStringBuilder sb = new SpannableStringBuilder(text);
                ForegroundColorSpan fcs = new ForegroundColorSpan(Color.parseColor("#4ab556"));
                StyleSpan b = new StyleSpan(android.graphics.Typeface.BOLD);
                sb.setSpan(fcs, text.split(" ")[0].length() + text.split(" ")[1].length() + 2,
                        text.split(" ")[0].length() + text.split(" ")[1].length() + 2 +
                                text.split(" ")[2].length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);

                sb.setSpan(b, text.split(" ")[0].length() + text.split(" ")[1].length() + 2,
                        text.split(" ")[0].length() + text.split(" ")[1].length() + 2 +
                                text.split(" ")[2].length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);

                matchTeams[fixtureElement.getFixtureIndex()].setText(sb);
            }

            if (fixtureElement.getStatus().trim().equalsIgnoreCase("live")) {
                startBlinkAnimation(matchTeams[fixtureElement.getFixtureIndex()]);
            }
        }
    }

    public void setTypeFOURGroupElements() {

        for (FixturePOJO fixtureElement : fixtureArrayList) {

            Log.d("index", fixtureElement.getFixtureIndex() + "");

            String text = "";
            if (fixtureElement.getFixtureIndex() != 3 && fixtureElement.getFixtureIndex() != 4 &&
                    fixtureElement.getFixtureIndex() != 5 && fixtureElement.getFixtureIndex() != 6) {
                match[fixtureElement.getFixtureIndex()].setText("MATCH " + fixtureElement.getFixture());

                if (fixtureElement.getFixtureIndex() != 7 && fixtureElement.getFixtureIndex() != 8) {

                    if (fixtureElement.getDept1().contains("WINNER OF")) {
                        text = text + "W" + fixtureElement.getDept1().trim().split(" ")[2] + " Vs ";
                    } else if (fixtureElement.getDept1().contains("LOSER OF")) {
                        text = text + "L" + fixtureElement.getDept1().trim().split(" ")[2] + " Vs ";
                    } else {
                        text = text + fixtureElement.getDept1().trim() + " Vs ";
                    }

                    if (fixtureElement.getDept2().contains("WINNER OF")) {
                        text = text + "W" + fixtureElement.getDept2().trim().split(" ")[2];
                    } else if (fixtureElement.getDept2().contains("LOSER OF")) {
                        text = text + "L" + fixtureElement.getDept2().trim().split(" ")[2];
                    } else {
                        text = text + fixtureElement.getDept2().trim();
                    }

                    text = text.replace("M_TECH","M.TECH");
                    text = text.replace("PhD_MSc_MS","PhD/MSc/MS");
                    if(text.length()>12){
                        matchTeams[fixtureElement.getFixtureIndex()].setTextSize(TypedValue.COMPLEX_UNIT_DIP, 13);
                    }
                    if(text.length()>17){
                        matchTeams[fixtureElement.getFixtureIndex()].setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
                    }
                    matchTeams[fixtureElement.getFixtureIndex()].setText(text);
                    //matchTeams[fixtureElement.getFixtureIndex()].setSelected(true);

                } else {
                    text = "";
                    if (fixtureElement.getFixtureIndex() == 7) {
                        text = fixtureElement.getDept1().trim() + " Vs " + fixtureElement.getDept2();
                        matchTeams[fixtureElement.getFixtureIndex()].setText("W(A) Vs W(D)");
                    } else if (fixtureElement.getFixtureIndex() == 8) {
                        text = fixtureElement.getDept1().trim() + " Vs " + fixtureElement.getDept2();
                        matchTeams[fixtureElement.getFixtureIndex()].setText("W(B) Vs W(C)");
                    }
                }

                if (fixtureElement.getWinner().trim().equalsIgnoreCase(fixtureElement.getDept1().trim())) {
                    SpannableStringBuilder sb = new SpannableStringBuilder(text);
                    ForegroundColorSpan fcs = new ForegroundColorSpan(Color.parseColor("#4ab556"));
                    StyleSpan b = new StyleSpan(android.graphics.Typeface.BOLD);
                    sb.setSpan(fcs, 0, text.split(" ")[0].length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                    sb.setSpan(b, 0, text.split(" ")[0].length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                    matchTeams[fixtureElement.getFixtureIndex()].setText(sb);
                } else if (fixtureElement.getWinner().equalsIgnoreCase(fixtureElement.getDept2().trim())) {
                    SpannableStringBuilder sb = new SpannableStringBuilder(text);
                    ForegroundColorSpan fcs = new ForegroundColorSpan(Color.parseColor("#4ab556"));
                    StyleSpan b = new StyleSpan(android.graphics.Typeface.BOLD);
                    sb.setSpan(fcs, text.split(" ")[0].length() + text.split(" ")[1].length() + 2,
                            text.split(" ")[0].length() + text.split(" ")[1].length() + 2 +
                                    text.split(" ")[2].length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);

                    sb.setSpan(b, text.split(" ")[0].length() + text.split(" ")[1].length() + 2,
                            text.split(" ")[0].length() + text.split(" ")[1].length() + 2 +
                                    text.split(" ")[2].length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);

                    matchTeams[fixtureElement.getFixtureIndex()].setText(sb);
                }

                if (fixtureElement.getStatus().equalsIgnoreCase("live")) {
                    startBlinkAnimation(matchTeams[fixtureElement.getFixtureIndex()]);
                }

            } else {
                grpBteam3.setText(fixtureArrayList.get(2).getWinner());
                grpDteam3.setText(fixtureArrayList.get(1).getWinner());
            }

            if (fixtureElement.getFixtureIndex() == 7) {
                if (grpAteam1.getText().toString().equalsIgnoreCase(fixtureElement.getDept1())) {
                    grpAteam1.setTextColor(Color.parseColor("#4ab556"));
                } else if (grpAteam2.getText().toString().equalsIgnoreCase(fixtureElement.getDept1())) {
                    grpAteam2.setTextColor(Color.parseColor("#4ab556"));
                } else if (grpAteam3.getText().toString().equalsIgnoreCase(fixtureElement.getDept1())) {
                    grpAteam3.setTextColor(Color.parseColor("#4ab556"));
                }

                if (grpDteam1.getText().toString().equalsIgnoreCase(fixtureElement.getDept2())) {
                    grpDteam1.setTextColor(Color.parseColor("#4ab556"));
                } else if (grpDteam2.getText().toString().equalsIgnoreCase(fixtureElement.getDept2())) {
                    grpDteam2.setTextColor(Color.parseColor("#4ab556"));
                } else if (grpDteam3.getText().toString().equalsIgnoreCase(fixtureElement.getDept2())) {
                    grpDteam3.setTextColor(Color.parseColor("#4ab556"));
                }
            }
            if (fixtureElement.getFixtureIndex() == 8) {
                if (grpBteam1.getText().toString().equalsIgnoreCase(fixtureElement.getDept1())) {
                    grpBteam1.setTextColor(Color.parseColor("#4ab556"));
                } else if (grpBteam2.getText().toString().equalsIgnoreCase(fixtureElement.getDept1())) {
                    grpBteam2.setTextColor(Color.parseColor("#4ab556"));
                } else if (grpBteam3.getText().toString().equalsIgnoreCase(fixtureElement.getDept1())) {
                    grpBteam3.setTextColor(Color.parseColor("#4ab556"));
                }

                if (grpCteam1.getText().toString().equalsIgnoreCase(fixtureElement.getDept2())) {
                    grpCteam1.setTextColor(Color.parseColor("#4ab556"));
                } else if (grpCteam2.getText().toString().equalsIgnoreCase(fixtureElement.getDept2())) {
                    grpCteam2.setTextColor(Color.parseColor("#4ab556"));
                } else if (grpCteam3.getText().toString().equalsIgnoreCase(fixtureElement.getDept2())) {
                    grpDteam3.setTextColor(Color.parseColor("#4ab556"));
                }
            }

        }

        //setGroupFOURqualifiers();
    }

    public void setTypeSIXGroupElements() {

        for (FixturePOJO fixtureElement : fixtureArrayList) {

            Log.d("index", fixtureElement.getFixtureIndex() + "");

            String text = "";
            if (fixtureElement.getFixtureIndex() != 6 && fixtureElement.getFixtureIndex() != 7) {
                match[fixtureElement.getFixtureIndex()].setText("MATCH " + fixtureElement.getFixture());

                if (fixtureElement.getFixtureIndex() != 8 && fixtureElement.getFixtureIndex() != 9) {

                    if (fixtureElement.getDept1().contains("WINNER OF")) {
                        text = text + "W" + fixtureElement.getDept1().trim().split(" ")[2] + " Vs ";
                    } else if (fixtureElement.getDept1().contains("LOSER OF")) {
                        text = text + "L" + fixtureElement.getDept1().trim().split(" ")[2] + " Vs ";
                    } else {
                        text = text + fixtureElement.getDept1().trim() + " Vs ";
                    }

                    if (fixtureElement.getDept2().contains("WINNER OF")) {
                        text = text + "W" + fixtureElement.getDept2().trim().split(" ")[2];
                    } else if (fixtureElement.getDept2().contains("LOSER OF")) {
                        text = text + "L" + fixtureElement.getDept2().trim().split(" ")[2];
                    } else {
                        text = text + fixtureElement.getDept2().trim();
                    }

                    text = text.replace("M_TECH","M.TECH");
                    text = text.replace("PhD_MSc_MS","PhD/MSc/MS");
                    if(text.length()>12){
                        matchTeams[fixtureElement.getFixtureIndex()].setTextSize(TypedValue.COMPLEX_UNIT_DIP, 13);
                    }
                    if(text.length()>17){
                        matchTeams[fixtureElement.getFixtureIndex()].setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
                    }
                    matchTeams[fixtureElement.getFixtureIndex()].setText(text);
                    //matchTeams[fixtureElement.getFixtureIndex()].setSelected(true);

                } else {
                    text = "";
                    if (fixtureElement.getFixtureIndex() == 8) {
                        text = fixtureElement.getDept1().trim() + " Vs " + fixtureElement.getDept2();
                        matchTeams[fixtureElement.getFixtureIndex()].setText("W(A) Vs R(B)");
                    } else if (fixtureElement.getFixtureIndex() == 9) {
                        text = fixtureElement.getDept1().trim() + " Vs " + fixtureElement.getDept2();
                        matchTeams[fixtureElement.getFixtureIndex()].setText("W(B) Vs R(A)");
                    }
                }

                if (fixtureElement.getWinner().trim().equalsIgnoreCase(fixtureElement.getDept1().trim())) {
                    SpannableStringBuilder sb = new SpannableStringBuilder(text);
                    ForegroundColorSpan fcs = new ForegroundColorSpan(Color.parseColor("#4ab556"));
                    StyleSpan b = new StyleSpan(android.graphics.Typeface.BOLD);
                    sb.setSpan(fcs, 0, text.split(" ")[0].length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                    sb.setSpan(b, 0, text.split(" ")[0].length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                    matchTeams[fixtureElement.getFixtureIndex()].setText(sb);
                } else if (fixtureElement.getWinner().equalsIgnoreCase(fixtureElement.getDept2().trim())) {
                    SpannableStringBuilder sb = new SpannableStringBuilder(text);
                    ForegroundColorSpan fcs = new ForegroundColorSpan(Color.parseColor("#4ab556"));
                    StyleSpan b = new StyleSpan(android.graphics.Typeface.BOLD);
                    sb.setSpan(fcs, text.split(" ")[0].length() + text.split(" ")[1].length() + 2,
                            text.split(" ")[0].length() + text.split(" ")[1].length() + 2 +
                                    text.split(" ")[2].length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);

                    sb.setSpan(b, text.split(" ")[0].length() + text.split(" ")[1].length() + 2,
                            text.split(" ")[0].length() + text.split(" ")[1].length() + 2 +
                                    text.split(" ")[2].length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);

                    matchTeams[fixtureElement.getFixtureIndex()].setText(sb);
                }

                if (fixtureElement.getStatus().equalsIgnoreCase("live")) {
                    startBlinkAnimation(matchTeams[fixtureElement.getFixtureIndex()]);
                }

            } else {
                grpAteam2.setText(fixtureArrayList.get(1).getWinner());
                grpAteam3.setText(fixtureArrayList.get(2).getWinner());
                grpBteam1.setText(fixtureArrayList.get(3).getWinner());
                grpBteam2.setText(fixtureArrayList.get(4).getWinner());
                grpAteam3.setText(fixtureArrayList.get(5).getWinner());
            }

            if (fixtureElement.getFixtureIndex() == 8) {
                //winner A
                if (grpAteam1.getText().toString().equalsIgnoreCase(fixtureElement.getDept1())) {
                    grpAteam1.setTextColor(Color.parseColor("#4ab556"));
                } else if (grpAteam2.getText().toString().equalsIgnoreCase(fixtureElement.getDept1())) {
                    grpAteam2.setTextColor(Color.parseColor("#4ab556"));
                } else if (grpAteam3.getText().toString().equalsIgnoreCase(fixtureElement.getDept1())) {
                    grpAteam3.setTextColor(Color.parseColor("#4ab556"));
                }

                if (grpBteam1.getText().toString().equalsIgnoreCase(fixtureElement.getDept2())) {
                    grpBteam1.setTextColor(Color.parseColor("#4ab556"));
                } else if (grpBteam2.getText().toString().equalsIgnoreCase(fixtureElement.getDept2())) {
                    grpBteam2.setTextColor(Color.parseColor("#4ab556"));
                } else if (grpBteam3.getText().toString().equalsIgnoreCase(fixtureElement.getDept2())) {
                    grpBteam3.setTextColor(Color.parseColor("#4ab556"));
                }
            }

            //Runner B
            if (fixtureElement.getFixtureIndex() == 9) {
                //winner B
                if (grpBteam1.getText().toString().equalsIgnoreCase(fixtureElement.getDept1())) {
                    grpBteam1.setTextColor(Color.parseColor("#4ab556"));
                } else if (grpBteam2.getText().toString().equalsIgnoreCase(fixtureElement.getDept1())) {
                    grpBteam2.setTextColor(Color.parseColor("#4ab556"));
                } else if (grpBteam3.getText().toString().equalsIgnoreCase(fixtureElement.getDept1())) {
                    grpBteam3.setTextColor(Color.parseColor("#4ab556"));
                }

                //runner A
                if (grpAteam1.getText().toString().equalsIgnoreCase(fixtureElement.getDept2())) {
                    grpAteam1.setTextColor(Color.parseColor("#4ab556"));
                } else if (grpAteam2.getText().toString().equalsIgnoreCase(fixtureElement.getDept2())) {
                    grpAteam2.setTextColor(Color.parseColor("#4ab556"));
                } else if (grpAteam3.getText().toString().equalsIgnoreCase(fixtureElement.getDept2())) {
                    grpAteam3.setTextColor(Color.parseColor("#4ab556"));
                }
            }

        }

        //setGroupSIXfilter();

    }

    public void setTypeSEVENGroupElements() {

        for (FixturePOJO fixtureElement : fixtureArrayList) {

            Log.d("index", fixtureElement.getFixtureIndex() + "");

            String text = "";
            if (fixtureElement.getFixtureIndex() != 1 && fixtureElement.getFixtureIndex() != 2 &&
                    fixtureElement.getFixtureIndex() != 3 && fixtureElement.getFixtureIndex() != 4) {
                match[fixtureElement.getFixtureIndex()].setText("MATCH " + fixtureElement.getFixture());

                if (fixtureElement.getFixtureIndex() != 5 && fixtureElement.getFixtureIndex() != 6
                        && fixtureElement.getFixtureIndex() != 7 && fixtureElement.getFixtureIndex() != 8) {

                    if (fixtureElement.getDept1().contains("WINNER OF")) {
                        text = text + "W" + fixtureElement.getDept1().trim().split(" ")[2] + " Vs ";
                    } else if (fixtureElement.getDept1().contains("LOSER OF")) {
                        text = text + "L" + fixtureElement.getDept1().trim().split(" ")[2] + " Vs ";
                    } else {
                        text = text + fixtureElement.getDept1().trim() + " Vs ";
                    }

                    if (fixtureElement.getDept2().contains("WINNER OF")) {
                        text = text + "W" + fixtureElement.getDept2().trim().split(" ")[2];
                    } else if (fixtureElement.getDept2().contains("LOSER OF")) {
                        text = text + "L" + fixtureElement.getDept2().trim().split(" ")[2];
                    } else {
                        text = text + fixtureElement.getDept2().trim();
                    }

                    text = text.replace("M_TECH","M.TECH");
                    text = text.replace("PhD_MSc_MS","PhD/MSc/MS");
                    if(text.length()>12){
                        matchTeams[fixtureElement.getFixtureIndex()].setTextSize(TypedValue.COMPLEX_UNIT_DIP, 13);
                    }
                    if(text.length()>17){
                        matchTeams[fixtureElement.getFixtureIndex()].setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
                    }
                    matchTeams[fixtureElement.getFixtureIndex()].setText(text);
                    //matchTeams[fixtureElement.getFixtureIndex()].setSelected(true);

                } else {
                    text = "";
                    if (fixtureElement.getFixtureIndex() == 5) {
                        text = fixtureElement.getDept1().trim() + " Vs " + fixtureElement.getDept2();
                        matchTeams[fixtureElement.getFixtureIndex()].setText("W(A) Vs R(C)");
                    } else if (fixtureElement.getFixtureIndex() == 6) {
                        text = fixtureElement.getDept1().trim() + " Vs " + fixtureElement.getDept2();
                        matchTeams[fixtureElement.getFixtureIndex()].setText("W(D) Vs R(B)");
                    } else if (fixtureElement.getFixtureIndex() == 7) {
                        text = fixtureElement.getDept1().trim() + " Vs " + fixtureElement.getDept2();
                        matchTeams[fixtureElement.getFixtureIndex()].setText("W(B) Vs R(D)");
                    } else if (fixtureElement.getFixtureIndex() == 8) {
                        text = fixtureElement.getDept1().trim() + " Vs " + fixtureElement.getDept2();
                        matchTeams[fixtureElement.getFixtureIndex()].setText("W(C) Vs R(A)");
                    }
                }

                if (fixtureElement.getWinner().trim().equalsIgnoreCase(fixtureElement.getDept1().trim())) {
                    SpannableStringBuilder sb = new SpannableStringBuilder(text);
                    ForegroundColorSpan fcs = new ForegroundColorSpan(Color.parseColor("#4ab556"));
                    StyleSpan b = new StyleSpan(android.graphics.Typeface.BOLD);
                    sb.setSpan(fcs, 0, text.split(" ")[0].length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                    sb.setSpan(b, 0, text.split(" ")[0].length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                    matchTeams[fixtureElement.getFixtureIndex()].setText(sb);
                } else if (fixtureElement.getWinner().equalsIgnoreCase(fixtureElement.getDept2().trim())) {
                    SpannableStringBuilder sb = new SpannableStringBuilder(text);
                    ForegroundColorSpan fcs = new ForegroundColorSpan(Color.parseColor("#4ab556"));
                    StyleSpan b = new StyleSpan(android.graphics.Typeface.BOLD);
                    sb.setSpan(fcs, text.split(" ")[0].length() + text.split(" ")[1].length() + 2,
                            text.split(" ")[0].length() + text.split(" ")[1].length() + 2 +
                                    text.split(" ")[2].length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);

                    sb.setSpan(b, text.split(" ")[0].length() + text.split(" ")[1].length() + 2,
                            text.split(" ")[0].length() + text.split(" ")[1].length() + 2 +
                                    text.split(" ")[2].length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);

                    matchTeams[fixtureElement.getFixtureIndex()].setText(sb);
                }

                if (fixtureElement.getStatus().equalsIgnoreCase("live")) {
                    startBlinkAnimation(matchTeams[fixtureElement.getFixtureIndex()]);
                }

            } else {

            }

            if (fixtureElement.getFixtureIndex() == 5) {
                if (grpAteam1.getText().toString().equalsIgnoreCase(fixtureElement.getDept1())) {
                    grpAteam1.setTextColor(Color.parseColor("#4ab556"));
                } else if (grpAteam2.getText().toString().equalsIgnoreCase(fixtureElement.getDept1())) {
                    grpAteam2.setTextColor(Color.parseColor("#4ab556"));
                } else if (grpAteam3.getText().toString().equalsIgnoreCase(fixtureElement.getDept1())) {
                    grpAteam3.setTextColor(Color.parseColor("#4ab556"));
                }

                if (grpCteam1.getText().toString().equalsIgnoreCase(fixtureElement.getDept2())) {
                    grpCteam1.setTextColor(Color.parseColor("#4ab556"));
                } else if (grpCteam2.getText().toString().equalsIgnoreCase(fixtureElement.getDept2())) {
                    grpCteam2.setTextColor(Color.parseColor("#4ab556"));
                } else if (grpCteam3.getText().toString().equalsIgnoreCase(fixtureElement.getDept2())) {
                    grpCteam3.setTextColor(Color.parseColor("#4ab556"));
                }
            }

            if (fixtureElement.getFixtureIndex() == 6) {
                if (grpDteam1.getText().toString().equalsIgnoreCase(fixtureElement.getDept1())) {
                    grpDteam1.setTextColor(Color.parseColor("#4ab556"));
                } else if (grpDteam2.getText().toString().equalsIgnoreCase(fixtureElement.getDept1())) {
                    grpDteam2.setTextColor(Color.parseColor("#4ab556"));
                } else if (grpDteam3.getText().toString().equalsIgnoreCase(fixtureElement.getDept1())) {
                    grpDteam3.setTextColor(Color.parseColor("#4ab556"));
                }

                if (grpBteam1.getText().toString().equalsIgnoreCase(fixtureElement.getDept2())) {
                    grpBteam1.setTextColor(Color.parseColor("#4ab556"));
                } else if (grpBteam2.getText().toString().equalsIgnoreCase(fixtureElement.getDept2())) {
                    grpBteam2.setTextColor(Color.parseColor("#4ab556"));
                } else if (grpBteam3.getText().toString().equalsIgnoreCase(fixtureElement.getDept2())) {
                    grpBteam3.setTextColor(Color.parseColor("#4ab556"));
                }
            }

            if (fixtureElement.getFixtureIndex() == 7) {
                if (grpBteam1.getText().toString().equalsIgnoreCase(fixtureElement.getDept1())) {
                    grpBteam1.setTextColor(Color.parseColor("#4ab556"));
                } else if (grpBteam2.getText().toString().equalsIgnoreCase(fixtureElement.getDept1())) {
                    grpBteam2.setTextColor(Color.parseColor("#4ab556"));
                } else if (grpBteam3.getText().toString().equalsIgnoreCase(fixtureElement.getDept1())) {
                    grpBteam3.setTextColor(Color.parseColor("#4ab556"));
                }

                if (grpDteam1.getText().toString().equalsIgnoreCase(fixtureElement.getDept2())) {
                    grpDteam1.setTextColor(Color.parseColor("#4ab556"));
                } else if (grpDteam2.getText().toString().equalsIgnoreCase(fixtureElement.getDept2())) {
                    grpDteam2.setTextColor(Color.parseColor("#4ab556"));
                } else if (grpDteam3.getText().toString().equalsIgnoreCase(fixtureElement.getDept2())) {
                    grpDteam3.setTextColor(Color.parseColor("#4ab556"));
                }
            }

            if (fixtureElement.getFixtureIndex() == 8) {
                //winner B
                if (grpCteam1.getText().toString().equalsIgnoreCase(fixtureElement.getDept1())) {
                    grpCteam1.setTextColor(Color.parseColor("#4ab556"));
                } else if (grpCteam2.getText().toString().equalsIgnoreCase(fixtureElement.getDept1())) {
                    grpCteam2.setTextColor(Color.parseColor("#4ab556"));
                } else if (grpCteam3.getText().toString().equalsIgnoreCase(fixtureElement.getDept1())) {
                    grpCteam3.setTextColor(Color.parseColor("#4ab556"));
                }

                //runner D
                if (grpAteam1.getText().toString().equalsIgnoreCase(fixtureElement.getDept2())) {
                    grpAteam1.setTextColor(Color.parseColor("#4ab556"));
                } else if (grpAteam2.getText().toString().equalsIgnoreCase(fixtureElement.getDept2())) {
                    grpAteam2.setTextColor(Color.parseColor("#4ab556"));
                } else if (grpAteam3.getText().toString().equalsIgnoreCase(fixtureElement.getDept2())) {
                    grpAteam3.setTextColor(Color.parseColor("#4ab556"));
                }
            }

        }
    }

    public void setTypeEIGHTGroupElements() {

        for (FixturePOJO fixtureElement : fixtureArrayList) {

            Log.d("index", fixtureElement.getFixtureIndex() + "");

            String text = "";
            if (fixtureElement.getFixtureIndex() != 1 && fixtureElement.getFixtureIndex() != 2 &&
                    fixtureElement.getFixtureIndex() != 3 && fixtureElement.getFixtureIndex() != 4) {
                match[fixtureElement.getFixtureIndex()].setText("MATCH " + fixtureElement.getFixture());

                if (fixtureElement.getFixtureIndex() != 5 && fixtureElement.getFixtureIndex() != 6
                        && fixtureElement.getFixtureIndex() != 7 && fixtureElement.getFixtureIndex() != 8) {

                    if (fixtureElement.getDept1().contains("WINNER OF")) {
                        text = text + "W" + fixtureElement.getDept1().trim().split(" ")[2] + " Vs ";
                    } else if (fixtureElement.getDept1().contains("LOSER OF")) {
                        text = text + "L" + fixtureElement.getDept1().trim().split(" ")[2] + " Vs ";
                    } else {
                        text = text + fixtureElement.getDept1().trim() + " Vs ";
                    }

                    if (fixtureElement.getDept2().contains("WINNER OF")) {
                        text = text + "W" + fixtureElement.getDept2().trim().split(" ")[2];
                    } else if (fixtureElement.getDept2().contains("LOSER OF")) {
                        text = text + "L" + fixtureElement.getDept2().trim().split(" ")[2];
                    } else {
                        text = text + fixtureElement.getDept2().trim();
                    }

                    text = text.replace("M_TECH","M.TECH");
                    text = text.replace("PhD_MSc_MS","PhD/MSc/MS");
                    if(text.length()>12){
                        matchTeams[fixtureElement.getFixtureIndex()].setTextSize(TypedValue.COMPLEX_UNIT_DIP, 13);
                    }
                    if(text.length()>17){
                        matchTeams[fixtureElement.getFixtureIndex()].setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
                    }
                    matchTeams[fixtureElement.getFixtureIndex()].setText(text);
                    //matchTeams[fixtureElement.getFixtureIndex()].setSelected(true);

                } else {
                    text = "";
                    if (fixtureElement.getFixtureIndex() == 5) {
                        text = fixtureElement.getDept1().trim() + " Vs " + fixtureElement.getDept2();
                        matchTeams[fixtureElement.getFixtureIndex()].setText("W(A) Vs R(C)");
                    } else if (fixtureElement.getFixtureIndex() == 6) {
                        text = fixtureElement.getDept1().trim() + " Vs " + fixtureElement.getDept2();
                        matchTeams[fixtureElement.getFixtureIndex()].setText("W(D) Vs R(B)");
                    } else if (fixtureElement.getFixtureIndex() == 7) {
                        text = fixtureElement.getDept1().trim() + " Vs " + fixtureElement.getDept2();
                        matchTeams[fixtureElement.getFixtureIndex()].setText("W(B) Vs R(D)");
                    } else if (fixtureElement.getFixtureIndex() == 8) {
                        text = fixtureElement.getDept1().trim() + " Vs " + fixtureElement.getDept2();
                        matchTeams[fixtureElement.getFixtureIndex()].setText("W(C) Vs R(A)");
                    }
                }

                if (fixtureElement.getWinner().trim().equalsIgnoreCase(fixtureElement.getDept1().trim())) {
                    SpannableStringBuilder sb = new SpannableStringBuilder(text);
                    ForegroundColorSpan fcs = new ForegroundColorSpan(Color.parseColor("#4ab556"));
                    StyleSpan b = new StyleSpan(android.graphics.Typeface.BOLD);
                    sb.setSpan(fcs, 0, text.split(" ")[0].length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                    sb.setSpan(b, 0, text.split(" ")[0].length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                    matchTeams[fixtureElement.getFixtureIndex()].setText(sb);
                } else if (fixtureElement.getWinner().equalsIgnoreCase(fixtureElement.getDept2().trim())) {
                    SpannableStringBuilder sb = new SpannableStringBuilder(text);
                    ForegroundColorSpan fcs = new ForegroundColorSpan(Color.parseColor("#4ab556"));
                    StyleSpan b = new StyleSpan(android.graphics.Typeface.BOLD);
                    sb.setSpan(fcs, text.split(" ")[0].length() + text.split(" ")[1].length() + 2,
                            text.split(" ")[0].length() + text.split(" ")[1].length() + 2 +
                                    text.split(" ")[2].length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);

                    sb.setSpan(b, text.split(" ")[0].length() + text.split(" ")[1].length() + 2,
                            text.split(" ")[0].length() + text.split(" ")[1].length() + 2 +
                                    text.split(" ")[2].length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);

                    matchTeams[fixtureElement.getFixtureIndex()].setText(sb);
                }

                if (fixtureElement.getStatus().equalsIgnoreCase("live")) {
                    startBlinkAnimation(matchTeams[fixtureElement.getFixtureIndex()]);
                }

            } else {

            }

            if (fixtureElement.getFixtureIndex() == 5) {
                if (grpAteam1.getText().toString().equalsIgnoreCase(fixtureElement.getDept1())) {
                    grpAteam1.setTextColor(Color.parseColor("#4ab556"));
                } else if (grpAteam2.getText().toString().equalsIgnoreCase(fixtureElement.getDept1())) {
                    grpAteam2.setTextColor(Color.parseColor("#4ab556"));
                } else if (grpAteam3.getText().toString().equalsIgnoreCase(fixtureElement.getDept1())) {
                    grpAteam3.setTextColor(Color.parseColor("#4ab556"));
                }

                if (grpCteam1.getText().toString().equalsIgnoreCase(fixtureElement.getDept2())) {
                    grpCteam1.setTextColor(Color.parseColor("#4ab556"));
                } else if (grpCteam2.getText().toString().equalsIgnoreCase(fixtureElement.getDept2())) {
                    grpCteam2.setTextColor(Color.parseColor("#4ab556"));
                } else if (grpCteam3.getText().toString().equalsIgnoreCase(fixtureElement.getDept2())) {
                    grpCteam3.setTextColor(Color.parseColor("#4ab556"));
                }
            }

            if (fixtureElement.getFixtureIndex() == 6) {
                if (grpDteam1.getText().toString().equalsIgnoreCase(fixtureElement.getDept1())) {
                    grpDteam1.setTextColor(Color.parseColor("#4ab556"));
                } else if (grpDteam2.getText().toString().equalsIgnoreCase(fixtureElement.getDept1())) {
                    grpDteam2.setTextColor(Color.parseColor("#4ab556"));
                } else if (grpDteam3.getText().toString().equalsIgnoreCase(fixtureElement.getDept1())) {
                    grpDteam3.setTextColor(Color.parseColor("#4ab556"));
                }

                if (grpBteam1.getText().toString().equalsIgnoreCase(fixtureElement.getDept2())) {
                    grpBteam1.setTextColor(Color.parseColor("#4ab556"));
                } else if (grpBteam2.getText().toString().equalsIgnoreCase(fixtureElement.getDept2())) {
                    grpBteam2.setTextColor(Color.parseColor("#4ab556"));
                } else if (grpBteam3.getText().toString().equalsIgnoreCase(fixtureElement.getDept2())) {
                    grpBteam3.setTextColor(Color.parseColor("#4ab556"));
                }
            }

            if (fixtureElement.getFixtureIndex() == 7) {
                if (grpBteam1.getText().toString().equalsIgnoreCase(fixtureElement.getDept1())) {
                    grpBteam1.setTextColor(Color.parseColor("#4ab556"));
                } else if (grpBteam2.getText().toString().equalsIgnoreCase(fixtureElement.getDept1())) {
                    grpBteam2.setTextColor(Color.parseColor("#4ab556"));
                } else if (grpBteam3.getText().toString().equalsIgnoreCase(fixtureElement.getDept1())) {
                    grpBteam3.setTextColor(Color.parseColor("#4ab556"));
                }

                if (grpDteam1.getText().toString().equalsIgnoreCase(fixtureElement.getDept2())) {
                    grpDteam1.setTextColor(Color.parseColor("#4ab556"));
                } else if (grpDteam2.getText().toString().equalsIgnoreCase(fixtureElement.getDept2())) {
                    grpDteam2.setTextColor(Color.parseColor("#4ab556"));
                } else if (grpDteam3.getText().toString().equalsIgnoreCase(fixtureElement.getDept2())) {
                    grpDteam3.setTextColor(Color.parseColor("#4ab556"));
                }
            }

            if (fixtureElement.getFixtureIndex() == 8) {
                //winner B
                if (grpCteam1.getText().toString().equalsIgnoreCase(fixtureElement.getDept1())) {
                    grpCteam1.setTextColor(Color.parseColor("#4ab556"));
                } else if (grpCteam2.getText().toString().equalsIgnoreCase(fixtureElement.getDept1())) {
                    grpCteam2.setTextColor(Color.parseColor("#4ab556"));
                } else if (grpCteam3.getText().toString().equalsIgnoreCase(fixtureElement.getDept1())) {
                    grpCteam3.setTextColor(Color.parseColor("#4ab556"));
                }

                //runner D
                if (grpAteam1.getText().toString().equalsIgnoreCase(fixtureElement.getDept2())) {
                    grpAteam1.setTextColor(Color.parseColor("#4ab556"));
                } else if (grpAteam2.getText().toString().equalsIgnoreCase(fixtureElement.getDept2())) {
                    grpAteam2.setTextColor(Color.parseColor("#4ab556"));
                } else if (grpAteam3.getText().toString().equalsIgnoreCase(fixtureElement.getDept2())) {
                    grpAteam3.setTextColor(Color.parseColor("#4ab556"));
                }
            }

        }
    }

    public void startBlinkAnimation(TextView textView) {

        ObjectAnimator colorAnim = ObjectAnimator.ofInt(textView, "textColor",
                Color.parseColor("#404f50"), Color.parseColor("#4ab556"));
        colorAnim.setEvaluator(new ArgbEvaluator());
        colorAnim.setRepeatCount(ValueAnimator.INFINITE);
        colorAnim.setRepeatMode(ValueAnimator.REVERSE);
        colorAnim.setDuration(500);
        colorAnim.start();

    }

}
