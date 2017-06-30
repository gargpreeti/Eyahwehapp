package com.example.adapter.adapterProfile;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.model.model_profile.Model_BuyerHistory;
import com.example.zotal102.yahwahapp.R;

public class CustomBuyerHistory extends BaseAdapter {

    private Context mContext;
    public static Model_BuyerHistory buy;
    TextView orderno,date,pname,address,p_qty,pincode,p_amount,landmark,p_type,city,paymnt_type,state,phonenum;
    ImageView img;

    public CustomBuyerHistory(Context c, Model_BuyerHistory  buy) {

        mContext = c;
        this.buy = buy;

   }


    @Override
    public int getCount() {
        // TODO Auto-generated method stub

       return buy.getAl_buyer_history().size() ;
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

            convertView1 = inflater.inflate(R.layout.custom_buyer, null);

        // img=(ImageView)convertView1.findViewById(R.id.img);
            orderno = (TextView) convertView1.findViewById(R.id.orderno);
            date= (TextView) convertView1.findViewById(R.id.date);
            pname = (TextView) convertView1.findViewById(R.id.pname);
            address = (TextView) convertView1.findViewById(R.id.address);
            p_qty= (TextView) convertView1.findViewById(R.id.p_qty);
            pincode=(TextView)convertView1.findViewById(R.id.pincode);
            p_amount = (TextView) convertView1.findViewById(R.id.p_amount);
            landmark = (TextView) convertView1.findViewById(R.id.landmark);
            p_type = (TextView) convertView1.findViewById(R.id.p_type);
            city = (TextView) convertView1.findViewById(R.id.city);
            paymnt_type = (TextView) convertView1.findViewById(R.id.paymnt_type);
            state = (TextView) convertView1.findViewById(R.id.state);
            phonenum = (TextView) convertView1.findViewById(R.id.phonenum);

    }
              orderno.setText("OrderNo:"+" "+buy.getAl_buyer_history().get(position).getid());
        date.setText("Date:"+" "+buy.getAl_buyer_history().get(position).getOrder_date());
        pname.setText(buy.getAl_buyer_history().get(position).getName());
        address.setText("Address:"+" "+buy.getAl_buyer_history().get(position).getAddress());
        p_qty.setText("Qty:"+" "+buy.getAl_buyer_history().get(position).getProduct_qty());
        pincode.setText("Pincode:"+" "+buy.getAl_buyer_history().get(position).getPincode());
        p_amount.setText("Amount:"+" "+buy.getAl_buyer_history().get(position).getProduct_amount());
        landmark.setText("Landmark:"+" "+buy.getAl_buyer_history().get(position).getLandmark());

        String typ = null;
        if(buy.getAl_buyer_history().get(position).getProduct_type().equals("p")){

            typ="Product";
        }
        else if(buy.getAl_buyer_history().get(position).getProduct_type().equals("e")){
            typ="Event";
        }
        else if(buy.getAl_buyer_history().get(position).getProduct_type().equals("s")){
            typ="Service";
        }
        p_type.setText("Product Type:"+" "+typ);

        city.setText("City:"+" "+buy.getAl_buyer_history().get(position).getCity());
        paymnt_type.setText("Payment Type:"+" "+buy.getAl_buyer_history().get(position).getPayment_type());
        state.setText("State:"+" "+buy.getAl_buyer_history().get(position).getState());
        phonenum.setText("Phone No.:"+" "+buy.getAl_buyer_history().get(position).getPhonenum());




        return convertView1;


    }



}





