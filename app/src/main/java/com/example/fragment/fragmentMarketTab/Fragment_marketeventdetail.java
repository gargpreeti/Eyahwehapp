package com.example.fragment.fragmentMarketTab;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adapter.adapterMarket.CustomGridEventMarket;
import com.example.fragment.fragmentHomeTab.FragmentHome;
import com.example.model.model_market.Json_AddCart;
import com.example.model.model_market.Json_EventDescriptionMarket;
import com.example.model.model_market.Json_Follow;
import com.example.model.model_market.Model_getEventDescriptionMarket;
import com.example.zotal102.yahwahapp.R;
import com.squareup.picasso.Picasso;


public class  Fragment_marketeventdetail extends Fragment {

   TextView tv_proname,tv_desc,tv_price,tv_user,tv_sdt,tv_edt,tv_stime,tv_etime;
   ImageView user_img,service_img,bck_img;
   Button btn_msg,btn_chkout,btn_follow,btn_unfollow;

   String status;
    public static String tv_to,tv_id;
 public static int value=0;

    public static TextView tv_cart8;
    ImageView img_cart;

    @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {
       // TODO Auto-generated method stub

       View view = inflater.inflate(R.layout.fragment_marketeventdetail, container, false);

         img_cart=(ImageView) view.findViewById(R.id.cart);
        tv_cart8=(TextView) view.findViewById(R.id.total_cart);


        tv_cart8.setText(String.valueOf(FragmentMarket.tv_cart.getText()));


       tv_user=(TextView)view.findViewById(R.id.text_username);
      bck_img=(ImageView)view.findViewById(R.id.back);
       tv_proname=(TextView)view.findViewById(R.id.service_name);
       tv_desc=(TextView)view.findViewById(R.id.desc_text);
       tv_price=(TextView)view.findViewById(R.id.price_text);
       tv_sdt=(TextView)view.findViewById(R.id.sdt_text);
       tv_edt=(TextView)view.findViewById(R.id.edt_text);
       tv_stime=(TextView)view.findViewById(R.id.stime_text);
       tv_etime=(TextView)view.findViewById(R.id.etime_text);

       user_img=(ImageView)view.findViewById(R.id.user_img);
       service_img=(ImageView)view.findViewById(R.id.imageView4);
       btn_follow=(Button)view.findViewById(R.id.imageButton);
       btn_unfollow=(Button)view.findViewById(R.id.imageButton1);

       Bundle bundle = getArguments();
       Model_getEventDescriptionMarket m=(Model_getEventDescriptionMarket)bundle.getSerializable("eventdatamarket");


       tv_user.setText(m.getPusername());
       tv_proname.setText(m.getPname());
       tv_desc.setText(m.getPdesc());
       tv_price.setText(String.valueOf(m.getPprice()));
       tv_sdt.setText(m.getSdate());
       tv_edt.setText(m.getEdate());
       tv_stime.setText(m.getStime());
       tv_etime.setText(m.getEtime());

       tv_to=m.getPusername();
       tv_id= String.valueOf(m.getOwnerid());

      Picasso.with(getActivity()).load(m.getPuserimage()).into(user_img);
       Picasso.with(getActivity()).load(m.getPimg()).resize(400,220).into(service_img);

       if(Json_EventDescriptionMarket.follow.equals("1")){
           btn_unfollow.setVisibility(View.VISIBLE);

       }
       else{

           btn_follow.setVisibility(View.VISIBLE);
       }


       btn_msg=(Button)view.findViewById(R.id.msg);
       btn_chkout=(Button)view.findViewById(R.id.checkout);
       bck_img.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               FragmentManager fragmentManager = getFragmentManager();

               fragmentManager
                       .beginTransaction()
                       .replace(R.id.activity_main_content_fragment,
                               new FragmentMarket()).commit();
           }
       });


       btn_follow.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               String ownrid = String.valueOf(Json_EventDescriptionMarket.obj_eventDescriptionmarket.getOwnerid());
               status="1";
               new Json_Follow(getActivity()).execute(FragmentHome.userid,ownrid,status);

               Toast.makeText(getActivity(), "You have started following", Toast.LENGTH_LONG).show();
               btn_unfollow.setVisibility(View.VISIBLE);
               btn_follow.setVisibility(View.INVISIBLE);
           }

       });

       btn_unfollow.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               String ownrid = String.valueOf(Json_EventDescriptionMarket.obj_eventDescriptionmarket.getOwnerid());
               status="0";
               new Json_Follow(getActivity()).execute(FragmentHome.userid,ownrid, status);
               Toast.makeText(getActivity(), "You have stopped following", Toast.LENGTH_LONG).show();
               btn_follow.setVisibility(View.VISIBLE);
               btn_unfollow.setVisibility(View.INVISIBLE);
           }

       });




       btn_msg.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               FragmentManager fragmentManager = getFragmentManager();

               fragmentManager
                       .beginTransaction()
                       .replace(R.id.activity_main_content_fragment,
                               new NewMessageFragmentMarket()).commit();
           }
       });




       btn_chkout.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               new Json_AddCart(getActivity()).execute(FragmentHome.userid,FragmentHome.usertokn, CustomGridEventMarket.eid,"e","1");


               String val="1";
               value=Integer.parseInt(val);

               int tv = Integer.parseInt(String.valueOf(tv_cart8.getText()));

               value=value+tv;

               FragmentMarket.tv_cart.setText(String.valueOf(value));
               tv_cart8.setText(String.valueOf(value));
           }
       });

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

       return view;

   }



}
