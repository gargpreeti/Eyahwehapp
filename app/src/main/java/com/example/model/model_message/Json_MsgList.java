package com.example.model.model_message;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

import com.example.adapter.adapterMessage.CustomListMessage;
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


public class Json_MsgList extends AsyncTask<String, String, String> {
    ProgressDialog loading;
    Context context;
    ListView lv;
    public static   Model_Msg model_msg;
    String thrd;

    public Json_MsgList(Context context, ListView lv) {
        // TODO Auto-generated constructor stub
           this.lv=lv;
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
                RegisterUrl.inbox);

        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);

            nameValuePairs.add(new BasicNameValuePair("user_id", params[0]));




            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String result = EntityUtils.toString(entity);

                model_msg= new Model_Msg();
                ArrayList<MessageDetail> al_msgdetail = new ArrayList<MessageDetail>();
                // JSONObject cid = main_obj.getJSONObject();
               JSONObject main_obj = new JSONObject(result);
                JSONArray ary_msg=main_obj.getJSONArray("users");

                for (int i=0;i<ary_msg.length();i++){
                    JSONObject obj = ary_msg.getJSONObject(i);
                    String sendr=obj.getString("sender");
                    String img= obj.getString("image");
                    String msg= obj.getString("message");
                    String dt= obj.getString("date");
                     thrd =obj.getString("thread");
                    String name=obj.getString("username");
                    String other=obj.getString("otheruser");
                    String otherimage=obj.getString("otheruserimage");
                    String othrid=obj.getString("otheruser_id");

                    MessageDetail mg= new MessageDetail();
                    mg.setSender(sendr);
                    mg.setUser_image(img);
                    mg.setMsg(msg);
                    mg.setMsg_date(dt);
                    mg.setMsg_thread(thrd);
                    mg.setName(name);
                    mg.setOtheruser(other);
                    mg.setOtheruserimage(otherimage);
                    mg.setOtheruserid(othrid);

                    al_msgdetail.add(mg);

                }


                     model_msg.setAl_msg_detail(al_msgdetail);

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

           lv.setAdapter(new CustomListMessage(context, model_msg));



    }

}
