package spider.app.sportsfete19;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import yalantis.com.sidemenu.interfaces.ScreenShotable;

/**
 * Created by Konstantin on 22.12.2014.
 */
public class ContentFragment extends Fragment implements ScreenShotable {
    public static final String LIVE = "Live";
    public static final String LEADERBOARD = "LeaderBoard";
    public static final String SCHEDULE = "Schedule";
    public static final String SEARCH = "Search";
    public static final String SPORTS = "Sports";
    public static final String GAME = "Game";
    public static final String FIRST = "First";
    public static final String SPONSOR = "Sponsors";
    public static final String SIGNOUT = "SignOut";

    private View containerView;
    protected ImageView mImageView,imageView2;
    protected int res;

    public static ContentFragment newInstance(int resId) {
        ContentFragment contentFragment = new ContentFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Integer.class.getName(), resId);
        contentFragment.setArguments(bundle);
        return contentFragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.containerView = view.findViewById(R.id.container);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        res = getArguments().getInt(Integer.class.getName());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        mImageView = (ImageView) rootView.findViewById(R.id.image_content);
        mImageView.setBackgroundColor(Color.RED);
        //Picasso.with(getContext()).load(res).centerCrop().into(mImageView);
        mImageView.setClickable(true);
        mImageView.setFocusable(true);
        return rootView;
    }

    @Override
    public void takeScreenShot() {

    }

    @Override
    public Bitmap getBitmap() {
        return null;
    }
}
