package com.example.model.model_friends;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

import com.example.adapter.adapterFriends.CustomGridProduct2;
import com.example.model.model_profile.ProductDetail;
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

public class Json_FriendsDescription1 extends AsyncTask<String, String, String> {

	Context context;
   public static Model_getFriendsDescription obj_friendsDescription;
	ListView lv;

	public Json_FriendsDescription1(Context context, ListView lv) {
		// TODO Auto-generated constructor stub
		this.lv = lv;
		this.context = context;

	}


	@Override
	protected String doInBackground(String... params) {

		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(
				RegisterUrl.viewprofile);

		try {
			// Add your data
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("user_id", params[0]));
			nameValuePairs.add(new BasicNameValuePair("access_token", params[1]));
			nameValuePairs.add(new BasicNameValuePair("other_user_id", params[2]));
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			// Execute HTTP Post Request
			HttpResponse response = httpclient.execute(httppost);

			HttpEntity entity = response.getEntity();
			if (entity != null) {
				String result = EntityUtils.toString(entity);

				Log.e("resultttttt friends",""+result);

				JSONObject main_obj = new JSONObject(result);
				JSONObject data = main_obj.getJSONObject("users");
				String username=data.getString("username");
				String image=data.getString("image");
				String follow=data.getString("follow");




				obj_friendsDescription = new Model_getFriendsDescription();
				obj_friendsDescription.setUsername(username);
				obj_friendsDescription.setUserimage(image);
				obj_friendsDescription.setFollow(follow);


				ArrayList<ProductDetail> al_productdetail = new ArrayList<ProductDetail>();

				JSONArray ary_products = main_obj.getJSONArray("products");
				for (int i = 0; i < ary_products.length(); i++) {
					JSONObject obj = ary_products.getJSONObject(i);
					String product_id = obj.getString("id");
					String product_name = obj.getString("name");
					String product_image = obj.getString("image");
					String product_price = obj.getString("price");
					String product_like = obj.getString("like");
					String product_comment = obj.getString("comment");
					String product_liked=obj.getString("liked");

					ProductDetail p = new ProductDetail();
					p.setProduct_id(product_id);
					p.setProduct_name(product_name);
					p.setProduct_image(product_image);
					p.setProduct_price(Double.parseDouble(product_price));
					p.setProduct_like(Integer.parseInt(product_like));
					p.setProduct_comment(Integer.parseInt(product_comment));
					p.setProduct_liked(product_liked);

					al_productdetail.add(p);

				}
				obj_friendsDescription.setAl_product_detail(al_productdetail);
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

		lv.setAdapter(new CustomGridProduct2(context, obj_friendsDescription));


	}
}