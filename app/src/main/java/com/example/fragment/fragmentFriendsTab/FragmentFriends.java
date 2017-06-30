package com.example.fragment.fragmentFriendsTab;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fragment.FragmentProfileTab.Frag_ProfileOption;
import com.example.fragment.FragmentProfileTab.Frag_notification;
import com.example.fragment.fragmentHomeTab.FragmentHome;
import com.example.fragment.fragmentMarketTab.Fragment_cart;
import com.example.model.model_market.Json_ShowCartTotal2;
import com.example.pager.NonSwipePager;
import com.example.pager.TabsFragmentFriends;
import com.example.zotal102.yahwahapp.R;


public class FragmentFriends extends Fragment {


    NonSwipePager viewPager;
    public TabLayout tab_layout;
    private String[] tabs = {"Discover", "Followers","Following"};
    private TabsFragmentFriends tabsAdapter;
    public  static  TextView tv_cart3;
    ImageView crt_img;
    ImageView img_noti,img_setting;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        View view = inflater.inflate(R.layout.fragment_friends, container, false);

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        tv_cart3=(TextView) view.findViewById(R.id.total_cart);
        crt_img=(ImageView) view.findViewById(R.id.cart);
        viewPager=(NonSwipePager)view.findViewById(R.id.pager);
        tab_layout=(TabLayout)view.findViewById(R.id.tab_layout);
        img_setting=(ImageView)view.findViewById(R.id.more);

        tabsAdapter = new TabsFragmentFriends(getChildFragmentManager());
        viewPager.setAdapter(tabsAdapter);

        new Json_ShowCartTotal2(getActivity()).execute(FragmentHome.userid, FragmentHome.usertokn);
        tv_cart3.setText(String.valueOf(Json_ShowCartTotal2.totalcart));


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

    crt_img.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();

                fragmentManager
                .beginTransaction()
                .replace(R.id.activity_main_content_fragment,
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