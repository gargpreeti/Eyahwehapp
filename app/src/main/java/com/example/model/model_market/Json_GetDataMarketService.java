package com.example.model.model_market;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.adapter.adapterMarket.CustomGridServiceMarket;
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


public class Json_GetDataMarketService extends AsyncTask<String, String, String> {

    //ProgressDialog loading;
    Context context;

    public static Model_Market model_market=new Model_Market();
    public CustomGridServiceMarket customGridServiceMarket;

    public Json_GetDataMarketService(Context context, CustomGridServiceMarket customGridServiceMarket) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.customGridServiceMarket=customGridServiceMarket;
    }
    @Override
    protected void onPreExecute() {


        super.onPreExecute();
       // loading = new ProgressDialog(context);
      //  loading.setMessage("loading");
       // loading.show();
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

              model_market = new Model_Market();
                model_market.setFilter_type(filtertype);
                model_market.setSearch_text(searchtext);
                model_market.setType(type);
                model_market.setLatitude(lati);
                model_market.setLongitude(longi);
           ArrayList<ServiciesMarket> al_services_market = new ArrayList<ServiciesMarket>();


             JSONArray ary_servicies_market = main_obj.getJSONArray("services");
                for (int i = 0; i < ary_servicies_market.length(); i++) {
                    JSONObject obj = ary_servicies_market.getJSONObject(i);
                    String service_id = obj.getString("id");
                    String service_name = obj.getString("name");
                    String service_image = obj.getString("image");
                    String service_price = obj.getString("price");
                    String service_like = obj.getString("like");
                    String service_comment = obj.getString("comment");
                    String serviceliked = obj.getString("liked");

                    ServiciesMarket sm = new ServiciesMarket();
                    sm.setService_id(service_id);
                    sm.setService_name(service_name);
                    sm.setService_image(service_image);
                    sm.setService_price(Double.parseDouble(service_price));
                    sm.setService_like(Integer.parseInt(service_like));
                    sm.setService_comment(Integer.parseInt(service_comment));
                    sm.setService_liked(serviceliked);

                    al_services_market.add(sm);

                }


                model_market.setAl_servicies_detail_market(al_services_market);

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
     //   loading.dismiss();
          ArrayList<ServiciesMarket> serviceDetailMarketArrayList=new ArrayList<>();
        ArrayList<ServiciesMarket> serviceDetailMarkets= customGridServiceMarket.getCurrentList();

        for (int i = 0; i <model_market.getAl_servicies_detail_market().size(); i++) {
            boolean check=true;
            for (int j = 0; j< serviceDetailMarkets.size() ; j++) {

                if (serviceDetailMarkets.get(j).getService_id().equalsIgnoreCase(model_market.getAl_servicies_detail_market().get(i).getService_id()) )
                {
                    check=false;
                }
            }
            if(check)
            {
                serviceDetailMarketArrayList.add(model_market.getAl_servicies_detail_market().get(i));
            }
        }
        customGridServiceMarket.setAddList(serviceDetailMarketArrayList);



    }

}
