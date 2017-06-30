package com.example.fragment.fragmentMarketTab;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.fragment.fragmentHomeTab.FragmentHome;
import com.example.model.model_message.Json_SendMessage;
import com.example.zotal102.yahwahapp.R;


public class NewMessageFragmentMarketP extends Fragment {

    EditText ed_txt;
    ImageView bck_img;
    EditText txt_msg;
    Button btn_submit;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        View view = inflater.inflate(R.layout.fragment_newmsgmrkt, container, false);

        ed_txt=(EditText)view.findViewById(R.id.ed_to);
        txt_msg=(EditText)view.findViewById(R.id.txt_msg);
        btn_submit=(Button)view.findViewById(R.id.submit);
        ed_txt.setEnabled(false);


         ed_txt.setText(Fragment_marketproductdeatil1.tv_to);

        bck_img=(ImageView)view.findViewById(R.id.back);

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

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String msg=txt_msg.getText().toString().trim();
                  if(msg.equals("")){
                    Toast.makeText(getActivity(), "Please enter message", Toast.LENGTH_LONG).show();

                }
                else {
                    new Json_SendMessage(getActivity()).execute(FragmentHome.userid, Fragment_marketproductdeatil1.tv_id, msg);
                }

            }
        });

        return view;

    } }