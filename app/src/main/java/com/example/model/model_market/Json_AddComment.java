package com.example.model.model_market;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

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


public class Json_AddComment extends AsyncTask<String, String, String> {
     String res;
    Context context;


    public Json_AddComment(Context context) {
        // TODO Auto-generated constructor stub

        this.context = context;

    }

    @Override
    protected String doInBackground(String... params) {

        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(
                RegisterUrl.commentadd);

        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(4);

            nameValuePairs.add(new BasicNameValuePair("item_id", params[0]));
           	nameValuePairs.add(new BasicNameValuePair("user_id", params[1]));
            nameValuePairs.add(new BasicNameValuePair("type", params[2]));
             nameValuePairs.add(new BasicNameValuePair("comment", params[3]));


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

        Toast.makeText(context, "comment posted successfully", Toast.LENGTH_LONG).show();

    }

}
