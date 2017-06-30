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

import com.example.adapter.adapterFriends.CustomGridEvent2;
import com.example.fragment.fragmentHomeTab.FragmentHome;
import com.example.model.model_market.Json_AddCart;
import com.example.model.model_market.Model_getEventDescriptionMarket;
import com.example.zotal102.yahwahapp.R;
import com.squareup.picasso.Picasso;


public class Fragment_friendseventdetail extends Fragment {

   TextView tv_proname,tv_desc,tv_price,tv_sdt,tv_edt,tv_stime,tv_etime;;
   ImageView service_img,bck_img;
   Button btn_chkout;
    ImageView img_cart;
    public static int value=0;
    public static TextView tv_cart12;

   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {
       // TODO Auto-generated method stub

       View view = inflater.inflate(R.layout.fragment_friendseventdetail, container, false);



       img_cart=(ImageView) view.findViewById(R.id.cart);
       tv_cart12=(TextView) view.findViewById(R.id.total_cart);
       tv_cart12.setText(String.valueOf(Fragment_discover.tv_cart4.getText()));


       bck_img=(ImageView)view.findViewById(R.id.back);
       tv_proname=(TextView)view.findViewById(R.id.service_name);
       tv_desc=(TextView)view.findViewById(R.id.desc_text);
       tv_price=(TextView)view.findViewById(R.id.price_text);
       tv_sdt=(TextView)view.findViewById(R.id.sdt_text);
       tv_edt=(TextView)view.findViewById(R.id.edt_text);
       tv_stime=(TextView)view.findViewById(R.id.stime_text);
       tv_etime=(TextView)view.findViewById(R.id.etime_text);

       service_img=(ImageView)view.findViewById(R.id.imageView4);


       Bundle bundle = getArguments();
       Model_getEventDescriptionMarket m=(Model_getEventDescriptionMarket)bundle.getSerializable("eventdatamarket");



       tv_proname.setText(m.getPname());
       tv_desc.setText(m.getPdesc());
       tv_price.setText(String.valueOf(m.getPprice()));
       tv_sdt.setText(m.getSdate());

       tv_edt.setText(m.getEdate());
       tv_stime.setText(m.getStime());
       tv_etime.setText(m.getEtime());

    Picasso.with(getActivity()).load(m.getPimg()).resize(450,220).into(service_img);





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


               new Json_AddCart(getActivity()).execute(FragmentHome.userid, FragmentHome.usertokn, CustomGridEvent2.eid, "e", "1");



               String val="1";
               value=Integer.parseInt(val);

               int tv = Integer.parseInt(String.valueOf(tv_cart12.getText()));

               value=value+tv;



               FragmentHome.tv_cart2.setText(String.valueOf(value));
               FragmentFriends.tv_cart3.setText(String.valueOf(value));

               tv_cart12.setText(String.valueOf(value));

           }
       });


       return view;

   }



}
