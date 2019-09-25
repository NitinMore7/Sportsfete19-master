package spider.app.sportsfete19;


import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.messaging.FirebaseMessaging;
import com.truizlop.fabreveallayout.FABRevealLayout;

import java.util.ArrayList;
import java.util.List;

import devlight.io.library.ntb.NavigationTabBar;
import spider.app.sportsfete19.Home.HomeFragment;
import spider.app.sportsfete19.Leaderboard.LeaderboardFragment;
import spider.app.sportsfete19.Schedule.DeptSelectionRecyclerAdapter;
import spider.app.sportsfete19.Schedule.ScheduleFragment;
import spider.app.sportsfete19.SportDetails.SportDetailsFragment;
import spider.app.sportsfete19.Tutorial.TutorialHelper;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence;
import uk.co.deanwild.materialshowcaseview.ShowcaseConfig;
import yalantis.com.sidemenu.interfaces.Resourceble;
import yalantis.com.sidemenu.interfaces.ScreenShotable;
import yalantis.com.sidemenu.model.SlideMenuItem;
import yalantis.com.sidemenu.util.ViewAnimator;
import yalantis.com.sidemenu.util.ViewAnimator.ViewAnimatorListener;


import static spider.app.sportsfete19.R.array.filter_department_array;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,DepartmentUpdateCallback,ViewAnimator.ViewAnimatorListener{

    ArrayList<View> menuItems;
    private DrawerLayout flowingDrawer;
    Menu menu;
    int lastViewFragment=0;
    public Toolbar toolbar;
    //MyDatabase myDatabase;
    public View view;
    NavigationTabBar navigationTabBar;
    NavigationView navigationView;
    RecyclerView dept_recycler, sport_recycler;
    RelativeLayout selection_header;

    FABRevealLayout sportSelection;
    TextView selectedSportView;
    HomeFragment homeFragment;
    LeaderboardFragment leaderboardFragment;
    ScheduleFragment scheduleFragment;
    UserSearch searchFragment;
    SportDetailsFragment sportDetailsFragment;


    MyDatabase myDatabase;
    UserSearch userSearch;


    String[] deptArraySharedPreference=new String[15];
    String[] sportArraySharedPreference=new String[31];
    List<String> deptlist, recycler_deptList, sportList, recycler_sportList;
    DeptSelectionRecyclerAdapter recyclerAdapter, sportAdapter;

    public String selectedDepartment = "ALL";
    public String selectedSport = "ALL";
    private static final String TAG="MainActivity";
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private List<SlideMenuItem> list = new ArrayList<>();
    private ViewAnimator viewAnimator;
    private int res = R.drawable.athletics;
    private LinearLayout linearLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ContentFragment contentFragment = ContentFragment.newInstance(R.drawable.athletics);
        Bundle arguments = new Bundle();
        arguments.putString("target", "live");
        HomeFragment homeFragment = new HomeFragment();
        homeFragment.setArguments(arguments);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, homeFragment)
                .commit();
        drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.setScrimColor(Color.TRANSPARENT);
        linearLayout = findViewById(R.id.left_drawer);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawers();
            }
        });



        setActionBar();
        createMenuList();

        /*
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mAdapter = new MenuList(getApplicationContext(), (ArrayList<SlideMenuItem>) list);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        */

        viewAnimator = new ViewAnimator<>(this, list, contentFragment, drawerLayout, (ViewAnimatorListener) this);
        drawerLayout.setStatusBarBackgroundColor(Color.RED);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        sportSelection = findViewById(R.id.fab_reveal_layout);
        selectedSportView = findViewById(R.id.selected_sport);

        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu_white);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        getSupportActionBar().setTitle("");

        lastViewFragment = 0;

        for(int i = 0; i < toolbar.getChildCount(); i++)
        { View view = toolbar.getChildAt(i);
            Log.d("font set","true"+"");
            if(view instanceof TextView) {
                TextView textView = (TextView) view;
                textView.setTypeface(Typeface.createFromAsset(getAssets(),  "fonts/HammersmithOneRegular.ttf"));
                Log.d("font set","true"+"");
            }
        }

        navigationTabBar = findViewById(R.id.custom_navigation);


        final ArrayList<NavigationTabBar.Model> models = new ArrayList<>();
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.live_match),
                        Color.parseColor("#000000"))
                        .selectedIcon(getResources().getDrawable(R.drawable.live_matches_unselected))
                        .title("Live")
                        .badgeTitle("NTB")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.upcoming),
                        Color.parseColor("#000000")
                ).title("Upcoming")
                        .selectedIcon(getResources().getDrawable(R.drawable.upcoming_unselected))
                        .badgeTitle("with")
                        .build()  );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.completed),
                        Color.parseColor("#000000")
                ).title("Completed")
                        .selectedIcon(getResources().getDrawable(R.drawable.completedunselected))
                        .badgeTitle("state")
                        .build()
        );
        navigationTabBar.setModels(models);
        navigationTabBar.setTitleMode(NavigationTabBar.TitleMode.ACTIVE);
        navigationTabBar.setTypeface(Typeface.createFromAsset(getAssets(),  "fonts/InconsolataBold.ttf"));
        navigationTabBar.setSelected(true);
        navigationTabBar.setModelIndex(0);
        navigationTabBar.setOnTabBarSelectedIndexListener(new NavigationTabBar.OnTabBarSelectedIndexListener() {
            @Override
            public void onStartTabSelected(NavigationTabBar.Model model, int index) {
            }

            @Override
            public void onEndTabSelected(NavigationTabBar.Model model, int index) {
                Log.d("pos",""+index);
                switch (index){
                    case 0: try {
                        Bundle arguments = new Bundle();
                        lastViewFragment = 0;
                        arguments.putString("target", "live");
                        HomeFragment homeFragment = new HomeFragment();
                        homeFragment.setArguments(arguments);
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.fragment_container, homeFragment,"LIVE");
                        //TODO:dont call after activity is paused
                        transaction.commit();
                        invalidateOptionsMenu();
                        getSupportActionBar().setTitle("Live");
                    }catch(IllegalStateException ignored){
                        ignored.printStackTrace();
                    }

                        break;

                    case 1:  try {
                        Bundle arguments2 = new Bundle();
                        arguments2.putString("target", "upcoming");
                        lastViewFragment = 0;
                        HomeFragment homeFragment2 = new HomeFragment();
                        homeFragment2.setArguments(arguments2);
                        FragmentTransaction transaction2 = getSupportFragmentManager().beginTransaction();
                        transaction2.replace(R.id.fragment_container, homeFragment2,"UPCOMING");
                        transaction2.commit();
                        invalidateOptionsMenu();
                        getSupportActionBar().setTitle("Upcoming");
                    }catch(IllegalStateException ignored){
                        ignored.printStackTrace();
                    }
                        break;

                    case 2: try {
                        Bundle arguments3 = new Bundle();
                        arguments3.putString("target", "completed");
                        lastViewFragment = 0;
                        HomeFragment homeFragment3 = new HomeFragment();
                        homeFragment3.setArguments(arguments3);
                        FragmentTransaction transaction3 = getSupportFragmentManager().beginTransaction();
                        transaction3.replace(R.id.fragment_container, homeFragment3,"COMPLETED");
                        transaction3.commit();
                        invalidateOptionsMenu();
                        getSupportActionBar().setTitle("Completed");
                    }catch(IllegalStateException ignored){
                        ignored.printStackTrace();
                    }
                        break;
                }
            }
        });



        view =  findViewById(R.id.view_id);
        flowingDrawer = findViewById(R.id.drawer_layout);
        View navButton = TutorialHelper.getNavButtonView(toolbar);


        ShowcaseConfig config = new ShowcaseConfig();
        config.setDelay(300); // half second between each showcase view

        MaterialShowcaseSequence sequence = new MaterialShowcaseSequence(this, "23");

        sequence.setConfig(config);

        sequence.addSequenceItem(navigationTabBar,
                "Switch between Live,Upcoming and Completed events", "NEXT");

        sequence.addSequenceItem(navButton,
                "Touch here to check out other features", "GOT IT");


        sequence.start();
 /*

        scalingll = findViewById(R.id.scaling_layout);
        navigationView = findViewById(R.id.nav_view);

        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);
        menu=navigationView.getMenu();
        Log.d("menu", navigationView.getMenu().getItem(0).getTitle().toString());
        navigationView.getMenu().getItem(0).setChecked(true);
        onNavigationItemSelected(navigationView.getMenu().getItem(0));
*/

        setDrawerTypeface();

        FirebaseMessaging.getInstance().subscribeToTopic("important");

        deptArraySharedPreference=getResources().getStringArray(R.array.filter_department_array);
        deptlist = new ArrayList<>();
        recycler_deptList = new ArrayList<>();
        sportList = new ArrayList<>();
        recycler_sportList = new ArrayList<>();

