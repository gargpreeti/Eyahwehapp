package com.example.model.model_profile;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.util.Log;

import com.example.adapter.adapterProfile.CustomGridEvent1;
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

public class Json_GetDataEvent extends AsyncTask<String, String, String> {

	ProgressDialog loading;
	Context context;
	public static Model model=new Model();
	public CustomGridEvent1 customGridEvent1;


	public Json_GetDataEvent(Context context, CustomGridEvent1 customGridEvent1) {
		// TODO Auto-generated constructor stub
		this.customGridEvent1 = customGridEvent1;
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
					model.setUser_id(userid);
				model.setAccesstoken(access_token);
				model.setUser_name(username);
				model.setUser_image(userimage);
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

					Event e = new Event();
					e.setEvent_id(event_id);
					e.setEvent_name(event_name);
					e.setEvent_image(event_image);
					e.setEvent_price(Double.parseDouble(event_price));
					e.setEvent_like(Integer.parseInt(event_like));
					e.setEvent_comment(Integer.parseInt(event_comment));
					al_event .add(e);

				}


				model.setAl_event_detail(al_event);
				Log.e("event result----",""+result);

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
		customGridEvent1.setAddList(model);

	}

}
