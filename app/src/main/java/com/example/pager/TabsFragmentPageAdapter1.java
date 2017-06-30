package com.example.pager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.fragment.fragmentFriendsTab.EventFragment1;
import com.example.fragment.fragmentFriendsTab.ProductFragment1;
import com.example.fragment.fragmentFriendsTab.ServiceFragment1;

public class TabsFragmentPageAdapter1 extends FragmentPagerAdapter {

    public ProductFragment1 productFragment1;
      public ServiceFragment1 serviceFragment1;
    public TabsFragmentPageAdapter1(FragmentManager fm) {
        super(fm);
        // TODO Auto-generated constructor stub
    }

    @Override
    public Fragment getItem(int index) {
        // TODO Auto-generated method stub
        if (index == 0) {

            productFragment1=new ProductFragment1();

            return productFragment1;
        }
        if (index == 1) {
            return new EventFragment1();
        }

        if (index == 2) {

            serviceFragment1 = new ServiceFragment1();
        }

        if (index == 3) {

            serviceFragment1 = new ServiceFragment1();
        }
        return serviceFragment1;

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
       String title=" ";
        switch (position){
            case 0:
                title="Game";
                break;
            case 1:
                title="Movie";
                break;
            case 2:
                title="Study";
                break;
        }

        return title;
    }
}