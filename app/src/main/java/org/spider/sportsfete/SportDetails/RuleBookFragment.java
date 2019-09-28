package org.spider.sportsfete.SportDetails;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.analytics.FirebaseAnalytics;

import org.spider.sportsfete.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class RuleBookFragment extends Fragment {

    private static final String TAG="RuleBookFragment";
    String selectedSport;
    TextView alertbox_content;


    public RuleBookFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        selectedSport= getArguments().getString("SELECTED_SPORT");
        return inflater.inflate(R.layout.fragment_rule_book, container, false);


    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(getActivity().getApplicationContext());
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "RuleBook");
        mFirebaseAnalytics.logEvent("RuleBookFragment",bundle);
        TextView alertbox_title = (TextView)
                view.findViewById(R.id.title);
        if(selectedSport.equals("0"))
        {
            alertbox_title.setText("Athletics");
            alertbox_content = (TextView)view.findViewById(R.id.content);
            alertbox_content.setText(Html.fromHtml(getString(R.string.Athletics), null, new MyTagHandler()));
        }
        else if(selectedSport.equals("1"))
        {
            alertbox_title.setText("Badminton");
            alertbox_content = (TextView)view.findViewById(R.id.content);
            alertbox_content.setText(Html.fromHtml(getString(R.string.Badminton), null, new MyTagHandler()));
        }
        else if(selectedSport.equals("2"))
        {
            alertbox_title.setText("Basketball");
            alertbox_content = (TextView)view.findViewById(R.id.content);
            alertbox_content.setText(Html.fromHtml(getString(R.string.Basketball), null, new MyTagHandler()));
        }
        else if(selectedSport.equals("3"))
        {
            alertbox_title.setText("Basketball");
            alertbox_content = (TextView)view.findViewById(R.id.content);
            alertbox_content.setText(Html.fromHtml(getString(R.string.Basketball_Girls), null, new MyTagHandler()));
        }
        else if(selectedSport.equals("4"))
        {
            alertbox_title.setText("Carrom");
            alertbox_content = (TextView)view.findViewById(R.id.content);
            alertbox_content.setText(Html.fromHtml(getString(R.string.Carrom), null, new MyTagHandler()));
        }
        else if(selectedSport.equals("5"))
        {
            alertbox_title.setText("Chess");
            alertbox_content = (TextView)view.findViewById(R.id.content);
            alertbox_content.setText(Html.fromHtml(getString(R.string.Chess), null, new MyTagHandler()));
        }
        else if(selectedSport.equals("6"))
        {
            alertbox_title.setText("Cricket");
            alertbox_content = (TextView)view.findViewById(R.id.content);
            alertbox_content.setText(Html.fromHtml(getString(R.string.Cricket), null, new MyTagHandler()));
        }
        else if(selectedSport.equals("7"))
        {
            alertbox_title.setText("Cricket");
            alertbox_content = (TextView)view.findViewById(R.id.content);
            alertbox_content.setText(Html.fromHtml(getString(R.string.Cricket_Girls), null, new MyTagHandler()));
        }
        else if(selectedSport.equals("8"))
        {
            alertbox_title.setText("Football");
            alertbox_content = (TextView)view.findViewById(R.id.content);
            alertbox_content.setText(Html.fromHtml(getString(R.string.Football), null, new MyTagHandler()));
        }
        else if(selectedSport.equals("9"))
        {
            alertbox_title.setText("Football");
            alertbox_content = (TextView)view.findViewById(R.id.content);
            alertbox_content.setText(Html.fromHtml(getString(R.string.Football_Girls), null, new MyTagHandler()));
        }
        else if(selectedSport.equals("10"))
        {
            alertbox_title.setText("Handball");
            alertbox_content = (TextView) view.findViewById(R.id.content);
            alertbox_content.setText(Html.fromHtml(getString(R.string.Handball_Boys), null, new MyTagHandler()));
        }
        else if(selectedSport.equals("11"))
        {
            alertbox_title.setText("Handball");
            alertbox_content = (TextView) view.findViewById(R.id.content);
            alertbox_content.setText(Html.fromHtml(getString(R.string.Handball_Girls), null, new MyTagHandler()));
        }
        else if(selectedSport.equals("12"))
        {
            alertbox_title.setText("Hockey");
            alertbox_content = (TextView) view.findViewById(R.id.content);
            alertbox_content.setText(Html.fromHtml(getString(R.string.Hockey), null, new MyTagHandler()));
        }
        else if(selectedSport.equals("13"))
        {
            alertbox_title.setText("Hockey");
            alertbox_content = (TextView) view.findViewById(R.id.content);
            alertbox_content.setText(Html.fromHtml(getString(R.string.Hockey_Girls), null, new MyTagHandler()));
        }
        else if(selectedSport.equals("14"))
        {
            alertbox_title.setText("Kabaddi(Boys)");
            alertbox_content = (TextView) view.findViewById(R.id.content);
            alertbox_content.setText(Html.fromHtml(getString(R.string.Kabaddi), null, new MyTagHandler()));
        }
        else if(selectedSport.equals("15"))
        {
            alertbox_title.setText("Kabaddi(Girls)");
            alertbox_content = (TextView) view.findViewById(R.id.content);
            alertbox_content.setText(Html.fromHtml(getString(R.string.Kabaddi_Girls), null, new MyTagHandler()));
        }
        else if(selectedSport.equals("16"))
        {
            alertbox_title.setText("Kho Kho");
            alertbox_content = (TextView) view.findViewById(R.id.content);
            alertbox_content.setText(Html.fromHtml(getString(R.string.KhoKho), null, new MyTagHandler()));
        }
        else if(selectedSport.equals("17"))
        {
            alertbox_title.setText("Kho Kho");
            alertbox_content = (TextView) view.findViewById(R.id.content);
            alertbox_content.setText(Html.fromHtml(getString(R.string.KhoKho_Girls), null, new MyTagHandler()));
        }
        else if(selectedSport.equals("18"))
        {
            alertbox_title.setText("Marathon");
            alertbox_content = (TextView) view.findViewById(R.id.content);
            alertbox_content.setText(Html.fromHtml(getString(R.string.Marathon), null, new MyTagHandler()));
        }
        else if(selectedSport.equals("19"))
        {
            alertbox_title.setText("Power Lifting");
            alertbox_content = (TextView) view.findViewById(R.id.content);
            alertbox_content.setText(Html.fromHtml(getString(R.string.PowerLifting), null, new MyTagHandler()));
        }
        else if(selectedSport.equals("20"))
        {
            alertbox_title.setText("Power Lifting");
            alertbox_content = (TextView) view.findViewById(R.id.content);
            alertbox_content.setText(Html.fromHtml(getString(R.string.PowerLifting_girls), null, new MyTagHandler()));
        }
        else if(selectedSport.equals("21"))
        {
            alertbox_title.setText("Swimming");
            alertbox_content = (TextView) view.findViewById(R.id.content);
            alertbox_content.setText(Html.fromHtml(getString(R.string.Swimming), null, new MyTagHandler()));
        }
        else if(selectedSport.equals("22"))
        {
            alertbox_title.setText("Table Tennis");
            alertbox_content = (TextView) view.findViewById(R.id.content);
            alertbox_content.setText(Html.fromHtml(getString(R.string.TableTennis), null, new MyTagHandler()));
        }

        else if(selectedSport.equals("23"))
        {
            alertbox_title.setText("Tennis");
            alertbox_content = (TextView) view.findViewById(R.id.content);
            alertbox_content.setText(Html.fromHtml(getString(R.string.Tennis), null, new MyTagHandler()));
        }
        else if(selectedSport.equals("24"))
        {
            alertbox_title.setText("Throwball");
            alertbox_content = (TextView) view.findViewById(R.id.content);
            alertbox_content.setText(Html.fromHtml(getString(R.string.Throwball), null, new MyTagHandler()));
        }
        else if(selectedSport.equals("25"))
        {
            alertbox_title.setText("Volley Ball");
            alertbox_content = (TextView) view.findViewById(R.id.content);
            alertbox_content.setText(Html.fromHtml(getString(R.string.Volleyball), null, new MyTagHandler()));
        }
        else if(selectedSport.equals("26"))
        {
            alertbox_title.setText("Volley Ball");
            alertbox_content = (TextView) view.findViewById(R.id.content);
            alertbox_content.setText(Html.fromHtml(getString(R.string.Volleyball_Girls), null, new MyTagHandler()));
        }
        else if(selectedSport.equals("27"))
        {
            alertbox_title.setText("SportsQuiz");
            TextView alertbox_content = (TextView) view.findViewById(R.id.content);
            alertbox_content.setText(Html.fromHtml(getString(R.string.SportsQuiz), null, new MyTagHandler()));
        }
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
    public void onDestroyView(){
        Runtime.getRuntime().gc();
        super.onDestroyView();
    }

    }
