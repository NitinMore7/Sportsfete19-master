package spider.app.sportsfete19.Schedule;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


/**
 * Created by dhananjay on 21/1/17.
 */

public class ScheduleViewPagerAdapter extends FragmentStatePagerAdapter {

    String[] tabs={"Day 0","Day 1","Day 2","Day 3"};

    public ScheduleViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new Day1Fragment();
            case 1:
                return new Day2Fragment();
            case 2:
                return new Day3Fragment();
            case 3:
                return new Day4Fragment();
        }
        return null;
    }


    @Override
    public int getCount() {
        return tabs.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabs[position];
    }
}
