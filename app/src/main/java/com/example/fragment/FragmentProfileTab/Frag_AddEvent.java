package com.example.fragment.FragmentProfileTab;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Base64;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.com.example.zoptal102.GooglePlacesAutocompleteAdapter;
import com.example.com.example.zoptal102.Register;
import com.example.fragment.fragmentHomeTab.FragmentHome;
import com.example.fragment.fragmentMarketTab.Fragment_cart;
import com.example.url.RegisterUrl;
import com.example.zotal102.yahwahapp.R;
import com.soundcloud.android.crop.Crop;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;


public class Frag_AddEvent extends Fragment implements AdapterView.OnItemClickListener{

    TextView tv_name;
    String result2="";
    String uploadImage1;
    int eventid;

    ImageView v_img,sdt_img,edt_img,bck_img;
    EditText ed_name,ed_desc,ed_tags,ed_price,ed_sdt,ed_enddt,ed_stime,ed_endtime;
    Button btn_post,btn_upload;

     JSONObject jsonObject;
     AutoCompleteTextView ed_loc;
    private Bitmap bitmap;
    RelativeLayout bck_layout;

    private SimpleDateFormat dateFormatter;
    private DatePickerDialog fromDatePickerDialog;
    private DatePickerDialog toDatePickerDialog;
    public static TextView tv_cart12;
    ImageView img_cart;
    Double val;




    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        View view = inflater.inflate(R.layout.fragment_addevent, container, false);



        img_cart=(ImageView) view.findViewById(R.id.cart);
        tv_cart12=(TextView) view.findViewById(R.id.total_cart);

        tv_cart12.setText(String.valueOf(FragmentHome.tv_cart2.getText()));


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

       ed_loc = (AutoCompleteTextView) view.findViewById(R.id.autoCompleteTextView);

        ed_loc.setAdapter(new GooglePlacesAutocompleteAdapter(getActivity(), R.layout.list_item));

        ed_loc.setOnItemClickListener(this);

        bitmap= BitmapFactory.decodeResource(getResources(), R.drawable.noimage);

        bck_layout = (RelativeLayout) view.findViewById(R.id.header_layout1);
        tv_name=(TextView)view.findViewById(R.id.ed_name);
       bck_img = (ImageView) view.findViewById(R.id.back);

        ed_name=(EditText)view.findViewById(R.id.ed_name);
        ed_desc=(EditText)view.findViewById(R.id.ed_desc);
        ed_tags=(EditText)view.findViewById(R.id.ed_tags);
        ed_price=(EditText)view.findViewById(R.id.ed_price);
        ed_sdt=(EditText)view.findViewById(R.id.ed_sdt);
        ed_enddt=(EditText)view.findViewById(R.id.ed_date);
        ed_stime=(EditText)view.findViewById(R.id.ed_strt);
        ed_endtime=(EditText)view.findViewById(R.id.ed_end);
        v_img=(ImageView)view.findViewById(R.id.ed_img);
        sdt_img=(ImageView)view.findViewById(R.id.st_img);
        edt_img=(ImageView)view.findViewById(R.id.edt_img);


           ed_sdt.setEnabled(false);
           ed_enddt.setEnabled(false);

        dateFormatter = new SimpleDateFormat("yyyy-MM-dd",Locale.US);
        setDateTimeField();
        btn_post=(Button)view.findViewById(R.id.postproduct);
        btn_upload=(Button)view.findViewById(R.id.upload);


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
                int hour = mcurrentTime.get(Calendar.HOUR);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;

                mTimePicker = new TimePickerDialog(getActivity(),  AlertDialog.THEME_DEVICE_DEFAULT_LIGHT,new TimePickerDialog.OnTimeSetListener() {
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

                mTimePicker = new TimePickerDialog(getActivity(), AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, new TimePickerDialog.OnTimeSetListener() {
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

                }, hour, minute,false);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });

