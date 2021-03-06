package com.example.model.model_market;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

import com.example.fragment.fragmentFriendsTab.FragmentFriends;
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

public class Json_ShowCartTotal2 extends AsyncTask<String, String, String> {
    ProgressDialog loading;
    Context context;
  Model_showCart model_cart;
    ListView list;
 public static int totalcart;
    public Json_ShowCartTotal2(Context context) {
        // TODO Auto-generated constructor stub

        this.list =list;
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
                RegisterUrl.showcart);

        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("user_id", params[0]));
            nameValuePairs.add(new BasicNameValuePair("access_token", params[1]));


            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String result = EntityUtils.toString(entity);

                JSONObject main_obj = new JSONObject(result);


                model_cart = new Model_showCart();


                ArrayList<Cart> cart = new ArrayList<Cart>();



                JSONArray data=main_obj.getJSONArray("items");

                for(int i = 0; i <data.length(); i++){
                    JSONObject obj = data.getJSONObject(i);
                    String itemid = obj.getString("item_id");
                    String itemtype = obj.getString("item_type");
                    String itemquantity= obj.getString("quantity");
                    String itemprice = obj.getString("price");
                    String itemimage = obj.getString("image");
                    String itemname = obj.getString("name");

                    Cart crt= new Cart();
                    crt.setItemid(Integer.parseInt(itemid));
                    crt.setItem_type(itemtype);
                    crt.setItem_quantity(itemquantity);
                    crt.setItem_price(itemprice);
                    crt.setItem_image(itemimage);
                    crt.setItem_name(itemname);



                    cart.add(crt);

                }


                model_cart.setAl_cart(cart);

                totalcart= model_cart.getAl_cart().size();

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

       FragmentFriends.tv_cart3.setText(String.valueOf(totalcart));







    }
}