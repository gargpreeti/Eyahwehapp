package com.example.pager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.fragment.FragmentProfileTab.EventFragment;
import com.example.fragment.FragmentProfileTab.ProductFragment;
import com.example.fragment.FragmentProfileTab.ServiceFragment;

public class TabsFragmentPageAdapter extends FragmentPagerAdapter {

    public static ProductFragment productFragment;
    public static ServiceFragment serviceFragment;
    public static EventFragment eventFragment;
    public  TabsFragmentPageAdapter(FragmentManager fm) {
        super(fm);
        // TODO Auto-generated constructor stub
    }

    @Override
    public Fragment getItem(int index) {
        // TODO Auto-generated method stub
        if (index == 0) {

            productFragment=new ProductFragment();
             return productFragment;
        }
        if (index == 1) {
            eventFragment=new EventFragment();
             return eventFragment;

        }
        if (index == 2)
    serviceFragment=new ServiceFragment();
return serviceFragment;

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return 3;
    }

}