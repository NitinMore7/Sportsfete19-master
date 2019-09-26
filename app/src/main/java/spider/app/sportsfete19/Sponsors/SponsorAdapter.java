package spider.app.sportsfete19.Sponsors;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import spider.app.sportsfete19.R;



public class SponsorAdapter extends RecyclerView.Adapter<SponsorAdapter.MyViewHolder> {


    private List<Sponsor> sponsorlist;
    private Context context;
    public Context getContext() {
        return this.context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public View mview;
        ImageView logo;
        TextView category;
        TextView company;

        public MyViewHolder(View itemView) {
            super(itemView);
            mview = itemView;
            logo = mview.findViewById(R.id.imageView);
            company=mview.findViewById(R.id.company);
        }

    }

    public SponsorAdapter(Context context, List<Sponsor> s) {
        sponsorlist=s;
        this.context=context;
    }

    @Override
    public SponsorAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.custom_card_sponsor, parent, false);
        return new SponsorAdapter.MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.company.setText(sponsorlist.get(position).getCompany());
        holder.logo.setImageResource(sponsorlist.get(position).getResID());
    }

    @Override
    public int getItemCount() {
        return sponsorlist.size();
    }
}
