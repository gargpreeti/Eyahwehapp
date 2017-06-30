package com.example.fragment.fragmentFriendsTab;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.adapter.adapterFriends.CustomGridService2;
import com.example.fragment.fragmentHomeTab.FragmentHome;
import com.example.model.model_market.Json_AddCart;
import com.example.model.model_market.Model_getServiceDescriptionMarket;
import com.example.zotal102.yahwahapp.R;
import com.squareup.picasso.Picasso;


public class Fragment_friendsservicedetail extends Fragment {

   TextView tv_proname,tv_desc,tv_price;
   ImageView service_img,bck_img;
   Button btn_chkout;
    public static int value=0;
    public static TextView tv_cart11;
    ImageView img_cart;

   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {
       // TODO Auto-generated method stub

       View view = inflater.inflate(R.layout.fragment_friendsservicedetail, container, false);


       img_cart=(ImageView) view.findViewById(R.id.cart);
       tv_cart11=(TextView) view.findViewById(R.id.total_cart);


       tv_cart11.setText(String.valueOf(Fragment_discover.tv_cart4.getText()));

       bck_img=(ImageView)view.findViewById(R.id.back);
       tv_proname=(TextView)view.findViewById(R.id.service_name);
       tv_desc=(TextView)view.findViewById(R.id.desc_text);
       tv_price=(TextView)view.findViewById(R.id.price_text);
        service_img=(ImageView)view.findViewById(R.id.imageView4);


       Bundle bundle = getArguments();
       Model_getServiceDescriptionMarket m=(Model_getServiceDescriptionMarket)bundle.getSerializable("servicedatamarket");



       tv_proname.setText(m.getSname());
       tv_desc.setText(m.getSdesc());
       tv_price.setText(String.valueOf(m.getSprice()));

       Picasso.with(getActivity()).load(m.getSimg()).resize(450,220).into(service_img);



       btn_chkout=(Button)view.findViewById(R.id.checkout);
       bck_img.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {


               FragmentManager fragmentManager = getFragmentManager();

               fragmentManager.popBackStack();


           }
       });



       btn_chkout.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               new Json_AddCart(getActivity()).execute(FragmentHome.userid,FragmentHome.usertokn, CustomGridService2.sid,"s","1");



               String val="1";
               value=Integer.parseInt(val);

               int tv = Integer.parseInt(String.valueOf(tv_cart11.getText()));

               value=value+tv;

                tv_cart11.setText(String.valueOf(value));
           }
       });


       return view;

   }



}
