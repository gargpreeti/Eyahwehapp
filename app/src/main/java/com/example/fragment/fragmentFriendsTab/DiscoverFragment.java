package com.example.fragment.fragmentFriendsTab;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.adapter.adapterFriends.CustomGridFriends;
import com.example.fragment.fragmentHomeTab.FragmentHome;
import com.example.model.model_friends.Json_Friends_Discover;
import com.example.model.model_friends.Json_Friends_Discover_Srch;
import com.example.zotal102.yahwahapp.R;

public class DiscoverFragment extends Fragment {

    GridView grid;
    public static   EditText ed_srch;
    ImageView srch_icon;
    String srch;
    CustomGridFriends customGridFriends;
    Boolean flag=false;
    int pagenum=1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.fragment_discover, container, false);

        grid = (GridView) view.findViewById(R.id.gridView1);
        ed_srch=(EditText)view.findViewById(R.id.srch_txt);
        srch_icon=(ImageView)view.findViewById(R.id.srch_icon);

        srch =ed_srch.getText().toString().trim();


      ed_srch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    srch = ed_srch.getText().toString().trim();
                    String filter = "0";


                    new Json_Friends_Discover_Srch(getActivity(), grid).execute(FragmentHome.userid, srch, filter);


                    return true;
                }
                return false;
            }


        });


        String filter = "0";
        customGridFriends =new CustomGridFriends(getActivity(), Json_Friends_Discover.model_discover);
        grid.setAdapter(customGridFriends);

      new Json_Friends_Discover(getActivity(), customGridFriends).execute(FragmentHome.userid, srch, filter, "1");



     grid.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

                if (scrollState == 2)
                    flag = true;

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {


                    // TODO Auto-generated method stub
                    if ((visibleItemCount == (totalItemCount - firstVisibleItem))
                            && flag) {
                        flag = false;
                        String filter = "0";
                        pagenum++;
                        String newpage= String.valueOf(pagenum);

                        new Json_Friends_Discover(getActivity(), customGridFriends).execute(FragmentHome.userid, srch, filter, newpage);

                    }
            }
        });



        srch_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                srch =ed_srch.getText().toString().trim();
                String filter = "0";
                 new Json_Friends_Discover_Srch(getActivity(), grid).execute(FragmentHome.userid, srch, filter);

            }
        });
        return view;
    }
}


