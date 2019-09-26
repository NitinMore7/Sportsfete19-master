package spider.app.sportsfete19.Sponsors;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.azoft.carousellayoutmanager.CarouselLayoutManager;
import com.azoft.carousellayoutmanager.CarouselZoomPostLayoutListener;
import com.azoft.carousellayoutmanager.CenterScrollListener;

import java.util.ArrayList;
import java.util.List;

import spider.app.sportsfete19.R;
import yalantis.com.sidemenu.interfaces.ScreenShotable;


/**
 * A simple {@link Fragment} subclass.
 */
public class SponsorFragment extends Fragment implements ScreenShotable {

    private RecyclerView recyclerView;
    private SponsorAdapter mAdapter;
    private List<Sponsor> sponsorlist;


    public SponsorFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sponsor, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view,savedInstanceState);
        final CarouselLayoutManager layoutManager = new CarouselLayoutManager(CarouselLayoutManager.HORIZONTAL,true);
        recyclerView = (RecyclerView)view.findViewById(R.id.my_recycler_view);
        sponsorlist= new ArrayList<>();
        sponsorlist.add(new Sponsor("Oneplus",R.drawable.oneplus));
        sponsorlist.add(new Sponsor("Poorvika",R.drawable.poorvika));
        sponsorlist.add(new Sponsor("RedBull",R.drawable.redbull));
        sponsorlist.add(new Sponsor("Indian Overseas Bank",R.drawable.overseasbank));
        sponsorlist.add(new Sponsor("Lifestyle",R.drawable.lifestyle));
        sponsorlist.add(new Sponsor("Naturals",R.drawable.naturals));
        sponsorlist.add(new Sponsor("Superstar Pizza",R.drawable.superstarpizza));
        sponsorlist.add(new Sponsor("Pepsi",R.drawable.pepsi));
        sponsorlist.add(new Sponsor("Andavar",R.drawable.andavar));
        layoutManager.setPostLayoutListener(new CarouselZoomPostLayoutListener());
        mAdapter = new SponsorAdapter(getActivity(),sponsorlist);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mAdapter);
        recyclerView.addOnScrollListener(new CenterScrollListener());

    }

    @Override
    public void takeScreenShot() {

    }

    @Override
    public Bitmap getBitmap() {
        return null;
    }
}
