package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.widget.ImageButton;


public class ListNeighbourPagerAdapter extends FragmentPagerAdapter {

    public ListNeighbourPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    /**
     * getItem is called to instantiate the fragment for the given page.
     */
    @Override
    public Fragment getItem(int position) {
        if(position == 1) {
            return NeighbourFragment.newInstance("favorites");
        } else {
            return NeighbourFragment.newInstance("neighbours");
        }
    }

    /**
     * get the number of pages
     */
    @Override
    public int getCount() {
        return 2;
    }
}