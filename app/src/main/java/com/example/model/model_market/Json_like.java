package com.example.model.model_market;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.util.Log;

import com.example.url.RegisterUrl;
import com.example.zotal102.yahwahapp.R;

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


public class Json_like extends AsyncTask<String, String, String> {
    String res;
    Context context;

    ProgressDialog loading;
    public Json_like(Context context) {
        // TODO Auto-generated constructor stub

        this.context = context;

    }
    @Override
    protected void onPreExecute() {


        super.onPreExecute();
        loading = new ProgressDialog(context, R.style.AppCompatAlertDialogStyle);
        loading.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        loading.show();
    }
    @Override
    protected String doInBackground(String... params) {

        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(
                RegisterUrl.like);

        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(4);

            nameValuePairs.add(new BasicNameValuePair("item_id", params[0]));
            nameValuePairs.add(new BasicNameValuePair("user_id", params[1]));
            nameValuePairs.add(new BasicNameValuePair("type", params[2]));
            nameValuePairs.add(new BasicNameValuePair("status", params[3]));



            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String result = EntityUtils.toString(entity);

                JSONObject main_obj = new JSONObject(result);
                res=main_obj.getString(result);
                String res1=main_obj.getString("like");

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
        loading.dismiss();


    }

}
