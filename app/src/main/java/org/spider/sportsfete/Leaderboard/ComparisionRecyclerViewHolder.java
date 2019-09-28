package org.spider.sportsfete.Leaderboard;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.spider.sportsfete.R;

/*
 * Created by Srikanth Arugula on 9/1/18
 */


public class ComparisionRecyclerViewHolder extends RecyclerView.ViewHolder {

    TextView sportNameTV, dept1scoreTV, dept2scoreTV;

    public ComparisionRecyclerViewHolder(View itemView) {
        super(itemView);
        sportNameTV = (TextView) itemView.findViewById(R.id.sport);
        dept1scoreTV = (TextView) itemView.findViewById(R.id.dept1_score);
        dept2scoreTV = (TextView) itemView.findViewById(R.id.dept2_score);
    }
}
