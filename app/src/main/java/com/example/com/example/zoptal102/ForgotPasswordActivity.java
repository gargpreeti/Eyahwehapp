package com.example.com.example.zoptal102;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.url.RegisterUrl;
import com.example.zotal102.yahwahapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class ForgotPasswordActivity extends Activity {

    EditText ed_email;
    Button btn_submit;
    ImageView tv_bck;
    String result2;
    JSONObject jsonObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpw);

         btn_submit=(Button)findViewById(R.id.button);
        tv_bck=(ImageView)findViewById(R.id.back);
        ed_email=(EditText)findViewById(R.id.ed_email);


       btn_submit.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               String email =  ed_email.getText().toString().trim();

               if(email.equals("")){

                   AlertDialog.Builder builder1 = new AlertDialog.Builder(ForgotPasswordActivity.this);
                   builder1.setMessage((Html.fromHtml("<font color='#000000'>" + "Please enter email-id")));
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





//                   AlertDialog alertDialog = new AlertDialog.Builder(
//                           ForgotPasswordActivity.this).create();
//
//                   alertDialog.setMessage("Please enter email-id");
//
//                   alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
//                       public void onClick(DialogInterface dialog, int which) {
//
//
//                           dialog.dismiss();
//                       }
//                   });
//
//                   // Showing Alert Message
//                   alertDialog.show();

               }
               else {
                   forgotpw("", email);
               }


           }
       });


   tv_bck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ForgotPasswordActivity.this,
                        MainActivity1.class);

                startActivity(i);

            }
        });



    }



    private void forgotpw(String usertokn,String email) {

        class ForgotPw extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            Register ruc = new Register();


            @Override
            protected void onPreExecute() {
                super.onPreExecute();

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                if(result2.equals("true")) {

                    AlertDialog.Builder builder1 = new AlertDialog.Builder(ForgotPasswordActivity.this);
                    builder1.setMessage((Html.fromHtml("<font color='#000000'>" + "An email was just sent to you")));
                    builder1.setCancelable(false);

                    builder1.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
//                                Toast.makeText(MainActivity1.this, "You exit from app",
//                                        Toast.LENGTH_LONG).show();
                            finish();

                        }
                    });

                    AlertDialog alert = builder1.create();
                    alert.show();
                    Button pbutton = alert.getButton(DialogInterface.BUTTON_POSITIVE);
                    pbutton.setBackgroundColor(Color.WHITE);
                    pbutton.setTextColor(Color.BLACK);







//                    AlertDialog alertDialog = new AlertDialog.Builder(
//                            ForgotPasswordActivity.this).create();
//
//                    alertDialog.setMessage("Email sent successfully");
//
//
//                    alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int which) {
//
//
//                            finish();
//                        }
//                    });
//
//
//                    alertDialog.show();
   }
                else{

                    AlertDialog.Builder builder1 = new AlertDialog.Builder(ForgotPasswordActivity.this);
                    builder1.setMessage((Html.fromHtml("<font color='#000000'>" + "Email you entered is not valid")));
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





//                    AlertDialog alertDialog = new AlertDialog.Builder(
//                            ForgotPasswordActivity.this).create();
//
//                    alertDialog.setMessage("Email you entered is not valid");
//
//                    alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int which) {
//
//                           dialog.dismiss();
//                        }
//                    });
//                    alertDialog.show();

                }

            }

            @Override
            protected String doInBackground(String... params) {


                HashMap<String, String> data = new HashMap<String,String>();
                data.put("email", params[1]);
                data.put("access_token", params[0]);

                String result1 = ruc.sendPostRequest(RegisterUrl.forgot,data);



                try {
                    jsonObject = new JSONObject(result1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                try {
                    result2 = jsonObject.getString("result");
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                return result2;
            }
        }

        ForgotPw ru = new ForgotPw();
        ru.execute(usertokn,email);
    }

}








