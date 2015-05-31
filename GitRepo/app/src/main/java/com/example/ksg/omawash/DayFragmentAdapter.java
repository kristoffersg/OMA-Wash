package com.example.ksg.omawash;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by lassebuesvendsen on 30/05/15.
 */
public class DayFragmentAdapter extends FragmentPagerAdapter
{
    ArrayList<ArrayList<ISlotItem>> weekList;
    ArrayList<ISlotItem>            dayList;

    public DayFragmentAdapter(ArrayList<ArrayList<ISlotItem>> weekList,FragmentManager manager)
    {
        super(manager);
        this.weekList = weekList;
    }

    @Override
    public Fragment getItem(int position) {
        //dayList = weekList.get(position);
        return DayFragment.newInstance(1,weekList);
    }

    @Override
    public int getCount() {
        return 7;
    }
}
