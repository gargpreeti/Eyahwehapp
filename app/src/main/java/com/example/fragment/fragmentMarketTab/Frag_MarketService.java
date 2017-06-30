package com.example.fragment.fragmentMarketTab;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.adapter.adapterMarket.CustomGridServiceMarket;
import com.example.common.InteractiveScrollView;
import com.example.fragment.fragmentHomeTab.FragmentHome;
import com.example.model.model_market.Json_GetDataMarketService;
import com.example.model.model_market.Json_GetDataMarketService1;
import com.example.model.model_market.Json_GetDataMarketService_srch;
import com.example.zotal102.yahwahapp.R;

public class Frag_MarketService extends Fragment {

    Button btn_area,btn_nation;
    GPSTracker gps;

    CustomGridServiceMarket customGridServiceMarket;
    double latitude, longitude;
    ListView grid1;
    String filtr,lat,lng,type,srch;
    public static String service_name;
    ImageView srch_icon;
    public static EditText ed_srch;
    InteractiveScrollView scrollView2;
    int pagenum=1;
    String newpage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.fragment_marketservice, container, false);

        grid1 = (ListView) view.findViewById(R.id.gridView1);

        srch_icon=(ImageView)view.findViewById(R.id.srch_icon);
        ed_srch=(EditText)view.findViewById(R.id.srch_txt);
        scrollView2=(InteractiveScrollView)view.findViewById(R.id.scrollViewId);


        srch="";
        filtr="all";
        lat="";
        lng="";
        type="s";


        btn_area=(Button)view.findViewById(R.id.area);
        btn_nation = (Button) view.findViewById(R.id.nation);
        btn_nation.setBackgroundDrawable(getResources().getDrawable(R.drawable.nationwide));
        btn_area.setBackgroundDrawable(getResources().getDrawable(R.drawable.inyourarea));

        grid1.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {


                FragmentManager fragmentManager1 = getFragmentManager();

                fragmentManager1
                        .beginTransaction()
                        .replace(R.id.activity_main_content_fragment,
                                new Fragment_marketservicedetail()).commit();


            }
        });
        customGridServiceMarket	=new CustomGridServiceMarket(getActivity(), Json_GetDataMarketService.model_market);
        grid1.setAdapter(customGridServiceMarket);

        new Json_GetDataMarketService(getActivity(),customGridServiceMarket).execute(filtr, srch, type, lat, lng, FragmentHome.userid, "1");


        ed_srch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    srch = ed_srch.getText().toString().trim();

                    new Json_GetDataMarketService_srch(getActivity(), grid1).execute(filtr, srch, type, lat, lng, FragmentHome.userid, "1");
                    ed_srch.setText("");

                    return true;
                }
                return false;
            }


        });


        scrollView2.setOnBottomReachedListener(new InteractiveScrollView.OnBottomReachedListener() {
            @Override
            public void onBottomReached() {

                pagenum++;
                newpage = String.valueOf(pagenum);
                new Json_GetDataMarketService(getActivity(), customGridServiceMarket).execute(filtr, srch, type, lat, lng, FragmentHome.userid, newpage);

            }
        });




        btn_area.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                grid1.setVisibility(View.INVISIBLE);


                srch="";
                filtr="inarea";
                  type="s";
                gps = new GPSTracker(getActivity());
                if(gps.canGetLocation()){
                 latitude = gps.getLatitude();
                 longitude = gps.getLongitude();

                  new Json_GetDataMarketService1(getActivity(),grid1).execute(filtr, srch, type, String.valueOf(latitude), String.valueOf(longitude), FragmentHome.userid, "1");

                  grid1.setVisibility(View.VISIBLE);

                }
                else{
                    gps.showSettingsAlert();


                }

                grid1.setVerticalScrollBarEnabled(false);


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
                type="s";

               new Json_GetDataMarketService(getActivity(),customGridServiceMarket).execute(filtr, srch, type, lat, lng, FragmentHome.userid, "1");

                grid1.setAdapter(new CustomGridServiceMarket(getActivity(), Json_GetDataMarketService.model_market));
                btn_nation.setBackgroundDrawable(getResources().getDrawable(R.drawable.nationwide));
                grid1.setVisibility(View.VISIBLE);

                grid1.setVerticalScrollBarEnabled(false);
                btn_area.setBackgroundDrawable(getResources().getDrawable(R.drawable.inyourarea));

            }
        });

        srch_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                srch = ed_srch.getText().toString().trim();

             new Json_GetDataMarketService_srch(getActivity(),grid1).execute(filtr, srch, type, lat, lng, FragmentHome.userid, "1");
                   ed_srch.setText("");

            }
        });

        return view;
    }


}