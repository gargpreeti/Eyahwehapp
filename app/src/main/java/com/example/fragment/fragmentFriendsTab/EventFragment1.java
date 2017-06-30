package com.example.fragment.fragmentFriendsTab;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.example.adapter.adapterFriends.CustomGridFriends;
import com.example.fragment.fragmentHomeTab.FragmentHome;
import com.example.model.model_friends.Json_EventDescriptionFriends;
import com.example.model.model_friends.Json_FriendsDescription2;
import com.example.zotal102.yahwahapp.R;


public class EventFragment1 extends Fragment {

    GridView grid1;

    View view;
   public static String eid;
    public static TextView tv_msg;
    public static String product_name;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.fragment_event11, container, false);

        grid1 = (GridView) view.findViewById(R.id.gridView1);
        tv_msg=(TextView)view.findViewById(R.id.tv_msg);


        new Json_FriendsDescription2(getActivity(),grid1).execute(FragmentHome.userid, FragmentHome.usertokn, CustomGridFriends.otherid);


            grid1.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

              eid = Json_FriendsDescription2.obj_friendsDescription.getAl_event_detail().get(position).getEvent_id();
                       new Json_EventDescriptionFriends(getActivity()).execute(eid,FragmentHome.userid,"e");


            }
        });





        return view;
    }







}












