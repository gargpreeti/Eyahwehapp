package com.example.model.model_profile;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.example.fragment.FragmentProfileTab.Frag_EditEvent;
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

public class Json_EventDescription extends AsyncTask<String, String, String> {

	Context context;
	Model_getEventDescription obj_eventDescription;
	public static  Frag_EditEvent frag_editEvent;
	public Json_EventDescription(Context context) {
		// TODO Auto-generated constructor stub

		this.context = context;

	}

	@Override
	protected String doInBackground(String... params) {

		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(
				RegisterUrl.event_detail);

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
				String eimage = data.getString("image");
				String eid = data.getString("id");
				String ename = data.getString("name");
				String edescription = data.getString("description");
				String etags = data.getString("tags");
				String eprice = data.getString("price");
				String elocation = data.getString("location");
				String eurl = data.getString("url");
				String esdate=data.getString("start_date");
				String eedate=data.getString("end_date");
				String estime=data.getString("start_time");
				String eetime=data.getString("end_time");
				String edate = data.getString("date");
				String estatus = data.getString("status");

				obj_eventDescription = new Model_getEventDescription();
				obj_eventDescription.setEid(Integer.parseInt(eid));
				obj_eventDescription.setEdescription(edescription);
				obj_eventDescription.setEdate(edate);
				obj_eventDescription.setEimage(eimage);
				obj_eventDescription.setElocation(elocation);
				obj_eventDescription.setEname(ename);
				obj_eventDescription.setEstdate(esdate);
				obj_eventDescription.setEenddate(eedate);
				obj_eventDescription.setEstime(estime);
				obj_eventDescription.setEendtime(eetime);
				obj_eventDescription.setEsatus(estatus);
				obj_eventDescription.setEurl(eurl);
				obj_eventDescription.setEtags(etags);
				obj_eventDescription.setEprice(Double.parseDouble(eprice));
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
		bundle.putSerializable("eventdata", obj_eventDescription);

		FragmentManager fragmentManager1 = ((FragmentActivity) context).getSupportFragmentManager();

		frag_editEvent= new Frag_EditEvent();
		frag_editEvent.setArguments(bundle);
		fragmentManager1
				.beginTransaction()
				.replace(R.id.activity_main_content_fragment,
						frag_editEvent).addToBackStack(null).commit();



	}
}