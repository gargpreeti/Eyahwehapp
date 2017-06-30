package com.example.model.model_friends;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.GridView;

import com.example.adapter.adapterFriends.CustomGridEvent2;
import com.example.model.model_profile.Event;
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

public class Json_FriendsDescription2 extends AsyncTask<String, String, String> {

	Context context;
  public static Model_getFriendsDescription obj_friendsDescription;
	GridView lv;

	public Json_FriendsDescription2(Context context, GridView lv) {
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
				String follow=data.getString("follow");


				obj_friendsDescription = new Model_getFriendsDescription();
				obj_friendsDescription.setUsername(username);
				obj_friendsDescription.setUserimage(image);
				obj_friendsDescription.setFollow(follow);



				ArrayList<Event> al_event = new ArrayList<Event>();



				JSONArray ary_events = main_obj.getJSONArray("events");
				for (int i = 0; i < ary_events.length(); i++) {
					JSONObject obj = ary_events.getJSONObject(i);
					String event_id = obj.getString("id");
					String event_name = obj.getString("name");
					String event_image = obj.getString("image");
					String event_price = obj.getString("price");
					String event_like = obj.getString("like");
					String event_comment = obj.getString("comment");
					String evnet_liked=obj.getString("liked");

					Event e = new Event();
					e.setEvent_id(event_id);
					e.setEvent_name(event_name);
					e.setEvent_image(event_image);
					e.setEvent_price(Double.parseDouble(event_price));
					e.setEvent_like(Integer.parseInt(event_like));
					e.setEvent_comment(Integer.parseInt(event_comment));
					e.setEvent_liked(evnet_liked);
					al_event .add(e);

				}


				obj_friendsDescription.setAl_event_detail(al_event);

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

		lv.setAdapter(new CustomGridEvent2(context, obj_friendsDescription));


	}
}