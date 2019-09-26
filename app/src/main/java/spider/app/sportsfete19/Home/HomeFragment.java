package spider.app.sportsfete19.Home;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityOptionsCompat;
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
import com.twotoasters.jazzylistview.JazzyHelper;
import com.twotoasters.jazzylistview.recyclerview.JazzyRecyclerViewScrollListener;
import com.yalantis.phoenix.PullToRefreshView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.functions.Action1;
import spider.app.sportsfete19.API.ApiInterface;
import spider.app.sportsfete19.API.StatusEventDetailsPOJO;
import spider.app.sportsfete19.DepartmentUpdateCallback;
import spider.app.sportsfete19.EventInfo.EventInfoActivity;
import spider.app.sportsfete19.MainActivity;
import spider.app.sportsfete19.R;
import spider.app.sportsfete19.Schedule.StatusEventsDetailRecyclerAdapter;
import spider.app.sportsfete19.WrapContentLinearLayoutManager;
import yalantis.com.sidemenu.interfaces.ScreenShotable;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements Callback<List<StatusEventDetailsPOJO>>, SwipeRefreshLayout.OnRefreshListener, ScreenShotable {

    private static final String TAG="HomeFragment";
    List<StatusEventDetailsPOJO> eventList;
    List<StatusEventDetailsPOJO> filter_eventList;
    List<StatusEventDetailsPOJO> temp_filter_eventList;
    StatusEventsDetailRecyclerAdapter eventRecyclerAdapter;
    RecyclerView recyclerView;
    PullToRefreshView swipeRefreshLayout;
    Call<List<StatusEventDetailsPOJO>> call;
    ApiInterface apiInterface;
    Context context;
    SharedPreferences prefs;
    String lastUpdatedTimestamp;
    SimpleDateFormat simpleDateFormat;
    String formattedDate;
    View view;
    ViewGroup viewGroup;
    DepartmentUpdateCallback departmentUpdateCallback;
    String status = "live";
    private int currentTransitionEffect = JazzyHelper.TILT;
    JazzyRecyclerViewScrollListener jazzyRecyclerViewScrollListener;

    private int prevSize = 0, cueSize = 0;

    BroadcastReceiver receiver = new BroadcastReceiver(){
        @Override
        public void onReceive(Context contextBroadcast, final Intent intent) {
            //recyclerView.smoothScrollToPosition(0);
            new Filter().execute();
            //filterList();
        }
    };

    public void filterList() {
        MainActivity mainActivity = (MainActivity) getActivity();
        if ((mainActivity) != null) {
            prevSize = filter_eventList.size();
            //temp_filter_eventList.addAll(filter_eventList);

            try {
                if (mainActivity.selectedDepartment == null) {
                    (mainActivity).selectedDepartment = "ALL";
                    temp_filter_eventList.clear();
                    temp_filter_eventList.addAll(eventList);
                } else if ((mainActivity).selectedDepartment.equalsIgnoreCase("ALL")) {
                    temp_filter_eventList.clear();
                    temp_filter_eventList.addAll(eventList);
                }
                if ((mainActivity).selectedSport == null) {
                    (mainActivity).selectedSport = "ALL";
                    temp_filter_eventList.clear();
                    temp_filter_eventList.addAll(eventList);
                } else if ((mainActivity).selectedSport.equalsIgnoreCase("ALL")) {
                    temp_filter_eventList.clear();
                    temp_filter_eventList.addAll(eventList);
                }

                if ((mainActivity).selectedDepartment != null && (mainActivity).selectedSport != null) {
                    if ((mainActivity).selectedDepartment.replaceAll("\\d", "").equalsIgnoreCase("ALL")
                            && (mainActivity).selectedSport.replaceAll("\\d", "").equalsIgnoreCase("ALL")) {
                        temp_filter_eventList.clear();
                        temp_filter_eventList.addAll(eventList);
                    } else if ((mainActivity).selectedDepartment.replaceAll("\\d", "").equalsIgnoreCase("ALL")
                            && !(mainActivity).selectedSport.replaceAll("\\d", "").equalsIgnoreCase("ALL")) {

                        temp_filter_eventList.clear();
                        for (StatusEventDetailsPOJO statusEventDetailsPOJO : eventList) {
                            if (statusEventDetailsPOJO.getId().toUpperCase().replace("Q", "").replaceAll("\\d", "").equalsIgnoreCase((mainActivity).selectedSport)) {
                                temp_filter_eventList.add(statusEventDetailsPOJO);
                            }
                        }

                    } else if (!(mainActivity).selectedDepartment.replaceAll("\\d", "").equalsIgnoreCase("ALL")
                            && (mainActivity).selectedSport.replaceAll("\\d", "").equalsIgnoreCase("ALL")) {

                        temp_filter_eventList.clear();
                        for (StatusEventDetailsPOJO statusEventDetailsPOJO : eventList) {
                            if (statusEventDetailsPOJO.getEliminationType().matches("individual"))
                                temp_filter_eventList.add(statusEventDetailsPOJO);
                            else if (statusEventDetailsPOJO.getDept1().replaceAll("\\d", "").equalsIgnoreCase((mainActivity).selectedDepartment)
                                    || statusEventDetailsPOJO.getDept2().replaceAll("\\d", "").equalsIgnoreCase((mainActivity).selectedDepartment)) {
                                temp_filter_eventList.add(statusEventDetailsPOJO);
                            }
                        }

                    } else {
                        temp_filter_eventList.clear();
                        for (StatusEventDetailsPOJO statusEventDetailsPOJO : eventList) {
                            if (statusEventDetailsPOJO.getEliminationType().matches("individual") && (statusEventDetailsPOJO.getId().toUpperCase().replace("Q", "").replaceAll("\\d", "").equalsIgnoreCase((mainActivity).selectedSport)))
                                temp_filter_eventList.add(statusEventDetailsPOJO);
                            else if ((statusEventDetailsPOJO.getDept1().replaceAll("\\d", "").equalsIgnoreCase((mainActivity).selectedDepartment)
                                    || statusEventDetailsPOJO.getDept2().replaceAll("\\d", "").equalsIgnoreCase((mainActivity).selectedDepartment))
                                    && (statusEventDetailsPOJO.getId().toUpperCase().replaceAll("\\d", "").replace("Q", "").equalsIgnoreCase((mainActivity).selectedSport))) {
                                temp_filter_eventList.add(statusEventDetailsPOJO);
                            }
                        }
                    }
                }

                cueSize = temp_filter_eventList.size();
            } catch (ConcurrentModificationException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void takeScreenShot() {


    }

    @Override
    public Bitmap getBitmap() {
        return null;
    }

    private class Filter extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            filterList();
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


    public HomeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewGroup = container;
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context=getContext();
        this.view=view;
        prefs = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());

        eventList=new ArrayList<>();
        filter_eventList=new ArrayList<>();
        temp_filter_eventList = new ArrayList<>();

        getEventsLastUpdate();
        showEventsLastUpdate();

        Bundle arguments = getArguments();
        String target = arguments.getString("target");
        status = target;

        apiInterface = ApiInterface.retrofit.create(ApiInterface.class);
        recyclerView= (RecyclerView) view.findViewById(R.id.home_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new WrapContentLinearLayoutManager(getActivity()));

        jazzyRecyclerViewScrollListener = new JazzyRecyclerViewScrollListener();
        jazzyRecyclerViewScrollListener.setTransitionEffect(currentTransitionEffect);
        //recyclerView.setOnScrollListener(jazzyRecyclerViewScrollListener);

        eventRecyclerAdapter=new StatusEventsDetailRecyclerAdapter(filter_eventList, getActivity(), new StatusEventsDetailRecyclerAdapter.MyAdapterListener() {
            @Override
            public void onItemSelected(int position, View view1, View view2, View view3, View view4) {
                StatusEventDetailsPOJO selectedEvent=filter_eventList.get(position);
                Intent intent = new Intent(context, EventInfoActivity.class);
                intent.putExtra("SELECTED_EVENT", new Gson().toJson(selectedEvent));
                startActivity(intent);
            }
        });

        recyclerView.setAdapter(eventRecyclerAdapter);

        swipeRefreshLayout= (PullToRefreshView) view.findViewById(R.id.home_swipe_to_refresh);
        swipeRefreshLayout.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                call = apiInterface.getEventByStatus(status);
                call.enqueue(HomeFragment.this);
                swipeRefreshLayout.setRefreshing(true);
                FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
                Bundle bundle = new Bundle();
                bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "Live events Fetched");
                mFirebaseAnalytics.logEvent("LiveEvents",bundle);
            }
        });
        swipeRefreshLayout.setRefreshing(true);

        onRefresh();
        setClickListener();

    }

    void setClickListener(){
        rx.Observable<String> observable= eventRecyclerAdapter.getPositionClicks();
        observable.subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                StatusEventDetailsPOJO selectedEvent=filter_eventList.get(Integer.parseInt(s));
                Intent intent = new Intent(context, EventInfoActivity.class);
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        getActivity(),
                        view,
                        "scene_transition");
                intent.putExtra("SELECTED_EVENT", new Gson().toJson(selectedEvent));
                startActivity(intent);
            }
        });
    }

    @Override
    public void onResponse(Call<List<StatusEventDetailsPOJO>> call, Response<List<StatusEventDetailsPOJO>> response) {
        final List<StatusEventDetailsPOJO> responseList=response.body();
        Log.d("response","");
        if(responseList!=null){
            eventList.clear();

            putEventsLastUpdate();
            getEventsLastUpdate();
            showEventsLastUpdate();


            for (int i = 0; i <responseList.size() ; i++)
                eventList.add(responseList.get(i));

            if(eventList.size()==0 && viewGroup.getContext()!=null){
                Snackbar.make(viewGroup,"There are currently no "+status+" events!", Snackbar.LENGTH_LONG).show();
            }else{
                Collections.sort(eventList, new Comparator<StatusEventDetailsPOJO>(){
                    @Override
                    public int compare(StatusEventDetailsPOJO o1, StatusEventDetailsPOJO o2) {
                        return (int) (o1.getStartTime() - o2.getStartTime());
                    }
                });

                //filter_eventList.clear();
                temp_filter_eventList.clear();
                //eventRecyclerAdapter.notifyDataSetChanged();
                prevSize = 0;
                //filter_eventList.addAll(eventList);
                temp_filter_eventList.addAll(eventList);
                //eventRecyclerAdapter.notifyDataSetChanged();
                //cueSize = filter_eventList.size();
                new Filter().execute();
                //filterList();
            }
        }

        eventRecyclerAdapter.notifyDataSetChanged();
        Log.d(TAG, "onResponse: ");
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onFailure(Call<List<StatusEventDetailsPOJO>> call, Throwable t) {
        Log.d(TAG, "onFailure: "+t.toString());
        swipeRefreshLayout.setRefreshing(false);
        Toast.makeText(context, "Device Offline", Toast.LENGTH_SHORT).show();
        eventRecyclerAdapter.notifyDataSetChanged();

    }

    @Override
    public void onRefresh() {
        call = apiInterface.getEventByStatus(status);
        call.enqueue(this);
        swipeRefreshLayout.setRefreshing(true);
        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "Live events Fetched");
        mFirebaseAnalytics.logEvent("LiveEvents",bundle);
    }


    private void putEventsLastUpdate() {
        simpleDateFormat = new SimpleDateFormat("EEEE, MMMM d, h:mm a", Locale.ENGLISH);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
        formattedDate = simpleDateFormat.format(System.currentTimeMillis());
        prefs.edit().putString("EVENTS_LAST_UPDATED","Last Updated at : "+ formattedDate).apply();
    }

    private void getEventsLastUpdate(){
        lastUpdatedTimestamp=prefs.getString("EVENTS_LAST_UPDATED","PULL DOWN TO REFRESH");

    }

    private void showEventsLastUpdate(){
        if(viewGroup.getContext()!=null){
            //Snackbar.make(viewGroup, lastUpdatedTimestamp,Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        IntentFilter filter = new IntentFilter();
        filter.addAction("update_home_fragment_department");
        if(getActivity()!=null) {
            getActivity().registerReceiver(receiver, filter);
        }
    }

    @Override
    public void onPause(){
        super.onPause();
        if(getActivity()!=null) {
            getActivity().unregisterReceiver(receiver);
        }
    }

    @Override
    public void onDestroyView(){
        Runtime.getRuntime().gc();
        super.onDestroyView();
    }

    @Override
    public void onDestroy(){
        Runtime.getRuntime().gc();
        super.onDestroy();
    }
}
