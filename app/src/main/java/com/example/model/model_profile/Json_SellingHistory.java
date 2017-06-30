package com.example.model.model_profile;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

import com.example.adapter.adapterProfile.CustomSellingHistory;
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

public class Json_SellingHistory extends AsyncTask<String, String, String> {

    ProgressDialog loading;
    Context context;
    public  Model_SellingHistory model_sellingHistory;
    ListView list;

    public Json_SellingHistory(Context context, ListView list) {
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
                RegisterUrl.sellinghistory);

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

                Log.e("selling history",""+result);
                model_sellingHistory = new Model_SellingHistory();


                ArrayList<SellingHistory> sellhistory= new ArrayList<SellingHistory>();


                JSONArray ary_products =main_obj.getJSONArray("data");

                for (int i = 0; i < ary_products.length(); i++) {

                    JSONObject obj = ary_products.getJSONObject(i);
                    String itemid = obj.getString("item_id");
                    String type = obj.getString("type");
                    String uimg= obj.getString("uimage");
                    String uname=obj.getString("username");
                    String amount=obj.getString("amount");
                    String qty=obj.getString("qty");
                    String date=obj.getString("date");
                    String itemname=obj.getString("item_name");
                    String tamount=obj.getString("tamount");
                    String image=obj.getString("image");

                    String orderid=obj.getString("order_id");
                    String pincode=obj.getString("pincode");
                    String address=obj.getString("address");
                    String landmark=obj.getString("landmark");
                    String city=obj.getString("city");
                    String state=obj.getString("state");
                    String phone=obj.getString("phone");



                    SellingHistory sell = new SellingHistory();

                    sell.setItemid(Integer.parseInt(itemid));
                    sell.setItem_type(type);
                    sell.setUserimage(uimg);
                    sell.setBuyername(uname);
                    sell.setAmount(amount);
                    sell.setQty(qty);
                    sell.setDate(date);
                    sell.setItem_name(itemname);
                    sell.setTamount(tamount);
                    sell.setImg(image);
                    sell.setOrderid(orderid);
                    sell.setPincode(pincode);
                    sell.setAddress(address);
                    sell.setLandmark(landmark);
                    sell.setCity(city);
                    sell.setState(state);
                    sell.setPhone(phone);

                    sellhistory.add(sell);

                }


                model_sellingHistory.setAl_selling_history(sellhistory);




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

        list.setAdapter(new CustomSellingHistory(context,model_sellingHistory));



    }
}