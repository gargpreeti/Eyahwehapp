package com.example.com.example.zoptal102;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.example.url.RegisterUrl;
import com.example.zotal102.yahwahapp.R;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import io.fabric.sdk.android.Fabric;

public class MainActivity1 extends AppCompatActivity {

    public static final String MyPREFERENCES = "MyPrefs1" ;
    SharedPreferences sharedpreferences1;
    public static final String Name1 = "nameKey1";
    public static final String Tokn = "toknKey";
    public static final String Name2 = "nameKey2";
    public static final String Email = "emailKey";

    public static final String ChkStatus = "chkstatus";

    EditText ed_username,ed_pw;
    ImageView logo_img;
    TextView tv_forgotpw,tv_signup;
    Button btn_signin;
    JSONObject jsonObject = null;
    String result2 = null,ss,s1,sn,se;
    AlertDialog alertDialog;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    public static String regId;

   public  NotificationCompat.Builder notificationBuilder;
    public static String  message=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());

        setContentView(R.layout.activity_signin);


        sharedpreferences1 = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        ed_username=(EditText)findViewById(R.id.ed_user);
        ed_pw=(EditText)findViewById(R.id.ed_password);

        logo_img=(ImageView)findViewById(R.id.imageView);
        tv_signup=(TextView)findViewById(R.id.signup);
        tv_forgotpw=(TextView)findViewById(R.id.forgotpassword);
        btn_signin=(Button)findViewById(R.id.signin);


        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // checking for type intent filter
                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);

                    displayFirebaseRegId();

                }
                else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                    // new push notification is received
                       message= intent.getStringExtra("message");


                   // Toast.makeText(getApplicationContext(), "Push notification: " + message, Toast.LENGTH_LONG).show();

        //final Uri alarmSound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
           //     + "://" +getPackageName() + "/raw/notification");
           // Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);

                    final Uri alarmSound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
                            + "://" +getPackageName() + "/raw/organic_lvdsrrf");
                    notificationBuilder = new NotificationCompat.Builder(MainActivity1.this)
                            .setSmallIcon(R.drawable.notificationicon)
                            .setContentTitle("Eyahweh Notification")
                            .setContentText(message)
                            .setAutoCancel(true)
                           .setSound(alarmSound)
                           ;


                    NotificationManager notificationManager =
                            (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                    notificationManager.notify(0, notificationBuilder.build());
                    //txtMessage.setText(message);
                }
            }
        };

        displayFirebaseRegId();


        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

          login();


            }
        });

        tv_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity1.this,
                        SignUpActivity.class);

                startActivity(i);
            }
        });

        tv_forgotpw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity1.this,
                        ForgotPasswordActivity.class);

                startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    // Fetches reg id from shared preferences
    // and displays on the screen
    private void displayFirebaseRegId() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
       regId= pref.getString("regId", null);

        Log.e("regid---",""+regId);
        if (!TextUtils.isEmpty(regId)) {
            //  Toast.makeText(getApplicationContext(), "Firebase Reg Id " + regId ,Toast.LENGTH_LONG).show();
        }
              else{}
        //  Toast.makeText(getApplicationContext(), "Firebase Reg Id is not received yet!" ,Toast.LENGTH_LONG).show();

    }
    @Override
    protected void onResume() {
        super.onResume();

        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.PUSH_NOTIFICATION));

        // clear the notification area when the app is opened
        NotificationUtils.clearNotifications(getApplicationContext());
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }

    private void login() {

        String username = ed_username.getText().toString().trim();
         String password = ed_pw.getText().toString().trim();
        String device_type = "android";
        String device_token = "123456789";
        String fregid=regId;

     if (username.equals("") || password.equals("")) {
      Toast.makeText(MainActivity1.this, "Username, Password should not be empty.", Toast.LENGTH_LONG).show();

       }
      else
        if(username.contains("@")){

            userLogin(username, password, device_type, device_token,fregid);

        }
       else  {


            if(NetworkConnection.isConnectedToInternet(MainActivity1.this))
            {
                userLogin(username, password, device_type, device_token,fregid);
            }
            else
            {
                Toast.makeText(MainActivity1.this, "Please check internet connection", Toast.LENGTH_LONG).show();
            }

      }
    }
    private void userLogin(final String username,final String password,String device_type,String device_token,String fregid){

        class UserLoginClass extends AsyncTask<String,Void,String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
               String msg="Logging in. Please wait.";

                loading = new ProgressDialog(MainActivity1.this, R.style.AppCompatAlertDialogStyle);
                loading.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                loading.show();
      }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();

                try {
                    if (result2.equals("true")) {

                        Toast.makeText(MainActivity1.this, "Logged in successfully.", Toast.LENGTH_SHORT).show();

                        SharedPreferences.Editor editor1 = sharedpreferences1.edit();
                        editor1.putString(Tokn, s1);
                        editor1.putString(Name1, ss);
                        editor1.putString(Name2, sn);
                        editor1.putString(Email, se);
                        editor1.putString(ChkStatus,"false");
                        editor1.commit();

                        Intent i = new Intent(MainActivity1.this, MainTabActivity.class);
                        startActivity(i);

                    } else {

                        Log.e("else---","test");
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity1.this);
                        builder1.setMessage((Html.fromHtml("<font color='#000000'>" + "Invalid login details")));
                        builder1.setCancelable(false);

                        builder1.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
//                                Toast.makeText(MainActivity1.this, "You exit from app",
//                                        Toast.LENGTH_LONG).show();
                                dialog.dismiss();

                            }
                        });

                        AlertDialog alert = builder1.create();
                        alert.show();
                        Button pbutton = alert.getButton(DialogInterface.BUTTON_POSITIVE);
                        pbutton.setBackgroundColor(Color.WHITE);
                        pbutton.setTextColor(Color.BLACK);






