package spider.app.sportsfete19.Following;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import spider.app.sportsfete19.MainActivity;
import spider.app.sportsfete19.R;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView;

/**
 * A simple {@link Fragment} subclass.
 */
public class SubscribeFragment extends Fragment {



    Context context;
    SharedPreferences prefs;
    TabLayout tabLayout;

    public SubscribeFragment() {
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getActivity().getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        //((AppCompatActivity)getActivity()).getSupportActionBar().setElevation(0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_subscribe, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.context=getContext();
        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());



        tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("Department"));
        tabLayout.addTab(tabLayout.newTab().setText("Sport"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


        final ViewPager viewPager = (ViewPager)view.findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter(getFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        new MaterialShowcaseView.Builder(getActivity())
                .setTarget(tabLayout)
                .setDismissText("GOT IT")
                .setContentText("Follow a Department or a Sport to get live notifications")
                .setDelay(400) // optional but starting animations immediately in onCreate can make them choppy
                .singleUse("haidz") // provide a unique ID used to ensure it is only shown once
                .show();

        viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                LinearLayout tabLay = (LinearLayout)((ViewGroup) tabLayout.getChildAt(0)).getChildAt(tab.getPosition());
                TextView tabTextView = (TextView) tabLay.getChildAt(1);
                tabTextView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                Typeface hammersmithOnefont = Typeface.createFromAsset(getActivity().getAssets(),  "fonts/HammersmithOneRegular.ttf");
                tabTextView.setTypeface(hammersmithOnefont);

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                LinearLayout tabLay = (LinearLayout)((ViewGroup) tabLayout.getChildAt(0)).getChildAt(tab.getPosition());
                TextView tabTextView = (TextView) tabLay.getChildAt(1);
                Typeface hammersmithOnefont = Typeface.createFromAsset(getActivity().getAssets(),  "fonts/HammersmithOneRegular.ttf");
                tabTextView.setTypeface(hammersmithOnefont);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        LinearLayout tabLay = (LinearLayout)((ViewGroup) tabLayout.getChildAt(0)).getChildAt(0);
        TextView tabTextView = (TextView) tabLay.getChildAt(1);
        tabTextView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        Typeface hammersmithOnefont = Typeface.createFromAsset(getActivity().getAssets(),  "fonts/HammersmithOneRegular.ttf");
        tabTextView.setTypeface(hammersmithOnefont);
        tabLay = (LinearLayout)((ViewGroup) tabLayout.getChildAt(0)).getChildAt(1);
        tabTextView = (TextView) tabLay.getChildAt(1);
        tabTextView.setTypeface(hammersmithOnefont);

        ((MainActivity)getActivity()).view.setVisibility(View.GONE);

    }

    @Override
    public void onDestroyView(){
        Runtime.getRuntime().gc();
        super.onDestroyView();
    }

}
