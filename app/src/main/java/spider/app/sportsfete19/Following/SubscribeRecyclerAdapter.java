package spider.app.sportsfete19.Following;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.varunest.sparkbutton.SparkEventListener;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import rx.Observable;
import rx.subjects.PublishSubject;
import spider.app.sportsfete19.R;

/**
 * Created by dhananjay on 6/2/17.
 */

public class SubscribeRecyclerAdapter extends RecyclerView.Adapter<SubscribeViewHolder> {

    List<String> departmentList;
    boolean[] checked;
    private final PublishSubject<String> onClickSubject = PublishSubject.create();
    Typeface customFont;
    Context context;
    MyAdapterListener onClickListener;

    public interface MyAdapterListener{
        public void buttonPressed(int position);
    }

    int[] drawable = {
            R.drawable.athletics,
            R.drawable.badminton,
            R.drawable.basketball,
            R.drawable.basketball,
            R.drawable.carrom,
            R.drawable.chess,
            R.drawable.cricket,
            R.drawable.cricket,
            R.drawable.football,
            R.drawable.football,
            R.drawable.handball,
            R.drawable.handball,
            R.drawable.hockey,
            R.drawable.kabaddi,
            R.drawable.khokho,
            R.drawable.khokho,
            R.drawable.powerlifting,
            R.drawable.swimming,
            R.drawable.tabletennis,
            R.drawable.tabletennis,
            R.drawable.tennis,
            R.drawable.tennis,
            R.drawable.thrwoball,
            R.drawable.volleyball,
            R.drawable.volleyball,
            R.drawable.waterpolo,
            R.drawable.weightlifting,
            R.drawable.sportsquiz
    };

    int[] dept_icon = {
            R.drawable.arch1,
            R.drawable.chem1,
            R.drawable.civil2,
            R.drawable.cse2,
            R.drawable.doms2,
            R.drawable.ece2,
            R.drawable.eee2,
            R.drawable.ice2,
            R.drawable.mca2,
            R.drawable.mech2,
            R.drawable.meta2,
            R.drawable.mtech2,
            R.drawable.phd2,
            R.drawable.prod2
    };


    public SubscribeRecyclerAdapter(List<String> departmentList,Context context, boolean[] checked, Typeface typeface, MyAdapterListener myAdapterListener){
        this.departmentList=departmentList;
        setHasStableIds(true);
        this.departmentList=departmentList;
        this.checked=checked;
        this.context=context;
        customFont = typeface;
        onClickListener = myAdapterListener;
    }

    @Override
    public SubscribeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            return new SubscribeViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.single_subscribe_item,
                    parent, false));
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(SubscribeViewHolder holder, final int position) {
        holder.departmentNameTv.setTypeface(customFont);
        holder.departmentNameTv.setText(departmentList.get(position));
        holder.sparkButton.setChecked(checked[position]);
        holder.sparkButton.setEventListener(new SparkEventListener() {
            @Override
            public void onEvent(ImageView button, boolean buttonState) {
                //onClickSubject.onNext(String.valueOf(position));
                onClickListener.buttonPressed(position);
            }
        });

        if (departmentList.get(0).trim().equalsIgnoreCase("ATHLETICS")) {
            //holder.dept_icon.setImageResource(drawable[position]);
            Picasso.with(context).load(drawable[position])
                    .skipMemoryCache()
                    .fit()
                    .into(holder.dept_icon);
            holder.dept_icon.setColorFilter(Color.parseColor("#16282a"));
        } else {

            if(departmentList.get(position).equalsIgnoreCase("m_tech")){
                holder.departmentNameTv.setText("M.Tech");
            }else if(departmentList.get(position).equalsIgnoreCase("phd_msc_ms")) {
                holder.departmentNameTv.setText("PhD/MSc/MS");
            }

            holder.dept_icon.setColorFilter(null);
            setDeptIcon(holder.dept_icon, departmentList.get(position).trim().toUpperCase());
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void setDeptIcon(CircleImageView imageView, String dept){
        Log.d("department",dept+"");
        switch(dept){
            case "ARCHI":setImageResource((dept_icon[0]),imageView);
                imageView.setFillColor(Color.WHITE);break;
            case "CHEM":setImageResource((dept_icon[1]),imageView);
                imageView.setFillColor(Color.WHITE);break;
            case "CIVIL":setImageResource((dept_icon[2]),imageView);
                imageView.setFillColor(Color.WHITE);break;
            case "CSE":imageView.setImageResource((dept_icon[3]));
                imageView.setFillColor(Color.parseColor("#16282a"));break;
            case "DOMS":setImageResource((dept_icon[4]),imageView);
                imageView.setFillColor(Color.WHITE);break;
            case "ECE":setImageResource((dept_icon[5]),imageView);
                imageView.setFillColor(Color.WHITE);break;
            case "EEE":setImageResource((dept_icon[6]),imageView);
                imageView.setFillColor(Color.WHITE);break;
            case "ICE":setImageResource((dept_icon[7]),imageView);
                imageView.setFillColor(Color.parseColor("#16282a"));break;
            case "CA":setImageResource((dept_icon[8]),imageView);
                imageView.setFillColor(Color.WHITE);break;
            case "MECH":setImageResource((dept_icon[9]),imageView);
                imageView.setFillColor(Color.WHITE);break;
            case "META":setImageResource((dept_icon[10]),imageView);
                imageView.setFillColor(Color.WHITE);break;
            case "M_TECH":setImageResource((dept_icon[11]),imageView);
                imageView.setFillColor(Color.WHITE);break;
            case "PHD_MSC_MS":setImageResource((dept_icon[12]),imageView);
                imageView.setFillColor(Color.WHITE);break;
            case "PROD":setImageResource((dept_icon[13]),imageView);
                imageView.setFillColor(Color.WHITE);break;
        }
    }

    public void setImageResource(int drawable, CircleImageView imageView){
        Picasso.with(context).load(drawable)
                .skipMemoryCache()
                .fit()
                .into(imageView);
    }

    @Override
    public int getItemCount() {
        return departmentList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public Observable<String> getPositionClicks(){
        return onClickSubject.asObservable();
    }

}
