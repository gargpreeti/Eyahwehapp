package com.example.fragment.FragmentProfileTab;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.format.DateFormat;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.adapter.adapterProfile.CustomGridEvent1;
import com.example.com.example.zoptal102.GooglePlacesAutocompleteAdapter;
import com.example.com.example.zoptal102.Register;
import com.example.fragment.fragmentHomeTab.FragmentHome;
import com.example.fragment.fragmentMarketTab.Fragment_cart;
import com.example.model.model_profile.Model_getEventDescription;
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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;


public class Frag_EditEvent extends Fragment  implements AdapterView.OnItemClickListener {

   String MyPREFERENCES = "MyPrefs1" ;
    SharedPreferences sharedpreferences1;
    String usertokn,userid,result2="";

    TextView tv_name;
    int eventid1;
     String name1 = "nameKey1";
     String tokn = "toknKey";
   String eveid = "productKey";

    EditText ed_name,ed_desc,ed_tags,ed_price,ed_sdt,ed_enddt,ed_stime,ed_endtime;
    AutoCompleteTextView ed_loc;
    Button btn_post,btn_upload,btn_cncl;
    ImageView edit_img,v_img,sdt_img,edt_img,bck_img;
    private Bitmap bitmap;

    private SimpleDateFormat dateFormatter;
    private DatePickerDialog fromDatePickerDialog;
    private DatePickerDialog toDatePickerDialog;

    public static TextView tv_cart16;
    ImageView img_cart;
    final int CROP_PIC = 15;
    Double val;
    LinearLayout name_lay,desc_lay,tag_lay,price_lay,loc_lay,sdt_lay,edt_lay,stime_lay,etime_lay;
   @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        View view = inflater.inflate(R.layout.fragment_editevent, container, false);


       img_cart=(ImageView) view.findViewById(R.id.cart);
       tv_cart16=(TextView) view.findViewById(R.id.total_cart);
       tv_cart16.setText(String.valueOf(FragmentHome.tv_cart2.getText()));


       img_cart.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               FragmentManager fragmentManager = getFragmentManager();

