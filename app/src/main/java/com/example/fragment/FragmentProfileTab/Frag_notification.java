package com.example.fragment.FragmentProfileTab;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.fragment.fragmentHomeTab.FragmentHome;
import com.example.model.model_profile.Json_Notificationlist;
import com.example.zotal102.yahwahapp.R;


public class Frag_notification extends Fragment {


     String result2="";
    RelativeLayout bck_layout;
    ImageView bck_img;

    ListView list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        View view = inflater.inflate(R.layout.fragment_notification, container, false);

        bck_img=(ImageView)view.findViewById(R.id.back);

        bck_layout=(RelativeLayout)view.findViewById(R.id.header_layout1);
        bck_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                FragmentManager fragmentManager1 = getFragmentManager();
                fragmentManager1.popBackStack();


            }
        });


        list=(ListView) view.findViewById(R.id.listview);

        new Json_Notificationlist(getActivity(),list).execute(FragmentHome.userid);



        return view;

    }



}



