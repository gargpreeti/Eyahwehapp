package com.example.model.model_friends;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.GridView;

import com.example.adapter.adapterFriends.CustomGridService2;
import com.example.model.model_profile.Servicies;
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

public class Json_FriendsDescription3 extends AsyncTask<String, String, String> {

	Context context;
  public static Model_getFriendsDescription obj_friendsDescription;
	GridView lv;

	public Json_FriendsDescription3(Context context, GridView lv) {
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
				ArrayList<Servicies> al_services = new ArrayList<Servicies>();

            JSONArray ary_servicies = main_obj.getJSONArray("services");
				for (int i = 0; i < ary_servicies.length(); i++) {
					JSONObject obj = ary_servicies.getJSONObject(i);
					String service_id = obj.getString("id");
					String service_name = obj.getString("name");
					String service_image = obj.getString("image");
					String service_price = obj.getString("price");
					String service_like = obj.getString("like");
					String service_comment = obj.getString("comment");
					String service_liked=obj.getString("liked");

					Servicies s = new Servicies();
					s.setService_id(service_id);
					s.setService_name(service_name);
					s.setService_image(service_image);
					s.setService_price(Double.parseDouble(service_price));
					s.setService_like(Integer.parseInt(service_like));
					s.setService_comment(Integer.parseInt(service_comment));
					s.setService_liked(service_liked);

					al_services.add(s);

				}


			obj_friendsDescription.setAl_servicies_detail(al_services);
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

		lv.setAdapter(new CustomGridService2(context, obj_friendsDescription));


	}
}