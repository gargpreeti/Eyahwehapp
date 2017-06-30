package com.example.model.model_home;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.util.Log;

import com.example.fragment.fragmentHomeTab.FragmentHome;
import com.example.url.RegisterUrl;
import com.example.zotal102.yahwahapp.R;
import com.squareup.picasso.Picasso;

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


public class Json_ads extends AsyncTask<String, String, String> {

    ProgressDialog loading;
     Model_Ads model_ads;

    String image;
    Context context;

    String img1,img2,img3;

    public Json_ads(Context context) {
        // TODO Auto-generated constructor stub
     //   this.list =list;
        this.context = context;
    }
    @Override
    protected void onPreExecute() {

        super.onPreExecute();
        loading = new ProgressDialog(context, R.style.AppCompatAlertDialogStyle);
        loading.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        loading.show();
//        loading = new ProgressDialog(context);
//        loading.setMessage("loading");
//        loading.show();
    }
    @Override
    protected String doInBackground(String... params) {

        HostnameVerifier hostnameVerifier = org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;

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
                RegisterUrl.advert);

        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
            nameValuePairs.add(new BasicNameValuePair("type", params[0]));

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpClient.execute(httppost);

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String result = EntityUtils.toString(entity);

                JSONObject main_obj = new JSONObject(result);


                ArrayList<Ads> ad = new ArrayList<Ads>();
                model_ads = new Model_Ads();
          JSONArray ary_products =main_obj.getJSONArray("ads");

                for (int i = 0; i < ary_products.length(); i++) {
                    JSONObject obj = ary_products.getJSONObject(i);
                    String id = obj.getString("id");
                    image = obj.getString("image");
                    String adtitle = obj.getString("ad_title");
                    String desc=obj.getString("description");
                    String link=obj.getString("link");


                    Ads f = new Ads();
                    f.setId(id);
                    f.setImage(image);
                    f.setAd_title(adtitle);
                    f.setDescription(desc);
                    f.setLink(link);



                   ad.add(f);

                }


                model_ads.setAl_ads(ad);



               img1=ad.get(0).getImage();
                img2=ad.get(1).getImage();
                img3=ad.get(2).getImage();

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

        Picasso.with(context).load(img1).resize(200,160).into(FragmentHome.img_add1);
        Picasso.with(context).load(img2).resize(200, 160).into(FragmentHome.img_add2);
        Picasso.with(context).load(img3).resize(200,160).into(FragmentHome.img_add3);

    }

}
