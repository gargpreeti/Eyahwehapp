package com.example.fragment.fragmentMarketTab;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fragment.FragmentProfileTab.Frag_ProfileOption;
import com.example.fragment.FragmentProfileTab.Frag_notification;
import com.example.fragment.fragmentHomeTab.FragmentHome;
import com.example.model.model_market.Json_ShowCartTotal1;
import com.example.pager.NonSwipePager;
import com.example.pager.TabsFragmentMarket;
import com.example.zotal102.yahwahapp.R;


public class FragmentMarket extends Fragment {


    public static RelativeLayout rel;
    public static TextView tv_cart;
    ImageView img_cart;
    NonSwipePager viewPager;
    public TabLayout tab_layout;
    private String[] tabs = {"Products", "Events","Services"};
    private TabsFragmentMarket tabsAdapter;

    ImageView img_noti,img_setting;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        View view = inflater.inflate(R.layout.fragment_market, container, false);

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        img_cart=(ImageView) view.findViewById(R.id.cart);
        tv_cart=(TextView) view.findViewById(R.id.total_cart);
        rel=(RelativeLayout)view.findViewById(R.id.activity_main_content_fragment);
        img_setting=(ImageView)view.findViewById(R.id.more);

        new Json_ShowCartTotal1(getActivity()).execute(FragmentHome.userid, FragmentHome.usertokn);

        tv_cart.setText(String.valueOf(Json_ShowCartTotal1.totalcart));

        viewPager=(NonSwipePager)view.findViewById(R.id.pager);
        tab_layout=(TabLayout)view.findViewById(R.id.tab_layout);

        img_noti=(ImageView)view.findViewById(R.id.noti);

        img_noti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fragmentManager = getFragmentManager();

                fragmentManager
                        .beginTransaction()
                        .replace(R.id.activity_main_content_fragment,
                                new Frag_notification()).addToBackStack(null).commit();
            }
        });



        tabsAdapter = new TabsFragmentMarket(getChildFragmentManager());
        viewPager.setAdapter(tabsAdapter);


        for (String tab_name : tabs) {
            tab_layout.addTab(tab_layout.newTab().setText(tab_name));
        }

        tab_layout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition(), false);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                Toast.makeText(getActivity(), "", Toast.LENGTH_LONG);
                viewPager.setCurrentItem(tab.getPosition(), false);
                Toast.makeText(getActivity(), "selected....."+tab.getPosition(), Toast.LENGTH_LONG);
            }
        });

        img_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();

                fragmentManager
                        .beginTransaction()
                        .replace(R.id.activity_main_content_fragment1,
                                new Fragment_cart()).addToBackStack(null).commit();

            }
        });
        img_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fragmentManager = getFragmentManager();

                fragmentManager
                        .beginTransaction()
                        .replace(R.id.activity_main_content_fragment,
                                new Frag_ProfileOption()).addToBackStack(null).commit();

            }
        });

        return view;

    }}