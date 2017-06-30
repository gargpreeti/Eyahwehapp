package com.example.model.model_profile;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

import com.example.adapter.adapterProfile.CustomBuyerHistory;
import com.example.url.RegisterUrl;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Json_BuyerHistory extends AsyncTask<String, String, String> {

    ProgressDialog loading;
    Context context;
    public  Model_BuyerHistory model_buyerHistory;
    ListView list;

    public Json_BuyerHistory(Context context, ListView list) {
        // TODO Auto-generated constructor stub

        this.list =list;
        this.context = context;
    }
    @Override
    protected void onPreExecute() {


        super.onPreExecute();

    }
    @Override
    protected String doInBackground(String... params) {

        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(
                RegisterUrl.buyerhistory);

        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("user_id", params[0]));



            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String result = EntityUtils.toString(entity);

                JSONObject main_obj = new JSONObject(result);

                Log.e("buyer history",""+result);
                model_buyerHistory = new Model_BuyerHistory();


                ArrayList<BuyerHistory> buyhistory= new ArrayList<BuyerHistory>();


                JSONArray ary_products =main_obj.getJSONArray("data");

                for (int i = 0; i < ary_products.length(); i++) {

                    JSONObject obj = ary_products.getJSONObject(i);
                    String itemid = obj.getString("id");
                    String orderdate = obj.getString("order_date");
                    String orderamount= obj.getString("order_amount");
                    String paymenttype=obj.getString("payment_type");
                    String address=obj.getString("address");
                    String name=obj.getString("name");
                    String pincode=obj.getString("pincode");
                    String landmark=obj.getString("landmark");
                    String city=obj.getString("city");
                    String state=obj.getString("state");
                    String phonenum=obj.getString("phonenum");
                    String price=obj.getString("price");
                    String productamount=obj.getString("product_amount");
                    String productqty=obj.getString("product_qty");
                    String producttype=obj.getString("product_type");
                    String time=obj.getString("time");

                    BuyerHistory buy = new BuyerHistory();
                    buy.setItemid(itemid);
                    buy.setOrder_date(orderdate);
                    buy.setOrder_amount(orderamount);
                    buy.setPayment_type(paymenttype);
                    buy.setAddress(address);
                    buy.setName(name);
                    buy.setPincode(pincode);
                    buy.setLandmark(landmark);
                    buy.setCity(city);
                    buy.setState(state);
                    buy.setPhonenum(phonenum);
                    buy.setPrice(price);
                    buy.setProduct_amount(productamount);
                    buy.setProduct_qty(productqty);
                    buy.setProduct_type(producttype);
                    buy.setTime(time);


                    buyhistory.add(buy);

                }


                model_buyerHistory.setAl_buyer_history(buyhistory);




            }

        } catch (Exception e) {
            Log.e("==+Error===", "Error===" + e);

        }

        return null;
    }
    @Override
    protected void onPostExecute(String result) {
        // TODO Auto-generated method stub
        super.onPostExecute(result);

        list.setAdapter(new CustomBuyerHistory(context,model_buyerHistory));



    }
}