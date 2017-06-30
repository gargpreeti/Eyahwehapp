package com.example.adapter.adapterProfile;


import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.com.example.zoptal102.QuickAction;
import com.example.model.model_profile.Model_SellingHistory;
import com.example.zotal102.yahwahapp.R;
import com.squareup.picasso.Picasso;

public class CustomSellingHistory extends BaseAdapter {

    private Context mContext;
    public static Model_SellingHistory sel;
    TextView txt_name,txt_amount,txt_buyer,txt_date,txt_tamount,txt_qty,txt_shipping;
    ImageView img;
    private QuickAction quickAction;
    TextView txt_name1,txt_pincode,txt_address,txt_landmrk,txt_city,txt_state,txt_phone,tv;

    public CustomSellingHistory(Context c,Model_SellingHistory  sel) {

        mContext = c;
        this.sel = sel;

   }


    @Override
    public int getCount() {
        // TODO Auto-generated method stub

       return sel.getAl_selling_history().size() ;
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

            convertView1 = inflater.inflate(R.layout.custom_selling, null);

         img=(ImageView)convertView1.findViewById(R.id.img);
        txt_name = (TextView) convertView1.findViewById(R.id.pname1);
        txt_amount= (TextView) convertView1.findViewById(R.id.amount);
        txt_buyer = (TextView) convertView1.findViewById(R.id.buyer);
        txt_date = (TextView) convertView1.findViewById(R.id.date);
       txt_tamount= (TextView) convertView1.findViewById(R.id.tamount);
       txt_qty=(TextView)convertView1.findViewById(R.id.qty);
        txt_shipping = (TextView) convertView1.findViewById(R.id.shipping);

    }
            txt_name.setText(sel.getAl_selling_history().get(position).getItem_name());
            txt_amount.setText("Price:"+" "+"$"+" "+sel.getAl_selling_history().get(position).getAmount());
            txt_buyer.setText("Buyer:"+" "+sel.getAl_selling_history().get(position).getBuyername());
            txt_date.setText("Date:"+" "+sel.getAl_selling_history().get(position).getDate());
           txt_tamount.setText("Total Amount:"+" "+sel.getAl_selling_history().get(position).getTamount());
          txt_qty.setText("Qty:"+" "+sel.getAl_selling_history().get(position).getQty());

        Picasso.with(mContext).load(sel.getAl_selling_history().get(position).getImg()).into(img);


        txt_shipping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(mContext,android.R.style.Theme_Translucent);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

                    dialog.setCancelable(true);
                    dialog.setContentView(R.layout.dialog);

                txt_name1 = (TextView) dialog.findViewById(R.id.name1);
                txt_pincode= (TextView) dialog.findViewById(R.id.pincode1);
                txt_address = (TextView) dialog.findViewById(R.id.address1);
                txt_landmrk = (TextView) dialog.findViewById(R.id.landmark1);
                txt_city= (TextView) dialog.findViewById(R.id.city1);
                txt_state= (TextView) dialog.findViewById(R.id.state1);
                txt_phone= (TextView)dialog.findViewById(R.id.phone1);
                tv= (TextView)dialog.findViewById(R.id.tv);


                txt_name1.setText(sel.getAl_selling_history().get(position).getBuyername());
                txt_pincode.setText(sel.getAl_selling_history().get(position).getPincode());
                txt_address.setText(sel.getAl_selling_history().get(position).getAddress());
                txt_landmrk.setText(sel.getAl_selling_history().get(position).getLandmark());
                txt_city.setText(sel.getAl_selling_history().get(position).getCity());
                txt_state.setText(sel.getAl_selling_history().get(position).getState());
                txt_phone.setText(sel.getAl_selling_history().get(position).getPhone());

tv.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        dialog.dismiss();
    }
});




                dialog.show();



            }
        });


        return convertView1;


    }
    @SuppressWarnings("unused")
    public void onClickTopButton(View view) {
        quickAction.show(view);
    }

    @SuppressWarnings("unused")
    public void onClickMiddleButton(View view) {
        quickAction.show(view);
    }

    @SuppressWarnings("unused")
    public void onClickBottomButton(View view) {
        quickAction.show(view);
    }




}





