package org.spider.sportsfete.EventInfo;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.spider.sportsfete.FireBaseServices.Comment;

import java.util.Date;
import java.util.List;

import org.spider.sportsfete.R;

/**
 * Created by srikanth on 2/2/17.
 */

public class CommentRecyclerAdapter extends RecyclerView.Adapter<CommentViewHolder> {
    List<Comment> commentList;
    Context context;
    Typeface hammersmithOnefont;

    public CommentRecyclerAdapter(List<Comment> commentList, Context context){
        this.commentList = commentList;
        this.context = context;
        setHasStableIds(true);
        hammersmithOnefont = Typeface.createFromAsset(context.getAssets(),  "fonts/HammersmithOneRegular.ttf");

    }

    @Override
    public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_commentary_item, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CommentViewHolder holder, int position) {
        if(position%2==0){
            holder.itemView.setBackgroundColor(Color.parseColor("#eeeeee"));
        }
        Comment commentItem = commentList.get(position);

        Log.d("position",""+position);
        String string=commentItem.getComment();
        holder.commentUsername.setText(commentItem.getUsername());
        holder.commentTv.setText(fromHtml(string));
        //holder.commentTv.setTypeface(hammersmithOnefont);
        holder.commentTimeTv.setText(getCurrentTime(commentItem.getTimestamp()));
        //TODO:get current eppoch timestamp
    }

    private String getCurrentTime(Long time) {
        String delegate = "hh:mm aaa";
        return (String) DateFormat.format(delegate, new Date(time));
    }

    @SuppressWarnings("deprecation")
    public static Spanned fromHtml(String html){
        Spanned result;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            result = Html.fromHtml(html,Html.FROM_HTML_MODE_LEGACY);
        } else {
            result = Html.fromHtml(html);
        }
        return result;
    }

    @Override
    public int getItemCount() {
        return commentList.size();
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
