package spider.app.sportsfete19.SportDetails;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.twotoasters.jazzylistview.JazzyHelper;
import com.twotoasters.jazzylistview.recyclerview.JazzyRecyclerViewScrollListener;

import java.util.HashMap;

import rx.functions.Action1;
import spider.app.sportsfete19.R;
import yalantis.com.sidemenu.interfaces.ScreenShotable;

/**
 * A simple {@link Fragment} subclass.
 */
public class SportDetailsFragment extends Fragment implements ScreenShotable {

    private static final String TAG="SportDetailsFragment";
    RecyclerView recyclerView;
    Context context;
    SportDetailsRecyclerAdapter recyclerAdapter;
    HashMap<String,Integer> game_fixture;

    String[] sportsArray ;
    private int currentTransitionEffect = JazzyHelper.TILT;
    JazzyRecyclerViewScrollListener jazzyRecyclerViewScrollListener;

    public SportDetailsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sport_details, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.context=getContext();

        game_fixture = new HashMap<String, Integer>();
        game_fixture = setMap(game_fixture);

        recyclerView= (RecyclerView) view.findViewById(R.id.rule_book_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        jazzyRecyclerViewScrollListener = new JazzyRecyclerViewScrollListener();
        jazzyRecyclerViewScrollListener.setTransitionEffect(currentTransitionEffect);
        recyclerView.setOnScrollListener(jazzyRecyclerViewScrollListener);
        sportsArray = getResources().getStringArray(R.array.sport_array);
        recyclerAdapter=new SportDetailsRecyclerAdapter(sportsArray,context);
        recyclerView.setAdapter(recyclerAdapter);

        setClickListener();
    }

    void setClickListener(){
        rx.Observable<String> observable= recyclerAdapter.getPositionClicks();
        observable.subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.d("index",game_fixture.get(sportsArray[Integer.parseInt(s)])+""+sportsArray[Integer.parseInt(s)]);
                Intent intent=new Intent(getActivity(), SportDetailsActivity.class);
                intent.putExtra("SELECTED_SPORT",s);
                intent.putExtra("FIXTURE_TYPE",game_fixture.get(sportsArray[Integer.parseInt(s)]));
                startActivity(intent);


            }
        });
    }

    public HashMap<String, Integer> setMap(HashMap<String, Integer> game_fixture){
        game_fixture.put("Athletics",0);
        game_fixture.put("Badminton(Mixed)",1);
        game_fixture.put("Basketball(Boys)",1);
        game_fixture.put("Basketball(Girls)",2);
        game_fixture.put("Carrom",3);
        game_fixture.put("Chess",0);
        game_fixture.put("Cricket(Boys)",1);
        game_fixture.put("Cricket(Girls)",1);
        game_fixture.put("Football(Boys)",4);
        //game_fixture.put("Football(Girls)",5);
        game_fixture.put("Handball(Boys)",1);
        game_fixture.put("Handball(Girls)",5);
        game_fixture.put("Hockey(Boys)",6);
        game_fixture.put("Kabbadi",1);
        game_fixture.put("Kho Kho(Boys)",7);
        game_fixture.put("Kho Kho(Girls)",8);
        game_fixture.put("Marathon",0);
        game_fixture.put("Swimming",0);
        game_fixture.put("Power Lifting",0);
        game_fixture.put("Table Tennis(Mixed)",1);
        //game_fixture.put("Table Tennis(Girls)",9);
        game_fixture.put("Tennis(Boys)",10);
        //game_fixture.put("Tennis(Girls)",11);
        game_fixture.put("Throwball",1);
        game_fixture.put("Volleyball(Boys)",1);
        game_fixture.put("Volleyball(Girls)",2);
        //game_fixture.put("Water Polo",1);
        //game_fixture.put("Weightlifting(Boys)",0);
        game_fixture.put("SportsQuiz",0);

        return game_fixture;
    }

    @Override
    public void takeScreenShot() {

    }

    @Override
    public Bitmap getBitmap() {
        return null;
    }
}
