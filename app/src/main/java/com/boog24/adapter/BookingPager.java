package com.boog24.adapter;

import com.boog24.fragment.booking.CancelledFragment;
import com.boog24.fragment.booking.CompletedFragment;
import com.boog24.fragment.booking.UpcomingFragment;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class BookingPager extends FragmentStatePagerAdapter {

    //integer to count number of tabs
    int tabCount;

    //Constructor to the class
    public BookingPager(FragmentManager fm, int tabCount) {
        super(fm);
        //Initializing tab count
        this.tabCount= tabCount;
    }

    //Overriding method getItem
    @Override
    public Fragment getItem(int position) {

        //Returning the current tabs
        switch (position) {
            case 0:
                UpcomingFragment tab1 = new UpcomingFragment();
                return tab1;
            case 1:
                CompletedFragment tab2 = new CompletedFragment();
                return tab2;
            case 2:
                CancelledFragment tab3 = new CancelledFragment();
                return tab3;

            default:
                return null;
        }
    }

    //Overriden method getCount to get the number of tabs
    @Override
    public int getCount() {
        return tabCount;
    }
}