               fragmentManager
                       .beginTransaction()
                       .replace(R.id.activity_main_content_fragment,
                               new Fragment_cart()).addToBackStack(null).commit();

           }
       });

         Bundle bundle = getArguments();
        final Model_getEventDescription m=(Model_getEventDescription)bundle.getSerializable("eventdata");

       sharedpreferences1 = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

       dateFormatter = new SimpleDateFormat("yyyy-MM-dd",Locale.US);
       setDateTimeField();

       bck_img=(ImageView)view.findViewById(R.id.back);
        tv_name=(TextView)view.findViewById(R.id.ed_name);

        ed_name=(EditText)view.findViewById(R.id.ed_name);
        ed_desc=(EditText)view.findViewById(R.id.ed_desc);
        ed_tags=(EditText)view.findViewById(R.id.ed_tags);
        ed_price=(EditText)view.findViewById(R.id.ed_price);
        ed_sdt=(EditText)view.findViewById(R.id.ed_sdt);
        ed_enddt=(EditText)view.findViewById(R.id.ed_date);
        ed_stime=(EditText)view.findViewById(R.id.ed_strt);
        ed_endtime=(EditText)view.findViewById(R.id.ed_end);
        ed_loc=(AutoCompleteTextView)view.findViewById(R.id.autoCompleteTextView);

        edit_img=(ImageView)view.findViewById(R.id.edit);
        sdt_img=(ImageView)view.findViewById(R.id.st_img);
        edt_img=(ImageView)view.findViewById(R.id.edt_img);
        v_img=(ImageView)view.findViewById(R.id.ed_img);

       btn_post=(Button)view.findViewById(R.id.postproduct);
       btn_upload=(Button)view.findViewById(R.id.upload);
       btn_cncl=(Button)view.findViewById(R.id.cncl);

       ed_loc = (AutoCompleteTextView) view.findViewById(R.id.autoCompleteTextView);

       ed_loc.setAdapter(new GooglePlacesAutocompleteAdapter(getActivity(), R.layout.list_item));

       ed_loc.setOnItemClickListener(this);


       name_lay=(LinearLayout)view.findViewById(R.id.lay_name);
       desc_lay=(LinearLayout)view.findViewById(R.id.lay_desc);
       tag_lay=(LinearLayout)view.findViewById(R.id.lay_tag);
       price_lay=(LinearLayout)view.findViewById(R.id.lay_price);
       loc_lay=(LinearLayout)view.findViewById(R.id.lay_auto);
       sdt_lay=(LinearLayout)view.findViewById(R.id.lay_sdt);
       edt_lay=(LinearLayout)view.findViewById(R.id.lay_edt);
       stime_lay=(LinearLayout)view.findViewById(R.id.lay_stime);
       etime_lay=(LinearLayout)view.findViewById(R.id.lay_etime);


       name_lay.setBackgroundColor(Color.parseColor("#E3E3E3"));
       desc_lay.setBackgroundColor(Color.parseColor("#E3E3E3"));
       tag_lay.setBackgroundColor(Color.parseColor("#E3E3E3"));
       price_lay.setBackgroundColor(Color.parseColor("#E3E3E3"));
       loc_lay.setBackgroundColor(Color.parseColor("#E3E3E3"));
       sdt_lay.setBackgroundColor(Color.parseColor("#E3E3E3"));
       edt_lay.setBackgroundColor(Color.parseColor("#E3E3E3"));
       stime_lay.setBackgroundColor(Color.parseColor("#E3E3E3"));
       etime_lay.setBackgroundColor(Color.parseColor("#E3E3E3"));
       btn_upload.setBackgroundColor(Color.parseColor("#E3E3E3"));
       btn_post.setBackgroundColor(Color.parseColor("#E3E3E3"));
       btn_cncl.setBackgroundColor(Color.parseColor("#E3E3E3"));


       ed_loc.setOnEditorActionListener(new TextView.OnEditorActionListener() {

           @Override
           public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
               if (actionId == EditorInfo.IME_ACTION_DONE) {
                        InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                   imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                   return true;
               }
               return false;
           }
       });

       ed_name.setEnabled(false);
        ed_desc.setEnabled(false);
        ed_tags.setEnabled(false);
        ed_price.setEnabled(false);
        ed_sdt.setEnabled(false);
        ed_enddt.setEnabled(false);
        ed_stime.setEnabled(false);
        ed_endtime.setEnabled(false);
        ed_loc.setEnabled(false);
        sdt_img.setClickable(false);
        edt_img.setClickable(false);
       btn_post.setEnabled(false);
       btn_upload.setEnabled(false);
       btn_upload.setClickable(false);
       btn_cncl.setClickable(false);
       btn_post.setEnabled(false);

        ed_name.setText(m.getEname());
        ed_desc.setText(m.getEdescription());
        ed_tags.setText(m.getEtags());
        ed_price.setText(String.valueOf(m.getEprice()));
        ed_sdt.setText(m.getEstdate());
        ed_enddt.setText(m.getEenddate());
        ed_stime.setText(m.getEstime());
        ed_endtime.setText(m.getEendtime());
        ed_loc.setText(m.getElocation());
        Picasso.with(getActivity()).load(m.getEimage()).into(v_img);



       Thread t1=new Thread()
       {
           public void run()
           {
               URL url = null;
               try {
                   url = new URL(m.getEimage());
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


    btn_cncl.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        name_lay.setBackgroundColor(Color.parseColor("#E3E3E3"));
        desc_lay.setBackgroundColor(Color.parseColor("#E3E3E3"));
        tag_lay.setBackgroundColor(Color.parseColor("#E3E3E3"));
        price_lay.setBackgroundColor(Color.parseColor("#E3E3E3"));
        loc_lay.setBackgroundColor(Color.parseColor("#E3E3E3"));
        sdt_lay.setBackgroundColor(Color.parseColor("#E3E3E3"));
        edt_lay.setBackgroundColor(Color.parseColor("#E3E3E3"));
        stime_lay.setBackgroundColor(Color.parseColor("#E3E3E3"));
        etime_lay.setBackgroundColor(Color.parseColor("#E3E3E3"));
        btn_upload.setBackgroundColor(Color.parseColor("#E3E3E3"));
        btn_post.setBackgroundColor(Color.parseColor("#E3E3E3"));
        btn_cncl.setBackgroundColor(Color.parseColor("#E3E3E3"));

        ed_name.setTextColor(Color.parseColor("#A9A9A9"));
        ed_desc.setTextColor(Color.parseColor("#A9A9A9"));
        ed_tags.setTextColor(Color.parseColor("#A9A9A9"));
        ed_price.setTextColor(Color.parseColor("#A9A9A9"));
        ed_loc.setTextColor(Color.parseColor("#A9A9A9"));
        ed_sdt.setTextColor(Color.parseColor("#A9A9A9"));
        ed_enddt.setTextColor(Color.parseColor("#808080"));
        ed_stime.setTextColor(Color.parseColor("#808080"));
        ed_endtime.setTextColor(Color.parseColor("#808080"));


        ed_name.setEnabled(false);
        ed_desc.setEnabled(false);
        ed_tags.setEnabled(false);
        ed_price.setEnabled(false);
        ed_sdt.setEnabled(false);
        ed_enddt.setEnabled(false);
        ed_stime.setEnabled(false);
        ed_endtime.setEnabled(false);
        ed_loc.setEnabled(false);
        sdt_img.setClickable(false);
        edt_img.setClickable(false);
        btn_post.setEnabled(false);
        btn_upload.setEnabled(false);
        btn_upload.setClickable(false);
        btn_post.setEnabled(false);


    }
    });



        edit_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                name_lay.setBackgroundColor(Color.parseColor("#3692B9"));
                desc_lay.setBackgroundColor(Color.parseColor("#3692B9"));
                tag_lay.setBackgroundColor(Color.parseColor("#3692B9"));
                price_lay.setBackgroundColor(Color.parseColor("#3692B9"));
                loc_lay.setBackgroundColor(Color.parseColor("#3692B9"));
                sdt_lay.setBackgroundColor(Color.parseColor("#3692B9"));
                edt_lay.setBackgroundColor(Color.parseColor("#3692B9"));
                stime_lay.setBackgroundColor(Color.parseColor("#3692B9"));
                etime_lay.setBackgroundColor(Color.parseColor("#3692B9"));


                btn_upload.setBackgroundColor(Color.parseColor("#3692B9"));
                btn_post.setBackgroundColor(Color.parseColor("#3692B9"));
                btn_cncl.setBackgroundColor(Color.parseColor("#000000"));


                ed_name.setTextColor(Color.parseColor("#808080"));
                ed_desc.setTextColor(Color.parseColor("#808080"));
                ed_tags.setTextColor(Color.parseColor("#808080"));
                ed_price.setTextColor(Color.parseColor("#808080"));
                ed_loc.setTextColor(Color.parseColor("#808080"));
                ed_sdt.setTextColor(Color.parseColor("#808080"));
                ed_enddt.setTextColor(Color.parseColor("#808080"));
                ed_stime.setTextColor(Color.parseColor("#808080"));
                ed_endtime.setTextColor(Color.parseColor("#808080"));


                ed_name.setEnabled(true);
                ed_desc.setEnabled(true);
                ed_tags.setEnabled(true);
                ed_price.setEnabled(true);
                ed_sdt.setEnabled(true);
                ed_enddt.setEnabled(true);
                ed_stime.setEnabled(true);
                ed_endtime.setEnabled(true);
                ed_loc.setEnabled(true);
                sdt_img.setClickable(true);
                edt_img.setClickable(true);
                btn_post.setEnabled(true);
                btn_upload.setEnabled(true);
                btn_upload.setClickable(true);
                btn_cncl.setClickable(true);
                btn_cncl.setEnabled(true);
                 btn_post.setEnabled(true);
            }
        });

       sdt_img.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               fromDatePickerDialog.show();
           }
       });

       edt_img.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               toDatePickerDialog.show();
           }
       });

       ed_stime.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               Calendar mcurrentTime = Calendar.getInstance();
               int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
               int minute = mcurrentTime.get(Calendar.MINUTE);
               TimePickerDialog mTimePicker;

               mTimePicker = new TimePickerDialog(getActivity(),AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, new TimePickerDialog.OnTimeSetListener() {
                   @Override
                   public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                       String aMpM = "AM";
                       if(selectedHour >11)
                       {
                           aMpM = "PM";
                       }

                       int currentHour;
                       if(selectedHour>11)
                       {
                           currentHour = selectedHour - 12;
                       }
                       else
                       {
                           currentHour = selectedHour;
                       }


                       ed_stime.setText(String.format("%02d:%02d %s", currentHour, selectedMinute,
                               aMpM + "\n"));



                   }
               }, hour, minute,false);//Yes 24 hour time
               mTimePicker.setTitle("Select Time");
               mTimePicker.show();

           }
       });

       ed_endtime.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Calendar mcurrentTime = Calendar.getInstance();
               int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
               int minute = mcurrentTime.get(Calendar.MINUTE);
               TimePickerDialog mTimePicker;

               mTimePicker = new TimePickerDialog(getActivity(),AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, new TimePickerDialog.OnTimeSetListener() {
                   @Override
                   public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {


                       String aMpM = "AM";
                       if(selectedHour >11)
                       {
                           aMpM = "PM";
                       }

                       int currentHour;
                       if(selectedHour>11)
                       {
                           currentHour = selectedHour - 12;
                       }
                       else
                       {
                           currentHour = selectedHour;
                       }


                       ed_endtime.setText(String.format("%02d:%02d %s", currentHour, selectedMinute,
                               aMpM + "\n"));

                 }
               }, hour, minute, false);//Yes 24 hour time
               mTimePicker.setTitle("Select Time");
               mTimePicker.show();

           }
       });



      bck_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


             FragmentManager fragmentManager = getFragmentManager();

                fragmentManager
                        .beginTransaction()
                        .replace(R.id.activity_main_content_fragment,
                                new FragmentProfile()).commit();
            }
        });



        btn_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (sharedpreferences1.contains(tokn)) {

                    usertokn=sharedpreferences1.getString(tokn,"");
                    userid=sharedpreferences1.getString(name1,"");
                   eventid1=sharedpreferences1.getInt(eveid,1);


                }

                try {
                   val = Double.parseDouble(ed_price.getText().toString().trim().toLowerCase());
                }
                catch (NumberFormatException e){

                }

                String name = ed_name .getText().toString().trim().toLowerCase();
                String description= ed_desc.getText().toString().trim().toLowerCase();
                String tags= ed_tags.getText().toString().trim().toLowerCase();
                String price = ed_price.getText().toString().trim().toLowerCase();
                String sdate= ed_sdt.getText().toString().trim().toLowerCase();
                String enddt = ed_enddt.getText().toString().trim().toLowerCase();
                String stime = ed_stime.getText().toString().trim().toLowerCase();
                String endtime = ed_endtime.getText().toString().trim().toLowerCase();
                String location= ed_loc.getText().toString().trim().toLowerCase();




                if(name.equals("") || sdate.equals("")||location.equals(""))
                {
                    Toast.makeText(getActivity(), "Name,Location should not be empty", Toast.LENGTH_LONG).show();
                }


                else if(val<=0){

                    Toast toast =Toast.makeText(getActivity(), "Price should be greater than 0", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }

                else {

                    editevent(userid, name, description, tags, price,location, sdate,enddt,stime,endtime, usertokn,CustomGridEvent1.eid,bitmap);
                }

            }
        });


       btn_upload.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               Crop.pickImage(getActivity(), 6);


           }
       });

        return view;

    }
    private void setDateTimeField() {
        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(getActivity(), AlertDialog.THEME_DEVICE_DEFAULT_LIGHT,new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                ed_sdt.setText(dateFormatter.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        toDatePickerDialog = new DatePickerDialog(getActivity(), AlertDialog.THEME_DEVICE_DEFAULT_LIGHT,new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                ed_enddt.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

    }


    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    public void onActivityResult1(int requestCode, int resultCode, Intent data) {


        try {
            if(requestCode==6) {
                beginCrop(data.getData());

            }
            if (requestCode ==6) {

                handleCrop(resultCode, data);
            }

        }
        catch (Exception e) {

        }


    }
    private void beginCrop(Uri source) {
        Uri destination = Uri.fromFile(new File(getActivity().getCacheDir(), "cropped"));
        Crop.of(source, destination).withAspect(1000,600).start(getActivity(), 6);

    }

    private void handleCrop(int resultCode, Intent result) {
        if (resultCode ==getActivity().RESULT_OK) {

            v_img.setImageURI(null);
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),Crop.getOutput(result));
            } catch (IOException e) {
                e.printStackTrace();
            }
            v_img.setImageURI(Crop.getOutput(result));
        }
        else if (resultCode == Crop.RESULT_ERROR) {
            Toast.makeText(getActivity(), Crop.getError(result).getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    private void editevent(String userid,String name, String description, String tags, String price,String location,String sdate,String enddt,String stime,String endtime,String usertokn,String eid, Bitmap bitmap) {

        class AddEvent extends AsyncTask<Object, Void, String> {
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
                    Toast.makeText(getActivity(), "Event Edited successfully", Toast.LENGTH_LONG).show();

                    FragmentManager fragmentManager1 =getActivity().getSupportFragmentManager();
                    FragmentProfile  frag_profile=new FragmentProfile();
                    fragmentManager1
                            .beginTransaction()
                            .replace(R.id.activity_main_content_fragment,
                                    frag_profile).commit();

                }
                else{

                    Toast.makeText(getActivity(), "Something went wrong, please try again!", Toast.LENGTH_LONG).show();

                }

            }

            @Override
            protected String doInBackground(Object... params) {

                HashMap<String, String> data = new HashMap<String,String>();


                Bitmap   bitmap = (Bitmap) params[12];
                String uploadImage1 = getStringImage(bitmap);
                data.put("user_id", (String) params[0]);
                data.put("name", (String) params[1]);
                data.put("description", (String) params[2]);
                data.put("tags", (String) params[3]);
                data.put("price", (String) params[4]);
                data.put("location", (String) params[5]);
                data.put("start_date", (String) params[6]);
                data.put("end_date", (String) params[7]);
                data.put("start_time", (String) params[8]);

                data.put("end_time", (String) params[9]);
                data.put("access_token", (String) params[10]);
                data.put("id", (String) params[11]);
                data.put("userfile", uploadImage1);
                String result1 = ruc.sendPostRequest(RegisterUrl.edit_event,data);

                Log.e("resluttttt event", result1);
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(result1);

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



                Log.e("resluttttt event", result1);

                return  result2;
            }
        }

        AddEvent ru = new AddEvent();
        ru.execute(userid, name, description, tags, price,location, sdate,enddt,stime,endtime, usertokn, eid,bitmap);
    }

    @Override
    public void onItemClick(AdapterView adapterView, View view, int position, long id) {
        String str = (String) adapterView.getItemAtPosition(position);
    }
}



