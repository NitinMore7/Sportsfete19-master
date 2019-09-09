package spider.app.sportsfete19.Marathon;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import spider.app.sportsfete19.R;

/**
 * Created by AVINASH on 1/24/2018.
 */

public class CustomAdapter extends ArrayAdapter<String> {

    Context context;
    String[] items;
    LayoutInflater inflator;

    public CustomAdapter(@NonNull Context context, int resource, String[] items) {
        super(context, resource, items);
        this.context = context;
        this.items = items;
        inflator = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflator.inflate(R.layout.dept_spinner_element, null);
        TextView tv = (TextView) convertView.findViewById(R.id.sport_name);
        tv.setTypeface(Typeface.createFromAsset(context.getAssets(),  "fonts/HammersmithOneRegular.ttf"));
        tv.setText(items[position]);
        if(items[position].equalsIgnoreCase("select department")){
            tv.setTextColor(Color.parseColor("#60000000"));
        }
        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        convertView = inflator.inflate(R.layout.dept_spinner_element, null);
        TextView tv = (TextView) convertView.findViewById(R.id.sport_name);
        tv.setText(items[position]);
        tv.setTypeface(Typeface.createFromAsset(context.getAssets(),  "fonts/HammersmithOneRegular.ttf"));
        return convertView;
    }
}
