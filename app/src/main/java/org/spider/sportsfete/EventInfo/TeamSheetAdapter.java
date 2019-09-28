package org.spider.sportsfete.EventInfo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import org.spider.sportsfete.R;

public class TeamSheetAdapter extends RecyclerView.Adapter<TeamSheetAdapter.MyViewHolder> {
    List<String> Teamlist;
    Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.team_sheet_txt);

        }
    }


    public TeamSheetAdapter(Context context,List<String> Teamlist) {
        this.context=context;this.Teamlist = Teamlist;
    }

    public void setTeamlist(List<String> teamlist)
    {
        this.Teamlist=teamlist;
        notifyDataSetChanged();
    }
    @Override
    public TeamSheetAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.team_sheet, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TeamSheetAdapter.MyViewHolder holder, int position) {
            String a=Teamlist.get(position);
            holder.title.setText(a);
    }

    @Override
    public int getItemCount() {
        return Teamlist.size();
    }
}
