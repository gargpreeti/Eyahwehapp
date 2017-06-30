package com.example.fragment.FragmentProfileTab;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.com.example.zoptal102.MainActivity1;
import com.example.com.example.zoptal102.Register;
import com.example.fragment.fragmentHomeTab.FragmentHome;
import com.example.fragment.fragmentMarketTab.Fragment_cart;
import com.example.model.model_profile.Json_shareapp;
import com.example.url.RegisterUrl;
import com.example.zotal102.yahwahapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


public class Frag_ProfileOption extends Fragment {

     String result2="";
    RelativeLayout bck_layout;
    ImageView bck_img;
    TextView tv_logout,tv_myprofile,tv_help,tv_report,tv_bolg,tv_cart,tv_privacy,tv_terms,tv_adeve,tv_share,tv_sel;
    public static String txtmsg;
    LinearLayout bar_layout34,bar_layout3;

    public static final String MyPREFERENCES = "MyPrefs1" ;
    SharedPreferences sharedpreferences1;
    public static final String Name1 = "nameKey1";
    public static final String Tokn = "toknKey";
    public static final String Name2 = "nameKey2";
    public static final String Email = "emailKey";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        View view = inflater.inflate(R.layout.frag_profileoption, container, false);

        sharedpreferences1 = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);


        bck_img=(ImageView)view.findViewById(R.id.back);
        tv_logout=(TextView)view.findViewById(R.id.log);
        tv_cart=(TextView)view.findViewById(R.id.cart);
        tv_myprofile=(TextView)view.findViewById(R.id.profile);
        tv_help=(TextView)view.findViewById(R.id.help);
       tv_report=(TextView)view.findViewById(R.id.report);
        tv_bolg=(TextView)view.findViewById(R.id.blog);
        tv_privacy=(TextView)view.findViewById(R.id.privacy);
        tv_terms=(TextView)view.findViewById(R.id.terms);
        tv_adeve=(TextView)view.findViewById(R.id.abdeve1);
        tv_sel=(TextView)view.findViewById(R.id.sel);
        tv_share=(TextView)view.findViewById(R.id.share);
        bck_layout=(RelativeLayout)view.findViewById(R.id.header_layout1);
        bar_layout34=(LinearLayout)view.findViewById(R.id.bar_layout34);

        bar_layout3=(LinearLayout)view.findViewById(R.id.bar_layout3);


        bck_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fragmentManager1 = getFragmentManager();
                fragmentManager1.popBackStack();

//                FragmentManager fragmentManager = getFragmentManager();
//
//                fragmentManager
//                        .beginTransaction()
//                        .replace(R.id.activity_main_content_fragment,
//                                new FragmentProfile()).commit();
            }
        });

//        tv_logout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Log.e("logout====","ok");
//
//                logout(FragmentHome.userid, FragmentHome.usertokn);
//
//            }
//        });


        bar_layout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e("logout====","ok");

                logout(FragmentHome.userid, FragmentHome.usertokn);

            }
        });




        new Json_shareapp(getActivity()).execute();


        tv_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fragmentManager = getFragmentManager();

                fragmentManager
                        .beginTransaction()
                        .replace(R.id.activity_main_content_fragment,
                                new Fragment_cart()).addToBackStack(null).commit();
            }
        });


        tv_sel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fragmentManager = getFragmentManager();

                fragmentManager
                        .beginTransaction()
                        .replace(R.id.activity_main_content_fragment,
                                new Fragment_SellingHistory()).addToBackStack(null).commit();
            }
        });


        bar_layout34.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fragmentManager = getFragmentManager();

                fragmentManager
                        .beginTransaction()
                        .replace(R.id.activity_main_content_fragment,
                                new Fragment_BuyerHistory()).addToBackStack(null).commit();
            }
        });



        tv_myprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();

                fragmentManager
                        .beginTransaction()
                        .replace(R.id.activity_main_content_fragment,
                                new FragmentProfile()).commit();
            }
        });


        tv_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{"info@eyahweh.com"});
                email.putExtra(Intent.EXTRA_SUBJECT,"Re:Help Center");
                email.putExtra(Intent.EXTRA_TEXT, "");
                email.setType("message/rfc822");
                startActivity(Intent.createChooser(email, "Select Email Client"));
            }
        });

        tv_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{"support@eyahweh.com"});
                email.putExtra(Intent.EXTRA_SUBJECT,"Re:Report problem");
                email.putExtra(Intent.EXTRA_TEXT, "");
                email.setType("message/rfc822");
                startActivity(Intent.createChooser(email, "Select Email Client"));
            }
        });

        tv_bolg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent internetIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://eyahweh.com/blog/"));
                      internetIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(internetIntent);

            }
        });

        tv_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareIt();
            }
        });

        tv_privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                FragmentManager fragmentManager = getFragmentManager();

                fragmentManager
                        .beginTransaction()
                        .replace(R.id.activity_main_content_fragment,
                                new Fragment_privacy_policy()).commit();

            }
        });
        tv_terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fragmentManager = getFragmentManager();

                fragmentManager
                        .beginTransaction()
                        .replace(R.id.activity_main_content_fragment,
                                new Fragment_terms()).commit();

            }
        });

        tv_adeve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent internetIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://www.zoptal.com/"));
                internetIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(internetIntent);
            }
        });


        return view;

    }


    private void shareIt() {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "eyahwah App");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, txtmsg);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }


    private void logout(String userid, String usertokn) {

        class RegisterUser extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            Register ruc = new Register();


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = new ProgressDialog(getActivity(),R.style.AppCompatAlertDialogStyle);
                loading.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                loading.show();

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                if(result2.equals("true")) {
                    Toast.makeText(getActivity(), "Logged out successfully", Toast.LENGTH_LONG).show();

                    SharedPreferences.Editor editor1 = sharedpreferences1.edit();
                    editor1.putString(Tokn,"null");
                    editor1.putString(Name1,"");
                    editor1.putString(Name2,"");
                    editor1.putString(Email,"");
//                    editor1.putString(Name2, sn);

                    editor1.commit();

                     Intent i = new Intent(getActivity(),MainActivity1.class);
                     startActivity(i);

                }
                else{


                }

            }

            @Override
            protected String doInBackground(String... params) {


                HashMap<String, String> data = new HashMap<String,String>();
                data.put("user_id", params[0]);
                data.put("access_token", params[1]);

                String result1 = ruc.sendPostRequest(RegisterUrl.logout,data);

                JSONObject jsonObject = null;

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

          RegisterUser ru = new RegisterUser();
          ru.execute(userid,usertokn);
    }

}



