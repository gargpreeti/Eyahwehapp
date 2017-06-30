package com.example.fragment.fragmentMessageTab;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fragment.fragmentHomeTab.FragmentHome;
import com.example.fragment.fragmentMarketTab.Fragment_cart;
import com.example.model.model_market.Json_ShowCartTotal;
import com.example.model.model_market.Json_ShowCartTotal4;
import com.example.model.model_message.Json_MsgListDescription;
import com.example.model.model_message.Json_SendMessage;
import com.example.zotal102.yahwahapp.R;
import com.squareup.picasso.Picasso;

public class Frag_MsgDeatil extends Fragment {


    public static TextView tv_cart5;

    ImageView img_cart;
    ListView list;
    TextView txt_usrname,txt_msg,txt_user;
    ImageView img_user,bck_img;
     EditText ed_msg;
    Button btn_send;
    LinearLayout relativeLayout;
    Handler mHandler = new Handler();
      @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        View view = inflater.inflate(R.layout.fragament_msgdetail, container, false);
        list=(ListView) view.findViewById(R.id.list);

        ed_msg=(EditText)view.findViewById(R.id.msgtxt);
        btn_send=(Button)view.findViewById(R.id.snd);
        relativeLayout=(LinearLayout)view.findViewById(R.id.snd_lay);
        FragmentMessage.list.setVisibility(View.INVISIBLE);



         list.setClickable(false);

        new Json_MsgListDescription(getActivity(),list).execute(FragmentHome.userid,FragmentMessage.thr_msg);

        bck_img=(ImageView)view.findViewById(R.id.back);
        txt_user=(TextView)view.findViewById(R.id.tv_user);
        txt_usrname=(TextView)view.findViewById(R.id.text_username);
        txt_msg=(TextView)view.findViewById(R.id.msg1);
        img_user = (ImageView) view.findViewById(R.id.user_img);
        img_cart=(ImageView) view.findViewById(R.id.cart);
        tv_cart5=(TextView) view.findViewById(R.id.total_cart);
        txt_user.setText(FragmentMessage.othername);
        Picasso.with(getActivity()).load(FragmentMessage.otherimg).into(img_user);

        new Json_ShowCartTotal4(getActivity()).execute(FragmentHome.userid, FragmentHome.usertokn);

        FragmentMessage.img_msg.setClickable(false);
        tv_cart5.setText(String.valueOf(Json_ShowCartTotal.totalcart));



     ed_msg.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {



                getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

            }
        });

       btn_send.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               String msgtxt = ed_msg.getText().toString().trim();

               Log.e("userid....",""+FragmentHome.userid);
               Log.e("otherid....",""+FragmentMessage.othrid);
               Log.e("senderid....",""+FragmentMessage.senderid);

//
//               if(FragmentHome.userid.equals(FragmentMessage.othrid)){
//                   Log.e("same id----","ok");
//                   new Json_SendMessage(getActivity()).execute(FragmentHome.userid, FragmentMessage.senderid, msgtxt);
//               }

               if(msgtxt.equals("")){
                   Log.e("if else----","ok");
                   Toast.makeText(getActivity(), "Please enter message", Toast.LENGTH_LONG).show();
               }
              else if(FragmentHome.userid.equals(FragmentMessage.othrid)){
                   Log.e("same id----","ok");
           new Json_SendMessage(getActivity()).execute(FragmentHome.userid, FragmentMessage.senderid, msgtxt);
          }

                   else {
                   Log.e("if----","ok");
                   new Json_SendMessage(getActivity()).execute(FragmentHome.userid, FragmentMessage.othrid, msgtxt);

               }
               new Json_MsgListDescription(getActivity(),list).execute(FragmentHome.userid,FragmentMessage.thr_msg);


               ed_msg.setText("");



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



      bck_img.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {

              FragmentMessage.list.setVisibility(View.VISIBLE);
              FragmentManager fragmentManager = getFragmentManager();
              fragmentManager.popBackStack();



          }
      });
          new Thread(new Runnable() {
              @Override
              public void run() {
                  // TODO Auto-generated method stub
                  while (true) {
                      try {
                          Thread.sleep(5000);
                          mHandler.post(new Runnable() {

                              @Override
                              public void run() {
                                  // TODO Auto-generated method stub
                                  // Write your code here to update the UI.

                                  new Json_MsgListDescription(getActivity(),list).execute(FragmentHome.userid,FragmentMessage.thr_msg);
                              }
                          });
                      } catch (Exception e) {
                          // TODO: handle exception
                      }
                  }
              }
          }).start();

        return view;

    }



}
