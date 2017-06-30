package com.example.model.model_home;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.util.Log;

import com.example.adapter.adapterHome.CustomListAdapter;
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


public class Json_homelistfeed extends AsyncTask<String, String, String> {

   ProgressDialog loading;

    public static Model_Home model_home = new Model_Home();
    Context context;
    public CustomListAdapter customListAdapter;

    public Json_homelistfeed(Context context, CustomListAdapter customListAdapter) {
        // TODO Auto-generated constructor stub
        this.customListAdapter = customListAdapter;
        this.context = context;

    }

    @Override
    protected void onPreExecute() {

        super.onPreExecute();

        loading = new ProgressDialog(context,R.style.AppCompatAlertDialogStyle);
        loading.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
           loading.show();

    }

    @Override
    protected String doInBackground(String... params) {

//        HostnameVerifier hostnameVerifier = org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;
//
//        DefaultHttpClient client = new DefaultHttpClient();
//       // HttpClient httpclient = new DefaultHttpClient();
//
//        SchemeRegistry registry = new SchemeRegistry();
//        SSLSocketFactory socketFactory = SSLSocketFactory.getSocketFactory();
//        socketFactory.setHostnameVerifier((X509HostnameVerifier) hostnameVerifier);
//        registry.register(new Scheme("https", socketFactory, 443));
//        SingleClientConnManager mgr = new SingleClientConnManager(client.getParams(), registry);
//        DefaultHttpClient httpClient = new DefaultHttpClient(mgr, client.getParams());
//
//// Set verifier
//        HttpsURLConnection.setDefaultHostnameVerifier(hostnameVerifier);


        HttpClient httpclient = new DefaultHttpClient();
           HttpPost httppost = new HttpPost(
                RegisterUrl.newsfeed);


            try {
                // Add your data
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
                nameValuePairs.add(new BasicNameValuePair("page_no", params[0]));
                nameValuePairs.add(new BasicNameValuePair("no_of_post", params[1]));
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                // Execute HTTP Post Request
                HttpResponse response = httpclient.execute(httppost);

                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    String result = EntityUtils.toString(entity);

                    JSONObject main_obj = new JSONObject(result);


                    model_home = new Model_Home();

                    ArrayList<Feed> feed = new ArrayList<Feed>();

                    JSONArray ary_products = main_obj.getJSONArray("data");

                    for (int i = 0; i < ary_products.length(); i++) {
                        JSONObject obj = ary_products.getJSONObject(i);
                        String id = obj.getString("id");
                        String title = obj.getString("title");
                        String image = obj.getString("image");
                        String video = obj.getString("video");
                        String videourl = obj.getString("video_url");
                        String link = obj.getString("link");
                        String desc = obj.getString("description");


                        Feed f = new Feed();
                        f.setId(id);
                        f.setTitle(title);
                        f.setImage(image);
                        f.setVideo(video);
                        f.setVideourl(videourl);
                        f.setLink(link);
                        f.setDesc(desc);


                        feed.add(f);
                        Log.e("feed data-----",""+result);

                    }


                    model_home.setAl_feed(feed);


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

           // list.setAdapter(new CustomListAdapter(context, model_home));

        ArrayList<Feed> feedArrayList=new ArrayList<>();
        ArrayList<Feed> feed= customListAdapter.getCurrentList();

        for (int i = 0; i <model_home.getAl_feed().size(); i++) {
            boolean check=true;
            for (int j = 0; j< feed.size() ; j++) {

                if (feed.get(j).getId().equalsIgnoreCase(model_home.getAl_feed().get(i).getId()) )
                {
                    check=false;
                }
            }
            if(check)
            {
                feedArrayList.add(model_home.getAl_feed().get(i));
            }
        }
        customListAdapter.setAddList(feedArrayList);

    }

    }

