package org.spider.sportsfete.Schedule;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import org.spider.sportsfete.API.Event;

import java.util.List;

import rx.Observable;
import rx.subjects.PublishSubject;
import org.spider.sportsfete.R;

/**
 * Created by dhananjay on 21/1/17.
 */

public class EventRecyclerAdapter extends RecyclerView.Adapter<EventViewHolder> {

    private static final String TAG="EventAdapter";
    List<Event> eventList;
    Context context;
    Animation blinkAnimation;
    Typeface inconsolataBoldFont,francoisOneRegularFont;
    Typeface hammersmithOnefont;
    private final PublishSubject<String> onClickSubject = PublishSubject.create();

    private static final String FLAG_STATUS_UPCOMING = "u";
    private static final String FLAG_STATUS_LIVE = "l";
    private static final String FLAG_STATUS_COMPLETED = "c";

    public EventRecyclerAdapter(List<Event> eventList, Context context){
        this.eventList=eventList;
        this.context=context;
        inconsolataBoldFont = Typeface.createFromAsset(context.getAssets(),  "fonts/InconsolataBold.ttf");
        francoisOneRegularFont = Typeface.createFromAsset(context.getAssets(),  "fonts/FrancoisOneRegular.ttf");
        hammersmithOnefont = Typeface.createFromAsset(context.getAssets(),  "fonts/HammersmithOneRegular.ttf");
        blinkAnimation = new AlphaAnimation(0.0f, 1.0f);
        blinkAnimation.setDuration(250);
        blinkAnimation.setStartOffset(20);
        blinkAnimation.setRepeatMode(Animation.REVERSE);
        blinkAnimation.setRepeatCount(Animation.INFINITE);

        setHasStableIds(true);

    }


    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new EventViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.event_recycler_item, parent, false));
    }

    @Override
    public void onBindViewHolder(EventViewHolder holder, final int position) {
        holder.getView().setAnimation(AnimationUtils.loadAnimation(context,R.anim.zoom_in));
        Event event=eventList.get(position);
        if(event.getParticipants().size()==0) {
            holder.nonVersusEventLl.setVisibility(View.GONE);
            holder.versusEventLl.setVisibility(View.VISIBLE);
            holder.team1Tv.setTypeface(inconsolataBoldFont);
            holder.team1Tv.setText(event.getTeamA());
            holder.team1Tv.setSelected(true);
            holder.team1Tv.setSingleLine(true);
            if(event.getTeamB()==null){
                holder.team2Tv.setVisibility(View.GONE);
                holder.vsTv.setVisibility(View.GONE);
            }else{

                holder.team2Tv.setText(event.getTeamB());
                holder.team2Tv.setTypeface(inconsolataBoldFont);
                holder.team2Tv.setSelected(true);
                holder.team2Tv.setSingleLine(true);
            }
        }else if(event.getParticipants().size()==2){
            holder.nonVersusEventLl.setVisibility(View.GONE);
            holder.versusEventLl.setVisibility(View.VISIBLE);
            holder.team1Tv.setTypeface(inconsolataBoldFont);
            holder.team2Tv.setTypeface(inconsolataBoldFont);
            holder.team1Tv.setText(event.getParticipants().get(0));
            holder.team2Tv.setText(event.getParticipants().get(1));

        }else {
            String participantsString="";
            holder.nonVersusEventLl.setVisibility(View.VISIBLE);
            holder.versusEventLl.setVisibility(View.GONE);
            for (int i = 0; i <event.getParticipants().size(); i++) {
                participantsString += (event.getParticipants().get(i)+"\t\t\t");
            }
            holder.participantsTV.setTypeface(inconsolataBoldFont);
            holder.participantsTV.setSingleLine(true);
            holder.participantsTV.setSelected(true);
            holder.participantsTV.setText(participantsString);
        }
        holder.nameTV.setTypeface(hammersmithOnefont);
        holder.nameTV.setText(event.getName());
        holder.roundTv.setTypeface(hammersmithOnefont);
        String round=event.getRound();
        if(!round.equals(event.getFixture()))
            round=round+" : "+event.getFixture();
        holder.roundTv.setText(round);
        holder.venueTV.setTypeface(francoisOneRegularFont);
        holder.venueTV.setText(event.getVenue());
        holder.statusTv.setAllCaps(true);
        String status="";
        if(eventList.get(position).getStatus().equals(FLAG_STATUS_UPCOMING)){
            holder.statusTv.setTextColor(Color.parseColor("#009688"));
            status="UPCOMING";
            holder.startTimeTv.setText(formatStartTime(event.getStartTime()));
        }else if(eventList.get(position).getStatus().equals(FLAG_STATUS_LIVE)){
            holder.statusTv.setTextColor(Color.parseColor("#FF5722"));
            status="LIVE";
            holder.statusTv.startAnimation(blinkAnimation);
            holder.startTimeTv.setText("");
        }else{
            holder.statusTv.setTextColor(Color.parseColor("#4CAF50"));
            status="COMPLETED";
            holder.startTimeTv.setText("");
            String winner=event.getWinner();
            //setting color
            if(event.getWinner()!=null){
                if(event.getParticipants().size()!=0){
                    if(winner.equals(event.getParticipants().get(0)))
                        holder.team1Tv.setTextColor(Color.parseColor("#fbc02d"));
                    else
                        holder.team2Tv.setTextColor(Color.parseColor("#fbc02d"));
                }else if(event.getTeamA()!=null&&event.getWinner().equals(event.getTeamA()))
                    holder.team1Tv.setTextColor(Color.parseColor("#fbc02d"));
                else if(event.getTeamB()!=null&&event.getWinner().equals(event.getTeamB()))
                    holder.team2Tv.setTextColor(Color.parseColor("#fbc02d"));
            }

        }
        holder.statusTv.setText(status);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickSubject.onNext(String.valueOf(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public Observable<String> getPositionClicks(){
        return onClickSubject.asObservable();
    }

    private Spanned formatStartTime(String unformattedStartTime){

        String date = unformattedStartTime.substring(8,10) + "-" + unformattedStartTime.substring(5,7) + "-" + unformattedStartTime.substring(0,4);
        String time = unformattedStartTime.substring(11,16);
        String formatted = date + "\nStarts at "+"<b>"+time+"</b>";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(formatted,Html.FROM_HTML_MODE_COMPACT);
        }
        else{
            return Html.fromHtml(formatted);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}