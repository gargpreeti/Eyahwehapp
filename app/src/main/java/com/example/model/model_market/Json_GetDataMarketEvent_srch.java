package com.example.model.model_market;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

import com.example.adapter.adapterMarket.CustomGridEventMarket;
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


public class Json_GetDataMarketEvent_srch extends AsyncTask<String, String, String> {

    ProgressDialog loading;
    public static Model_Market model_market;
  ListView gridView;

    Context context;


    public CustomGridEventMarket customGridEventMarket;


    public Json_GetDataMarketEvent_srch(Context context, ListView gridView) {
        // TODO Auto-generated constructor stub
         this.context = context;
        this.gridView=gridView;
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
                RegisterUrl.search_item);


        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(7);
            nameValuePairs.add(new BasicNameValuePair("filter_type", params[0]));
            nameValuePairs.add(new BasicNameValuePair("search_text", params[1]));
            nameValuePairs.add(new BasicNameValuePair("type", params[2]));
            nameValuePairs.add(new BasicNameValuePair("latitude", params[3]));
            nameValuePairs.add(new BasicNameValuePair("longitude", params[4]));
            nameValuePairs.add(new BasicNameValuePair("user_id", params[5]));
            nameValuePairs.add(new BasicNameValuePair("page_number", params[6]));

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String result = EntityUtils.toString(entity);

                JSONObject main_obj = new JSONObject(result);
                JSONObject data = main_obj.getJSONObject("data");

                String filtertype = data.getString("filter_type");
                String searchtext = data.getString("search_text");
                String type = data.getString("type");
                String lati = data.getString("latitude");
                String longi = data.getString("longitude");

                model_market=new Model_Market();


                model_market.setFilter_type(filtertype);
                model_market.setSearch_text(searchtext);
                model_market.setType(type);
                model_market.setLatitude(lati);
                model_market.setLongitude(longi);

        ArrayList<EventMarket> al_event_market = new ArrayList<EventMarket>();

        JSONArray ary_events = main_obj.getJSONArray("events");
                for (int i = 0; i < ary_events.length(); i++) {
                    JSONObject obj = ary_events.getJSONObject(i);
                    String event_id = obj.getString("id");
                    String event_name = obj.getString("name");
                    String event_image = obj.getString("image");
                    String event_price = obj.getString("price");
                    String event_like = obj.getString("like");
                    String event_comment = obj.getString("comment");
                    String liked=obj.getString("liked");

                    EventMarket e = new EventMarket();
                    e.setEvent_id(event_id);
                    e.setEvent_name(event_name);
                    e.setEvent_image(event_image);
                    e.setEvent_price(Double.parseDouble(event_price));
                    e.setEvent_like(Integer.parseInt(event_like));
                    e.setEvent_comment(Integer.parseInt(event_comment));
                    e.setEvent_liked(liked);
                    al_event_market .add(e);

                }
             model_market.setAl_event_detail(al_event_market);

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
        gridView.setAdapter(new CustomGridEventMarket(context, model_market));
    }

}
