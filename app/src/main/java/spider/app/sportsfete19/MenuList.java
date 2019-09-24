package spider.app.sportsfete19;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.util.ArrayList;

import yalantis.com.sidemenu.model.SlideMenuItem;

public class MenuList extends RecyclerView.Adapter<MenuList.ExampleViewHolder>  {

    public ArrayList<SlideMenuItem> product;
    Context context;

    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(final View view) {

        }
    };
    public MenuList(){}
    public MenuList(Context context,ArrayList<SlideMenuItem> product) {
        this.product = product;
        this.context = context;
    }

    public class ExampleViewHolder extends RecyclerView.ViewHolder {

        public FrameLayout parentlayout;
        public ImageView imageView;


        public ExampleViewHolder(View itemView) {
            super(itemView);
            parentlayout = itemView.findViewById(R.id.container);
            imageView =  itemView.findViewById(R.id.image_content);
        }
    }
    @Override

    public MenuList.ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_main,
                parent, false);
        MenuList.ExampleViewHolder evh = new MenuList.ExampleViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(final MenuList.ExampleViewHolder holder, final int position) {

        final SlideMenuItem currentItem = product.get(position);
        Log.d("CUREENT ITEM",currentItem.getName());
        holder.imageView.setImageResource(currentItem.getImageRes());
        
        holder.itemView.setTag(product.get(position));
        holder.itemView.setOnClickListener(mOnClickListener);

        /*Glide.with(context)
                .load(bitmap).apply(new RequestOptions()
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_background)
                .fitCenter()
                .override(50,50))
                .into(holder.image);
                */

        Log.d("load","PRODUCT LOADED");
    }

    @Override
    public int getItemCount() {
        if(product!=null)
            return product.size();
        else
            return 0;
    }
    public void filterList(ArrayList<SlideMenuItem> filteredList) {
        product = filteredList;
        notifyDataSetChanged();
    }
}


