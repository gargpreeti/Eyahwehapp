package com.example.model.model_profile;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

import com.example.adapter.adapterProfile.CustomListNotification;
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

public class Json_Notificationlist extends AsyncTask<String, String, String> {

	ProgressDialog loading;
	ListView list;
	Context context;
	public ArrayList<ModelNotification> al_notification= new ArrayList<ModelNotification>();

	public Json_Notificationlist(Context context,  ListView list) {
		// TODO Auto-generated constructor stub

		this.context = context;
		this.list=list;
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
				RegisterUrl.notificationlist);

		try {
			// Add your data
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("user_id", params[0]));

			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			// Execute HTTP Post Request
			HttpResponse response = httpclient.execute(httppost);

			HttpEntity entity = response.getEntity();
			if (entity != null) {
				String result = EntityUtils.toString(entity);

				Log.e("notication result----",""+result);

				JSONObject main_obj = new JSONObject(result);
				JSONArray ary_msg=main_obj.getJSONArray("data");

				for (int i=0;i<ary_msg.length();i++){
					JSONObject obj = ary_msg.getJSONObject(i);
					String msg= obj.getString("message");
					String type= obj.getString("type");


					ModelNotification f = new ModelNotification();

					f.setMsg(msg);
					f.setType(type);


					al_notification.add(f);
					Log.e("notication result----",""+result);

				}

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

		//customGridProduct1.setAddList(model);
		list.setAdapter(new CustomListNotification(context,al_notification));
		}

}
