package spider.app.sportsfete19.SportDetails;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import rx.Observable;
import rx.subjects.PublishSubject;
import spider.app.sportsfete19.R;

/**
 * Created by dhananjay on 6/2/17.
 */

public class SportDetailsRecyclerAdapter extends RecyclerView.Adapter<SportDetailsViewHolder> {

    String[] sportsList;
    private final PublishSubject<String> onClickSubject = PublishSubject.create();
    Typeface hammersmithOnefont;
    Context context;

    SportDetailsRecyclerAdapter(String[] sportsList, Context context){
        this.sportsList=sportsList;
        this.context=context;
        setHasStableIds(true);
        hammersmithOnefont = Typeface.createFromAsset(context.getAssets(),  "fonts/HammersmithOneRegular.ttf");
    }

    @Override
    public SportDetailsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SportDetailsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.single_rule_book_item, parent, false));
    }

    @Override
    public void onBindViewHolder(SportDetailsViewHolder holder, final int position) {
        holder.sportNameTv.setText(sportsList[position]);
        holder.sportNameTv.setTypeface(hammersmithOnefont);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickSubject.onNext(String.valueOf(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return sportsList.length;
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
    }
