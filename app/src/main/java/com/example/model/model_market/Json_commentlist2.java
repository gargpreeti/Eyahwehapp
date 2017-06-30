package com.example.model.model_market;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

import com.example.adapter.adapterMarket.CustomListComment2;
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
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Json_commentlist2 extends AsyncTask<String, String, String> {

    ProgressDialog loading;
    public static Model_Comment model_comment;
     ListView list;
    Context context;

    public Json_commentlist2(Context context,ListView list) {
        // TODO Auto-generated constructor stub

        this.context = context;
        this.list=list;
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
                RegisterUrl.comment_list);


        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("id", params[0]));
            nameValuePairs.add(new BasicNameValuePair("type", params[1]));

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String result = EntityUtils.toString(entity);

                JSONObject main_obj = new JSONObject(result);
               // JSONObject data = main_obj.getJSONObject("user_feeds");


                model_comment = new Model_Comment();


                ArrayList<Comment> comment = new ArrayList<Comment>();


                JSONArray ary_comment =main_obj.getJSONArray("data");

                for (int i = 0; i < ary_comment.length(); i++) {
                    JSONObject obj = ary_comment.getJSONObject(i);
                    String id = obj.getString("id");
                    String uname=obj.getString("username");
                    String cmt=obj.getString("comment");
                    String uid=obj.getString("userid");

                    Comment f = new Comment();
                    f.setId(id);
                    f.setName(uname);
                    f.setComment(cmt);
                    f.setUserid(uid);
                   comment.add(f);

                }


                model_comment.setAl_comment(comment);



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

        list.setAdapter(new CustomListComment2(context, model_comment));


    }

}
