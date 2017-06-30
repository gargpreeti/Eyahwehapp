package com.example.fragment.FragmentProfileTab;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.com.example.zoptal102.Register;
import com.example.fragment.fragmentHomeTab.FragmentHome;
import com.example.url.RegisterUrl;
import com.example.zotal102.yahwahapp.R;
import com.soundcloud.android.crop.Crop;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Edit_Profile extends Fragment {


    TextView tv_chnge, tv_username;
    EditText ed_email, ed_pw, ed_phn,ed_mrchnt,ed_uname;
    Button btn_Save;
    String result2="";
    RelativeLayout bck_layout;
    ImageView img_user,bck_img;
    private Bitmap bitmap;
    View view;

    String paypid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        view= inflater.inflate(R.layout.edit_profile, container, false);

        bck_img = (ImageView) view.findViewById(R.id.back);
        tv_chnge = (TextView) view.findViewById(R.id.change);
        tv_username = (TextView) view.findViewById(R.id.text_username);
        ed_uname = (EditText) view.findViewById(R.id.ed_uname);
        ed_email = (EditText) view.findViewById(R.id.ed_email);
        ed_pw = (EditText) view.findViewById(R.id.ed_pw);
        ed_phn = (EditText) view.findViewById(R.id.ed_phn);
        ed_mrchnt = (EditText) view.findViewById(R.id.ed_mrchnt);
        btn_Save = (Button) view.findViewById(R.id.save);
        img_user = (ImageView) view.findViewById(R.id.user_img);

        bck_layout = (RelativeLayout) view.findViewById(R.id.header_layout1);


        tv_username.setText(FragmentProfile.uname);
        ed_uname.setText(FragmentProfile.uname);
        ed_email.setText(FragmentProfile.email);
        ed_phn.setText(FragmentProfile.phone);
        ed_mrchnt.setText(FragmentProfile.paypalid);
        Picasso.with(getActivity()).load(FragmentProfile.img).into(img_user);


        Thread t1=new Thread()
        {
            public void run()
            {
                URL url = null;
                try {
                    url = new URL(FragmentProfile.img);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                         try {
                    bitmap= BitmapFactory.decodeStream(url.openConnection().getInputStream());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        t1.start();


        ed_pw.setEnabled(false);

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



      img_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Crop.pickImage(getActivity(),2);


            }
        });

        tv_chnge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();

                fragmentManager
                        .beginTransaction()
                        .replace(R.id.activity_main_content_fragment,
                                new Change_pw()).commit();
            }
        });

        btn_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String email = ed_email.getText().toString().trim().toLowerCase();
                String uname = ed_uname.getText().toString().trim().toLowerCase();
                String phone = ed_phn.getText().toString().trim().toLowerCase();
                String mrchnt_id = ed_mrchnt.getText().toString().trim().toLowerCase();
                String device_type = "android";
                String device_token = "123456789";

                if (uname.equals("")||email.equals("") || device_type.equals("") || mrchnt_id.equals("")) {
                    Toast.makeText(getActivity(), "Username/Email/Marchant-Id should not be empty ", Toast.LENGTH_LONG).show();
                } else if (!isValidEmail(email) && !isValidEmail(mrchnt_id)) {
                    ed_email.setError("Invalid Email");
                    ed_mrchnt.setError("Invalid Email");

                } else {


                    editprofile(FragmentHome.userid,uname, email, phone, device_token, device_type, FragmentHome.usertokn, mrchnt_id, bitmap);

                }

            }
        });

        return view;
    }


    public void onActivityResult1(int requestCode, int resultCode, Intent data) {

        try {
            if(requestCode==2) {
                beginCrop(data.getData());

            }
            if (requestCode ==2) {

                handleCrop(resultCode, data);
            }


        }
        catch (Exception e) {

        }

    }



    private void beginCrop(Uri source) {
        Uri destination = Uri.fromFile(new File(getActivity().getCacheDir(), "cropped"));
        Crop.of(source, destination).asSquare().start(getActivity(), 2);

    }

    private void handleCrop(int resultCode, Intent result) {
        if (resultCode ==getActivity().RESULT_OK) {

             img_user.setImageURI(null);
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),Crop.getOutput(result));
            } catch (IOException e) {
                e.printStackTrace();
            }
            img_user.setImageURI(Crop.getOutput(result));
        }
        else if (resultCode == Crop.RESULT_ERROR) {
            Toast.makeText(getActivity(), Crop.getError(result).getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    // validating email id
    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public String getStringImage(Bitmap bmp) {

       ByteArrayOutputStream baos = new ByteArrayOutputStream();
       bmp.compress(Bitmap.CompressFormat.JPEG,20, baos);
       byte[] imageBytes = baos.toByteArray();
       String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
       return encodedImage;
    }

    private void editprofile(String userid, String username,String email, String phone, final String device_token, final String device_type, String usertokn, String mrchnt_id,Bitmap bitmap) {

        class EditProfile extends AsyncTask<Object, Void, String> {
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
                Toast.makeText(getActivity(), "Profile editing successful", Toast.LENGTH_LONG).show();

                Log.e("in posttt pid----",""+paypid);
                FragmentManager fragmentManager = getFragmentManager();

                fragmentManager
                        .beginTransaction()
                        .replace(R.id.activity_main_content_fragment,
                                new FragmentProfile()).commit();

            }

            @Override
            protected String doInBackground(Object... params) {

                Bitmap bitmap = (Bitmap) params[8];
                String uploadImage1 =getStringImage(bitmap);

                HashMap<String, String> data = new HashMap<String, String>();
                data.put("user_id", (String) params[0]);
                data.put("username", (String) params[1]);
                data.put("email", (String) params[2]);
                data.put("phone", (String) params[3]);
                data.put("device_token", (String) params[4]);
                data.put("device_type", (String) params[5]);
                data.put("access_token", (String) params[6]);
                data.put("PayPal_id", (String) params[7]);
                data.put("userfile", uploadImage1);

                String result1 = ruc.sendPostRequest(RegisterUrl.edit_profile, data);


                Log.e("result profile----",""+result1);
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


                } catch (Exception e) {
                    e.printStackTrace();
                }



                return result2;
            }
        }

        EditProfile ru = new EditProfile();
        ru.execute(userid,username,email, phone, device_token, device_type, usertokn,mrchnt_id,bitmap);
    }

}



