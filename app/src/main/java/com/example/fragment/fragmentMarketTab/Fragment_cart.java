package com.example.fragment.fragmentMarketTab;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adapter.adapterMarket.CustomCart;
import com.example.adapter.adapterMarket.CustomGridProductMarket;
import com.example.com.example.zoptal102.PayPalIntegrationActivity;
import com.example.com.example.zoptal102.Register;
import com.example.fragment.fragmentHomeTab.FragmentHome;
import com.example.model.model_market.Json_ShowCart;
import com.example.model.model_market.Json_ShowCartTotal;
import com.example.url.RegisterUrl;
import com.example.zotal102.yahwahapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


public class Fragment_cart extends Fragment {

    public static TextView tv_cart1;
    String result2,s;
    JSONObject jsonObject;
     public static ListView crt_list1;
    public static   Button btn_order;
      public static TextView tv_total,tv_totalservicetx;

     String MyPREFERENCES = "MyPrefs1";
    SharedPreferences sharedpreferences1;
    String usertokn, userid;
    String name1 = "nameKey1";
     String tokn = "toknKey";
    public static int deletePosition;
     ImageView bck_img;
    public static TextView txt_subtotal,txt_msg,tv_servicetx,dlr,dlr1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        View view = inflater.inflate(R.layout.fragment_cart, container, false);


        sharedpreferences1 = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        if (sharedpreferences1.contains(tokn)) {

            usertokn = sharedpreferences1.getString(tokn, "");
            userid = sharedpreferences1.getString(name1, "");

        }

        crt_list1=(ListView) view.findViewById(R.id.list_view);
        tv_cart1=(TextView) view.findViewById(R.id.total_cart);
        txt_msg=(TextView) view.findViewById(R.id.tv_msg);
        txt_subtotal=(TextView) view.findViewById(R.id.tv_stotal);
        tv_servicetx=(TextView) view.findViewById(R.id.tv_servicetx);
        dlr=(TextView) view.findViewById(R.id.dlr);
       dlr1=(TextView) view.findViewById(R.id.dlr1);


        txt_subtotal.setVisibility(View.VISIBLE);
        tv_servicetx.setVisibility(View.VISIBLE);
//        dlr.setVisibility(View.VISIBLE);
//        dlr1.setVisibility(View.VISIBLE);

        new Json_ShowCart(getActivity(),crt_list1).execute(userid, usertokn);
        tv_cart1.setText(String.valueOf(CustomGridProductMarket.value));

        btn_order=(Button)view.findViewById(R.id.order);
        tv_total=(TextView)view.findViewById(R.id.textView7);
        tv_totalservicetx=(TextView)view.findViewById(R.id.textView6);

       tv_cart1.setText(String.valueOf(FragmentHome.tv_cart2.getText()));


        if(tv_cart1.getText().equals("0")){

            btn_order.setEnabled(false);
            txt_msg.setVisibility(View.VISIBLE);
            txt_subtotal.setVisibility(View.INVISIBLE);
            btn_order.setVisibility(View.INVISIBLE);
            tv_servicetx.setVisibility(View.INVISIBLE);



        }



        btn_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i = new Intent(getActivity(),PayPalIntegrationActivity.class);
                startActivity(i);



            }
        });
       bck_img=(ImageView)view.findViewById(R.id.back);

        bck_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            FragmentManager fragmentManager1 = getFragmentManager();
           fragmentManager1.popBackStack();


            }
        });



        return view;

    }


    // method to remove list item
    public void removeItemFromList(final int position) {
        deletePosition = position;

        AlertDialog.Builder alert = new AlertDialog.Builder(
                getActivity());

        alert.setTitle("Delete");
        alert.setMessage("Do you want delete this item?");
        alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {



                Itemdlt(userid, usertokn, Json_ShowCart.model_cart.getAl_cart().get(deletePosition).getItemid());


                String val="-1";
                int value=Integer.parseInt(val);
                int tv=Integer.parseInt(String.valueOf(FragmentHome.tv_cart2.getText()));
                value=value+tv;


                new Json_ShowCartTotal(getActivity()).execute(userid, usertokn);

                FragmentHome.tv_cart2.setText(String.valueOf(value));

                tv_cart1.setText(String.valueOf(value));

                if(tv_cart1.getText().equals("0")){

                    btn_order.setEnabled(false);
                    txt_msg.setVisibility(View.VISIBLE);
                    txt_subtotal.setVisibility(View.INVISIBLE);
                    btn_order.setVisibility(View.INVISIBLE);

                    Fragment_cart.tv_total.setVisibility(View.INVISIBLE);

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

    private void Itemdlt(final String userid, final String usertokn,final int pid) {



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
                    Toast.makeText(getActivity(), "Item deleted successfully", Toast.LENGTH_LONG).show();

                    Json_ShowCart.model_cart.getAl_cart().remove(deletePosition);


                   crt_list1.setAdapter(new CustomCart(getActivity(), Json_ShowCart.model_cart));

                }
                else{

                    Toast.makeText(getActivity(), "Something went wrong, please try again!", Toast.LENGTH_LONG).show();

                }

            }

            @Override
            protected String doInBackground(String... params) {

                HashMap<String, String> data = new HashMap<String, String>();
                data.put("user_id", params[0]);
                data.put("access_token", params[1]);
                data.put("item_id", params[2]);

                String result1 = ruc.sendPostRequest(RegisterUrl.dltcart, data);


                try {
                    jsonObject = new JSONObject(result1);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    result2 = jsonObject.optString("result");
                } catch (Exception e) {
                    e.printStackTrace();
                }



                return result2;
            }
        }

        Productdlt ru = new Productdlt();
        ru.execute(userid,usertokn, String.valueOf(pid));

    }








}