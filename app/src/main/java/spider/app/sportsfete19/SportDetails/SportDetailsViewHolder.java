package spider.app.sportsfete19.SportDetails;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hanks.htextview.HTextView;

import spider.app.sportsfete19.R;

/**
 * Created by dhananjay on 6/2/17.
 */

public class SportDetailsViewHolder extends RecyclerView.ViewHolder {

    TextView sportNameTv;
    RelativeLayout relativeLayout;
    //ImageView ruleBookIv;

    public SportDetailsViewHolder(View itemView) {
        super(itemView);
        //ruleBookIv=  itemView.findViewById(R.id.rule_book_image);
        sportNameTv =  itemView.findViewById(R.id.sport_name);
        relativeLayout = itemView.findViewById(R.id.relLayout);
    }
}
