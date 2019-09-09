package spider.app.sportsfete19.Following;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Abhi Nabera on 2/21/2018.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {


    int mNoofTabs;

    public PagerAdapter(FragmentManager fm, int NumberofTabs){
        super(fm);
        this.mNoofTabs = NumberofTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch(position) {

            case 0:
                SubscribeDepartment subscribeDepartment = new SubscribeDepartment();
                return subscribeDepartment;
            case 1:
                SubscribeSport subscribeSport = new SubscribeSport();
                return subscribeSport;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNoofTabs;
    }
}
