package com.example.fragment.fragmentFriendsTab;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adapter.adapterFriends.CustomGridFriends;
import com.example.fragment.fragmentHomeTab.FragmentHome;
import com.example.fragment.fragmentMarketTab.Fragment_cart;
import com.example.model.model_friends.Json_FriendsDescription;
import com.example.model.model_friends.Model_getFriendsDescription;
import com.example.model.model_market.Json_Follow;
import com.example.pager.NonSwipePager;
import com.example.pager.TabsFragmentPageAdapter1;
import com.example.zotal102.yahwahapp.R;
import com.squareup.picasso.Picasso;


public class Fragment_discover extends Fragment

{
    Button btn_follow,btn_unfollow,btn_msg;
    ImageView img_user,bck_img;
    TextView tv_name;
    NonSwipePager viewPager;

    public TabLayout tab_layout;
    private String[] tabs = {"Product", "Event","Services"};

    private TabsFragmentPageAdapter1 tabsAdapter;
    String status;
     public static String tv_to,tv_id;
    ImageView img_cart;
    public static TextView tv_cart4;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        View view = inflater.inflate(R.layout.discover_detail, container, false);


        Bundle bundle = getArguments();
        Model_getFriendsDescription m=(Model_getFriendsDescription)bundle.getSerializable("friendsdata");



        bck_img=(ImageView)view.findViewById(R.id.back);
        btn_msg=(Button)view.findViewById(R.id.msg);
        viewPager=(NonSwipePager)view.findViewById(R.id.pager);
        tab_layout=(TabLayout)view.findViewById(R.id.tab_layout);

        btn_follow=(Button)view.findViewById(R.id.imageButton);
        btn_unfollow=(Button)view.findViewById(R.id.imageButton1);
        tv_name=(TextView)view.findViewById(R.id.txt_name);
        img_user=(ImageView)view.findViewById(R.id.user_img);

        img_cart=(ImageView) view.findViewById(R.id.cart);
        tv_cart4=(TextView) view.findViewById(R.id.total_cart);

       tv_cart4.setText(String.valueOf(FragmentFriends.tv_cart3.getText()));

        tv_name.setText(m.getUsername());
        Picasso.with(getActivity()).load(m.getUserimage()).into(img_user);


        tv_to=m.getUsername();
        tv_id= CustomGridFriends.otherid;
        if(Json_FriendsDescription.follow.equals("1")){
            btn_unfollow.setVisibility(View.VISIBLE);

        }
        else{

            btn_follow.setVisibility(View.VISIBLE);
        }


        tabsAdapter = new TabsFragmentPageAdapter1(getChildFragmentManager());
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
                Toast.makeText(getActivity(), "selected....." + tab.getPosition(), Toast.LENGTH_LONG);
            }
        });

        img_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fragmentManager = getFragmentManager();

                fragmentManager
                        .beginTransaction()
                        .replace(R.id.activity_main_content_fragment,
                                new Fragment_cart()).addToBackStack(null).commit();

            }
        });



        btn_follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                  status = "1";
                new Json_Follow(getActivity()).execute(FragmentHome.userid, CustomGridFriends.otherid, status);

                Toast.makeText(getActivity(), "You have started following", Toast.LENGTH_LONG).show();
                btn_unfollow.setVisibility(View.VISIBLE);
                btn_follow.setVisibility(View.INVISIBLE);
            }

        });

        btn_unfollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            status = "0";
                new Json_Follow(getActivity()).execute(FragmentHome.userid, CustomGridFriends.otherid, status);
                Toast.makeText(getActivity(), "You have stopped following", Toast.LENGTH_LONG).show();
                btn_follow.setVisibility(View.VISIBLE);
                btn_unfollow.setVisibility(View.INVISIBLE);
            }

        });


        btn_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();

                fragmentManager
                        .beginTransaction()
                        .replace(R.id.activity_main_content_fragment,
                                new NewMessageFragmentFriendsP()).commit();
            }
        });

        bck_img.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             FragmentManager fragmentManager = getFragmentManager();

             fragmentManager
                     .beginTransaction()
                     .replace(R.id.activity_main_content_fragment,
                             new FragmentFriends()).commit();
         }
     });



        return view;

    }


}

