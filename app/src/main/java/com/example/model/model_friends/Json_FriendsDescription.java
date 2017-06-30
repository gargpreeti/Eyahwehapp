package com.example.model.model_friends;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.example.fragment.fragmentFriendsTab.Fragment_discover;
import com.example.model.model_profile.ProductDetail;
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

public class Json_FriendsDescription extends AsyncTask<String, String, String> {

	Context context;
   public static Model_getFriendsDescription obj_friendsDescription;

	public static  String follow;
	public Json_FriendsDescription(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;

	}



	@Override
	protected String doInBackground(String... params) {

		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(
				RegisterUrl.viewprofile);

		try {
			// Add your data
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
			nameValuePairs.add(new BasicNameValuePair("user_id", params[0]));
			nameValuePairs.add(new BasicNameValuePair("access_token", params[1]));
			nameValuePairs.add(new BasicNameValuePair("other_user_id", params[2]));
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			// Execute HTTP Post Request
			HttpResponse response = httpclient.execute(httppost);

			HttpEntity entity = response.getEntity();
			if (entity != null) {
				String result = EntityUtils.toString(entity);

				JSONObject main_obj = new JSONObject(result);
				JSONObject data = main_obj.getJSONObject("users");
				String username=data.getString("username");
				String image=data.getString("image");
				follow=data.getString("follow");


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

					ProductDetail p = new ProductDetail();
					p.setProduct_id(product_id);
					p.setProduct_name(product_name);
					p.setProduct_image(product_image);
					p.setProduct_price(Double.parseDouble(product_price));
					p.setProduct_like(Integer.parseInt(product_like));
					p.setProduct_comment(Integer.parseInt(product_comment));

					al_productdetail.add(p);

				}

           Log.e("friends product result===",""+result);

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


	    Bundle bundle = new Bundle();
		bundle.putSerializable("friendsdata", obj_friendsDescription);

		FragmentManager fragmentManager1 = ((FragmentActivity) context).getSupportFragmentManager();
		Fragment_discover f= new Fragment_discover();
		f.setArguments(bundle);
		fragmentManager1
				.beginTransaction()
				.replace(R.id.activity_main_content_fragment,
					f).addToBackStack(null).commit();

	}
}