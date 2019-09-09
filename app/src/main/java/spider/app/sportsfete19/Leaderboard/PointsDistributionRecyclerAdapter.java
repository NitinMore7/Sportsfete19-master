package spider.app.sportsfete19.Leaderboard;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import spider.app.sportsfete19.R;

/**
 * Created by akashj on 21/1/17.
 */

public class PointsDistributionRecyclerAdapter extends RecyclerView.Adapter<PointsDistributionRecyclerAdapter.ViewHolder>{

    private static final String TAG="LeaderboardAdapter";
    List<String> points = new ArrayList<>();
    Context context;
    Typeface inconsolataBoldFont,hammersmithOneFont;
    int gold,silver,bronze;


    public PointsDistributionRecyclerAdapter(List<String> points, Context context){
        this.points = points;
        this.context=context;
        inconsolataBoldFont = Typeface.createFromAsset(context.getAssets(),  "fonts/InconsolataBold.ttf");
        hammersmithOneFont = Typeface.createFromAsset(context.getAssets(),  "fonts/HammersmithOneRegular.ttf");
        gold=Color.parseColor("#fbc02d");
        silver=Color.parseColor("#9e9e9e");
        bronze=Color.parseColor("#a1887f");
        setHasStableIds(true);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder( LayoutInflater.from(parent.getContext()).inflate(R.layout.point_distribution_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.departmentTV.setTypeface(inconsolataBoldFont);
        holder.departmentTV.setText(points.get(holder.getAdapterPosition()).split(":")[0].trim()+" : ");
        holder.scoreTV.setTypeface(hammersmithOneFont);
        holder.scoreTV.setText(points.get(holder.getAdapterPosition()).split(":")[1].trim());
    }

    @Override
    public int getItemCount() {
        return points.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView departmentTV, scoreTV;
        public ViewHolder(View itemView) {
            super(itemView);
            departmentTV= (TextView) itemView.findViewById(R.id.department);
            scoreTV= (TextView) itemView.findViewById(R.id.points);
        }
    }

}
