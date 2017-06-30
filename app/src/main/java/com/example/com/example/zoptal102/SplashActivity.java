package com.example.com.example.zoptal102;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import com.example.zotal102.yahwahapp.R;
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;


public class SplashActivity extends Activity {

    public static int splash_show_time = 3000;

    String MyPREFERENCES = "MyPrefs1";
    SharedPreferences sharedpreferences1;
    public  String usertokn="null", userid,useremail,uname;
    public String payid="";
    String name1 = "nameKey1";
    String tokn = "toknKey";
    String email = "emailKey";
    String paypayid = "paypalid";

    public String utkn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());

           setContentView(R.layout.splash);

        sharedpreferences1 = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);


        if (sharedpreferences1.contains(tokn)) {
            usertokn = sharedpreferences1.getString(tokn, "");
            userid = sharedpreferences1.getString(name1, "");
            useremail = sharedpreferences1.getString(email, "");

        }
        utkn=usertokn;
        new BackgroundTask().execute();

    }
    private class BackgroundTask extends AsyncTask<Void,Void,Void>
    {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        protected Void doInBackground(Void... params) {

            try {
                Thread.sleep(splash_show_time);
            } catch (InterruptedException e) {

                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);


            if(utkn.equals("null")) {
                Intent i = new Intent(SplashActivity.this,
                        MainActivity1.class);

                startActivity(i);
                finish();
            }
            else {
                Intent i = new Intent(SplashActivity.this, MainTabActivity.class);
                startActivity(i);
                finish();
            }
        }

    }



}
