package com.example.fragment.FragmentProfileTab;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adapter.adapterProfile.CustomGridProduct1;
import com.example.com.example.zoptal102.Register;
import com.example.fragment.fragmentHomeTab.FragmentHome;
import com.example.model.model_profile.Json_GetData;
import com.example.url.RegisterUrl;
import com.example.zotal102.yahwahapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class ProductFragment extends Fragment {

    ListView list;
    public Frag_AddProduct frag_productProfile;
    View  view;
    String result2="",s;
    Button btn_add;
    public static TextView tv_msg,tv_paypalmsg;
    int deletePosition;
    public static String pid;

    ScrollView scrollView66;
    CustomGridProduct1 customGridProduct1;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // TODO Auto-generated method stub2..
        view = inflater.inflate(R.layout.fragment_productview, container, false);

        list = (ListView) view.findViewById(R.id.gridView1);
        scrollView66=(ScrollView)view.findViewById(R.id.scrollView);
        tv_msg=(TextView)view.findViewById(R.id.tv_msg);
        tv_paypalmsg=(TextView)view.findViewById(R.id.tv_paypalmsg);

        frag_productProfile = new Frag_AddProduct();

        btn_add = (Button) view.findViewById(R.id.add);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentProfile.layout.removeAllViewsInLayout();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager
                        .beginTransaction()
                        .replace(R.id.activity_main_content_fragment,
                                frag_productProfile).commit();
            }
        });

        customGridProduct1	=new CustomGridProduct1(getActivity(), Json_GetData.model);
        list.setAdapter(customGridProduct1);



        new Json_GetData(getActivity(), customGridProduct1).execute(FragmentHome.userid, FragmentHome.usertokn, "1");


        return view;
    }

    // method to remove list item
 protected void removeItemFromList(final int position) {
        deletePosition = position;

        AlertDialog.Builder alert = new AlertDialog.Builder(
                getActivity());

        alert.setTitle("Delete");
        alert.setMessage("Do you want delete this item?");
        alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                try {
              productdlt(FragmentHome.userid, Json_GetData.model.getAl_product_detail().get(deletePosition).getProduct_id());
              }
                catch (IndexOutOfBoundsException e){


                }

            }
        });
        alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                dialog.dismiss();
            }
        });

        alert.show();

    }

    private void productdlt(final String userid, final String pid) {

        class Productdlt extends AsyncTask<String, Void, String> {
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

                    Toast.makeText(getActivity(), "Product deleted successfully", Toast.LENGTH_LONG).show();
                 Json_GetData.model.getAl_product_detail().remove(deletePosition);
                    new Json_GetData(getActivity(), customGridProduct1).execute(FragmentHome.userid, FragmentHome.usertokn, "1");

                }
                else{

                    Toast.makeText(getActivity(), "Something went wrong, please try again!", Toast.LENGTH_LONG).show();

                }

            }

            @Override
            protected String doInBackground(String... params) {

                HashMap<String, String> data = new HashMap<String, String>();
                data.put("user_id", params[0]);
                data.put("id", params[1]);

                String result1 = ruc.sendPostRequest(RegisterUrl.dlt, data);

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
           return result2;
            }
        }

        Productdlt ru = new Productdlt();
        ru.execute(userid, pid);

    }


}












