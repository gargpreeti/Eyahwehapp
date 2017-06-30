package com.example.model.model_home;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

import com.example.adapter.adapterHome.CustomListAdapter;
import com.example.url.RegisterUrl;
import com.example.zotal102.yahwahapp.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.SingleClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;


public class Json_homelistfeedbckup extends AsyncTask<String, String, String> {

  ProgressDialog loading;

    public static Model_Home model_home;
    ListView list;
    Context context;



    public Json_homelistfeedbckup(Context context, ListView list) {
        // TODO Auto-generated constructor stub
        this.list = list;
        this.context = context;

    }

    @Override
    protected void onPreExecute() {

        super.onPreExecute();

//        ProgressBar spinner = new android.widget.ProgressBar(
//                context,
//                null,
//                android.R.attr.progressBarStyle);
//
//        spinner.getIndeterminateDrawable().setColorFilter(0xFFFF0000, android.graphics.PorterDuff.Mode.MULTIPLY);
//        spinner.setVisibility(View.VISIBLE);

//        ProgressBar mSpinner = new ProgressBar(context);
//        mSpinner.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
//        mSpinner.setBackgroundResource(R.drawable.circular_progress_bar);
//        mSpinner.setIndeterminate(true);
//        mSpinner.setVisibility(View.VISIBLE);
        loading = new ProgressDialog(context,R.style.AppCompatAlertDialogStyle);
        loading.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
      //  loading = new ProgressDialog(context);
      //  loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        loading.show();
       // loading.setContentView(R.layout.progress_layout);



      //  loading.setMessage("loading");
      //  loading.show();
    }

    @Override
    protected String doInBackground(String... params) {

        HostnameVerifier hostnameVerifier = SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;

        DefaultHttpClient client = new DefaultHttpClient();
       // HttpClient httpclient = new DefaultHttpClient();

        SchemeRegistry registry = new SchemeRegistry();
        SSLSocketFactory socketFactory = SSLSocketFactory.getSocketFactory();
        socketFactory.setHostnameVerifier((X509HostnameVerifier) hostnameVerifier);
        registry.register(new Scheme("https", socketFactory, 443));
        SingleClientConnManager mgr = new SingleClientConnManager(client.getParams(), registry);
        DefaultHttpClient httpClient = new DefaultHttpClient(mgr, client.getParams());

// Set verifier
        HttpsURLConnection.setDefaultHostnameVerifier(hostnameVerifier);




        HttpPost httppost = new HttpPost(
                RegisterUrl.home_page);


        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
            nameValuePairs.add(new BasicNameValuePair("user_id", params[0]));

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpClient.execute(httppost);

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String result = EntityUtils.toString(entity);

                JSONObject main_obj = new JSONObject(result);


                model_home = new Model_Home();

                ArrayList<Feed> feed = new ArrayList<Feed>();

                JSONArray ary_products = main_obj.getJSONArray("feed");

                for (int i = 0; i < ary_products.length(); i++) {
                    JSONObject obj = ary_products.getJSONObject(i);
                    String id = obj.getString("id");
                    String image = obj.getString("image");
                    String name = obj.getString("name");
                    String uname = obj.getString("username");
                    String type = obj.getString("type");


                    Feed f = new Feed();
                    f.setId(id);
                    f.setImage(image);
                    f.setName(name);
                    f.setUsername(uname);
                    f.setType(type);

                    feed.add(f);

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

            list.setAdapter(new CustomListAdapter(context, model_home));


    }

}
