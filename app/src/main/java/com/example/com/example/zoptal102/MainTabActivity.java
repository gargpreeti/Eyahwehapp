package com.example.com.example.zoptal102;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.example.fragment.FragmentProfileTab.FragmentProfile;
import com.example.fragment.fragmentFriendsTab.FragmentFriends;
import com.example.fragment.fragmentHomeTab.FragmentHome;
import com.example.fragment.fragmentMarketTab.FragmentMarket;
import com.example.fragment.fragmentMessageTab.FragmentMessage;
import com.example.model.model_profile.Json_EventDescription;
import com.example.model.model_profile.Json_ProductDescription;
import com.example.model.model_profile.Json_ServiceDescription;
import com.example.zotal102.yahwahapp.R;
import com.google.firebase.messaging.FirebaseMessaging;

import io.fabric.sdk.android.Fabric;

public class MainTabActivity extends FragmentActivity {

    /* Tab identifiers */
    static String TAB_A = "Home Tab";
    static String TAB_B = "Market Tab";
    static String TAB_C = "Friends Tab";
    static String TAB_D = "Message tab";
    static String TAB_E = "User Tab";
    public static TabHost mTabHost;

    FragmentHome fragment1;
    FragmentProfile fragment5;
    FragmentMarket fragment2;
    FragmentFriends fragment3;
    FragmentMessage fragment4;

