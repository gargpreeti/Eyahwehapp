package com.example.model.model_message;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

import com.example.adapter.adapterMessage.CustomListMessageDesc;
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


public class Json_MsgListDescription extends AsyncTask<String, String, String> {

    Context context;
   public static   Model_MsgDesc model_msgdesc;
    ListView lv;
   public static String thrd;

    public Json_MsgListDescription(Context context, ListView lv) {
        // TODO Auto-generated constructor stub
        this.lv=lv;
        this.context = context;

    }

    @Override
    protected String doInBackground(String... params) {

        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(
                RegisterUrl.inbox_detail);

        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);

            nameValuePairs.add(new BasicNameValuePair("user_id", params[0]));
            nameValuePairs.add(new BasicNameValuePair("thread", params[1]));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String result = EntityUtils.toString(entity);

                model_msgdesc= new Model_MsgDesc();

                ArrayList<MessageDetailDesc> al_msgdetaildesc = new ArrayList<MessageDetailDesc>();

                JSONObject main_obj = new JSONObject(result);
                JSONArray ary_msg=main_obj.getJSONArray("users");
                for (int i=0;i<ary_msg.length();i++){
                    JSONObject obj = ary_msg.getJSONObject(i);
                    String sendr=obj.getString("sender");
                    String img= obj.getString("image");
                    String msg= obj.getString("message");
                    String dt= obj.getString("date");
                    thrd=obj.getString("thread");
                    String name=obj.getString("username");


                    MessageDetailDesc mg= new MessageDetailDesc();
                    mg.setSender(sendr);
                    mg.setUser_image(img);
                    mg.setMsg(msg);
                    mg.setName(name);

                    al_msgdetaildesc.add(mg);

                }
                model_msgdesc.setAl_msg_detaildesc(al_msgdetaildesc);

           Log.e("msg result----",""+result);

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

       lv.setAdapter(new CustomListMessageDesc(context, model_msgdesc));


    }

}
