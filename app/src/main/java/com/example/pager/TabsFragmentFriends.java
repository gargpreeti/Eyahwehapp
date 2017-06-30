package com.example.pager;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.fragment.fragmentFriendsTab.DiscoverFragment;
import com.example.fragment.fragmentFriendsTab.FollowersFragment;
import com.example.fragment.fragmentFriendsTab.FollowingFragment;

public class TabsFragmentFriends extends FragmentPagerAdapter {

    public TabsFragmentFriends(FragmentManager fm) {
        super(fm);
        // TODO Auto-generated constructor stub
    }



    @Override
    public Fragment getItem(int index) {
        // TODO Auto-generated method stub
        if(index == 0)
              return new DiscoverFragment();

 if(index == 1)
            return new FollowersFragment();
            if(index == 2)
               return new FollowingFragment();

        return null;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return 3;
    }

}