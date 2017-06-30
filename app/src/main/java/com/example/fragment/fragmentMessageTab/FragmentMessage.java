package com.example.fragment.fragmentMessageTab;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.com.example.zoptal102.MainTabActivity;
import com.example.fragment.FragmentProfileTab.Frag_ProfileOption;
import com.example.fragment.fragmentHomeTab.FragmentHome;
import com.example.model.model_friends.Json_Friends_List;
import com.example.model.model_message.Json_MsgList;
import com.example.zotal102.yahwahapp.R;


public class FragmentMessage extends Fragment {

    public  static RelativeLayout lay;
     public static ListView list;
    public static ImageView img_msg;
    String srch = "";
    String filter = "0";
    public static String othrid,othername,otherimg,senderid;
    public static String thr_msg;
    public static TextView tv_msg;
    Handler mHandler = new Handler();

    RelativeLayout bck_layout;
    ImageView img_setting;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        View view = inflater.inflate(R.layout.fragment_message, container, false);
        img_msg=(ImageView)view.findViewById(R.id.msg1);
        list=(ListView) view.findViewById(R.id.list);
        tv_msg=(TextView)view.findViewById(R.id.tv_msg);
        img_setting=(ImageView)view.findViewById(R.id.more);

       lay=(RelativeLayout)view.findViewById(R.id.activity_main_content_fragment);

        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

        list.setClickable(true);

         bck_layout=(RelativeLayout)view.findViewById(R.id.header_layout1);


        bck_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                	FragmentManager fragmentManager1 = getFragmentManager();
                    fragmentManager1.popBackStack();

            }
        });

        int val= MainTabActivity.pos;
        Log.e("tab selected",""+val);

        if(val==4) {

            bck_layout.setVisibility(View.GONE);
        }


        new Json_MsgList(getActivity(),list).execute(FragmentHome.userid);

     new Thread(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                while (true) {
                    try {
                        Thread.sleep(20000);
                        mHandler.post(new Runnable() {

                            @Override
                            public void run() {
                                // TODO Auto-generated method stub
                                // Write your code here to update the UI.

                                new Json_MsgList(getActivity(),list).execute(FragmentHome.userid);
                            }
                        });
                    } catch (Exception e) {
                        // TODO: handle exception
                    }
                }
            }
        }).start();


        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        img_msg.setClickable(true);



     list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
         @Override
         public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


             thr_msg=Json_MsgList.model_msg.getAl_msg_detail().get(position).getMsg_thread();

             othrid=Json_MsgList.model_msg.getAl_msg_detail().get(position).getOtheruserid();

             othername=Json_MsgList.model_msg.getAl_msg_detail().get(position).getOtheruser();

            otherimg=Json_MsgList.model_msg.getAl_msg_detail().get(position).getOtheruserimage();
             senderid=Json_MsgList.model_msg.getAl_msg_detail().get(position).getSender();


             FragmentManager fragmentManager1 = getFragmentManager();
             Frag_MsgDeatil f=
                     new Frag_MsgDeatil();

             fragmentManager1
                     .beginTransaction()
                     .replace(R.id.activity_main_content_fragment,
                             f).addToBackStack(null).commit();




         }
     });

       img_msg.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        new Json_Friends_List(getActivity()).execute(FragmentHome.userid, srch, filter);

    }
       });

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



        return view;

    }



}