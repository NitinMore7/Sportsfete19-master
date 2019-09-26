package spider.app.sportsfete19.Schedule;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

public class DeptSelectionRecyclerAdapter extends RecyclerView.Adapter<DeptSelectionRecyclerAdapter.ViewHolder>{

    private static final String TAG="LeaderboardAdapter";
    List<String> points = new ArrayList<>();
    Context context;
    Typeface inconsolataBoldFont,hammersmithOneFont;
    int gold,silver,bronze;
    String selectedDepartment;

    MyAdapterListener onClickListener;

    public interface MyAdapterListener {
        void onItemSelected(int position, View view);
    }

    public void setSelectedDepartment(String selectedDepartment){
        this.selectedDepartment = selectedDepartment;
    }

    public DeptSelectionRecyclerAdapter(List<String> points, String selectedDepartment, Context context, MyAdapterListener myAdapterListener){
        this.points = points;
        this.context=context;
        inconsolataBoldFont = Typeface.createFromAsset(context.getAssets(),  "fonts/InconsolataBold.ttf");
        hammersmithOneFont = Typeface.createFromAsset(context.getAssets(),  "fonts/HammersmithOneRegular.ttf");
        gold=Color.parseColor("#fbc02d");
        silver=Color.parseColor("#9e9e9e");
        bronze=Color.parseColor("#a1887f");
        setHasStableIds(true);
        onClickListener = myAdapterListener;
        this.selectedDepartment = selectedDepartment;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder( LayoutInflater.from(parent.getContext()).inflate(R.layout.dept_recycler_element, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        //Log.d("position",""+position);
        holder.departmentTV.setTypeface(inconsolataBoldFont);
        holder.departmentTV.setText(points.get(position));

        if(selectedDepartment!=null) {
            //Log.d("selected dept",""+selectedDepartment);
            if (points.get(position).equalsIgnoreCase(selectedDepartment.replace("_","/"))
                    ||points.get(position).equalsIgnoreCase(selectedDepartment.replace("_","."))) {
                Log.d("selected dept2",""+selectedDepartment);
                holder.departmentTV.setTextColor(context.getResources().getColor(android.R.color.white));
                holder.departmentTV.setBackgroundColor(context.getResources().getColor(R.color.dept_box_background));
            } else {
                holder.departmentTV.setTextColor(context.getResources().getColor(R.color.colorTabtext));
                holder.departmentTV.setBackgroundColor(Color.parseColor("#f1efef"));
            }
        }else{
            if(position==0){
                holder.departmentTV.setTextColor(context.getResources().getColor(android.R.color.white));
                holder.departmentTV.setBackgroundColor(context.getResources().getColor(R.color.dept_box_background));
            }
        }
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

        TextView departmentTV;
        public ViewHolder(View itemView) {
            super(itemView);
            departmentTV= (TextView) itemView.findViewById(R.id.dept_name);

            departmentTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickListener.onItemSelected(getAdapterPosition(),view);
                }
            });
        }
    }

}
