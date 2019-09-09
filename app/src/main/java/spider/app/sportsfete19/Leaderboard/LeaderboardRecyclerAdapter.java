package spider.app.sportsfete19.Leaderboard;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import spider.app.sportsfete19.API.Leaderboard;
import spider.app.sportsfete19.R;

/**
 * Created by akashj on 21/1/17.
 */

public class LeaderboardRecyclerAdapter extends RecyclerView.Adapter<LeaderboardRecyclerViewHolder>{

    private static final String TAG="LeaderboardAdapter";
    List<Leaderboard> standingList = new ArrayList<>();
    //List<String> points_distribution = new ArrayList<>();
    Context context;
    Typeface inconsolataBoldFont,hammersmithOneFont;
    int gold,silver,bronze;

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

    MyAdapterListener onClickListener;

    public interface MyAdapterListener {
        void onItemSelected(int position, ExpandableLayout expandableLayout, ImageView imageView);
    }

    public LeaderboardRecyclerAdapter(List<Leaderboard> standingList, Context context, MyAdapterListener myAdapterListener){
        this.standingList = standingList;
        this.context=context;
        inconsolataBoldFont = Typeface.createFromAsset(context.getAssets(),  "fonts/InconsolataBold.ttf");
        hammersmithOneFont = Typeface.createFromAsset(context.getAssets(),  "fonts/HammersmithOneRegular.ttf");
        gold=Color.parseColor("#fbc02d");
        silver=Color.parseColor("#9e9e9e");
        bronze=Color.parseColor("#a1887f");
        onClickListener = myAdapterListener;
        setHasStableIds(true);
    }

    @Override
    public LeaderboardRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LeaderboardRecyclerViewHolder( LayoutInflater.from(parent.getContext()).inflate(R.layout.single_leaderboard_item, parent, false));
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(final LeaderboardRecyclerViewHolder holder, final int position) {
        Leaderboard standing=standingList.get(holder.getAdapterPosition());

        holder.departmentTV.setTypeface(hammersmithOneFont);
        holder.departmentTV.setText(standing.getDept());
        holder.scoreTV.setTypeface(hammersmithOneFont);
        holder.scoreTV.setText(standing.getTotal()+"");
        holder.rankTv.setTypeface(hammersmithOneFont);
        holder.rankTv.setText((position+1)+"");

        List<String> points_distribution = new ArrayList<>();
        //points_distribution.clear();
        points_distribution.addAll(standing.getSplitup());

        if(standing.getDept().equalsIgnoreCase("m_tech")){
            holder.departmentTV.setText("M.Tech");
        }else if(standing.getDept().equalsIgnoreCase("phd_msc_ms")) {
            holder.departmentTV.setText("PhD/MSc/MS");
        }


        holder.medalIv.setVisibility(View.GONE);

        /*
        {
            if(standingList.get(0)==standingList.get(1)||
                    standingList.get(1)!=standingList.get(2)){
                holder.medalIv.setVisibility(View.GONE);
            }else{
                switch(position){
                    case 0: holder.medalIv.setImageDrawable(context.getDrawable(R.drawable.gold));
                        holder.medalIv.setVisibility(View.VISIBLE);break;
                    case 1: holder.medalIv.setImageDrawable(context.getDrawable(R.drawable.silver));
                        holder.medalIv.setVisibility(View.VISIBLE);break;
                    case 2: holder.medalIv.setImageDrawable(context.getDrawable(R.drawable.bronze));
                        holder.medalIv.setVisibility(View.VISIBLE);break;
                }
            }
        }
*/
        setDeptIcon(holder.department_icon,standing.getDept().trim().toUpperCase());

        Log.d("size",standing.getDept() + " " + standing.getSplitup().size()+"");
        if(!standing.getSplitup().isEmpty()) {
            holder.recyclerView.setHasFixedSize(true);
            holder.recyclerView.setLayoutManager(new LinearLayoutManager(context) {
                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            });

            Collections.sort(points_distribution, new Comparator<String>(){
                @Override
                public int compare( String o1, String o2) {
                    return Double.compare(Double.parseDouble(o2.split(":")[1].trim()), Double.parseDouble(o1.split(":")[1].trim()));
//                    return (int) (Double.parseDouble(o2.split(":")[1].trim()) -
//                            Double.parseDouble(o1.split(":")[1].trim()));
                }
            });

            PointsDistributionRecyclerAdapter recyclerAdapter = new PointsDistributionRecyclerAdapter(points_distribution, context);
            holder.recyclerView.setAdapter(null);
            holder.recyclerView.setAdapter(recyclerAdapter);
            holder.expandableLayout.setExpanded(false);
        }else{
            points_distribution.clear();
            PointsDistributionRecyclerAdapter recyclerAdapter = new PointsDistributionRecyclerAdapter(points_distribution, context);
            holder.recyclerView.setAdapter(recyclerAdapter);
            holder.expandableLayout.setExpanded(false);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListener.onItemSelected(position, holder.expandableLayout, holder.arrow_indicator);
            }
        });
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
        return standingList.size();
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
