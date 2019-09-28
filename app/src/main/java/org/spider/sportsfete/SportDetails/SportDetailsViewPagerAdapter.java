package org.spider.sportsfete.SportDetails;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

/**
 * Created by dhananjay on 7/2/17.
 */

public class SportDetailsViewPagerAdapter extends FragmentStatePagerAdapter {

    String[] tabs={"RuleBook", "Fixtures"};
    String selectedSport;
    int count = 2;


    public SportDetailsViewPagerAdapter(FragmentManager fm,String selectedSport) {
        super(fm);
        this.selectedSport=selectedSport;
    }

    @Override
    public Fragment getItem(int position) {
        Log.i("Sport", selectedSport);
        switch (position) {
            case 0:
                RuleBookFragment ruleBookFragment = new RuleBookFragment();
                Bundle bundle = new Bundle();
                bundle.putString("SELECTED_SPORT", selectedSport);
                ruleBookFragment.setArguments(bundle);
                return ruleBookFragment;
            case 1:
                FixturesFragment fixturesFragment = new FixturesFragment();
                Bundle bundle1 = new Bundle();
                bundle1.putString("SELECTED_SPORT", selectedSport);
                fixturesFragment.setArguments(bundle1);
                return fixturesFragment;
        }

        return null;
    }

    @Override
    public int getCount() {
        if(selectedSport.equals("17") || selectedSport.equals("18") || selectedSport.equals("27")){
            count = 2;
        }else{
            count = 2;
        }
        return count;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabs[position];
    }
}
