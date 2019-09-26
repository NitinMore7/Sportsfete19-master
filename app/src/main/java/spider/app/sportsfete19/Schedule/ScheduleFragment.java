package spider.app.sportsfete19.Schedule;


import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import spider.app.sportsfete19.ButtonBounce;
import spider.app.sportsfete19.DepartmentUpdateCallback;
import spider.app.sportsfete19.R;
import yalantis.com.sidemenu.interfaces.ScreenShotable;

/**
 * A simple {@link Fragment} subclass.
 */
public class ScheduleFragment extends Fragment implements ScreenShotable {

    private static final String TAG="ScheduleFragment";
    String selectedDept;
    String[] dialogItems;
    Context context;
    DepartmentUpdateCallback departmentUpdateCallback;
    List deptList;
    SharedPreferences prefs;
    String lastUpdatedTimestamp;
    ViewGroup viewGroup;
    int selectedDay;
    int index;
    ScheduleViewPagerAdapter scheduleViewPagerAdapter;
    ViewPager viewPager;
    RecyclerView recyclerView;

    String[] deptArraySharedPreference=new String[15];
    String[] sportArraySharedPreference=new String[31];
    List<String> deptlist, recycler_deptList, sportList, recycler_sportList;
    DeptSelectionRecyclerAdapter recyclerAdapter, sportAdapter;

    public String selectedSport = "ALL";
    public static boolean refresh_check = true;

