package com.example.fragment.fragmentFriendsTab;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.adapter.adapterFriends.CustomGridFriends;
import com.example.fragment.fragmentHomeTab.FragmentHome;
import com.example.model.model_friends.Json_FriendsDescription1;
import com.example.zotal102.yahwahapp.R;

public class ProductFragment1 extends Fragment {

    ListView grid;
    View view;

  public static TextView tv_msg;
    public static String product_name;
    public static  String pid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        view = inflater.inflate(R.layout.fragment_productviewfriends, container, false);

        grid = (ListView) view.findViewById(R.id.gridView1);

        tv_msg=(TextView)view.findViewById(R.id.tv_msg);



     new Json_FriendsDescription1(getActivity(),grid).execute(FragmentHome.userid, FragmentHome.usertokn, CustomGridFriends.otherid);





        return view;
    }



    }












