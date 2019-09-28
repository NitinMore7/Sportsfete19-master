package org.spider.sportsfete.EventInfo;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.spider.sportsfete.R;

/**
 * Created by srikanth on 2/2/17.
 */

public class CommentViewHolder extends RecyclerView.ViewHolder{
    TextView commentTv, commentTimeTv, commentUsername;

    public CommentViewHolder(View itemView) {
        super(itemView);
        commentTv = (TextView) itemView.findViewById(R.id.comment_textview);
        commentTimeTv = (TextView)itemView.findViewById(R.id.comment_time);
        commentUsername = (TextView) itemView.findViewById(R.id.comment_username);
    }
}