        btn_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String name = ed_name .getText().toString().trim().toLowerCase();
                String description=  ed_desc.getText().toString().trim().toLowerCase();
                String tags= ed_tags.getText().toString().trim().toLowerCase();
                String price = ed_price.getText().toString().trim().toLowerCase();
                String sdate= ed_sdt.getText().toString().trim().toLowerCase();
                String enddt = ed_enddt.getText().toString().trim().toLowerCase();
                String stime = ed_stime.getText().toString().trim().toLowerCase();
                String endtime = ed_endtime.getText().toString().trim().toLowerCase();
                String location= ed_loc.getText().toString().trim().toLowerCase();

        try {
          val = Double.parseDouble(ed_price.getText().toString().trim().toLowerCase());
        }
        catch (NumberFormatException e){

        }
                if(name.equals("") || price.equals("")||sdate.equals("")||enddt.equals("")||stime.equals("")||endtime.equals("")||location.equals(""))
                {
                    Toast toast =Toast.makeText(getActivity(), "Name,Price,Start Date,End Date,Start Time,End Time,Location should not be empty", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
                else if(val<=0){

                    Toast toast =Toast.makeText(getActivity(), "Price should be greater than 0", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }

                else if(FragmentProfile.paypalid==null) {

                    Toast toast =Toast.makeText(getActivity(), "Please add paypal marchant_id before adding any Product/Event/Service", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();

                }
                else {
                      addevent(FragmentHome.userid, name, description, tags, price, location, sdate, enddt, stime, endtime, FragmentHome.usertokn, bitmap);
                }

            }
        });

        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Crop.pickImage(getActivity(),4);



            }
        });


        return view;

    }
   public void onActivityResult1(int requestCode, int resultCode, Intent data) {
        try {
            if(requestCode==4) {
               beginCrop(data.getData());

            }
            if (requestCode ==4) {

                handleCrop(resultCode, data);
            }


        }
        catch (Exception e) {

        }

    }


    private void beginCrop(Uri source) {
        Uri destination = Uri.fromFile(new File(getActivity().getCacheDir(), "cropped"));
        Crop.of(source, destination).withAspect(1000,600).start(getActivity(),4);

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



    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }


    private void setDateTimeField() {
      Calendar newCalendar = Calendar.getInstance();

        fromDatePickerDialog = new DatePickerDialog(getActivity(),AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, new DatePickerDialog.OnDateSetListener() {


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


    private void addevent(String userid,String name,String description, String tags, String price,String location ,String sdate,String enddt,String stime,String endtime,String usertokn,Bitmap bitmap) {

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


                    Toast toast =Toast.makeText(getActivity(), "Event added successfully", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();

                    FragmentManager fragmentManager1 =getActivity().getSupportFragmentManager();
                    FragmentProfile  frag_profile=new FragmentProfile();
                    fragmentManager1
                            .beginTransaction()
                            .replace(R.id.activity_main_content_fragment,
                                    frag_profile).commit();


                }
                else{

                    Toast toast =Toast.makeText(getActivity(), "Something went wrong, please try again!", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();


                }

            }

            @Override
            protected String doInBackground(Object... params) {

                HashMap<String, String> data = new HashMap<String,String>();

                Bitmap   bitmap = (Bitmap) params[11];
                uploadImage1= getStringImage(bitmap);
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
                data.put("userfile", uploadImage1);

                String result1 = ruc.sendPostRequest(RegisterUrl.add_event,data);


                try {
                    jsonObject = new JSONObject(result1);

                    JSONObject  obj=	jsonObject.getJSONObject("data");
                    eventid =(int) obj.get("id");

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
        AddEvent ru = new AddEvent();
        ru.execute(userid, name, description, tags, price,location, sdate,enddt,stime,endtime, usertokn,bitmap);
    }

    @Override
    public void onItemClick(AdapterView adapterView, View view, int position, long id) {

        String str = (String) adapterView.getItemAtPosition(position);

    }
}



