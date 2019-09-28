package org.spider.sportsfete.Schedule;


import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.Gson;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;
import com.twotoasters.jazzylistview.JazzyHelper;
import com.twotoasters.jazzylistview.recyclerview.JazzyRecyclerViewScrollListener;

import org.spider.sportsfete.API.ApiInterface;
import org.spider.sportsfete.API.EventDetailsPOJO;
import org.spider.sportsfete.DatabaseHelper;
import org.spider.sportsfete.DepartmentUpdateCallback;
import org.spider.sportsfete.EventInfo.EventInfoActivity;
import org.spider.sportsfete.WrapContentLinearLayoutManager;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.functions.Action1;
import org.spider.sportsfete.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Day2Fragment extends Fragment implements Callback<List<EventDetailsPOJO>>, SwipeRefreshLayout.OnRefreshListener{

    private static final String TAG="Day2Fragment";
    List<EventDetailsPOJO> eventList;
    List<EventDetailsPOJO> filter_eventList;
    List<EventDetailsPOJO> temp_filter_eventList;
    Day2EventsDetailRecyclerAdapter eventRecyclerAdapter;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    Call<List<EventDetailsPOJO>> call;
    ApiInterface apiInterface;
    DatabaseHelper helper;
    Dao<EventDetailsPOJO,Long> dao;
    int selectedDay=1;
    String selectedDept, selectedSport;
    Context context;
    SimpleDateFormat simpleDateFormat;
    String formattedDate;
    SharedPreferences prefs;
    DepartmentUpdateCallback departmentUpdateCallback;
    boolean isVisibleToUser=false;

    private int prevSize = 0, cueSize = 0;
    private static int a=0;

    private int currentTransitionEffect = JazzyHelper.TILT;
    JazzyRecyclerViewScrollListener jazzyRecyclerViewScrollListener;

    BroadcastReceiver receiver = new BroadcastReceiver(){
        @Override
        public void onReceive(Context contextBroadcast, Intent intent) {
            //updateAdapter();
            new Filter().execute();
        }
    };

    BroadcastReceiver receiver2 = new BroadcastReceiver(){
        @Override
        public void onReceive(Context contextBroadcast, Intent intent) {
            recyclerView.smoothScrollToPosition(0);
        }
    };

    public Day2Fragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_day_2, container, false);
    }

    @Override
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);

        context=getContext();

        if(getActivity()!=null) {

            prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
            apiInterface = ApiInterface.retrofit.create(ApiInterface.class);
            eventList = new ArrayList<>();
            filter_eventList = new ArrayList<>();
            temp_filter_eventList = new ArrayList<>();

            getSelectedDept();
            getSelectedSport();

            try {
                helper = OpenHelperManager.getHelper(context, DatabaseHelper.class);
                dao = helper.getEventsDetailDao();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            recyclerView = (RecyclerView) getActivity().findViewById(R.id.day_2_recycler_view);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new WrapContentLinearLayoutManager(getActivity()));

            jazzyRecyclerViewScrollListener = new JazzyRecyclerViewScrollListener();
            jazzyRecyclerViewScrollListener.setTransitionEffect(currentTransitionEffect);
            //recyclerView.setOnScrollListener(jazzyRecyclerViewScrollListener);

            Log.d(TAG, "onViewCreated: selectedDept" + selectedDept);

            eventRecyclerAdapter = new Day2EventsDetailRecyclerAdapter(filter_eventList, getActivity());
            recyclerView.setAdapter(eventRecyclerAdapter);

            swipeRefreshLayout = (SwipeRefreshLayout) getActivity().findViewById(R.id.day_2_swipe_to_refresh);
            swipeRefreshLayout.setOnRefreshListener(this);

            //updateAdapter();
            new LoadEventData().execute();
/*
        if(ScheduleFragment.refresh_check) {
            if (bundle == null) {
                swipeRefreshLayout.setRefreshing(true);
                onRefresh();
            }
        }else {

        }
*/      //TODO:set sportsfete date
            if (bundle == null) {
                if (bundle == null) {

                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ssZZZZ");
                    Date gmt = null;
                    try {
                        gmt = formatter.parse("2018-03-16T18:23:20+0000");
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    long millisecondsSinceEpoch0 = gmt.getTime();
                    long currentTimeEpoch = System.currentTimeMillis();

                    if (millisecondsSinceEpoch0 > currentTimeEpoch) {
                        swipeRefreshLayout.setRefreshing(true);
                        onRefresh();
                    }
                }

            }

            setClickListener();
            if(a==0)
            {onRefresh();a++;}
        }
    }


    private void putSelectedDay() {
        Log.d(TAG, "putSelectedDay: "+selectedDay);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("SELECTED_DAY",selectedDay);
        editor.apply();
    }

    void setClickListener(){
        rx.Observable<String> observable= eventRecyclerAdapter.getPositionClicks();
        observable.subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                EventDetailsPOJO selectedEvent=filter_eventList.get(Integer.parseInt(s));
                Intent intent = new Intent(context, EventInfoActivity.class);
                intent.putExtra("SELECTED_EVENT", new Gson().toJson(selectedEvent));
                startActivity(intent);
            }
        });
    }

    @Override
    public void onResponse(Call<List<EventDetailsPOJO>> call, final Response<List<EventDetailsPOJO>> response) {
        //swipeRefreshLayout.setRefreshing(false);
        final List<EventDetailsPOJO> responseList=response.body();
        //swipeRefreshLayout.setRefreshing(false);
        if(responseList!=null && responseList.size() > 0){
                Log.d(TAG, "onResponse:response received ");
                Thread thread=new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            //TableUtils.clearTable(helper.getConnectionSource(), EventDetailsPOJO.class);
                            DeleteBuilder<EventDetailsPOJO, Long> deleteBuilder = dao.deleteBuilder();
                            deleteBuilder.where().eq("day",1);
                            deleteBuilder.delete();
                            for (int i = 0; i <responseList.size() ; i++) {
                                dao.create(responseList.get(i));
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        if(getActivity()!=null)
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    //putEventsLastUpdate();
                                    //swipeRefreshLayout.setRefreshing(false);
                                    eventList.clear();
                                    eventList.addAll(response.body());
                                    new LoadEventData().execute();
                                    //updateAdapter();
                                    //departmentUpdateCallback.updateScheduleFragment();
                                }
                            });
                    }
                });
                thread.start();
        }
        else {
            swipeRefreshLayout.setRefreshing(false);
            Log.d("responseList","is null or empty");
        }
        Log.d(TAG, "onResponse: ");
    }

    @Override
    public void onFailure(Call<List<EventDetailsPOJO>> call, Throwable t) {
        //Log.d(TAG, "onFailure: "+t.toString());
        t.printStackTrace();
        swipeRefreshLayout.setRefreshing(false);
        Toast.makeText(context, "Device Offline", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onRefresh() {
        call = apiInterface.getSchedule2(1);
        call.enqueue(this);
        //loadingView.startAnimation();
        //loadingView.setVisibility(View.VISIBLE);
        swipeRefreshLayout.setRefreshing(true);
        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "Day 2 Schedule fetched");
        mFirebaseAnalytics.logEvent("Schedule",bundle);
    }

    public void getSelectedDept() {
        if (getActivity() != null) {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
            selectedDept = prefs.getString("DEPT", "ALL");
        }
    }

    public void getSelectedSport() {
        if (getActivity() != null) {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
            selectedSport = prefs.getString("SPORT", "ALL");
        }
    }

    public void LoadEventsfromDB(){
        Log.d("SEL dept + sport",selectedDept+" "+ selectedSport);
        List<EventDetailsPOJO>dbList,newDbList=new ArrayList<>();
        try {
            QueryBuilder<EventDetailsPOJO,Long> queryBuilder= null;
            queryBuilder = helper.getEventsDetailDao().queryBuilder();
            Log.d(TAG, "updateAdapter: "+selectedDay);
            queryBuilder.where().eq("day",selectedDay);
            dbList=queryBuilder.query();
            Log.d("db size","-----------"+dbList.size());

            eventList.clear();
            eventList.addAll(dbList);

            Collections.sort(eventList, new Comparator<EventDetailsPOJO>(){
                @Override
                public int compare(EventDetailsPOJO o1, EventDetailsPOJO o2) {
                    return (int) (o1.getStartTime() - o2.getStartTime());
                }
            });

            //filter_eventList.addAll(dbList);
            temp_filter_eventList.addAll(dbList);
            filterList();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateAdapter(){

        getSelectedSport();
        getSelectedDept();
        filterList();

    }

    @SuppressLint("LongLogTag")
    public void filterList(){

        getSelectedSport();
        getSelectedDept();

        prevSize = filter_eventList.size();
        //temp_filter_eventList.addAll(filter_eventList);

        try{

            if(selectedDept!=null&&selectedSport!=null){
                if(selectedDept.replaceAll("\\d","").equalsIgnoreCase("ALL")
                        &&(selectedSport.replaceAll("\\d","").equalsIgnoreCase("ALL"))){
                    temp_filter_eventList.clear();
                    temp_filter_eventList.addAll(eventList);
                }else if(selectedDept.replaceAll("\\d","").equalsIgnoreCase("ALL")
                        &&!selectedSport.replaceAll("\\d","").equalsIgnoreCase("ALL")){

                    temp_filter_eventList.clear();
                    for (EventDetailsPOJO statusEventDetailsPOJO : new ArrayList<EventDetailsPOJO>(eventList)) {
                        if (statusEventDetailsPOJO.getId().toUpperCase().replace("Q","").replaceAll("\\d","").equalsIgnoreCase(selectedSport)) {
                            temp_filter_eventList.add(statusEventDetailsPOJO);
                        }
                    }

                }else if(!selectedDept.equalsIgnoreCase("ALL")
                        &&selectedSport.equalsIgnoreCase("ALL")){

                    temp_filter_eventList.clear();
                    for (EventDetailsPOJO statusEventDetailsPOJO : new ArrayList<EventDetailsPOJO>(eventList)) {
                        if (statusEventDetailsPOJO.getDept1().replaceAll("\\d","").equalsIgnoreCase(selectedDept)
                                || statusEventDetailsPOJO.getDept2().replaceAll("\\d","").equalsIgnoreCase(selectedDept)) {
                            temp_filter_eventList.add(statusEventDetailsPOJO);
                        }
                    }

                }else{
                    temp_filter_eventList.clear();
                    for (EventDetailsPOJO statusEventDetailsPOJO : new ArrayList<EventDetailsPOJO>(eventList)) {
                        if ((statusEventDetailsPOJO.getDept1().replaceAll("\\d","").equalsIgnoreCase(selectedDept)
                                || statusEventDetailsPOJO.getDept2().replaceAll("\\d","").equalsIgnoreCase(selectedDept))
                                &&(statusEventDetailsPOJO.getId().replace("Q","").replaceAll("\\d","").equalsIgnoreCase(selectedSport))) {
                            temp_filter_eventList.add(statusEventDetailsPOJO);
                        }
                    }
                }

                cueSize = temp_filter_eventList.size();

                Log.d(TAG+"filtered size",""+prevSize+" "+cueSize);
            }

        }catch(ConcurrentModificationException e){
            e.printStackTrace();
        }
    }

    private class LoadEventData extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            LoadEventsfromDB();
            return null;
        }

        @Override
        protected void onPostExecute(Void v){
            if (eventRecyclerAdapter != null) {
                filter_eventList.clear();
                eventRecyclerAdapter.notifyDataSetChanged();
                filter_eventList.addAll(temp_filter_eventList);
                eventRecyclerAdapter.notifyDataSetChanged();
                //eventRecyclerAdapter.notifyItemRangeRemoved(0,prevSize);
                //eventRecyclerAdapter.notifyItemRangeInserted(0, cueSize);
            }
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    private class Filter extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            updateAdapter();
            return null;
        }

        @Override
        protected void onPostExecute(Void v){
            swipeRefreshLayout.setRefreshing(false);
            if (eventRecyclerAdapter != null) {
                filter_eventList.clear();
                eventRecyclerAdapter.notifyDataSetChanged();
                filter_eventList.addAll(temp_filter_eventList);
                eventRecyclerAdapter.notifyDataSetChanged();
                //eventRecyclerAdapter.notifyItemRangeRemoved(0, prevSize);
                //eventRecyclerAdapter.notifyItemRangeInserted(0, cueSize);
            }
        }
    }

    private void putEventsLastUpdate() {
        simpleDateFormat = new SimpleDateFormat("EEEE, MMMM d, h:mm a", Locale.ENGLISH);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
        formattedDate = simpleDateFormat.format(System.currentTimeMillis());
        prefs.edit().putString("EVENTS_LAST_UPDATED","Last Updated at : "+ formattedDate).apply();
    }

    @Override
    public void onResume(){
        IntentFilter filter = new IntentFilter();
        IntentFilter filter2 = new IntentFilter();
        filter2.addAction("scroll_to_top1");
        filter.addAction("update_department");
        if(getActivity()!=null) {
            getActivity().registerReceiver(receiver, filter);
            getActivity().registerReceiver(receiver2, filter2);
        }
        super.onResume();
    }


    @Override
    public void onPause() {
        super.onPause();
        if(getActivity()!=null) {
            getActivity().unregisterReceiver(receiver);
            getActivity().unregisterReceiver(receiver2);
        }
        if(isVisibleToUser){
            putSelectedDay();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser=isVisibleToUser;
    }

    @Override
    public void onDestroyView(){
        Runtime.getRuntime().gc();
        super.onDestroyView();
    }
}
