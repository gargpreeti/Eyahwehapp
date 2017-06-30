package com.example.fragment.fragmentMessageTab;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.fragment.fragmentHomeTab.FragmentHome;
import com.example.model.model_friends.Json_Friends_List;
import com.example.model.model_message.Json_SendMessage;
import com.example.zotal102.yahwahapp.R;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;


public class NewMessageFragment extends Fragment {


    Spinner spinner;
    ImageView bck_img;
    EditText txt_msg;
    Button btn_submit;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        View view = inflater.inflate(R.layout.fragment_newmsg, container, false);
        spinner = (Spinner)view.findViewById(R.id.spinner);
        txt_msg=(EditText)view.findViewById(R.id.txt_msg);
        btn_submit=(Button)view.findViewById(R.id.submit);

       List userlist = new ArrayList(new LinkedHashSet(Json_Friends_List.list2));


        // Creating adapter for spinner
      ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,userlist);
      dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        // attaching data adapter to spinner
       spinner.setAdapter(dataAdapter);

       bck_img=(ImageView)view.findViewById(R.id.back);
        bck_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


              FragmentManager fragmentManager = getFragmentManager();
              fragmentManager.popBackStack();

        }
        });


        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String msg=txt_msg.getText().toString().trim();
                String otheruserid=Json_Friends_List.al_friendsdetail.get(spinner.getSelectedItemPosition()).getUserid();

                if(msg.equals("")){
                    Toast.makeText(getActivity(), "Please enter message", Toast.LENGTH_LONG).show();

                }
                else {
                    new Json_SendMessage(getActivity()).execute(FragmentHome.userid, otheruserid, msg);

                    FragmentManager fragmentManager = getFragmentManager();

                    fragmentManager
                            .beginTransaction()
                            .replace(R.id.activity_main_content_fragment,
                                    new FragmentMessage()).commit();


                }


            }
        });

        return view;

    } }