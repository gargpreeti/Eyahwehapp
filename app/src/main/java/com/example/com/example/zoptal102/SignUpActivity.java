package com.example.com.example.zoptal102;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.url.RegisterUrl;
import com.example.zotal102.yahwahapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpActivity extends Activity {

    public static final String MyPREFERENCES = "MyPrefs1" ;
    SharedPreferences sharedpreferences1;
    public static final String Name1 = "nameKey1";
    public static final String Tokn = "toknKey";
    public static final String Name2 = "nameKey2";
    public static final String Email = "emailKey";



    EditText ed_name,ed_email,ed_pw,ed_confirmpw,ed_phn;
    Button btn_signup;

    TextView tv_sign,tv_msg;
    JSONObject jsonObject = null;
    String result2 = null;
    String   ss,s1,sn,se,sp;


    String username,password;
    AlertDialog alertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        sharedpreferences1 = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        ed_name = (EditText) findViewById(R.id.ed_user);
        ed_email = (EditText) findViewById(R.id.ed_email);
        ed_pw = (EditText) findViewById(R.id.ed_password);
        ed_confirmpw = (EditText) findViewById(R.id.ed_compw);
        ed_phn = (EditText) findViewById(R.id.ed_phone);


        btn_signup = (Button) findViewById(R.id.signup);

               tv_sign = (TextView) findViewById(R.id.sign);
        tv_msg = (TextView) findViewById(R.id.tv_alertmsg);

        tv_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(SignUpActivity.this,MainActivity1.class);

                startActivity(i);


            }
        });


        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                username = ed_name .getText().toString().trim();
                String email=  ed_email.getText().toString().trim();
                password= ed_pw.getText().toString().trim();
                String cpassword = ed_confirmpw.getText().toString().trim();
                String phone = ed_phn.getText().toString().trim();
                String fregid=MainActivity1.regId;

                if(username.equals("") ) {
                     ed_name.setError("Username should not be empty");
                }
                if(email.equals("")|| password.equals("")||cpassword.equals("")) {

                    ed_email.setError("Email should not be empty");
                }
                if(password.equals("")) {

                    ed_pw.setError("Password should not be empty");
                }
                if(cpassword.equals("")) {

                    ed_confirmpw.setError("Confirm password should not be empty");
                }

                else if (!isValidEmail(email)) {
                    ed_email.setError("Invalid Email");
                }
                else if(!isValidPassword(password)){
                    ed_pw.setError("Invalid Password");

                }
                else if(!cpassword.equals(password)){
                    ed_confirmpw.setError("Invalid Password");

                }

                else {

                    registercontact(username, email,password, cpassword,phone,fregid);
                }


            }
        });


    }

    // validating email id
    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    // validating password with retype password
    private boolean isValidPassword(String password) {
        if (password != null && password.length() > 2) {
            return true;
        }
        return false;
    }





    private void registercontact(final String username, String email, final String password, String cpassword, String phone, final String fregid) {

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
                    if(result2.equals("true")) {


                    Toast.makeText(SignUpActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                    String device_type="android";
                    String device_token="123456789";


                    userLogin(username, password, device_type, device_token,fregid);


                    ed_name.setText("");
                    ed_email.setText("");
                    ed_pw.setText("");
                    ed_confirmpw.setText("");
                    ed_phn.setText("");



                }
                else{

                        AlertDialog.Builder builder1 = new AlertDialog.Builder(SignUpActivity.this);
                        builder1.setTitle(Html.fromHtml("<font color='#000000'>" +"Could not signup"));
                        builder1.setMessage((Html.fromHtml("<font color='#000000'>" + "User Name/Email already exists")));
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


//
//                        alertDialog = new AlertDialog.Builder(
//                       SignUpActivity.this).create();
//
//                alertDialog.setTitle(Html.fromHtml("<font color='#000000'>" +"Could not signup"));
//
//                         alertDialog.setMessage(Html.fromHtml("<font color='#000000'>" + "User Name/Email already exists"));
//
//
//                      alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int which) {
//
//
//                            dialog.dismiss();
//                        }
//                    });
//
//                         alertDialog.show();
//
//
          }


            }

            @Override
            protected String doInBackground(String... params) {

                HashMap<String, String> data = new HashMap<String,String>();
                data.put("username",params[0]);
                data.put("email",params[1]);
                data.put("password",params[2]);
                data.put("cpassword",params[3]);
                data.put("phone",params[4]);
                data.put("firebase_registration_id",params[5]);

                String result1 = ruc.sendPostRequest(RegisterUrl.register,data);


                try {
                    jsonObject = new JSONObject(result1);
                    Log.e("reult-----",""+result1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                try {
                    result2 = jsonObject.optString("result");
                    Log.e("signup result====",""+result2);
                } catch (Exception e) {
                    e.printStackTrace();
                }


          return  result2;
            }
        }

        RegisterUser ru = new RegisterUser();
        ru.execute(username, email,password, cpassword,phone,fregid);
    }



    private void userLogin(final String username,final String password,String device_type,String device_token,String fregid){
        class UserLoginClass extends AsyncTask<String,Void,String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                String msg="Logging in. Please wait.";

                loading = new ProgressDialog(SignUpActivity.this);
                loading.setMessage(msg);
                loading.show();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();


                if(result2.equals("true")){

                    Toast.makeText(SignUpActivity.this, "Logged in successfully.", Toast.LENGTH_SHORT).show();

                    SharedPreferences.Editor editor1 = sharedpreferences1.edit();
                    editor1.putString(Tokn, s1);
                    editor1.putString(Name1, ss);
                    editor1.putString(Name2, sn);
                    editor1.putString(Email, se);
//                    editor1.putString(Name2, sn);

                    editor1.commit();

                    Intent i = new Intent(SignUpActivity.this, MainTabActivity.class);
                    startActivity(i);

                }
                else{

                    Toast.makeText(SignUpActivity.this, "Invalid details", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String,String> data = new HashMap<>();


                data.put("username",params[0]);
                data.put("password",params[1]);
                data.put("device_type",params[2]);
                data.put("device_token",params[3]);
                data.put("firebase_registration_id",params[4]);

                Register ruc = new Register();

                String result1 = ruc.sendPostRequest(RegisterUrl.login,data);

                try {
                    jsonObject = new JSONObject(result1);
                    JSONObject  obj=	jsonObject.getJSONObject("data");
                    s1 =(String) obj.get("access_token");
                    ss =(String) obj.get("id");
                    sn=(String)obj.get("username");
                    se=(String)obj.get("email");
//                    sp=(String)obj.get("username");



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








}



