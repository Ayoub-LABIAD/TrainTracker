package com.traintracker.traintracker.manager;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.traintracker.traintracker.map.MapFragment;

/**
 * Created by ayoub on 16-Aug-17.
 */

public class ManagerFragmentAdapter extends FragmentPagerAdapter {

    final int PAGE_COUNT = 2;
    private String tabTitles[] = new String[]{"Graphs", "Map"};
    private Context context;

    public ManagerFragmentAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0)
            return ManagerFragment.newInstance(1);
        else
            return MapFragment.newInstance(2);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}
