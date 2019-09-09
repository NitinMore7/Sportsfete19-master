package spider.app.sportsfete19.Following;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.varunest.sparkbutton.SparkButton;

import de.hdodenhof.circleimageview.CircleImageView;
import spider.app.sportsfete19.R;

/**
 * Created by dhananjay on 6/2/17.
 */

public class SubscribeViewHolder extends RecyclerView.ViewHolder  {

    TextView departmentNameTv;
    SparkButton sparkButton;
    CircleImageView dept_icon;

    public SubscribeViewHolder(View itemView) {
        super(itemView);
        departmentNameTv= (TextView) itemView.findViewById(R.id.subscribe_dept_name);
        sparkButton= (SparkButton) itemView.findViewById(R.id.subscribe_spark_button);
        dept_icon = (CircleImageView) itemView.findViewById(R.id.department_icon);
    }
}
