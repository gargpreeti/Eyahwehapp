package com.example.fragment.fragmentMarketTab;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adapter.adapterMarket.CustomGridServiceMarket;
import com.example.adapter.adapterMarket.CustomListComment1;
import com.example.fragment.fragmentHomeTab.FragmentHome;
import com.example.model.model_market.Json_AddComment;
import com.example.model.model_market.Json_commentlist1;
import com.example.model.model_market.Json_like;
import com.example.zotal102.yahwahapp.R;
import com.squareup.picasso.Picasso;


public class NewComment1 extends Fragment {
     ImageView img,img_bck;
    EditText ed_comment;
    TextView tv_like,tv_comment;
    Button btn_snd;
    ListView list;
    CheckBox ch;
    CustomListComment1  adaptercomment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        View view = inflater.inflate(R.layout.fragament_marketcomment, container, false);

        img=(ImageView)view.findViewById(R.id.product_img);
        img_bck=(ImageView)view.findViewById(R.id.back);
        ch=(CheckBox)view.findViewById(R.id.check);
        ed_comment=(EditText)view.findViewById(R.id.editText1);
        tv_like=(TextView)view.findViewById(R.id.like_txt);
        tv_comment=(TextView)view.findViewById(R.id.comment_txt);

        btn_snd=(Button)view.findViewById(R.id.button3);
        list=(ListView)view.findViewById(R.id.list);

        adaptercomment= new CustomListComment1(getActivity(), Json_commentlist1.model_comment);
        list.setAdapter(adaptercomment);

        Picasso.with(getActivity()).load(CustomGridServiceMarket.simg).resize(1100,550).into(img);
        tv_like.setText(CustomGridServiceMarket.totl_like);
        tv_comment.setText(CustomGridServiceMarket.totl_comment);

        img_bck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fragmentManager = getFragmentManager();
                         fragmentManager
                        .beginTransaction()
                        .replace(R.id.activity_main_content_fragment,

                                new FragmentMarket()).commit();

            }
        });

        btn_snd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String cmt = ed_comment.getText().toString().trim();
                String type = "s";


                if (cmt.equals("")) {
                    Toast.makeText(getActivity(), "Comment should not be empty", Toast.LENGTH_LONG).show();
                }
                //  String uid =Json_GetDataMarket.model_market.getAl_product_detail_market().get(position).getOwener_id();
                new Json_AddComment(getActivity()).execute(CustomGridServiceMarket.sid, FragmentHome.userid, type, cmt);

                String val = "1";
                int value = Integer.parseInt(val);
                value = value + Integer.parseInt(String.valueOf(tv_comment.getText()));
                tv_comment.setText(String.valueOf(value));

                ed_comment.setText("");
                adaptercomment = new CustomListComment1(getActivity(), Json_commentlist1.model_comment);
                list.setAdapter(adaptercomment);


            }
        });

     ch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton arg0, boolean isChecked) {

                if (isChecked) {



                        String val = "1";
                        int value = Integer.parseInt(val);
                        value = value + Integer.parseInt(String.valueOf(tv_like.getText()));


                        tv_like.setText(String.valueOf(value));

                        String type = "p";
                        new Json_like(getActivity()).execute(CustomGridServiceMarket.sid, FragmentHome.userid, type, "1");

                } else {
                    String st = "0";
                    String val = "-1";
                    int value = Integer.parseInt(val);
                    value = value + Integer.parseInt(String.valueOf(tv_like.getText()));
                    tv_like.setText(String.valueOf(value));

                    String type = "p";
                    new Json_like(getActivity()).execute(CustomGridServiceMarket.sid, FragmentHome.userid, type, st);

                }

            }

        });



        return view;


    } }