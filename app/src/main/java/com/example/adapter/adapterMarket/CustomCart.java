package com.example.adapter.adapterMarket;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.com.example.zoptal102.Register;
import com.example.fragment.fragmentHomeTab.FragmentHome;
import com.example.fragment.fragmentMarketTab.Fragment_cart;
import com.example.model.model_market.Json_ShowCart;
import com.example.model.model_market.Json_ShowCartTotal;
import com.example.model.model_market.Model_showCart;
import com.example.url.RegisterUrl;
import com.example.zotal102.yahwahapp.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;

public class CustomCart extends BaseAdapter {

    public static List<String> productamout;
    public static List<Integer> productid;
    public static List<Integer> sellerid;
    public static List<String> proqty;
    public static List<String> producttype;
    public static List<String> mrchntid;
    public static List<String> mrchnt_name;
    String result2,s;
    JSONObject jsonObject;
  //  int deletePosition;
    private Context mContext;
    public static Model_showCart m;

     TextView txt_name,txt_qty,txt_qtyvalue;
     TextView txt_price;
     ImageView img,dlt_img;

    public static List mrchntlist,mrchntname;

    public  ArrayList<String> listItems=new ArrayList<String>();

    public static  String pid;
    String val_qty;
    Double subtotal;
    public CustomCart(Context c, Model_showCart m) {

        mContext = c;
        this.m = m;

      // TODO Auto-generated constructor stub
    }


    @Override
    public int getCount() {
        // TODO Auto-generated method stub

        if(m.getAl_cart().size()>0){
            Fragment_cart.txt_msg.setVisibility(View.INVISIBLE);
            Fragment_cart.txt_subtotal.setVisibility(View.VISIBLE);
            Fragment_cart.btn_order.setVisibility(View.VISIBLE);
            Fragment_cart.dlr.setVisibility(View.VISIBLE);
            Fragment_cart.dlr1.setVisibility(View.VISIBLE);

        }
       return m.getAl_cart().size() ;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(final int position, View convertView1, ViewGroup parent) {
        // TODO Auto-generated method stub

        if (convertView1 == null){

            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView1 = inflater.inflate(R.layout.custom_cartview, null);
        convertView1.setBackgroundResource(R.drawable.bcklist);
        txt_name = (TextView) convertView1.findViewById(R.id.name);
        txt_qty = (TextView) convertView1.findViewById(R.id.qty);
        txt_qtyvalue = (TextView) convertView1.findViewById(R.id.qty_value);
        txt_price = (TextView) convertView1.findViewById(R.id.price);
        img = (ImageView) convertView1.findViewById(R.id.img);
        dlt_img = (ImageView) convertView1.findViewById(R.id.dlt);

    }
            txt_name.setText(Json_ShowCart.model_cart.getAl_cart().get(position).getItem_name());
            txt_price.setText(Json_ShowCart.model_cart.getAl_cart().get(position).getItem_price());
            txt_qtyvalue.setText(String.valueOf(Json_ShowCart.model_cart.getAl_cart().get(position).getItem_qty()));

            listItems.add(String.valueOf(txt_price));
            Picasso.with(mContext).load(Json_ShowCart.model_cart.getAl_cart().get(position).getItem_image()).into(img);


           productamout =new ArrayList<String>();
            productid=new ArrayList<Integer>();
            sellerid=new ArrayList<Integer>();
            proqty=new ArrayList<String>();
            producttype=new ArrayList<String>();
           mrchntid=new ArrayList<String>();
           mrchnt_name=new ArrayList<String>();
            double QuantyInt = 1;
            double PriceInt = 0;

            QuantyInt = Double.parseDouble(txt_qtyvalue.getText().toString());
            PriceInt = Double.parseDouble(Json_ShowCart.model_cart.getAl_cart().get(position).getItem_price());


            subtotal = (QuantyInt * PriceInt);

            txt_price.setText(String.valueOf(subtotal));

            double total=0;

            for (int i = 0; i <m.getAl_cart().size(); i++) {

                Double pri= Double.parseDouble(m.getAl_cart().get(i).getItem_price());

                Double qty= Double.valueOf(m.getAl_cart().get(i).getItem_qty());
                 val_qty= String.valueOf(m.getAl_cart().get(i).getItem_qty());
                total+=(qty*pri);

              productamout.add(m.getAl_cart().get(i).getItem_price());
              productid.add(m.getAl_cart().get(i).getItemid());
              sellerid.add(Integer.valueOf(m.getAl_cart().get(i).getSeller_id()));
              proqty.add(m.getAl_cart().get(i).getItem_quantity());
              producttype.add(m.getAl_cart().get(i).getItem_type());

               mrchntid.add(m.getAl_cart().get(i).getMrchntid());
                mrchnt_name.add(m.getAl_cart().get(i).getSeller_name());


       }

        mrchntlist= new ArrayList(new LinkedHashSet(mrchntid));
        mrchntname=new ArrayList(new LinkedHashSet(mrchnt_name));


            Double  tx=(total*6/100);
              Log.e("tx---",""+tx);
        Fragment_cart.tv_totalservicetx.setText(String.valueOf(tx));
             total=total+tx;

            Fragment_cart.tv_total.setText(String.valueOf(total));



    dlt_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                       removeItemFromList(position);
                     notifyDataSetChanged();



                }
            });


        return convertView1;


    }

    // method to remove list item
    public void removeItemFromList(final int position) {
            AlertDialog.Builder alert = new AlertDialog.Builder(
                mContext);

        alert.setTitle("Delete");
        alert.setMessage("Do you want delete this item?");
        alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                int val_total=0;
            val_total-=Integer.parseInt(String.valueOf(m.getAl_cart().get(position).getItem_qty()));
                int value=Integer.parseInt(String.valueOf(val_total));
            int tv=Integer.parseInt(String.valueOf(Fragment_cart.tv_cart1.getText()));
                value=value+tv;


                new Json_ShowCartTotal(mContext).execute(FragmentHome.userid, FragmentHome.usertokn);

                FragmentHome.tv_cart2.setText(String.valueOf(value));
                Fragment_cart.tv_cart1.setText(String.valueOf(value));

                if(Fragment_cart.tv_cart1.getText().equals("0")){

                    Fragment_cart.txt_msg.setVisibility(View.VISIBLE);
                    Fragment_cart.txt_subtotal.setVisibility(View.INVISIBLE);
                   Fragment_cart.btn_order.setVisibility(View.INVISIBLE);

                    Fragment_cart.tv_total.setVisibility(View.INVISIBLE);



                }

                Itemdlt(FragmentHome.userid, FragmentHome.usertokn, m.getAl_cart().get(position).getItemid());

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
                loading = new ProgressDialog(mContext);
                loading.setMessage("loading");
                loading.show();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                if(result2.equals("true")) {
                    Toast.makeText(mContext, "Item deleted successfully", Toast.LENGTH_LONG).show();


                    new Json_ShowCart(mContext,Fragment_cart.crt_list1).execute(userid, usertokn);

            }
                else{

                    Toast.makeText(mContext, "Something went wrong, please try again!", Toast.LENGTH_LONG).show();

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





