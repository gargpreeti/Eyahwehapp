package com.example.fragment.FragmentProfileTab;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.fragment.fragmentHomeTab.FragmentHome;
import com.example.model.model_profile.Json_BuyerHistory;
import com.example.zotal102.yahwahapp.R;


public class Fragment_BuyerHistory extends Fragment {


    public ListView crt_list1;
    ImageView bck_img;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        View view = inflater.inflate(R.layout.fragment_buyer, container, false);

         crt_list1=(ListView) view.findViewById(R.id.list);

       new Json_BuyerHistory(getActivity(),crt_list1).execute(FragmentHome.userid);


        bck_img=(ImageView)view.findViewById(R.id.back);

        bck_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                  FragmentManager fragmentManager1 = getFragmentManager();
                fragmentManager1.popBackStack();

               }
        });



        return view;

    }



}
