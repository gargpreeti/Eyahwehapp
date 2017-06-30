package com.example.fragment.fragmentHomeTab;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adapter.adapterHome.CustomListAdapter;
import com.example.com.example.zoptal102.MainTabActivity;
import com.example.com.example.zoptal102.NetworkConnection;
import com.example.fragment.FragmentProfileTab.Frag_ProfileOption;
import com.example.fragment.FragmentProfileTab.Frag_notification;
import com.example.fragment.FragmentProfileTab.FragmentProfile;
import com.example.fragment.fragmentFriendsTab.FragmentFriends;
import com.example.fragment.fragmentMarketTab.Fragment_cart;
import com.example.model.model_home.Json_homelistfeed;
import com.example.model.model_market.Json_ShowCartTotal;
import com.example.zotal102.yahwahapp.R;


public class FragmentHome extends Fragment {

    ListView list;
    public static TextView tv_cart2;
    public static ImageView img_cart;
    public static TextView tv_msg;
    public static  ImageView img_add1,img_add2,img_add3;

    String MyPREFERENCES = "MyPrefs1";
    SharedPreferences sharedpreferences1;
    public static String usertokn, userid;
    public String payid="";
    String name1 = "nameKey1";
    String tokn = "toknKey";
    String paypayid = "paypalid";
    public static RelativeLayout rel_lay;
    public static String pid,type1;

    TextView total_noti;
    ImageView img_noti,img_setting;
    CustomListAdapter customListAdapter;

    Boolean flag=false;
    int pagenum=1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        list=(ListView) view.findViewById(R.id.list);

        img_add1=(ImageView) view.findViewById(R.id.img1);
        img_add2=(ImageView) view.findViewById(R.id.img2);
        img_add3=(ImageView) view.findViewById(R.id.img3);
        img_setting=(ImageView)view.findViewById(R.id.more);

        img_cart=(ImageView) view.findViewById(R.id.cart);
        tv_cart2=(TextView) view.findViewById(R.id.total_cart);
        tv_msg=(TextView) view.findViewById(R.id.msg1);
        rel_lay=(RelativeLayout)view.findViewById(R.id.activity_main_content_fragment2);
        rel_lay.setVisibility(View.GONE);

        img_noti=(ImageView)view.findViewById(R.id.noti);
        total_noti=(TextView)view.findViewById(R.id.total_noti);

        total_noti.setVisibility(View.INVISIBLE);

        if(MainTabActivity.message!=null){
            total_noti.setVisibility(View.VISIBLE);
        }
        total_noti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                total_noti.setVisibility(View.GONE);
            }
        });

        img_cart.setClickable(true);
        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);


        sharedpreferences1 = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        if (sharedpreferences1.contains(tokn)) {

            usertokn = sharedpreferences1.getString(tokn, "");
            userid = sharedpreferences1.getString(name1, "");
            payid = sharedpreferences1.getString(paypayid, "");



        }
        if(payid.length()==0){
            createDialog();
        }
        else{
          //  Log.e("paypalid---","no value");

        }

        img_noti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fragmentManager = getFragmentManager();

                fragmentManager
                        .beginTransaction()
                        .replace(R.id.activity_main_content_fragment,
                                new Frag_notification()).addToBackStack(null).commit();
            }
        });


        new Json_ShowCartTotal(getActivity()).execute(userid, usertokn);

        tv_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();

                fragmentManager
                        .beginTransaction()
                        .replace(R.id.activity_main_content_fragment,
                                new FragmentFriends()).addToBackStack(null).commit();

            }
        });


        tv_cart2.setText(String.valueOf(Json_ShowCartTotal.totalcart));


//        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view,
//                                    int position, long id) {
//
//                pid= Json_homelistfeed.model_home.getAl_feed().get(position).getId();
//                type1 = Json_homelistfeed.model_home.getAl_feed().get(position).getType();
//
//
//
//                if (NetworkConnection.isConnectedToInternet(getActivity())) {
//                    new Json_ProductDescriptionMarket(getActivity()).execute(pid, userid, type1);
//
//                } else {
//                    Toast.makeText(getActivity(), "Please check internet connection", Toast.LENGTH_LONG).show();
//                }
//
//
//            }
//        });

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

        if(NetworkConnection.isConnectedToInternet(getActivity()))
        {

            customListAdapter= new CustomListAdapter(getActivity(),Json_homelistfeed.model_home);
            list.setAdapter(customListAdapter);
            new Json_homelistfeed(getActivity(),customListAdapter).execute("1","10");
            }
        else
        {
            Toast.makeText(getActivity(), "Please check internet connection", Toast.LENGTH_LONG).show();
        }


        img_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fragmentManager = getFragmentManager();

                fragmentManager
                        .beginTransaction()
                        .replace(R.id.activity_main_content_fragment,
                                new Frag_ProfileOption()).addToBackStack(null).commit();

            }
        });

        list.setOnScrollListener(new AbsListView.OnScrollListener() {
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

                    pagenum++;
                    String newpage= String.valueOf(pagenum);

                    if (NetworkConnection.isConnectedToInternet(getActivity())) {

                        new Json_homelistfeed(getActivity(),customListAdapter).execute(newpage,"10");

                    }
                    else {
                        Toast.makeText(getActivity(),"Please Check your internet connection", Toast.LENGTH_SHORT).show();


                    }



                }
            }
        });


        return view;

    }

    public void createDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage((Html.fromHtml("<font color='#000000'>" + "To add product/event/service please add Paypal email-id")));
        builder.setCancelable(false);
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {


                FragmentManager fragmentManager = getFragmentManager();

                fragmentManager
                        .beginTransaction()
                        .replace(R.id.activity_main_content_fragment,
                                new FragmentProfile()).commit();


            }
        });

        AlertDialog alert = builder.create();
        alert.show();
        Button nbutton = alert.getButton(DialogInterface.BUTTON_NEGATIVE);
        nbutton.setBackgroundColor(Color.WHITE);
        nbutton.setTextColor(Color.BLACK);
        Button pbutton = alert.getButton(DialogInterface.BUTTON_POSITIVE);
        pbutton.setBackgroundColor(Color.WHITE);
        pbutton.setTextColor(Color.BLACK);
    }

}
