package com.example.pager;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.example.fragment.fragmentHomeTab.FragmentHome;

import com.example.fragment.FragmentProfileTab.FragmentProfile;
import com.example.fragment.FragmentProfileTab.ProductFragment;
import com.example.zotal102.yahwahapp.R;

import java.util.List;
import java.util.Vector;



class ViewPagerFragmentActivity extends FragmentActivity{
    /** maintains the pager adapter*/
    private PagerAdapter mPagerAdapter;
      @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.view_pager);
             this.initialisePaging();
    }

    /**
     * Initialise the fragments to be paged
     */
    private void initialisePaging() {

        List<Fragment> fragments = new Vector<Fragment>();
        fragments.add(Fragment.instantiate(this, ProductFragment.class.getName()));
        fragments.add(Fragment.instantiate(this, FragmentHome.class.getName()));
        fragments.add(Fragment.instantiate(this, FragmentProfile.class.getName()));
        this.mPagerAdapter  = new PagerAdapter(super.getSupportFragmentManager(), fragments);
        //
        ViewPager pager = (ViewPager)super.findViewById(R.id.myViewPager);
        pager.setAdapter(this.mPagerAdapter);
    }
}