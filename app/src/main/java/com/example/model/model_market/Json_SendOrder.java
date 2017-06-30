package com.example.model.model_market;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

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
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Json_SendOrder extends AsyncTask<String, String, String> {

    String res;
    Context context;


    public Json_SendOrder(Context context) {
        // TODO Auto-generated constructor stub

        this.context = context;

    }

    @Override
    protected String doInBackground(String... params) {

        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(
                RegisterUrl.saveorder);

        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

            nameValuePairs.add(new BasicNameValuePair("user_id", params[0]));
            nameValuePairs.add(new BasicNameValuePair("access_token", params[1]));
            nameValuePairs.add(new BasicNameValuePair("order_amt", params[2]));
            nameValuePairs.add(new BasicNameValuePair("order_qty", params[3]));
            nameValuePairs.add(new BasicNameValuePair("payment_type", params[4]));
            nameValuePairs.add(new BasicNameValuePair("product_id", params[5]));
            nameValuePairs.add(new BasicNameValuePair("product_amount", params[6]));
            nameValuePairs.add(new BasicNameValuePair("product_qty", params[7]));
            nameValuePairs.add(new BasicNameValuePair("product_type", params[8]));
            nameValuePairs.add(new BasicNameValuePair("name", params[9]));
            nameValuePairs.add(new BasicNameValuePair("pincode", params[10]));
            nameValuePairs.add(new BasicNameValuePair("address", params[11]));
            nameValuePairs.add(new BasicNameValuePair("landmark", params[12]));
            nameValuePairs.add(new BasicNameValuePair("city", params[13]));
            nameValuePairs.add(new BasicNameValuePair("state", params[14]));
            nameValuePairs.add(new BasicNameValuePair("phonenum", params[15]));
            nameValuePairs.add(new BasicNameValuePair("seller_id", params[16]));



            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String result = EntityUtils.toString(entity);

                JSONObject main_obj = new JSONObject(result);
                res=main_obj.getString(result);
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



    }

}