    String MyPREFERENCES = "MyPrefs1";
    SharedPreferences sharedpreferences1;
    public static String usertokn, userid;
    String chkst = "chkstatus";
    String val;
   public static int pos=0;
    public static String regId;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    public  NotificationCompat.Builder notificationBuilder;
     public static String  message=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main_tab);



        fragment1 = new FragmentHome();
        fragment2 = new FragmentMarket();
        fragment3 = new FragmentFriends();
        fragment4 = new FragmentMessage();
        fragment5 = new FragmentProfile();

        mTabHost = (TabHost) findViewById(android.R.id.tabhost);
        mTabHost.setOnTabChangedListener(listener);
        mTabHost.setup();

        sharedpreferences1 = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        if (sharedpreferences1.contains(chkst)) {
            val = sharedpreferences1.getString(chkst, "");


        }
        Log.e("pref val====",""+val);
        initializeTab();


        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // checking for type intent filter
                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);

                    displayFirebaseRegId();

                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                    // new push notification is received

                    //String message = intent.getStringExtra("message");

                    message= intent.getStringExtra("message");

     // Toast.makeText(getApplicationContext(), "Push notification: " + message, Toast.LENGTH_LONG).show();

                    final Uri alarmSound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
                        + "://" +getPackageName() + "/raw/organic_lvdsrrf");
                 //   Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                   // Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);

                    notificationBuilder = new NotificationCompat.Builder(getApplication())
                            .setSmallIcon(R.drawable.notificationicon)
                            .setContentTitle("Eyahweh Notification")
                            .setContentText(message)
                            .setAutoCancel(true)
                         .setSound(alarmSound);

                    NotificationManager notificationManager =
                            (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                    notificationManager.notify(0, notificationBuilder.build());

                    //txtMessage.setText(message);
                }
            }
        };

        displayFirebaseRegId();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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

    // Fetches reg id from shared preferences
    // and displays on the screen
    private void displayFirebaseRegId() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        regId= pref.getString("regId", null);


        if (!TextUtils.isEmpty(regId)){}
            //  Toast.makeText(getApplicationContext(), "Firebase Reg Id " + regId ,Toast.LENGTH_LONG).show();
              else{}
            //  Toast.makeText(getApplicationContext(), "Firebase Reg Id is not received yet!" ,Toast.LENGTH_LONG).show();

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

         if (requestCode == 1  && resultCode == RESULT_OK) {

                    fragment5.tabsAdapter.productFragment.frag_productProfile.onActivityResult1(requestCode, resultCode, data);

            }

        if (requestCode == 2 && resultCode == RESULT_OK) {
                   fragment5.edit_userprofile.onActivityResult1(requestCode, resultCode, data);
        }

        if (requestCode == 3 && resultCode == RESULT_OK) {
                    fragment5.tabsAdapter.serviceFragment.frag_serviceProfile.onActivityResult1(requestCode,resultCode,data);
        }

        if (requestCode == 4 && resultCode == RESULT_OK) {
            fragment5.tabsAdapter.eventFragment.frag_eventProfile.onActivityResult1(requestCode,resultCode,data);

     }

        if (requestCode == 5 && resultCode == RESULT_OK) {

                Json_ProductDescription.frag_editProduct.onActivityResult1(requestCode, resultCode, data);
        }
        if (requestCode == 6 && resultCode == RESULT_OK) {

            Json_EventDescription.frag_editEvent.onActivityResult1(requestCode, resultCode, data);
        }

        if (requestCode == 7 && resultCode == RESULT_OK) {

            Json_ServiceDescription.frag_editService.onActivityResult1(requestCode, resultCode, data);
        }


    }

    public void initializeTab() {

           TabHost.TabSpec spec = mTabHost.newTabSpec(TAB_A);
           spec.setContent(new TabHost.TabContentFactory() {
               public View createTabContent(String tag) {
                   return findViewById(android.R.id.tabcontent);
               }
           });
           spec.setIndicator(createTabView(TAB_A, R.drawable.home));
           mTabHost.addTab(spec);

           spec = mTabHost.newTabSpec(TAB_B);
           spec.setContent(new TabHost.TabContentFactory() {
               public View createTabContent(String tag) {
                   return findViewById(android.R.id.tabcontent);
               }
           });
           spec.setIndicator(createTabView(TAB_B, R.drawable.mrkt));
           mTabHost.addTab(spec);

           spec = mTabHost.newTabSpec(TAB_C);
           spec.setContent(new TabHost.TabContentFactory() {
               public View createTabContent(String tag) {
                   return findViewById(android.R.id.tabcontent);
               }
           });
           spec.setIndicator(createTabView(TAB_C, R.drawable.user));
           mTabHost.addTab(spec);

           spec = mTabHost.newTabSpec(TAB_D);
           spec.setContent(new TabHost.TabContentFactory() {
               public View createTabContent(String tag) {
                   return findViewById(android.R.id.tabcontent);
               }
           });
           spec.setIndicator(createTabView(TAB_D, R.drawable.chat));
           mTabHost.addTab(spec);
           spec = mTabHost.newTabSpec(TAB_E);

              mTabHost.setCurrentTab(5);
                 spec.setContent(new TabHost.TabContentFactory() {
               public View createTabContent(String tag) {
                   return findViewById(android.R.id.tabcontent);
               }
           });
           spec.setIndicator(createTabView(TAB_E, R.drawable.profl));

           mTabHost.addTab(spec);




    }

    /*
     * TabChangeListener for changing the tab when one of the tabs is pressed
     */
    TabHost.OnTabChangeListener listener = new TabHost.OnTabChangeListener() {
        public void onTabChanged(String tabId) {
            /*Set current tab..*/
            if (tabId.equals(TAB_A)) {

                pushFragments(tabId, fragment1);
                pos=1;
            } else if (tabId.equals(TAB_B)) {

                pushFragments(tabId, fragment2);
                pos=2;
            } else if (tabId.equals(TAB_C)) {

                pushFragments(tabId, fragment3);
                pos=3;

            }
            else if (tabId.equals(TAB_D)) {
                pushFragments(tabId, fragment4);
                pos=4;
            } else if (tabId.equals(TAB_E)) {
                pushFragments(tabId, fragment5);
                pos=5;
            }


        }
    };

    /*
     * adds the fragment to the FrameLayout
     */
    public void pushFragments(String tag, Fragment fragment) {

        FragmentManager manager = getSupportFragmentManager();
      FragmentTransaction ft = manager.beginTransaction();
       // ft.setCustomAnimations(R.anim.slideleft, R.anim.slideoutright);
        ft.replace(android.R.id.tabcontent, fragment);
        ft.commit();
    }


    /*
     * returns the tab view i.e. the tab icon and text
     */
    private View createTabView(final String text, final int id) {
        View view = LayoutInflater.from(this).inflate(R.layout.tabs_icon, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.tab_icon);
        imageView.setPadding(10,3,10,3);
        imageView.setImageDrawable(getResources().getDrawable(id));
        // ((TextView) view.findViewById(R.id.textView1)).setText(text);

        return view;
    }
    @Override
    public void onBackPressed()
    {
      //  super.onBackPressed();

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
                Toast.makeText(MainTabActivity.this, "You exited from app",
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
