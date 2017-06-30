package com.example.fragment.fragmentMarketTab;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.adapter.adapterMarket.CustomGridEventMarket;
import com.example.common.InteractiveScrollView;
import com.example.fragment.fragmentHomeTab.FragmentHome;
import com.example.model.model_market.Json_GetDataMarketEvent;
import com.example.model.model_market.Json_GetDataMarketEvent1;
import com.example.model.model_market.Json_GetDataMarketEvent_srch;
import com.example.zotal102.yahwahapp.R;


public class Frag_MarketEvent extends Fragment {

    GPSTracker gps;

    Button btn_area,btn_nation;
    ImageView img;
   ListView grid;
    String filtr,lat,lng,type,srch;
    ImageView srch_icon;
    public static EditText ed_srch;
    double latitude, longitude;
    CustomGridEventMarket customGridEventMarket;
     int pagenum=1;
    String newpage;
    InteractiveScrollView scrollView1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.fragment_marketevent, container, false);
        srch_icon=(ImageView)view.findViewById(R.id.srch_icon);
        ed_srch=(EditText)view.findViewById(R.id.srch_txt);
        scrollView1=(InteractiveScrollView)view.findViewById(R.id.scrollViewId);


        grid = (ListView) view.findViewById(R.id.gridView1);
        srch="";
        filtr="all";
        lat="";
        lng="";
        type="e";


        img=(ImageView)view.findViewById(R.id.imageView2);
        btn_area=(Button)view.findViewById(R.id.area);
        btn_nation=(Button)view.findViewById(R.id.nation);
        btn_nation.setBackgroundDrawable(getResources().getDrawable(R.drawable.nationwide));
        btn_area.setBackgroundDrawable(getResources().getDrawable(R.drawable.inyourarea));


        customGridEventMarket	=new CustomGridEventMarket(getActivity(), Json_GetDataMarketEvent.model_market);
        grid.setAdapter(customGridEventMarket);

        new Json_GetDataMarketEvent(getActivity(),customGridEventMarket).execute(filtr, srch, type, lat, lng, FragmentHome.userid, "1");


        if(ed_srch.equals("")){

            customGridEventMarket	=new CustomGridEventMarket(getActivity(), Json_GetDataMarketEvent.model_market);
            grid.setAdapter(customGridEventMarket);

            new Json_GetDataMarketEvent(getActivity(),customGridEventMarket).execute(filtr, srch, type, lat, lng, FragmentHome.userid, "1");

        }




        ed_srch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    srch = ed_srch.getText().toString().trim();
                    new Json_GetDataMarketEvent_srch(getActivity(), grid).execute(filtr, srch, type, lat, lng, FragmentHome.userid,"1");

                    ed_srch.setText("");
                       return true;
                }

                return false;
            }


        });


        scrollView1.setOnBottomReachedListener(new InteractiveScrollView.OnBottomReachedListener() {
            @Override
            public void onBottomReached() {

                pagenum++;
                 newpage = String.valueOf(pagenum);
                        new Json_GetDataMarketEvent(getActivity(),customGridEventMarket).execute(filtr, srch, type, lat, lng, FragmentHome.userid, newpage);


            }
        });



        btn_area.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                grid.setVisibility(View.INVISIBLE);

                srch="";
                filtr="inarea";
                  type="e";
                gps = new GPSTracker(getActivity());
                if(gps.canGetLocation()){
                   latitude = gps.getLatitude();
                   longitude = gps.getLongitude();


     new Json_GetDataMarketEvent1(getActivity(),grid).execute(filtr, srch, type, String.valueOf(latitude), String.valueOf(longitude), FragmentHome.userid, "1");

                      grid.setVisibility(View.VISIBLE);

                }
                else{
                    gps.showSettingsAlert();


                }


                grid.setVerticalScrollBarEnabled(false);

                btn_area.setBackgroundDrawable(getResources().getDrawable(R.drawable.inyourarea01));
                btn_nation.setBackgroundDrawable(getResources().getDrawable(R.drawable.nationwide0));


            }
        });

        btn_nation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                srch="";
                filtr="all";
                lat="";
                lng="";
                type="e";


                grid.setVisibility(View.VISIBLE);
                new Json_GetDataMarketEvent(getActivity(),customGridEventMarket).execute(filtr, srch, type, lat, lng, FragmentHome.userid,"1");

             customGridEventMarket	=new CustomGridEventMarket(getActivity(), Json_GetDataMarketEvent.model_market);
              grid.setAdapter(customGridEventMarket);

                btn_nation.setBackgroundDrawable(getResources().getDrawable(R.drawable.nationwide));
                grid.setVisibility(View.VISIBLE);

                grid.setVerticalScrollBarEnabled(false);
                btn_area.setBackgroundDrawable(getResources().getDrawable(R.drawable.inyourarea));
            }
        });


        srch_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                srch = ed_srch.getText().toString().trim();


              new Json_GetDataMarketEvent_srch(getActivity(), grid).execute(filtr, srch, type, lat, lng, FragmentHome.userid, "1");

                ed_srch.setText("");


            }
        });


        return view;
    }


}