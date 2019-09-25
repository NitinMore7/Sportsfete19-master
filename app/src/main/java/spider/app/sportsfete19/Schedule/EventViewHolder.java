package spider.app.sportsfete19.Schedule;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;
import spider.app.sportsfete19.R;

/**
 * Created by dhananjay on 21/1/17.
 */

public class EventViewHolder extends RecyclerView.ViewHolder {

    TextView nameTV,participantsTV,venueTV,team1Tv,team2Tv,statusTv, startTimeTv, roundTv, vsTv, undecided_match1, undecided_match2;
    LinearLayout versusEventLl,nonVersusEventLl, scene_ll;
    CircleImageView dept_icon1, dept_icon2;
    View view;

    public EventViewHolder(View itemView) {
        super(itemView);
        view=itemView;
        nameTV= (TextView) itemView.findViewById(R.id.event_name);
        roundTv = (TextView)itemView.findViewById(R.id.event_round);
        participantsTV = (TextView) itemView.findViewById(R.id.non_versus_event_participants);
        venueTV= (TextView) itemView.findViewById(R.id.event_venue);
        versusEventLl= (LinearLayout) itemView.findViewById(R.id.versus_event);
        nonVersusEventLl= (LinearLayout) itemView.findViewById(R.id.non_versus_event);
        team1Tv= (TextView) itemView.findViewById(R.id.team_1);
        team2Tv= (TextView) itemView.findViewById(R.id.team_2);
        statusTv= (TextView) itemView.findViewById(R.id.event_status);
        startTimeTv=(TextView) itemView.findViewById(R.id.event_timestamp);
        vsTv = (TextView)itemView.findViewById(R.id.vs_text);
        scene_ll = (LinearLayout) itemView.findViewById(R.id.scene_transition);
        dept_icon1 = (CircleImageView) itemView.findViewById(R.id.department1_icon);
        dept_icon2 = (CircleImageView) itemView.findViewById(R.id.department2_icon);
        undecided_match1 = (TextView) itemView.findViewById(R.id.undecided_match1);
        undecided_match2 = (TextView) itemView.findViewById(R.id.undecided_match2);
    }
    public View getView()
    {
        return view;
    }
}
