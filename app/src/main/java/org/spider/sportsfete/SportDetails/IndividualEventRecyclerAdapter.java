package org.spider.sportsfete.SportDetails;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.List;

import rx.Observable;
import rx.subjects.PublishSubject;
import org.spider.sportsfete.R;

/**
 * Created by dhananjay on 6/2/17.
 */

public class IndividualEventRecyclerAdapter extends RecyclerView.Adapter<IndividualEventRecyclerAdapter.EventDetailViewHolder> {

    List<List<String>> eventList;
    private final PublishSubject<String> onClickSubject = PublishSubject.create();
    Typeface hammersmithOnefont;
    Context context;
    MyAdapterListener onClickListener;

    public interface MyAdapterListener {
        void onItemSelected(int position, ExpandableLayout expandableLayout);
    }

    IndividualEventRecyclerAdapter(List<List<String>> eventList, Context context, MyAdapterListener myAdapterListener){
        this.eventList=eventList;
        this.onClickListener = myAdapterListener;
        this.context=context;
        setHasStableIds(true);
        hammersmithOnefont = Typeface.createFromAsset(context.getAssets(),  "fonts/HammersmithOneRegular.ttf");
    }

    @Override
    public EventDetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new EventDetailViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.individual_event_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final EventDetailViewHolder holder, final int position) {
        holder.sportNameTv.setText(eventList.get(position).get(3));

        holder.sportNameTv.setTypeface(hammersmithOnefont);
        holder.position2.setTypeface(hammersmithOnefont);
        holder.position3.setTypeface(hammersmithOnefont);
        holder.position1.setTypeface(hammersmithOnefont);

        holder.position1.setText(eventList.get(position).get(0));
        holder.position2.setText(eventList.get(position).get(1));
        holder.position3.setText(eventList.get(position).get(2));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListener.onItemSelected(position,holder.expandableLayout);
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

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class EventDetailViewHolder extends RecyclerView.ViewHolder {

        TextView sportNameTv,position1,position2,position3;
        ExpandableLayout expandableLayout;
        //ImageView ruleBookIv;

        public EventDetailViewHolder(View itemView) {
            super(itemView);
            //ruleBookIv= (ImageView) itemView.findViewById(R.id.rule_book_image);
            expandableLayout = (ExpandableLayout) itemView.findViewById(R.id.expandableLayout);
            sportNameTv = (TextView) itemView.findViewById(R.id.sport_name);
            position1 = (TextView) itemView.findViewById(R.id.position1);
            position2 = (TextView) itemView.findViewById(R.id.position2);
            position3 = (TextView) itemView.findViewById(R.id.position3);
        }
    }

}
