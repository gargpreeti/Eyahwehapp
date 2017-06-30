package com.example.fragment.FragmentProfileTab;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.com.example.zoptal102.Register;
import com.example.fragment.fragmentHomeTab.FragmentHome;
import com.example.url.RegisterUrl;
import com.example.zotal102.yahwahapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Change_pw extends Fragment {

    ImageView bck_img;
    EditText ed_old,ed_new,ed_confirm;
    Button btn_save,btn_cncl;
    String result2="";
    JSONObject jsonObject;
    RelativeLayout bck_layout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // TODO Auto-generated method stub

        View view = inflater.inflate(R.layout.change_pw, container, false);

        bck_img=(ImageView)view.findViewById(R.id.back);
        ed_old=(EditText)view.findViewById(R.id.old_pw);
        ed_new=(EditText)view.findViewById(R.id.new_pw);
        ed_confirm=(EditText)view.findViewById(R.id.confirm_pw);
        btn_save=(Button)view.findViewById(R.id.save);
        btn_cncl=(Button)view.findViewById(R.id.cncl);
        bck_layout=(RelativeLayout)view.findViewById(R.id.header_layout1);




        bck_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

        FragmentManager fragmentManager = getFragmentManager();

                fragmentManager
                        .beginTransaction()
                        .replace(R.id.activity_main_content_fragment,
                                new FragmentProfile()).commit();
            }
        });

        btn_cncl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();

                fragmentManager
                        .beginTransaction()
                        .replace(R.id.activity_main_content_fragment,
                                new FragmentProfile()).commit();
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                String old_pw = ed_old.getText().toString().trim();
                String new_pw = ed_new.getText().toString().trim();
                String confirm_pw = ed_confirm.getText().toString();


                if (old_pw.equals("") || new_pw.equals("") || confirm_pw.equals("")) {
                    Toast.makeText(getActivity(), "Please fill data in all fields ", Toast.LENGTH_LONG).show();
                }
                else {

                    change_pw(FragmentHome.userid,old_pw, new_pw, confirm_pw,FragmentHome.usertokn);
                }


            }
        });

        return view;

    }
    private void change_pw(String userid,final String old_pw,String new_pw,  String confirm_pw,final String usertokn) {

        class Changepw extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            Register ruc = new Register();


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                 loading = new ProgressDialog(getActivity());
                loading.setMessage("loading");
                loading.show();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                loading.dismiss();
             if(result2.equals("true")) {
                    Toast.makeText(getActivity(), "Password Changed successfully", Toast.LENGTH_LONG).show();
                }
                else{

                    Toast.makeText(getActivity(), "Incorrect old password ", Toast.LENGTH_LONG).show();

                }

            }

            @Override
            protected String doInBackground(String... params) {

                HashMap<String, String> data = new HashMap<String,String>();
                data.put("user_id", params[0]);
                data.put("old_password", params[1]);
                data.put("password", params[2]);
                data.put("cpassword", params[3]);
                data.put("access_token", params[4]);

                String result1 = ruc.sendPostRequest(RegisterUrl.changepw,data);



              try {
                    jsonObject = new JSONObject(result1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                try {
                    if(result2.isEmpty()){

                    }
                    result2 = jsonObject.getString("result");
                } catch (JSONException e) {
                    e.printStackTrace();
                }



                return  result2;
            }
        }

        Changepw ru = new Changepw();
        ru.execute(userid,old_pw, new_pw, confirm_pw,usertokn);
    }

}








