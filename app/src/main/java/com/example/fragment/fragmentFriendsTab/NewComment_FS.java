package com.example.fragment.fragmentFriendsTab;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adapter.adapterFriends.CustomGridService2;
import com.example.fragment.fragmentHomeTab.FragmentHome;
import com.example.model.model_friends.Json_commentlist_fs;
import com.example.model.model_market.Json_AddComment;
import com.example.zotal102.yahwahapp.R;
import com.squareup.picasso.Picasso;


public class NewComment_FS extends Fragment {


    ImageView img,img_bck;
    EditText ed_comment;
    public  static  TextView tv_comment;
    Button btn_snd;
    public static ListView list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        View view = inflater.inflate(R.layout.fragament_marketcomment, container, false);

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        img=(ImageView)view.findViewById(R.id.product_img);
        img_bck=(ImageView)view.findViewById(R.id.back);
          ed_comment=(EditText)view.findViewById(R.id.editText1);
           tv_comment=(TextView)view.findViewById(R.id.comment_txt);

        btn_snd=(Button)view.findViewById(R.id.button3);

        list=(ListView)view.findViewById(R.id.list);


        Picasso.with(getActivity()).load(CustomGridService2.s_img).resize(1100, 550).into(img);
          tv_comment.setText(CustomGridService2.totl_comment);


        new Json_commentlist_fs(getActivity(),list).execute(CustomGridService2.sid, "s");

        img_bck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.popBackStack();

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
                    else {
                    new Json_AddComment(getActivity()).execute(CustomGridService2.sid, FragmentHome.userid, type, cmt);

                    String val = "1";
                    int value = Integer.parseInt(val);
                    value = value + Integer.parseInt(String.valueOf(tv_comment.getText()));
                    tv_comment.setText(String.valueOf(value));

                    ed_comment.setText("");
                    new Json_commentlist_fs(getActivity(), list).execute(CustomGridService2.sid, "s");

                }
            }

        });


        return view;


    } }