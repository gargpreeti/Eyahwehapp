package com.example.model.model_profile;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.example.fragment.FragmentProfileTab.Frag_EditService;
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

public class Json_ServiceDescription extends AsyncTask<String, String, String> {

	Context context;
	Model_getServiceDescription obj_serviceDescription;

	public static Frag_EditService frag_editService;

	public Json_ServiceDescription(Context context) {
		// TODO Auto-generated constructor stub

		this.context = context;

	}

	@Override
	protected String doInBackground(String... params) {

		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(
				RegisterUrl.service_detail);

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

				obj_serviceDescription = new Model_getServiceDescription();
				obj_serviceDescription.setPid(Integer.parseInt(pid));
				obj_serviceDescription.setPdescription(pdescription);
				obj_serviceDescription.setPdate(pdate);
				obj_serviceDescription.setPimage(pimage);
				obj_serviceDescription.setPlocation(plocation);
				obj_serviceDescription.setPname(pname);
				obj_serviceDescription.setPsatus(pstatus);
				obj_serviceDescription.setPurl(purl);
				obj_serviceDescription.setPtags(ptags);
				obj_serviceDescription.setPprice(Double.parseDouble(pprice));


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
		bundle.putSerializable("servicedata", obj_serviceDescription);

		FragmentManager fragmentManager1 = ((FragmentActivity) context).getSupportFragmentManager();
		frag_editService=new Frag_EditService();
		frag_editService.setArguments(bundle);
		fragmentManager1
				.beginTransaction()
				.replace(R.id.activity_main_content_fragment,
						frag_editService).addToBackStack(null).commit();




	}
}