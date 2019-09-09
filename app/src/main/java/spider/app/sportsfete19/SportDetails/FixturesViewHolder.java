package spider.app.sportsfete19.SportDetails;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import spider.app.sportsfete19.R;

/**
 * Created by akashj on 8/2/17.
 */


public class FixturesViewHolder extends RecyclerView.ViewHolder {

    TextView participantsTV,venueTV,team1Tv,team2Tv,statusTv, startTimeTv, roundTv, vsTv, boys_girls;
    LinearLayout versusEventLl,nonVersusEventLl;

    public FixturesViewHolder(View itemView) {
        super(itemView);
        participantsTV = (TextView) itemView.findViewById(R.id.non_versus_event_participants_fixtures);
        venueTV= (TextView) itemView.findViewById(R.id.event_venue_fixtures);
        versusEventLl= (LinearLayout) itemView.findViewById(R.id.versus_event);
        nonVersusEventLl= (LinearLayout) itemView.findViewById(R.id.non_versus_event);
        team1Tv= (TextView) itemView.findViewById(R.id.team_1_fixtures);
        team2Tv= (TextView) itemView.findViewById(R.id.team_2_fixtures);
        statusTv= (TextView) itemView.findViewById(R.id.event_status_fixtures);
        startTimeTv=(TextView) itemView.findViewById(R.id.event_timestamp_fixtures);
        roundTv = (TextView)itemView.findViewById(R.id.fixtures_round_textview);
        vsTv = (TextView) itemView.findViewById(R.id.vs_text);
        boys_girls = (TextView) itemView.findViewById(R.id.boys_girls);
    }
}