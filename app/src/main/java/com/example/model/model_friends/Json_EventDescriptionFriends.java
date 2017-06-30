package com.example.model.model_friends;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.example.fragment.fragmentFriendsTab.Fragment_friendseventdetail;
import com.example.model.model_market.Model_getEventDescriptionMarket;
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

public class Json_EventDescriptionFriends extends AsyncTask<String, String, String> {

	public static List<String> list = new ArrayList<String>();
	Context context;
	public  static Model_getEventDescriptionMarket obj_eventDescriptionmarket;

	public Json_EventDescriptionFriends(Context context) {
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
				String follow = data.getString("follow");
				String pname = data.getString("name");
				String pdescription = data.getString("description");
				String ptags = data.getString("tags");
				String pprice = data.getString("price");
				String plocation = data.getString("location");
				String purl = data.getString("url");
				String pimage = data.getString("image");
				String pdate = data.getString("date");


				String sdt=data.getString("start_date");
				String edt=data.getString("end_date");
				String stime=data.getString("start_time");
				String etime=data.getString("end_time");
				String pstatus = data.getString("status");

				list.add(username);

				obj_eventDescriptionmarket = new Model_getEventDescriptionMarket();
				obj_eventDescriptionmarket.setOwnerid(Integer.parseInt(ownerid));
				obj_eventDescriptionmarket.setPusername(username);
				obj_eventDescriptionmarket.setPuserimage(uimage);
				obj_eventDescriptionmarket.setPfollow(follow);
				obj_eventDescriptionmarket.setPname(pname);
				obj_eventDescriptionmarket.setPdesc(pdescription);
				obj_eventDescriptionmarket.setPtags(ptags);
				obj_eventDescriptionmarket.setPdate(pdate);
				obj_eventDescriptionmarket.setPimg(pimage);
				obj_eventDescriptionmarket.setPlocation(plocation);
				obj_eventDescriptionmarket.setPurl(purl);
				obj_eventDescriptionmarket.setPimg(pimage);
				obj_eventDescriptionmarket.setPstatus(pstatus);
				obj_eventDescriptionmarket.setSdate(sdt);
				obj_eventDescriptionmarket.setEdate(edt);
				obj_eventDescriptionmarket.setStime(stime);
				obj_eventDescriptionmarket.setEtime(etime);

				obj_eventDescriptionmarket.setPprice(Double.parseDouble(pprice));

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
		bundle.putSerializable("eventdatamarket", obj_eventDescriptionmarket);

		FragmentManager fragmentManager1 = ((FragmentActivity) context).getSupportFragmentManager();
		Fragment_friendseventdetail f=
				new Fragment_friendseventdetail();
		f.setArguments(bundle);

		fragmentManager1
				.beginTransaction()
				.replace(R.id.activity_main_content_fragment,
						f).addToBackStack(null).commit();


	}
}