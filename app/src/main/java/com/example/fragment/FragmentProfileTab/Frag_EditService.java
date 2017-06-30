package com.example.fragment.FragmentProfileTab;



import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Base64;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adapter.adapterProfile.CustomGridService1;
import com.example.com.example.zoptal102.GooglePlacesAutocompleteAdapter;
import com.example.com.example.zoptal102.Register;
import com.example.fragment.fragmentHomeTab.FragmentHome;
import com.example.fragment.fragmentMarketTab.Fragment_cart;
import com.example.model.model_profile.Model_getServiceDescription;
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

public class Frag_EditService extends Fragment implements AdapterView.OnItemClickListener{

    FragmentManager fragmentManager;
    EditText ed_name,ed_desc,ed_tags,ed_price;
    AutoCompleteTextView ed_loc;

    Button btn_post,btn_upload,btn_cncl;
    private ImageView v_img,img_edit,bck_img;
    private Bitmap bitmap;
    Double val;

    String MyPREFERENCES = "MyPrefs1" ;
    SharedPreferences sharedpreferences1;
    String usertokn;
    String userid;
    String result2="";
    int serviceid1;
  String name1 = "nameKey1";
     String tokn = "toknKey";
   String serviceid="serviceKey";

    LinearLayout name_lay,desc_lay,tag_lay,price_lay,loc_lay;
    RelativeLayout bck_layout;

    private Uri picUri;
    final int CROP_PIC = 16;

    public static TextView tv_cart15;
    ImageView img_cart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        View view = inflater.inflate(R.layout.fragment_editservice, container, false);


        img_cart=(ImageView) view.findViewById(R.id.cart);
        tv_cart15=(TextView) view.findViewById(R.id.total_cart);


        tv_cart15.setText(String.valueOf(FragmentHome.tv_cart2.getText()));


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
        final Model_getServiceDescription ms=(Model_getServiceDescription)bundle.getSerializable("servicedata");
        sharedpreferences1 = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

       bck_img=(ImageView)view.findViewById(R.id.back);
        ed_name=(EditText)view.findViewById(R.id.ed_name);
        ed_desc=(EditText)view.findViewById(R.id.ed_desc);
        ed_tags=(EditText)view.findViewById(R.id.ed_tags);
        ed_price=(EditText)view.findViewById(R.id.ed_price);
        ed_loc=(AutoCompleteTextView)view.findViewById(R.id.autoCompleteTextView);
        v_img=(ImageView)view.findViewById(R.id.ed_img);
        bck_layout=(RelativeLayout)view.findViewById(R.id.header_layout1);
        btn_post=(Button)view.findViewById(R.id.postproduct);
        btn_upload=(Button)view.findViewById(R.id.upload);
        btn_cncl=(Button)view.findViewById(R.id.cncl);
        img_edit=(ImageView)view.findViewById(R.id.edit);

        ed_loc.setAdapter(new GooglePlacesAutocompleteAdapter(getActivity(), R.layout.list_item));

        ed_loc.setOnItemClickListener(this);





        name_lay=(LinearLayout)view.findViewById(R.id.lay_name);
        desc_lay=(LinearLayout)view.findViewById(R.id.lay_desc);
        tag_lay=(LinearLayout)view.findViewById(R.id.lay_tag);
        price_lay=(LinearLayout)view.findViewById(R.id.lay_price);
        loc_lay=(LinearLayout)view.findViewById(R.id.lay_auto);


        name_lay.setBackgroundColor(Color.parseColor("#E3E3E3"));
        desc_lay.setBackgroundColor(Color.parseColor("#E3E3E3"));
        tag_lay.setBackgroundColor(Color.parseColor("#E3E3E3"));
        price_lay.setBackgroundColor(Color.parseColor("#E3E3E3"));
        loc_lay.setBackgroundColor(Color.parseColor("#E3E3E3"));
        btn_upload.setBackgroundColor(Color.parseColor("#E3E3E3"));
        btn_post.setBackgroundColor(Color.parseColor("#E3E3E3"));
        btn_cncl.setBackgroundColor(Color.parseColor("#E3E3E3"));

        ed_name.setEnabled(false);
        ed_desc.setEnabled(false);
        ed_tags.setEnabled(false);
        ed_price.setEnabled(false);
        ed_loc.setEnabled(false);
        btn_upload.setEnabled(false);
        btn_post.setEnabled(false);
        btn_upload.setClickable(false);
        btn_cncl.setEnabled(false);
        btn_post.setEnabled(false);

