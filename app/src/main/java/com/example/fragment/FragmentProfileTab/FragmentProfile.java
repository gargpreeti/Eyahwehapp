package com.example.fragment.FragmentProfileTab;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.com.example.zoptal102.MainTabActivity;
import com.example.com.example.zoptal102.NetworkConnection;
import com.example.com.example.zoptal102.Register;
import com.example.fragment.fragmentHomeTab.FragmentHome;
import com.example.pager.NonSwipePager;
import com.example.pager.TabsFragmentPageAdapter;
import com.example.url.RegisterUrl;
import com.example.zotal102.yahwahapp.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class FragmentProfile extends Fragment {

    public static final String MyPREFERENCES = "MyPrefs1" ;
    SharedPreferences sharedpreferences1;
    public static final String Paypalid = "paypalid";
    TextView txt_username;
    Button btn_edit;
    ImageView img_setting,img_user,img_noti;
    NonSwipePager viewPager;
    public TabLayout tab_layout;
    private String[] tabs = {"Products", "Events","Services"};

    public TabsFragmentPageAdapter tabsAdapter;
    public static Edit_Profile edit_userprofile;

    JSONObject jsonObject;
    public static RelativeLayout layout;
    RelativeLayout bck_layout;
    public static String uname,email,phone,img,paypalid=null;
    String result2="";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub


        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        layout = (RelativeLayout)view.findViewById(R.id.activity_main_content_fragment);

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        img_setting=(ImageView)view.findViewById(R.id.more);
        txt_username=(TextView)view.findViewById(R.id.text_username);
        img_user=(ImageView)view.findViewById(R.id.user_img);
        img_noti=(ImageView)view.findViewById(R.id.noti);

        bck_layout=(RelativeLayout)view.findViewById(R.id.header_layout1);

        viewPager=(NonSwipePager)view.findViewById(R.id.pager);
        tab_layout=(TabLayout)view.findViewById(R.id.tab_layout);

        btn_edit=(Button)view.findViewById(R.id.edit);
        tabsAdapter = new TabsFragmentPageAdapter(getChildFragmentManager());
        viewPager.setAdapter(tabsAdapter);

      int val=MainTabActivity.pos;
        Log.e("tab selected",""+val);

        if(val==5){
            bck_layout.setVisibility(View.GONE);
        }

        sharedpreferences1 = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        for (String tab_name : tabs) {
            tab_layout.addTab(tab_layout.newTab().setText(tab_name));
        }

        tab_layout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition(),false);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                Toast.makeText(getActivity(), "", Toast.LENGTH_LONG);
                viewPager.setCurrentItem(tab.getPosition(), false);
                Toast.makeText(getActivity(), "selected....." + tab.getPosition(), Toast.LENGTH_LONG);
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

        bck_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                FragmentManager fragmentManager1 = getFragmentManager();
//                fragmentManager1.popBackStack();


                FragmentManager fragmentManager = getFragmentManager();

                fragmentManager
                        .beginTransaction()
                        .replace(R.id.activity_main_content_fragment,
                                new FragmentHome()).commit();

            }
        });




        edit_userprofile= new Edit_Profile();


        img_noti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fragmentManager = getFragmentManager();

                fragmentManager
                        .beginTransaction()
                        .replace(R.id.activity_main_content_fragment,
                                new Frag_notification()).addToBackStack(null).commit();

          }
        });



    btn_edit.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            FragmentManager fragmentManager = getFragmentManager();

            fragmentManager
                    .beginTransaction()
                    .replace(R.id.activity_main_content_fragment,
                            edit_userprofile).commit();

        }
    });

        if(NetworkConnection.isConnectedToInternet(getActivity()))
        {

            frgprofile(FragmentHome.userid, FragmentHome.usertokn);
        }
        else
        {
            Toast.makeText(getActivity(), "Please check internet connection", Toast.LENGTH_LONG).show();
        }




        return view;
    }


    private void frgprofile(String userid,final String usertokn) {

        class RegisterUser extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            Register ruc = new Register();


            @Override
            protected void onPreExecute() {
                super.onPreExecute();

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                txt_username.setText(uname);

                Picasso.with(getActivity()).load(img).into(img_user);

            }

            @Override
            protected String doInBackground(String... params) {

                HashMap<String, String> data = new HashMap<String,String>();
                data.put("user_id", params[0]);
                data.put("access_token", params[1]);

                Log.e("userid",""+params[0]);
                Log.e("access_token",""+params[1]);
                String result1 = ruc.sendPostRequest(RegisterUrl.profile,data);
                Log.e("profile result----",""+result1);

            try {
                    jsonObject = new JSONObject(result1);
                    JSONObject  obj=	jsonObject.getJSONObject("data");
                     uname =(String) obj.get("username");
                     email=(String) obj.get("email");
                     phone =(String) obj.get("phone");
                     img =(String) obj.get("image");
                    paypalid=(String) obj.get("PayPal_id");

                    if(obj.getString("PayPal_id").equals("")){


                    paypalid=null;
                        }
                else{
                    paypalid=(String) obj.get("PayPal_id");

                }
                SharedPreferences.Editor editor1 = sharedpreferences1.edit();
                editor1.putString(Paypalid, paypalid);

                editor1.commit();

                Log.e("profile paypalid---",""+paypalid);


            } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    if(result2.isEmpty()){

                    }
                    result2 = jsonObject.optString("result");
                } catch (Exception e) {
                    e.printStackTrace();
                }


                return  result2;
            }
        }

        RegisterUser ru = new RegisterUser();
        ru.execute(userid, usertokn);

    }

}








