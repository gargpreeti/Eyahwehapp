package com.example.model.model_market;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.adapter.adapterMarket.CustomGridProductMarket;
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


public class Json_GetDataMarket extends AsyncTask<String, String, String> {

 //   ProgressDialog loading;
    public static  Model_Market model_market=new Model_Market();
    public CustomGridProductMarket customGridProductMarket;
   Context context;

    public Json_GetDataMarket(Context context,CustomGridProductMarket customGridProductMarket) {
        // TODO Auto-generated constructor stub
        this.customGridProductMarket = customGridProductMarket;
        this.context = context;
    }
    @Override
    protected void onPreExecute() {


        super.onPreExecute();
       // loading = new ProgressDialog(context);
      //  loading.setMessage("loading");
      //loading.show();
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


                ArrayList<ProductDetailMarket> al_productdetail_market = new ArrayList<ProductDetailMarket>();

                JSONArray ary_products_market = main_obj.getJSONArray("products");

                for (int i = 0; i <ary_products_market.length(); i++) {
                    JSONObject obj = ary_products_market.getJSONObject(i);
                    String product_id = obj.getString("id");
                    String product_name = obj.getString("name");
                    String product_image = obj.getString("image");
                    String product_price = obj.getString("price");
                    String product_like = obj.getString("like");
                    String product_comment = obj.getString("comment");
                    String product_liked=obj.getString("liked");

                    ProductDetailMarket pm = new ProductDetailMarket();
                    pm.setProduct_id(product_id);
                    pm.setProduct_name(product_name);
                    pm.setProduct_image(product_image);
                    pm.setProduct_price(Double.parseDouble(product_price));
                    pm.setProduct_like(Integer.parseInt(product_like));
                    pm.setProduct_comment(Integer.parseInt(product_comment));
                    pm.setProduct_liked(product_liked);

                    al_productdetail_market.add(pm);

                }
                     model_market.setAl_product_detail_market(al_productdetail_market);

Log.e("mrkt product-----",""+result);
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


        ArrayList<ProductDetailMarket> productDetailMarketArrayList=new ArrayList<>();
        ArrayList<ProductDetailMarket> productDetailMarkets= customGridProductMarket.getCurrentList();

        for (int i = 0; i <model_market.getAl_product_detail_market().size(); i++) {
            boolean check=true;
            for (int j = 0; j< productDetailMarkets.size() ; j++) {

                if (productDetailMarkets.get(j).getProduct_id().equalsIgnoreCase(model_market.getAl_product_detail_market().get(i).getProduct_id()) )
                {
                    check=false;
                }
            }
            if(check)
            {
                productDetailMarketArrayList.add(model_market.getAl_product_detail_market().get(i));
            }
        }
        customGridProductMarket.setAddList(productDetailMarketArrayList);



    }

}
