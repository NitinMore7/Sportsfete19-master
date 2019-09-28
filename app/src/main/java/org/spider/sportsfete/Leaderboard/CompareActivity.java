package org.spider.sportsfete.Leaderboard;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import org.spider.sportsfete.API.ApiInterface;
import org.spider.sportsfete.API.Leaderboard;
import org.spider.sportsfete.Marathon.CustomAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import org.spider.sportsfete.R;

/*
 * Created by Srikanth Arugula on 9/1/18
 */

public class CompareActivity extends AppCompatActivity implements Callback<List<Leaderboard>> {

    private Spinner dept1, dept2;
    private RecyclerView comparisionRv;
    private String[] depts;
    private List<Leaderboard> scoreList;
    private int selected_item1 = 0, selected_item2 = 0;
    private ComparisionRecyclerAdapter adapter;
    private List<String> sports, dept1scores, dept2scores;
    private ApiInterface apiInterface;
    private final String TAG = "Compare Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare);

        initViews();
        scoreList = new ArrayList<>();
        sports = new ArrayList<>();
        dept1scores = new ArrayList<>();
        dept2scores = new ArrayList<>();

        adapter = new ComparisionRecyclerAdapter(sports, dept1scores, dept2scores);
        comparisionRv.setAdapter(adapter);

        apiInterface = ApiInterface.retrofit.create(ApiInterface.class);
        Call<List<Leaderboard>> call = apiInterface.getLeaderBoard();
        call.enqueue(this);
    }

    private void initViews(){
        dept1 = (Spinner) findViewById(R.id.dept1_spinner);
        dept2 = (Spinner) findViewById(R.id.dept2_spinner);
        comparisionRv = (RecyclerView) findViewById(R.id.comparision_rv);
        comparisionRv.setHasFixedSize(true);
        comparisionRv.setLayoutManager(new LinearLayoutManager(this));

        depts = getResources().getStringArray(R.array.department_array);
        depts[0] = "SELECT";

        CustomAdapter c = new CustomAdapter(this, R.layout.dept_spinner_element, depts);
        dept1.setAdapter(c);
        dept2.setAdapter(c);
    }

    @Override
    public void onResponse(Call<List<Leaderboard>> call, Response<List<Leaderboard>> response) {
        List<Leaderboard> responseList = response.body();
        if(responseList!=null || responseList.size()>0){
            scoreList.clear();
            scoreList.addAll(responseList);
            depts = getDepts(scoreList);
            assignSpinnerTasks();
        }
    }

    @Override
    public void onFailure(Call<List<Leaderboard>> call, Throwable t) {
        Log.d(TAG, "onFailure: "+t.toString());
        Toast.makeText(this, "Device Offline", Toast.LENGTH_SHORT).show();
    }

    private String[] getDepts(List<Leaderboard> scoreList) {
        String[] depts = new String[scoreList.size()+1];
        depts[0] = "SELECT";
        for(int i=1; i<=scoreList.size(); i++){
            depts[i] = scoreList.get(i-1).getDept();
        }

        return depts;
    }

    private void assignSpinnerTasks(){
        CustomAdapter c = new CustomAdapter(this, R.layout.dept_spinner_element, depts);
        dept1.setAdapter(c);
        dept2.setAdapter(c);

        dept1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selected_item1 = i;
                updateRecyclerAdapter(selected_item1, selected_item2);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        dept2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selected_item2 = i;
                updateRecyclerAdapter(selected_item1, selected_item2);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void updateRecyclerAdapter(int dept1, int dept2){
        generateComparisionData(dept1, dept2);
        ArrayIndexComparator comparator = new ArrayIndexComparator(sports);
        Integer[] indexes = comparator.createIndexArray();
        Arrays.sort(indexes, comparator);

        Log.i(TAG, "Reached");

        List<String> s=new ArrayList<>(), d1=new ArrayList<>(), d2=new ArrayList<>();
        for(int i=0; i<indexes.length; i++){
            s.add(sports.get(indexes[i])+":");
            d1.add(dept1scores.get(indexes[i]));
            d2.add(dept2scores.get(indexes[i]));
        }

        sports.clear();
        sports.addAll(s);
        dept1scores.clear();
        dept1scores.addAll(d1);
        dept2scores.clear();
        dept2scores.addAll(d2);

        Log.i(TAG, sports.toString());
        Log.i(TAG, dept1scores.toString());
        Log.i(TAG, dept2scores.toString());

        adapter.notifyDataSetChanged();
    }

    private void generateComparisionData(int dept1, int dept2){
        if(dept1==0 || dept2==0){
            sports.clear();
            dept1scores.clear();
            dept2scores.clear();
        }else{
            sports.clear();
            dept1scores.clear();
            dept2scores.clear();

            Leaderboard d1, d2;
            d1 = scoreList.get(dept1-1);
            d2 = scoreList.get(dept2-1);

            List<String> s1 = d1.getSplitup();
            List<String> s2 = d2.getSplitup();

            List<String> sport1 = new ArrayList<>(), sport2 = new ArrayList<>(), d1s = new ArrayList<>(), d2s = new ArrayList<>();
            for(int i=0; i<s1.size(); i++){
                sport1.add(s1.get(i).split(":")[0].trim());
                d1s.add(s1.get(i).split(":")[1].trim());
            }
            for(int i=0; i<s2.size(); i++){
                sport2.add(s2.get(i).split(":")[0].trim());
                d2s.add(s2.get(i).split(":")[1].trim());
            }

            for(int i=0; i<sport1.size(); i++){
                sports.add(sport1.get(i));
                dept1scores.add(d1s.get(i));

                //Searching for sport in dept2. If present add score and remove sport else put blank.
                int ind = sport2.indexOf(sport1.get(i));
                if(ind>=0){
                    dept2scores.add(d2s.get(ind));
                    sport2.remove(ind);
                    d2s.remove(ind);
                }else{
                    dept2scores.add("--");
                }
            }


            for(int i=0; i<sport2.size(); i++){
                sports.add(sport2.get(i));
                dept2scores.add(d2s.get(i));
                //Search not required as all common sports have been removed.
                dept1scores.add("--");
            }
        }
    }

    //TODO:Combine sports from both depts to form comparision chart.

}
