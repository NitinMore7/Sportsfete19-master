package org.spider.sportsfete.Leaderboard;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import org.spider.sportsfete.R;

/*
 * Created by Srikanth Arugula on 9/1/18
 */


public class ComparisionRecyclerAdapter extends RecyclerView.Adapter<ComparisionRecyclerViewHolder> {

    List<String> sports, dept1scores, dept2scores;

    public ComparisionRecyclerAdapter(List<String> sports, List<String> dept1, List<String> dept2){
        this.sports = sports;
        this.dept1scores = dept1;
        this.dept2scores = dept2;
    }

    @Override
    public ComparisionRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ComparisionRecyclerViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.compare_recycler_item,parent, false));
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(ComparisionRecyclerViewHolder holder, int position) {
        holder.sportNameTV.setText(sports.get(position));
        holder.dept1scoreTV.setText(dept1scores.get(position));
        holder.dept2scoreTV.setText(dept2scores.get(position));
    }

    @Override
    public int getItemCount() {
        return sports.size();
    }
}