    public ScheduleFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewGroup = container;
        return inflater.inflate(R.layout.fragment_schedule, container, false);
    }

    TabLayout tabLayout;

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context=getContext();


        Bundle arguments = getArguments();
        refresh_check = arguments.getBoolean("refresh");
            if(!arguments.getBoolean("refresh",true)) {
                Log.d("boolean------------",false+"");
            }

        deptArraySharedPreference=getResources().getStringArray(R.array.filter_department_array);
        deptlist = new ArrayList<>();
        recycler_deptList = new ArrayList<>();
        sportList = new ArrayList<>();
        recycler_sportList = new ArrayList<>();

        for ( int i = deptArraySharedPreference.length-1; i >=0; i--)
            deptlist.add(deptArraySharedPreference[i]);

        deptArraySharedPreference=getResources().getStringArray(R.array.department_array);
        for ( int i = deptArraySharedPreference.length-1; i >=0; i--)
            recycler_deptList.add(deptArraySharedPreference[i]);

        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());

        getEventsLastUpdate();

        showEventsLastUpdate();

        getSelectedDay();

        index=selectedDay-1;

        dialogItems = getResources().getStringArray(R.array.department_array);
        deptList=new ArrayList();
        deptList= Arrays.asList(dialogItems);
        viewPager = (ViewPager)view. findViewById(R.id.schedule_view_pager);
        scheduleViewPagerAdapter = new ScheduleViewPagerAdapter(getFragmentManager());
        viewPager.setAdapter(scheduleViewPagerAdapter);
        tabLayout = (TabLayout) view.findViewById(R.id.schedule_tab_layout);
        tabLayout.setupWithViewPager(viewPager);
        TabLayout.Tab currentTab = tabLayout.getTabAt(0);
        if (currentTab != null) {
            View customView = currentTab.getCustomView();
            if (customView != null) {
                customView.setSelected(true);
            }
            currentTab.select();
            viewPager.setCurrentItem(0);
        }

        LinearLayout tabLay = (LinearLayout)((ViewGroup) tabLayout.getChildAt(0)).getChildAt(0);
        TextView tabTextView = (TextView) tabLay.getChildAt(1);
        tabTextView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        final Typeface hammersmithOnefont = Typeface.createFromAsset(getActivity().getAssets(),  "fonts/HammersmithOneRegular.ttf");
        tabTextView.setTypeface(hammersmithOnefont);
        tabLay = (LinearLayout)((ViewGroup) tabLayout.getChildAt(0)).getChildAt(1);
        tabTextView = (TextView) tabLay.getChildAt(1);
        tabTextView.setTypeface(hammersmithOnefont);

        tabLay = (LinearLayout)((ViewGroup) tabLayout.getChildAt(0)).getChildAt(2);
        tabTextView = (TextView) tabLay.getChildAt(1);
        tabTextView.setTypeface(hammersmithOnefont);

        tabLay = (LinearLayout)((ViewGroup) tabLayout.getChildAt(0)).getChildAt(3);
        tabTextView = (TextView) tabLay.getChildAt(1);
        tabTextView.setTypeface(hammersmithOnefont);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                LinearLayout tabLay = (LinearLayout)((ViewGroup) tabLayout.getChildAt(0)).getChildAt(tab.getPosition());
                TextView tabTextView = (TextView) tabLay.getChildAt(1);
                tabTextView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                tabTextView.setTypeface(hammersmithOnefont);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                LinearLayout tabLay = (LinearLayout)((ViewGroup) tabLayout.getChildAt(0)).getChildAt(tab.getPosition());
                TextView tabTextView = (TextView) tabLay.getChildAt(1);
                tabTextView.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                tabTextView.setTypeface(hammersmithOnefont);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                Intent intent = new Intent();
                intent.setAction("scroll_to_top"+tab.getPosition());
                getActivity().sendBroadcast(intent);
            }
        });

        selectedDept = getSelectedDept();
        selectedSport = getSelectedSport();

        //sport filter list
        sportArraySharedPreference=getResources().getStringArray(R.array.filter_sport_array);
        for ( int i = sportArraySharedPreference.length-1; i >=0; i--)
            sportList.add(sportArraySharedPreference[i]);

        sportArraySharedPreference=getResources().getStringArray(R.array.short_sport_array);
        for ( int i = sportArraySharedPreference.length-1; i >=0; i--)
            recycler_sportList.add(sportArraySharedPreference[i]);

        recyclerView = (RecyclerView) getActivity().findViewById(R.id.dept_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,true));
        recyclerAdapter = new DeptSelectionRecyclerAdapter(recycler_deptList, selectedDept,
                getActivity(), new DeptSelectionRecyclerAdapter.MyAdapterListener() {
            @Override
            public void onItemSelected(int position, View view1) {
                if(position!=-1) {
                    selectedDept = deptlist.get(position);
                    bounceElement((TextView) view1);
                    Log.d("selected dept", selectedDept);
                    recyclerAdapter.setSelectedDepartment(selectedDept);
                    onUpdateDept(selectedDept);
                    recyclerAdapter.notifyDataSetChanged();
                    Intent intent = new Intent();
                    intent.setAction("update_department");
                    getActivity().sendBroadcast(intent);

                    recyclerAdapter.notifyDataSetChanged();
                }
        }});

        recyclerView.setAdapter(recyclerAdapter);

        //selectedsport = ids of sports
        //recycler sport= value to be displayed in recyclerview
        sportAdapter = new DeptSelectionRecyclerAdapter(recycler_sportList,
                recycler_sportList.get(sportList.indexOf(selectedSport)), getActivity(), new DeptSelectionRecyclerAdapter.MyAdapterListener() {
            @Override
            public void onItemSelected(int position, View view) {
                if(position!=-1) {
                    bounceElement((TextView) view);
                    selectedSport = sportList.get(position);
                    Log.d("selected sport", selectedSport);
                    sportAdapter.setSelectedDepartment(recycler_sportList.get(position));
                    onUpdateSport(selectedSport);
                    Intent intent = new Intent();
                    intent.setAction("update_department");
                    intent.putExtra("selectedSport", "" + sportList.get(position).toUpperCase());
                    getActivity().sendBroadcast(intent);
                    sportAdapter.notifyDataSetChanged();
                }
            }
        });

        Log.d("selecteddept",""+selectedDept);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(selectedDept!=null)
                recyclerView.smoothScrollToPosition(deptlist.indexOf(selectedDept));
                else {
                    recyclerView.smoothScrollToPosition(14);
                }
            }
        },300);


    }

    public void bounceElement(TextView textView){
        final Animation myAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.bounce);
        ButtonBounce interpolator = new ButtonBounce(0.2, 10);
        myAnim.setInterpolator(interpolator);
        textView.startAnimation(myAnim);
    }

    public void flipAnimation(final RecyclerView VrecyclerView, final RecyclerView INVrecyclerView){
        ObjectAnimator anim = (ObjectAnimator) AnimatorInflater.loadAnimator(getActivity(), R.animator.flipping);
        anim.setTarget(VrecyclerView);
        anim.setDuration(500);
        anim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                VrecyclerView.setVisibility(View.GONE);
                INVrecyclerView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        anim.start();
    }


    public String getSelectedDept(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        selectedDept= prefs.getString("DEPT","ALL");
        return selectedDept;
    }


    public String getSelectedSport(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        selectedSport= prefs.getString("SPORT","ALL");
        return selectedSport;
    }

    private void getSelectedDay() {
        selectedDay=prefs.getInt("SELECTED_DAY",1);
    }

    public void onUpdateDept(String updatedDept){
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("DEPT",updatedDept);
        editor.putInt("DEPT_INDEX",deptlist.indexOf(selectedDept));
        editor.apply();
    }

    public void onUpdateSport(String updatedSport){
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("SPORT",updatedSport);
        editor.apply();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //inflater.inflate(R.menu.actionbar_department_selector, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //if(item.getItemId()==R.id.dropdown) showDialog();
        return true;
    }

    private void getEventsLastUpdate(){
        lastUpdatedTimestamp=prefs.getString("EVENTS_LAST_UPDATED","PULL DOWN TO REFRESH");

    }

    private void showEventsLastUpdate(){
        if(viewGroup.getContext()!=null);
            //Snackbar.make(viewGroup, lastUpdatedTimestamp,Snackbar.LENGTH_SHORT).show();
    }

    private void putSelectedDay() {
        Log.d(TAG, "putSelectedDay: "+selectedDay);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("SELECTED_DAY",selectedDay);
        editor.apply();
    }

    @Override
    public void onPause() {
        super.onPause();
        selectedDay=viewPager.getCurrentItem()+1;
        putSelectedDay();
    }

    @Override
    public void onDestroy(){
        Runtime.getRuntime().gc();
        super.onDestroy();
    }

    @Override
    public void takeScreenShot() {

    }

    @Override
    public Bitmap getBitmap() {
        return null;
    }
}
