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

import com.example.adapter.adapterMarket.CustomGridProductMarket;
import com.example.fragment.fragmentHomeTab.FragmentHome;
import com.example.model.model_market.Json_AddCart;
import com.example.model.model_market.Json_Follow;
import com.example.model.model_market.Json_ProductDescriptionMarket1;
import com.example.model.model_market.Json_ShowCartTotal6;
import com.example.model.model_market.Model_getProductDescriptionMarket;
import com.example.zotal102.yahwahapp.R;
import com.squareup.picasso.Picasso;


public class Fragment_marketproductdeatil1 extends Fragment {


    TextView tv_proname,tv_desc,tv_price,txt_usrname;
    Button btn_msg,btn_chkout;
    Button btn_follow,btn_unfollow;
    ImageView user_img,product_img,bck_img;
    String status;

    public static int value=0;
    public static String tv_to,tv_id;
    public static TextView tv_cart7;
    ImageView img_cart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        View view = inflater.inflate(R.layout.fragament_marketproductdetail, container, false);

        img_cart=(ImageView) view.findViewById(R.id.cart);
        tv_cart7=(TextView) view.findViewById(R.id.total_cart);

        new Json_ShowCartTotal6(getActivity()).execute(FragmentHome.userid, FragmentHome.usertokn);

        tv_cart7.setText(String.valueOf(Json_ShowCartTotal6.totalcart));

        Bundle bundle = getArguments();
        Model_getProductDescriptionMarket m=(Model_getProductDescriptionMarket)bundle.getSerializable("productdatamarket");

        bck_img=(ImageView)view.findViewById(R.id.back);
        txt_usrname=(TextView)view.findViewById(R.id.text_username);

        tv_proname=(TextView)view.findViewById(R.id.pro_name);
        tv_desc=(TextView)view.findViewById(R.id.desc_text);
        tv_price=(TextView)view.findViewById(R.id.price_text);

        btn_msg=(Button)view.findViewById(R.id.msg);
        btn_chkout=(Button)view.findViewById(R.id.chkout);
        btn_follow=(Button)view.findViewById(R.id.imageButton);
        btn_unfollow=(Button)view.findViewById(R.id.imageButton1);
        product_img=(ImageView)view.findViewById(R.id.product_img);
        user_img=(ImageView)view.findViewById(R.id.user_img);


        txt_usrname.setText(m.getPusername());
        tv_proname.setText(m.getPname());
        tv_desc.setText(m.getPdesc());
        try {
            String pric = String.valueOf(Double.parseDouble(m.getPprice().toString()));
            tv_price.setText(pric);
        }
        catch (Exception e){}



        tv_to=m.getPusername();
        tv_id= String.valueOf(m.getOwnerid());
        Picasso.with(getActivity()).load(m.getPuserimage()).into(user_img);
        Picasso.with(getActivity()).load(m.getPimg()).resize(400,220).into(product_img);

        if(Json_ProductDescriptionMarket1.follow.equals("1")){
            btn_unfollow.setVisibility(View.VISIBLE);

        }
        else{

            btn_follow.setVisibility(View.VISIBLE);
        }


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

                String ownrid = String.valueOf(Json_ProductDescriptionMarket1.obj_productDescriptionmarket.getOwnerid());
                status = "1";
                new Json_Follow(getActivity()).execute(FragmentHome.userid, ownrid, status);

                Toast.makeText(getActivity(), "You have started following", Toast.LENGTH_LONG).show();
                btn_unfollow.setVisibility(View.VISIBLE);
                btn_follow.setVisibility(View.INVISIBLE);
            }

        });

        btn_unfollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String ownrid = String.valueOf(Json_ProductDescriptionMarket1.obj_productDescriptionmarket.getOwnerid());
                status = "0";
                new Json_Follow(getActivity()).execute(FragmentHome.userid, ownrid, status);
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
                                new NewMessageFragmentMarketP()).commit();


                 }
        });


        btn_chkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Json_AddCart(getActivity()).execute(FragmentHome.userid,FragmentHome.usertokn, CustomGridProductMarket.pid,"p","1");


                String val="1";
                value=Integer.parseInt(val);

                int tv = Integer.parseInt(String.valueOf(tv_cart7.getText()));

                value=value+tv;


               FragmentMarket.tv_cart.setText(String.valueOf(value));
                tv_cart7.setText(String.valueOf(value));


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



