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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
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
import java.util.ArrayList;
import java.util.HashMap;

public class Frag_AddProduct extends Fragment implements AdapterView.OnItemClickListener {

    FragmentManager fragmentManager1;
    EditText ed_name, ed_desc, ed_tags, ed_price;
    Button btn_post, btn_upload;
    ImageView bck_img, pro_img;
    public  FragmentProfile fragmentProfile;
    String result2 = "";
    int productid;
    AutoCompleteTextView ed_loc;
    JSONObject jsonObject;
    RelativeLayout bck_layout;


    private Bitmap bitmap;
    String name, description, tags, price, url, location;
    ArrayList<Integer> namelist = new ArrayList<>();
    String uploadImage1;
    public static TextView tv_cart11;
    ImageView img_cart;
    Double val;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        View view = inflater.inflate(R.layout.fragment_addproduct, container, false);


        img_cart = (ImageView) view.findViewById(R.id.cart);
        tv_cart11 = (TextView) view.findViewById(R.id.total_cart);
        tv_cart11.setText(String.valueOf(FragmentHome.tv_cart2.getText()));


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

        bck_img = (ImageView) view.findViewById(R.id.back);
        ed_name = (EditText) view.findViewById(R.id.ed_name);
        ed_desc = (EditText) view.findViewById(R.id.ed_desc);
        ed_tags = (EditText) view.findViewById(R.id.ed_tags);
        ed_price = (EditText) view.findViewById(R.id.ed_price);
        pro_img = (ImageView) view.findViewById(R.id.product_img);

        bck_layout = (RelativeLayout) view.findViewById(R.id.header_layout1);
        btn_post = (Button) view.findViewById(R.id.postproduct);
        btn_upload = (Button) view.findViewById(R.id.upload);


        bck_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager1 = getFragmentManager();
                fragmentProfile = new FragmentProfile();
                fragmentManager1
                        .beginTransaction()
                        .replace(R.id.activity_main_content_fragment,
                                fragmentProfile).commit();
            }
        });

       bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.noimage);


        btn_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name = ed_name.getText().toString().trim().toLowerCase();
                description = ed_desc.getText().toString().trim().toLowerCase();
                tags = ed_tags.getText().toString().trim().toLowerCase();
                price = ed_price.getText().toString().trim().toLowerCase();
                location = ed_loc.getText().toString().trim().toLowerCase();

                try {
                    val = Double.parseDouble(ed_price.getText().toString().trim().toLowerCase());
                }
                catch (NumberFormatException e){

                }

                if (name.equals("") || price.equals("") || location.equals("")) {

                    Toast toast = Toast.makeText(getActivity(), "Name,Price,Location should not be empty", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();


                } else if (val <= 0) {

                    Toast toast = Toast.makeText(getActivity(), "Price should be greater than 0", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else if (FragmentProfile.paypalid == null) {

                    Toast toast = Toast.makeText(getActivity(), "Please add paypal marchant_id before adding any Product/Event/Service", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();

                } else {

                    addproduct(FragmentHome.userid, name, description, tags, price, location, FragmentHome.usertokn, bitmap);
                }

            }
        });


        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            Crop.pickImage(getActivity(),1);


            }
        });

        return view;

    }


    public void onActivityResult1(int requestCode, int resultCode, Intent data) {

        try {
         if(requestCode==1) {

     beginCrop(data.getData());

         }
            if (requestCode ==1) {

             handleCrop(resultCode, data);
            }


        }
        catch (Exception e) {

        }
        }
    private void beginCrop(Uri source) {
        Uri destination = Uri.fromFile(new File(getActivity().getCacheDir(), "cropped"));
        Crop.of(source, destination).withAspect(1000,600).start(getActivity(), 1);


    }

    private void handleCrop(int resultCode, Intent result) {
        if (resultCode ==getActivity().RESULT_OK) {

            pro_img.setImageURI(null);

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),Crop.getOutput(result));


            } catch (IOException e) {
                e.printStackTrace();
            }
            pro_img.setImageURI(Crop.getOutput(result));

        }
        else if (resultCode == Crop.RESULT_ERROR) {
            Toast.makeText(getActivity(), Crop.getError(result).getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public String getStringImage(Bitmap bmp) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
         bmp.compress(Bitmap.CompressFormat.JPEG,20, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void addproduct(String userid, String name, String description, String tags, String price, String location, String usertokn, Bitmap bitmap) {

        class AddProduct extends AsyncTask<Object, Void, String> {
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


                Toast toast =  Toast.makeText(getActivity(), "Product added successfully", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();

                FragmentManager fragmentManager1 =getActivity().getSupportFragmentManager();
                FragmentProfile  frag_profile=new FragmentProfile();
                fragmentManager1
                        .beginTransaction()
                        .replace(R.id.activity_main_content_fragment,
                                frag_profile).commit();

            }

            @Override
            protected String doInBackground(Object... params) {
               Bitmap bitmap1= (Bitmap) params[7];
                uploadImage1 = getStringImage(bitmap1);
                HashMap<String, String> data = new HashMap<String, String>();
                data.put("user_id", (String) params[0]);
                data.put("name", (String) params[1]);
                data.put("description", (String) params[2]);
                data.put("tags", (String) params[3]);
                data.put("price", (String) params[4]);
                data.put("location", (String) params[5]);
                data.put("access_token", (String) params[6]);
               data.put("userfile", uploadImage1);

                String result1 = ruc.sendPostRequest(RegisterUrl.addproduct, data);

                try {
                    jsonObject = new JSONObject(result1);
                    JSONObject obj = jsonObject.getJSONObject("data");
                    productid = (int) obj.get("id");

                    namelist.add(productid);

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



                return result2;
            }
        }

        AddProduct ru = new AddProduct();
        ru.execute(userid, name, description, tags, price, location, usertokn,bitmap);
    }

    @Override
    public void onItemClick(AdapterView adapterView, View view, int position, long id) {

        String str = (String) adapterView.getItemAtPosition(position);

    }
}