//dept filter list
        for ( int i = 0; i <deptArraySharedPreference.length; i++)
            deptlist.add(deptArraySharedPreference[i]);

        deptArraySharedPreference=getResources().getStringArray(R.array.department_array);
        for ( int i = 0; i<deptArraySharedPreference.length; i++)
            recycler_deptList.add(deptArraySharedPreference[i]);

        //sport filter list
        sportArraySharedPreference=getResources().getStringArray(R.array.filter_sport_array);
        for ( int i = 0; i <sportArraySharedPreference.length; i++)
            sportList.add(sportArraySharedPreference[i]);

        sportArraySharedPreference=getResources().getStringArray(R.array.short_sport_array);
        for ( int i = 0; i <sportArraySharedPreference.length; i++)
            recycler_sportList.add(sportArraySharedPreference[i]);

        selection_header = findViewById(R.id.selection_header);
        dept_recycler = findViewById(R.id.main_dept_recycler);
        dept_recycler.setHasFixedSize(true);
        final LinearLayoutManager layoutManager=new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        dept_recycler.setLayoutManager(layoutManager);
        recyclerAdapter = new DeptSelectionRecyclerAdapter(recycler_deptList, "ALL",
                MainActivity.this, new DeptSelectionRecyclerAdapter.MyAdapterListener() {
            @Override
            public void onItemSelected(int position, View view) {
                bounceElement((TextView) view);
                selectedDepartment = deptlist.get(position);
                recyclerAdapter.setSelectedDepartment(deptlist.get(position));
                Intent intent = new Intent();
                intent.setAction("update_home_fragment_department");
                intent.putExtra("selectedDepartment", "" + deptlist.get(position));
                sendBroadcast(intent);
                recyclerAdapter.notifyDataSetChanged();
            }});
        dept_recycler.setAdapter(recyclerAdapter);

        //sport header
        sport_recycler = findViewById(R.id.main_sport_recycler);
        sport_recycler.setHasFixedSize(true);
        final LinearLayoutManager layoutManager1=new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        sport_recycler.setLayoutManager(layoutManager1);
        sportAdapter = new DeptSelectionRecyclerAdapter(recycler_sportList, "ALL", MainActivity.this, new DeptSelectionRecyclerAdapter.MyAdapterListener() {
            @Override
            public void onItemSelected(int position, View view) {
                bounceElement((TextView)view);
                selectedSport = sportList.get(position).toUpperCase();
                sportAdapter.setSelectedDepartment(recycler_sportList.get(position));
                Intent intent = new Intent();
                intent.setAction("update_home_fragment_department");
                intent.putExtra("selectedSport",""+sportList.get(position).toUpperCase());
                sendBroadcast(intent);
                sportAdapter.notifyDataSetChanged();
                sportSelection.revealMainView();
                selectedSportView.setText(selectedSport);
            }

        });
        sport_recycler.setAdapter(sportAdapter);
    }

    public void bounceElement(TextView textView){
        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        ButtonBounce interpolator = new ButtonBounce(0.2, 10);
        myAnim.setInterpolator(interpolator);
        textView.startAnimation(myAnim);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                flowingDrawer.openDrawer(Gravity.START);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    /*

    @Override
    public boolean onNavigationItemSelected(final MenuItem item) {
        final int id = item.getItemId();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (id == R.id.nav_home) {
                    Runtime.getRuntime().gc();
                    try {
                        navigationTabBar.setSelected(true);
                        navigationTabBar.setModelIndex(0);
                        navigationTabBar.setVisibility(View.VISIBLE);
                        lastViewFragment = 0;
                        Bundle arguments = new Bundle();
                        arguments.putString("target", "live");
                        homeFragment = new HomeFragment();
                        homeFragment.setArguments(arguments);
                        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.fragment_container, homeFragment);
                        fragmentTransaction.commit();
                        //fragmentTransaction.commit();
                        invalidateOptionsMenu();
                        getSupportActionBar().setTitle("Live");
                        selection_header.setVisibility(View.VISIBLE);
                    }catch(IllegalStateException ignored){
                        ignored.printStackTrace();
                    }

                } else if (id == R.id.nav_leaderboard) {
                    Runtime.getRuntime().gc();
                    try {
                        selection_header.setVisibility(View.GONE);
                        navigationTabBar.setVisibility(View.GONE);
                        lastViewFragment = 1;
                        leaderboardFragment = new LeaderboardFragment();
                        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.fragment_container, leaderboardFragment);
                        fragmentTransaction.commit();
                        //fragmentTransaction.commit();
                        invalidateOptionsMenu();
                        getSupportActionBar().setTitle("LeaderBoard");
                    }catch(IllegalStateException ignored){
                        ignored.printStackTrace();
                    }

        }else if(id==R.id.nav_schedule){
            Runtime.getRuntime().gc();
            try {
            selection_header.setVisibility(View.GONE);
            navigationTabBar.setVisibility(View.GONE);
            lastViewFragment=2;
            Bundle arguments = new Bundle();
            arguments.putBoolean("refresh", true);
            scheduleFragment=new ScheduleFragment();
            scheduleFragment.setArguments(arguments);
            FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container,scheduleFragment);
            fragmentTransaction.commit();
            //fragmentTransaction.commit();
            invalidateOptionsMenu();
            getSupportActionBar().setElevation(0);
            getSupportActionBar().setTitle("Schedule");
            }catch(IllegalStateException ignored){
                ignored.printStackTrace();
            }
        }
        else if(id==R.id.nav_following){
            Runtime.getRuntime().gc();
            try {
            selection_header.setVisibility(View.GONE);
            navigationTabBar.setVisibility(View.GONE);
            lastViewFragment=3;
            subscribeFragment =new SubscribeFragment();
            FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, subscribeFragment);
                fragmentTransaction.commit();
            //fragmentTransaction.commit();
            invalidateOptionsMenu();
            getSupportActionBar().setTitle("Following");
            }catch(IllegalStateException ignored){
                ignored.printStackTrace();
            }
        }
        else if(id==R.id.nav_events){
            Runtime.getRuntime().gc();
            try {
            selection_header.setVisibility(View.GONE);
            navigationTabBar.setVisibility(View.GONE);
            lastViewFragment=4;
            sportDetailsFragment = new SportDetailsFragment();
            FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, sportDetailsFragment);
                fragmentTransaction.commit();
            //fragmentTransaction.commit();
            invalidateOptionsMenu();
            getSupportActionBar().setTitle("Sports");
            }catch(IllegalStateException ignored){
                ignored.printStackTrace();
            }
        }else if(id == R.id.nav_registration){
            Runtime.getRuntime().gc();
            try {
                selection_header.setVisibility(View.GONE);
            navigationTabBar.setVisibility(View.GONE);
            lastViewFragment = 5;
            marathonRegistration = new MarathonRegistration();
            FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, marathonRegistration);
                fragmentTransaction.commit();
            //fragmentTransaction.commit();
            invalidateOptionsMenu();
            getSupportActionBar().setTitle("Marathon Registration");
            }catch(IllegalStateException ignored){
                ignored.printStackTrace();
            }
        }else if(id == R.id.searchBYroll){
            Runtime.getRuntime().gc();
            try {
                selection_header.setVisibility(View.GONE);
                navigationTabBar.setVisibility(View.GONE);
                lastViewFragment = 6;
                userSearch = new UserSearch();
                FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, userSearch);
                fragmentTransaction.commit();
                //fragmentTransaction.commit();
                invalidateOptionsMenu();
                getSupportActionBar().setTitle("Search User");
            }catch(IllegalStateException ignored){
                ignored.printStackTrace();
            }
        }

                }else if(id==R.id.nav_schedule){
                    Runtime.getRuntime().gc();
                    try {
                        selection_header.setVisibility(View.GONE);
                        navigationTabBar.setVisibility(View.GONE);
                        lastViewFragment=2;
                        Bundle arguments = new Bundle();
                        arguments.putBoolean("refresh", true);
                        scheduleFragment=new ScheduleFragment();
                        scheduleFragment.setArguments(arguments);
                        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.fragment_container,scheduleFragment);
                        fragmentTransaction.commit();
                        //fragmentTransaction.commit();
                        invalidateOptionsMenu();
                        getSupportActionBar().setElevation(0);
                        getSupportActionBar().setTitle("Schedule");
                    }catch(IllegalStateException ignored){
                        ignored.printStackTrace();
                    }
                }
                else if(id==R.id.nav_following){
                    Runtime.getRuntime().gc();
                    try {
                        selection_header.setVisibility(View.GONE);
                        navigationTabBar.setVisibility(View.GONE);
                        lastViewFragment=3;
                        subscribeFragment =new SubscribeFragment();
                        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.fragment_container, subscribeFragment);
                        fragmentTransaction.commit();
                        //fragmentTransaction.commit();
                        invalidateOptionsMenu();
                        getSupportActionBar().setTitle("Following");
                    }catch(IllegalStateException ignored){
                        ignored.printStackTrace();
                    }
                }
                else if(id==R.id.nav_events){
                    Runtime.getRuntime().gc();
                    try {
                        selection_header.setVisibility(View.GONE);
                        navigationTabBar.setVisibility(View.GONE);
                        lastViewFragment=4;
                        sportDetailsFragment = new SportDetailsFragment();
                        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.fragment_container, sportDetailsFragment);
                        fragmentTransaction.commit();
                        //fragmentTransaction.commit();
                        invalidateOptionsMenu();
                        getSupportActionBar().setTitle("Sports");
                    }catch(IllegalStateException ignored){
                        ignored.printStackTrace();
                    }
                }else if(id == R.id.nav_registration){
                    Runtime.getRuntime().gc();
                    try {
                        selection_header.setVisibility(View.GONE);
                        navigationTabBar.setVisibility(View.GONE);
                        lastViewFragment = 5;
                        marathonRegistration = new MarathonRegistration();
                        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.fragment_container, marathonRegistration);
                        fragmentTransaction.commit();
                        //fragmentTransaction.commit();
                        invalidateOptionsMenu();
                        getSupportActionBar().setTitle("Marathon Registration");
                    }catch(IllegalStateException ignored){
                        ignored.printStackTrace();
                    }
                }

                setDrawerTypeface();

                for(int i = 0; i < toolbar.getChildCount(); i++)
                { View view = toolbar.getChildAt(i);
                    if(view instanceof TextView) {
                        TextView textView = (TextView) view;
                        textView.setTypeface(Typeface.createFromAsset(getAssets(),  "fonts/HammersmithOneRegular.ttf"));
                    }
                }

            }
        },300);

        flowingDrawer.closeDrawer(Gravity.START);
        return true;
    }
*/
    public void setDrawerTypeface(){
        navigationView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                menuItems = new ArrayList<>(); // save Views in this array
                navigationView.getViewTreeObserver().removeOnGlobalLayoutListener(this); // remove the global layout listener
                for (int i = 0; i < menu.size(); i++) {// loops over menu items  to get the text view from each menu item
                    final MenuItem item = menu.getItem(i);
                    navigationView.findViewsWithText(menuItems, item.getTitle(), View.FIND_VIEWS_WITH_TEXT);
                }
                for (final View menuItem : menuItems) {// loops over the saved views and sets the font
                    ((TextView) menuItem).setTypeface(Typeface.createFromAsset(getAssets(),  "fonts/HammersmithOneRegular.ttf"), Typeface.BOLD);
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        Log.d("lastviewfragment", "" + (lastViewFragment == 0));
        if(flowingDrawer.isDrawerOpen(GravityCompat.START)) {
            flowingDrawer.closeDrawer(GravityCompat.START);
            flowingDrawer.closeDrawer(GravityCompat.START);
        }
        else if (lastViewFragment == 0) {
            super.onBackPressed();
        } else if (lastViewFragment != 0) {
            try {
                lastViewFragment = 0;
                navigationTabBar.setSelected(true);
                navigationTabBar.setModelIndex(0);
                navigationTabBar.setVisibility(View.VISIBLE);
                Bundle arguments = new Bundle();
                arguments.putString("target", "live");
                HomeFragment homeFragment = new HomeFragment();
                homeFragment.setArguments(arguments);
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, homeFragment);
                fragmentTransaction.commit();
                //fragmentTransaction.commit();
                getSupportActionBar().setTitle("Live");
                selection_header.setVisibility(View.VISIBLE);
            }catch(IllegalStateException ignored){
                ignored.printStackTrace();
            }

            //setDrawerTypeface();

            for (int i = 0; i < toolbar.getChildCount(); i++) {
                View view = toolbar.getChildAt(i);
                if (view instanceof TextView) {
                    TextView textView = (TextView) view;

                    textView.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/HammersmithOneRegular.ttf"));
                }
            }
        }
    }

    @Override
    public void onDestroy () {
        super.onDestroy();
        Runtime.getRuntime().gc();
    }
    private void createMenuList() {
        SlideMenuItem menuItem = new SlideMenuItem(ContentFragment.FIRST,R.drawable.ic_close);
        list.add(menuItem);
        SlideMenuItem menuItem0 = new SlideMenuItem(ContentFragment.LIVE,R.drawable.ic_action_live);
        list.add(menuItem0);
        SlideMenuItem menuItem1 = new SlideMenuItem(ContentFragment.LEADERBOARD, R.drawable.gold);
        list.add(menuItem1);
        SlideMenuItem menuItem2 = new SlideMenuItem(ContentFragment.SCHEDULE,R.drawable.ic_schedule);
        list.add(menuItem2);
        SlideMenuItem menuItem3 = new SlideMenuItem(ContentFragment.SEARCH, R.drawable.ic_searchuser);
        list.add(menuItem3);
        SlideMenuItem menuItem4 = new SlideMenuItem(ContentFragment.SPORTS, R.drawable.ic_basketball_img);
        list.add(menuItem4);
        SlideMenuItem menuItem5 = new SlideMenuItem(ContentFragment.GAME, R.drawable.ic_game);
        list.add(menuItem5);
        SlideMenuItem menuItem6 = new SlideMenuItem(ContentFragment.SIGNOUT,R.drawable.ic_signout3);
        list.add(menuItem6);
    }


    private void setActionBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                drawerLayout,         /* DrawerLayout object */
                toolbar,  /* nav drawer icon to replace 'Up' caret */
                R.string.navigation_drawer_open,  /* "open drawer" description */
                R.string.navigation_drawer_close  /* "close drawer" description */
        ) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                linearLayout.removeAllViews();
                linearLayout.invalidate();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                if (slideOffset > 0.6 && linearLayout.getChildCount() == 0)
                    viewAnimator.showMenuContent();
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        drawerLayout.addDrawerListener(drawerToggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_drawer, menu);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private ScreenShotable replaceLiveFragment(ScreenShotable screenShotable, int topPosition) {
        View view = findViewById(R.id.content_frame);
        int finalRadius = Math.max(view.getWidth(), view.getHeight());
        Animator animator = ViewAnimationUtils.createCircularReveal(view, 0, topPosition, 0, finalRadius);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.setDuration(ViewAnimator.CIRCULAR_REVEAL_ANIMATION_DURATION);

        animator.start();
        Runtime.getRuntime().gc();
        try {
            navigationTabBar.setSelected(true);
            navigationTabBar.setModelIndex(0);
            navigationTabBar.setVisibility(View.VISIBLE);
            lastViewFragment = 0;
            Bundle arguments = new Bundle();
            arguments.putString("target", "live");
            homeFragment = new HomeFragment();
            homeFragment.setArguments(arguments);
            //FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            //fragmentTransaction.replace(R.id.fragment_container, homeFragment);
            //fragmentTransaction.commit();
            //fragmentTransaction.commit();
            invalidateOptionsMenu();
            getSupportActionBar().setTitle("Live");
            selection_header.setVisibility(View.VISIBLE);
        }catch(IllegalStateException ignored){
            ignored.printStackTrace();
        }
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container,homeFragment).commit();
        return homeFragment;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private ScreenShotable replaceLeaderFragment(ScreenShotable screenShotable, int topPosition) {
        View view = findViewById(R.id.content_frame);
        int finalRadius = Math.max(view.getWidth(), view.getHeight());
        Animator animator = ViewAnimationUtils.createCircularReveal(view, 0, topPosition, 0, finalRadius);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.setDuration(ViewAnimator.CIRCULAR_REVEAL_ANIMATION_DURATION);

        animator.start();
        Runtime.getRuntime().gc();
        try {
            selection_header.setVisibility(View.GONE);
            navigationTabBar.setVisibility(View.GONE);
            lastViewFragment = 1;
            leaderboardFragment = new LeaderboardFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, leaderboardFragment);
            fragmentTransaction.commit();
            invalidateOptionsMenu();
            getSupportActionBar().setTitle("LeaderBoard");
        }catch(IllegalStateException ignored){
            ignored.printStackTrace();
        }

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container,leaderboardFragment).commit();
        return leaderboardFragment;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private ScreenShotable replaceScheduleFragment(ScreenShotable screenShotable, int topPosition) {
        View view = findViewById(R.id.content_frame);
        int finalRadius = Math.max(view.getWidth(), view.getHeight());
        Animator animator = ViewAnimationUtils.createCircularReveal(view, 0, topPosition, 0, finalRadius);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.setDuration(ViewAnimator.CIRCULAR_REVEAL_ANIMATION_DURATION);

        animator.start();
        Runtime.getRuntime().gc();
        try {
            selection_header.setVisibility(View.GONE);
            navigationTabBar.setVisibility(View.GONE);
            lastViewFragment=2;
            Bundle arguments = new Bundle();
            arguments.putBoolean("refresh", true);
            scheduleFragment=new ScheduleFragment();
            scheduleFragment.setArguments(arguments);
         //   FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
          //  fragmentTransaction.replace(R.id.fragment_container,scheduleFragment);
           // fragmentTransaction.commit();
            //fragmentTransaction.commit();
            invalidateOptionsMenu();
            getSupportActionBar().setElevation(0);
            getSupportActionBar().setTitle("Schedule");
        }catch(IllegalStateException ignored){
            ignored.printStackTrace();
        }
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, scheduleFragment).commit();
        return scheduleFragment;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private ScreenShotable replaceSearchFragment(ScreenShotable screenShotable, int topPosition) {
        View view = findViewById(R.id.content_frame);
        int finalRadius = Math.max(view.getWidth(), view.getHeight());
        Animator animator = ViewAnimationUtils.createCircularReveal(view, 0, topPosition, 0, finalRadius);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.setDuration(ViewAnimator.CIRCULAR_REVEAL_ANIMATION_DURATION);

        animator.start();
        Runtime.getRuntime().gc();
        try {
            selection_header.setVisibility(View.GONE);
            navigationTabBar.setVisibility(View.GONE);
            lastViewFragment=3;
            searchFragment = new UserSearch();
           // FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
            //fragmentTransaction.replace(R.id.fragment_container, subscribeFragment);
            //fragmentTransaction.commit();
            //fragmentTransaction.commit();
            invalidateOptionsMenu();
            getSupportActionBar().setTitle("Search");
        }catch(IllegalStateException ignored){
            ignored.printStackTrace();
        }
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container,searchFragment).commit();
        return searchFragment;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private ScreenShotable replaceSportsFragment(ScreenShotable screenShotable, int topPosition) {
        View view = findViewById(R.id.content_frame);
        int finalRadius = Math.max(view.getWidth(), view.getHeight());
        Animator animator = ViewAnimationUtils.createCircularReveal(view, 0, topPosition, 0, finalRadius);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.setDuration(ViewAnimator.CIRCULAR_REVEAL_ANIMATION_DURATION);

        animator.start();
        Runtime.getRuntime().gc();
        try {
            selection_header.setVisibility(View.GONE);
            navigationTabBar.setVisibility(View.GONE);
            lastViewFragment = 4;
            sportDetailsFragment = new SportDetailsFragment();
            // FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
            // fragmentTransaction.replace(R.id.fragment_container, sportDetailsFragment);
            // fragmentTransaction.commit();
            //fragmentTransaction.commit();
            invalidateOptionsMenu();
            getSupportActionBar().setTitle("Sports");
        } catch (IllegalStateException ignored) {
            ignored.printStackTrace();
        }
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, sportDetailsFragment).commit();
        return sportDetailsFragment;
    }

   @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
   @Override
    public ScreenShotable onSwitch(Resourceble slideMenuItem, ScreenShotable screenShotable, int position) {
        switch (slideMenuItem.getName()) {
            case ContentFragment.FIRST:
                return screenShotable;
            case ContentFragment.LIVE:
                return replaceLiveFragment(screenShotable, position);
            case ContentFragment.LEADERBOARD:
                return replaceLeaderFragment(screenShotable, position);
            case ContentFragment.SCHEDULE:
                return replaceScheduleFragment(screenShotable, position);
            case ContentFragment.SEARCH:
                return replaceSearchFragment(screenShotable, position);
            case ContentFragment.SPORTS:
                return replaceSportsFragment(screenShotable, position);
            case ContentFragment.SIGNOUT: {
                myDatabase = new MyDatabase(this);
                myDatabase.deleteAll();
                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
                return screenShotable;
            }
            default:
                return replaceLiveFragment(screenShotable, position);
        }
    }
    @Override
    public void disableHomeButton() {
        getSupportActionBar().setHomeButtonEnabled(false);

    }


    public void enableHomeButton() {
        getSupportActionBar().setHomeButtonEnabled(true);
        drawerLayout.closeDrawers();

    @Override
    public void onBackPressed() {
        sportSelection.revealMainView();
        Log.d("lastviewfragment", "" + (lastViewFragment == 0));
        if(flowingDrawer.isDrawerOpen(GravityCompat.START)) {
            flowingDrawer.closeDrawer(GravityCompat.START);
        }
        else if (lastViewFragment == 0) {
            super.onBackPressed();
        } else if (lastViewFragment != 0) {
            try {
                lastViewFragment = 0;
            navigationTabBar.setSelected(true);
            navigationTabBar.setModelIndex(0);
            navigationTabBar.setVisibility(View.VISIBLE);
            Bundle arguments = new Bundle();
            arguments.putString("target", "live");
            HomeFragment homeFragment = new HomeFragment();
            homeFragment.setArguments(arguments);
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, homeFragment);
                fragmentTransaction.commit();
            //fragmentTransaction.commit();
            getSupportActionBar().setTitle("Live");
            navigationView.setCheckedItem(R.id.nav_home);
            selection_header.setVisibility(View.VISIBLE);
            }catch(IllegalStateException ignored){
                ignored.printStackTrace();
            }

    }

    @Override
    public void addViewToContainer(View view) {
        linearLayout.addView(view);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

}
