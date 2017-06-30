package com.example.fragment.fragmentFriendsTab;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.adapter.adapterFriends.CustomGridFriendsFollowers;
import com.example.fragment.fragmentHomeTab.FragmentHome;
import com.example.model.model_friends.Json_Friends_Followers;
import com.example.model.model_friends.Json_Friends_Followers_srch;
import com.example.zotal102.yahwahapp.R;


public class FollowersFragment extends Fragment {

    GridView grid;
    public static EditText ed_srch;
    ImageView srch_icon;
    String srch="";
    CustomGridFriendsFollowers customGridFriendsFollowers;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.fragment_following, container, false);

        grid = (GridView) view.findViewById(R.id.gridView1);
          ed_srch=(EditText)view.findViewById(R.id.srch_txt);
        srch_icon=(ImageView)view.findViewById(R.id.srch_icon);



        customGridFriendsFollowers =new CustomGridFriendsFollowers(getActivity(), Json_Friends_Followers.model_discover);
        grid.setAdapter(customGridFriendsFollowers);


        String filter="2";
        new Json_Friends_Followers(getActivity(), customGridFriendsFollowers).execute(FragmentHome.userid,srch,filter,"1");

     grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {



                FragmentManager fragmentManager = getFragmentManager();

                fragmentManager
                        .beginTransaction()
                        .replace(R.id.activity_main_content_fragment,
                                new Fragment_discover()).commit();

            }
        });

        srch_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                srch = ed_srch.getText().toString().trim();
                String filter="2";
               new Json_Friends_Followers_srch(getActivity(), grid).execute(FragmentHome.userid, srch, filter,"1");
            }
        });
        return view;
    }

}