//                        alertDialog = new AlertDialog.Builder(
//                                MainActivity1.this).create();
//
//                        alertDialog.setTitle(Html.fromHtml("<font color='#000000'>" + "Could not signin"));
//                        alertDialog.setMessage(Html.fromHtml("<font color='#000000'>" + "Invalid login details"));
//                        Button nbutton = alertDialog.getButton(DialogInterface.BUTTON_NEUTRAL);
//                        nbutton.setBackgroundColor(Color.WHITE);
//                        nbutton.setTextColor(Color.BLACK);
//                        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//
//                                dialog.dismiss();
//                            }
//                        });
//
//                        alertDialog.show();
                    }
                }
                catch (Exception e){


                }
            }


            @Override
            protected String doInBackground(String... params) {
                HashMap<String,String> data = new HashMap<>();


                Log.e("username---",""+params[0]);
                Log.e("password---",""+params[1]);
                Log.e("device_type---",""+params[2]);
                Log.e("device_token---",""+params[3]);
                Log.e("firebase_registration_id---",""+params[4]);


                data.put("username",params[0]);
                data.put("password",params[1]);
                data.put("device_type",params[2]);
                data.put("device_token",params[3]);
                data.put("firebase_registration_id",params[4]);
                Register ruc = new Register();

                String result1 = ruc.sendPostRequest(RegisterUrl.login,data);
                Log.e("signin result1111---",""+result1);
                try {
                    jsonObject = new JSONObject(result1);
                    JSONObject  obj=	jsonObject.getJSONObject("data");
                    s1 =(String) obj.get("access_token");
                    ss =(String) obj.get("id");
                    sn=(String)obj.get("username");
                    se=(String)obj.get("email");


                    Log.e("signin result---",""+result1);


                } catch (JSONException e) {
                    e.printStackTrace();
                }


                try {
                    result2 = jsonObject.optString("result");
                } catch (Exception e) {
                    e.printStackTrace();
                }


                return  result2;
            }
        }
        UserLoginClass ulc = new UserLoginClass();
        ulc.execute(username, password,device_type,device_token,fregid);
    }
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();

        createDialog();


    }


    public void createDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage((Html.fromHtml("<font color='#000000'>" + "Do you want to exit from app?")));
        builder.setCancelable(false);
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity1.this, "You exited from app",
                        Toast.LENGTH_LONG).show();
                finish();

            }
        });

        AlertDialog alert = builder.create();
        alert.show();
        Button nbutton = alert.getButton(DialogInterface.BUTTON_NEGATIVE);
        nbutton.setBackgroundColor(Color.WHITE);
        nbutton.setTextColor(Color.BLACK);
        Button pbutton = alert.getButton(DialogInterface.BUTTON_POSITIVE);
        pbutton.setBackgroundColor(Color.WHITE);
        pbutton.setTextColor(Color.BLACK);
    }







}
