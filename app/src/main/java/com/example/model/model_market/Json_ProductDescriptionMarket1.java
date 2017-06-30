package com.example.model.model_market;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.example.fragment.fragmentMarketTab.Fragment_marketproductdeatil1;
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

public class Json_ProductDescriptionMarket1 extends AsyncTask<String, String, String> {

	public static List<String> list = new ArrayList<String>();
	Context context;
	public  static Model_getProductDescriptionMarket obj_productDescriptionmarket;
	public static String follow;
	public Json_ProductDescriptionMarket1(Context context) {
		// TODO Auto-generated constructor stub

		this.context = context;

	}

	@Override
	protected String doInBackground(String... params) {

		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(
				RegisterUrl.search_item_detail);

		try {
			// Add your data
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);

			nameValuePairs.add(new BasicNameValuePair("id", params[0]));
			nameValuePairs.add(new BasicNameValuePair("user_id", params[1]));
		    nameValuePairs.add(new BasicNameValuePair("type", params[2]));

			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			// Execute HTTP Post Request
			HttpResponse response = httpclient.execute(httppost);

			HttpEntity entity = response.getEntity();
			if (entity != null) {
				String result = EntityUtils.toString(entity);

				JSONObject main_obj = new JSONObject(result);
				JSONObject data = main_obj.getJSONObject("data");
				String ownerid=data.getString("owner_id");
				String username=data.getString("username");
				String uimage = data.getString("user_image");
				 follow = data.getString("follow");
				String pname = data.getString("name");
				String pdescription = data.getString("description");
				String ptags = data.getString("tags");
				String pprice = data.getString("price");
				String plocation = data.getString("location");
				String purl = data.getString("url");
				String pimage = data.getString("image");
				//String pid = data.getString("id");

				String pdate = data.getString("date");
				String pstatus = data.getString("status");

				list.add(username);
                 Log.e("============followww============",""+follow);

				obj_productDescriptionmarket = new Model_getProductDescriptionMarket();

				obj_productDescriptionmarket.setOwnerid(Integer.parseInt(ownerid));
				obj_productDescriptionmarket.setPusername(username);
				obj_productDescriptionmarket.setPuserimage(uimage);
				obj_productDescriptionmarket.setPfollow(follow);
				obj_productDescriptionmarket.setPname(pname);
				obj_productDescriptionmarket.setPdesc(pdescription);
				obj_productDescriptionmarket.setPtags(ptags);
				obj_productDescriptionmarket.setPdate(pdate);
				obj_productDescriptionmarket.setPimg(pimage);
				obj_productDescriptionmarket.setPlocation(plocation);
				obj_productDescriptionmarket.setPurl(purl);
				obj_productDescriptionmarket.setPimg(pimage);
				obj_productDescriptionmarket.setPstatus(pstatus);

				obj_productDescriptionmarket.setPprice(Double.parseDouble(pprice));


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
		bundle.putSerializable("productdatamarket", obj_productDescriptionmarket);

		FragmentManager fragmentManager1 = ((FragmentActivity) context).getSupportFragmentManager();

		Fragment_marketproductdeatil1 f=new Fragment_marketproductdeatil1();
		f.setArguments(bundle);

		fragmentManager1
				.beginTransaction()
				.replace(R.id.activity_main_content_fragment,
						f).commit();



	}
}