        ed_loc.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    // do something, e.g. set your TextView here via .setText()
                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });


        ed_name.setText(ms.getPname());
         ed_desc.setText(ms.getPdescription());
        ed_tags.setText(ms.getPtags());
        ed_price.setText(String.valueOf(ms.getPprice()));
        ed_loc.setText(ms.getPlocation());
           Picasso.with(getActivity()).load(ms.getPimage()).into(v_img);



        Thread t1=new Thread()
        {
            public void run()
            {
                URL url = null;
                try {
                    url = new URL(ms.getPimage());
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
                btn_upload.setBackgroundColor(Color.parseColor("#E3E3E3"));
                btn_post.setBackgroundColor(Color.parseColor("#E3E3E3"));
                btn_cncl.setBackgroundColor(Color.parseColor("#E3E3E3"));


                ed_name.setTextColor(Color.parseColor("#A9A9A9"));
                ed_desc.setTextColor(Color.parseColor("#A9A9A9"));
                ed_tags.setTextColor(Color.parseColor("#A9A9A9"));
                ed_price.setTextColor(Color.parseColor("#A9A9A9"));
                ed_loc.setTextColor(Color.parseColor("#A9A9A9"));

                ed_name.setEnabled(false);
                ed_desc.setEnabled(false);
                ed_tags.setEnabled(false);
                ed_price.setEnabled(false);
                ed_loc.setEnabled(false);
                btn_upload.setEnabled(false);
                btn_upload.setClickable(false);
                btn_post.setEnabled(false);
            }
        });






        bck_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fragmentManager = getFragmentManager();

                fragmentManager
                        .beginTransaction()
                        .replace(R.id.activity_main_content_fragment,
                                new FragmentProfile()).commit();
            }
        });

        img_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name_lay.setBackgroundColor(Color.parseColor("#3692B9"));
                desc_lay.setBackgroundColor(Color.parseColor("#3692B9"));
                tag_lay.setBackgroundColor(Color.parseColor("#3692B9"));
                price_lay.setBackgroundColor(Color.parseColor("#3692B9"));
                loc_lay.setBackgroundColor(Color.parseColor("#3692B9"));
                btn_upload.setBackgroundColor(Color.parseColor("#3692B9"));
                btn_post.setBackgroundColor(Color.parseColor("#3692B9"));
              btn_cncl.setBackgroundColor(Color.parseColor("#000000"));

                ed_name.setTextColor(Color.parseColor("#808080"));
                ed_desc.setTextColor(Color.parseColor("#808080"));
                ed_tags.setTextColor(Color.parseColor("#808080"));
                ed_price.setTextColor(Color.parseColor("#808080"));
                ed_loc.setTextColor(Color.parseColor("#808080"));

                ed_name.setEnabled(true);
                ed_desc.setEnabled(true);
                ed_tags.setEnabled(true);
                ed_price.setEnabled(true);
                ed_loc.setEnabled(true);
                btn_post.setEnabled(true);
                btn_upload.setEnabled(true);
                btn_upload.setClickable(true);
                btn_cncl.setEnabled(true);
                btn_cncl.setClickable(true);
                btn_post.setEnabled(true);



            }
        });


        btn_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (sharedpreferences1.contains(tokn)) {

                    usertokn=sharedpreferences1.getString(tokn,"");
                    userid=sharedpreferences1.getString(name1,"");
                    serviceid1=sharedpreferences1.getInt(serviceid,0);

                }

try {
  val = Double.parseDouble(ed_price.getText().toString().trim().toLowerCase());
}
catch (NumberFormatException e){

}


                String name = ed_name .getText().toString().trim().toLowerCase();
                String description=  ed_desc.getText().toString().trim().toLowerCase();
                String tags= ed_tags.getText().toString().trim().toLowerCase();
                String price = ed_price.getText().toString().trim().toLowerCase();
                String location= ed_loc.getText().toString().trim().toLowerCase();
                     if(name.equals("") || price.equals("")||location.equals(""))
                {
                    Toast.makeText(getActivity(), "Name,Price,Location should not be empty", Toast.LENGTH_LONG).show();
                }


                else if(val<=0){

                    Toast toast =Toast.makeText(getActivity(), "Price should be greater than 0", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }

                else {

                    editservice(userid, name, description, tags, price, location, usertokn, CustomGridService1.sid,bitmap);
                }

            }
        });


        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Crop.pickImage(getActivity(),7);


            }
        });

        return view;

    }

    public void onActivityResult1(int requestCode, int resultCode, Intent data) {


        try {
            if(requestCode==7) {
                beginCrop(data.getData());

            }
            if (requestCode ==7) {

                handleCrop(resultCode, data);
            }

        }
        catch (Exception e) {

        }



    }
    private void beginCrop(Uri source) {
        Uri destination = Uri.fromFile(new File(getActivity().getCacheDir(), "cropped"));
        Crop.of(source, destination).withAspect(1000,600).start(getActivity(),7);

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

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }


    private void editservice(String userid,String name, String description, String tags, String price,String location ,String usertokn,String serviceid1,Bitmap bitmap) {

        class EditService extends AsyncTask<Object, Void, String> {
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
                    Toast.makeText(getActivity(), "Service Edited successfully", Toast.LENGTH_LONG).show();

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

                Bitmap bitmap = (Bitmap) params[8];
                String uploadImage1 = getStringImage(bitmap);
                HashMap<String, String> data = new HashMap<String, String>();

                data.put("user_id", (String) params[0]);
                data.put("name", (String) params[1]);
                data.put("description", (String) params[2]);
                data.put("tags", (String) params[3]);
                data.put("price", (String) params[4]);
                data.put("location", (String) params[5]);
                     data.put("access_token", (String) params[6]);
                data.put("id", (String) params[7]);
                data.put("userfile", uploadImage1);
                String result1 = ruc.sendPostRequest(RegisterUrl.edit_service,data);

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

        EditService ru = new EditService();
        ru.execute(userid,name, description,tags, price,location,usertokn, serviceid1,bitmap);
    }

    @Override
    public void onItemClick(AdapterView adapterView, View view, int position, long id) {
        String str = (String) adapterView.getItemAtPosition(position);


    }
}



