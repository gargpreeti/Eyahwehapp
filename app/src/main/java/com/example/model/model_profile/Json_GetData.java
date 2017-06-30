package com.example.model.model_profile;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.util.Log;

import com.example.adapter.adapterProfile.CustomGridProduct1;
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

public class Json_GetData extends AsyncTask<String, String, String> {

	ProgressDialog loading;
	public static Model model=new Model();
	//ListView lv;

	public CustomGridProduct1 customGridProduct1;
	Context context;

	public Json_GetData(Context context,CustomGridProduct1 customGridProduct1) {
		// TODO Auto-generated constructor stub
		this.customGridProduct1 = customGridProduct1;
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
				RegisterUrl.timeline);

		try {
			// Add your data
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
			nameValuePairs.add(new BasicNameValuePair("user_id", params[0]));
			nameValuePairs.add(new BasicNameValuePair("access_token", params[1]));
			nameValuePairs.add(new BasicNameValuePair("page_number", params[2]));

			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			// Execute HTTP Post Request
			HttpResponse response = httpclient.execute(httppost);

			HttpEntity entity = response.getEntity();
			if (entity != null) {
				String result = EntityUtils.toString(entity);

				JSONObject main_obj = new JSONObject(result);
				JSONObject data = main_obj.getJSONObject("data");

				String userid = data.getString("user_id");
				String access_token = data.getString("access_token");
				String username = data.getString("username");
				String userimage = data.getString("image");
				String paypalid = data.getString("PayPal_id");

				model.setUser_id(userid);
				model.setAccesstoken(access_token);
				model.setUser_name(username);
				model.setUser_image(userimage);
				model.setPaypal_id(paypalid);

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

				model.setAl_product_detail(al_productdetail);

				Log.e("product result----",""+result);

			}

		} catch (Exception e) {
			Log.e("==+Error===", "Error product===" + e);

		}

		return null;
	}

	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		loading.dismiss();

		customGridProduct1.setAddList(model);

		}

}
