package com.example.pager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.fragment.fragmentMarketTab.Frag_MarketEvent;
import com.example.fragment.fragmentMarketTab.Frag_MarketProduct;
import com.example.fragment.fragmentMarketTab.Frag_MarketService;


public class TabsFragmentMarket extends FragmentPagerAdapter {

    public TabsFragmentMarket(FragmentManager fm) {
        super(fm);
        // TODO Auto-generated constructor stub
    }



    @Override
    public Fragment getItem(int index) {
        // TODO Auto-generated method stub
        if(index == 0)
             return new Frag_MarketProduct();
        if(index == 1)
            return new Frag_MarketEvent();
        if(index == 2)
            return new Frag_MarketService();
        return null;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return 3;
    }

}