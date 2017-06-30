package com.example.fragment.fragmentMarketTab;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.adapter.adapterMarket.CustomGridProductMarket;
import com.example.common.InteractiveScrollView;
import com.example.fragment.fragmentHomeTab.FragmentHome;
import com.example.model.model_market.Json_GetDataMarket;
import com.example.model.model_market.Json_GetDataMarket1;
import com.example.model.model_market.Json_GetDataMarket_srch;
import com.example.zotal102.yahwahapp.R;

public class Frag_MarketProduct extends Fragment {

    GPSTracker gps;
    ////// to find currnet location/////////
    protected Context context;
    public TabLayout tab_layout;
     ListView gridview;

    public static String product_name;
    View view;
    String filtr, type, srch;
    ImageView srch_icon;

    EditText ed_srch;
    Button btn_area, btn_nation;
    EditText txt_srch;
    String lat, lng;
    RelativeLayout rel;
    double latitude, longitude;
    InteractiveScrollView scrollView;
    int pagenum = 1;
    String newpage;
   CustomGridProductMarket   customGridProductMarket;


    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        view = inflater.inflate(R.layout.fragment_marketproduct, container, false);
        rel = (RelativeLayout) view.findViewById(R.id.lay1);

        srch_icon = (ImageView) view.findViewById(R.id.srch_icon);
        ed_srch = (EditText) view.findViewById(R.id.srch_txt);
        scrollView = (InteractiveScrollView) view.findViewById(R.id.scrollView);



        ///////////////////////////////////////
        gridview = (ListView) view.findViewById(R.id.gridView1);

        txt_srch = (EditText) view.findViewById(R.id.srch_txt);

        srch = "";
        filtr = "all";
         lat="";
        lng="";
        type = "p";

        ed_srch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    srch = ed_srch.getText().toString().trim();

                    new Json_GetDataMarket_srch(getActivity(),gridview).execute(filtr, srch, type, lat, lng, FragmentHome.userid,"1");
                    ed_srch.setText("");
                    return true;
                }
                return false;
            }


        });

        btn_area = (Button) view.findViewById(R.id.area);
        btn_nation = (Button) view.findViewById(R.id.nation);
        btn_nation.setBackgroundDrawable(getResources().getDrawable(R.drawable.nationwide));
        btn_area.setBackgroundDrawable(getResources().getDrawable(R.drawable.inyourarea));


        customGridProductMarket= new CustomGridProductMarket(getActivity(), Json_GetDataMarket.model_market);
        gridview.setAdapter(customGridProductMarket);

        new Json_GetDataMarket(getActivity(), customGridProductMarket).execute(filtr, srch, type, lat, lng, FragmentHome.userid, "1");


    scrollView.setOnBottomReachedListener(new InteractiveScrollView.OnBottomReachedListener() {
        @Override
        public void onBottomReached() {

            pagenum++;
               newpage = String.valueOf(pagenum);
                new Json_GetDataMarket(getActivity(), customGridProductMarket).execute(filtr, srch, type, lat, lng, FragmentHome.userid, newpage);



        }
    });



        srch_icon.setOnClickListener(new View.OnClickListener()

                                     {
                                         @Override
                                         public void onClick(View v) {
                                             srch = ed_srch.getText().toString().trim();

                                             new Json_GetDataMarket_srch(getActivity(),gridview).execute(filtr, srch, type, lat, lng, FragmentHome.userid,"1");

                                         }
                                     }

        );
        btn_area.setOnClickListener(new View.OnClickListener()

                                    {
                                        @Override
                                        public void onClick(View v) {

                                            srch = "";
                                            filtr = "inarea";
                                            type = "p";

                                            gps = new GPSTracker(getActivity());
                                            // check if GPS enabled
                                            if (gps.canGetLocation()) {
                                                latitude = gps.getLatitude();
                                                longitude = gps.getLongitude();

                                                Log.e("lat",""+latitude);
                                                Log.e("long",""+longitude);
new Json_GetDataMarket1(getActivity(), gridview).execute(filtr, srch, type, String.valueOf(latitude), String.valueOf(longitude), FragmentHome.userid,"1");


                                                gridview.setVisibility(View.VISIBLE);

                                            } else {
                                                gps.showSettingsAlert();

                                            }

                                            gridview.setVisibility(View.VISIBLE);

                                            gridview.setVerticalScrollBarEnabled(false);

                                            btn_area.setBackgroundDrawable(getResources().getDrawable(R.drawable.inyourarea01));
                                            btn_nation.setBackgroundDrawable(getResources().getDrawable(R.drawable.nationwide0));
                                        }
                                    }

        );
        btn_nation.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {

                srch = "";
                filtr = "all";
                lat = "";
                lng = "";
                type = "p";

                       new Json_GetDataMarket(getActivity(), customGridProductMarket).execute(filtr, srch, type, lat, lng, FragmentHome.userid, "1");

                customGridProductMarket= new CustomGridProductMarket(getActivity(), Json_GetDataMarket.model_market);
                gridview.setAdapter(customGridProductMarket);

                btn_nation.setBackgroundDrawable(getResources().getDrawable(R.drawable.nationwide));
                gridview.setVisibility(View.VISIBLE);

                gridview.setVerticalScrollBarEnabled(false);
                btn_area.setBackgroundDrawable(getResources().getDrawable(R.drawable.inyourarea));
            }
        });


        return view;
    }


}