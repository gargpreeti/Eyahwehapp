package com.example.model.model_profile;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.example.fragment.FragmentProfileTab.Frag_EditProduct;
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
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Json_ProductDescription extends AsyncTask<String, String, String> {

	Context context;
	Model_getProductDescription obj_productDescription;
	public  static Frag_EditProduct frag_editProduct;

	public Json_ProductDescription(Context context) {
		// TODO Auto-generated constructor stub

		this.context = context;

	}

	@Override
	protected String doInBackground(String... params) {

		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(
				RegisterUrl.product_detail);

		try {
			// Add your data
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
			nameValuePairs.add(new BasicNameValuePair("id", params[0]));

			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			// Execute HTTP Post Request
			HttpResponse response = httpclient.execute(httppost);

			HttpEntity entity = response.getEntity();
			if (entity != null) {
				String result = EntityUtils.toString(entity);

				JSONObject main_obj = new JSONObject(result);
				JSONObject data = main_obj.getJSONObject("data");
				String pimage = data.getString("image");
				String pid = data.getString("id");
				String pname = data.getString("name");
				String pdescription = data.getString("description");
				String ptags = data.getString("tags");
				String pprice = data.getString("price");
				String plocation = data.getString("location");
				String purl = data.getString("url");
				String pdate = data.getString("date");
				String pstatus = data.getString("status");

				obj_productDescription = new Model_getProductDescription();
				obj_productDescription.setPid(Integer.parseInt(pid));
				obj_productDescription.setPdescription(pdescription);
				obj_productDescription.setPdate(pdate);
				obj_productDescription.setPimage(pimage);
				obj_productDescription.setPlocation(plocation);
				obj_productDescription.setPname(pname);
				obj_productDescription.setPsatus(pstatus);
				obj_productDescription.setPurl(purl);
				obj_productDescription.setPtags(ptags);
				obj_productDescription.setPprice(Double.parseDouble(pprice));

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
		bundle.putSerializable("productdata", obj_productDescription);

		FragmentManager fragmentManager1 = ((FragmentActivity) context).getSupportFragmentManager();
			frag_editProduct=new Frag_EditProduct();
		  frag_editProduct.setArguments(bundle);
		 fragmentManager1
				.beginTransaction()
				.replace(R.id.activity_main_content_fragment,
					frag_editProduct).commit();


	}
}