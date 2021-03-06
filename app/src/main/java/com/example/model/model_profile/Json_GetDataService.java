package com.example.model.model_profile;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.util.Log;

import com.example.adapter.adapterProfile.CustomGridService1;
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

public class Json_GetDataService extends AsyncTask<String, String, String> {

	ProgressDialog loading;

	Context context;
	public static Model model=new Model();
	public CustomGridService1 customGridService1;

	public Json_GetDataService(Context context, CustomGridService1 customGridService1) {
		// TODO Auto-generated constructor stub
		this.customGridService1 =customGridService1;
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

					Servicies s = new Servicies();
					s.setService_id(service_id);
					s.setService_name(service_name);
					s.setService_image(service_image);
					s.setService_price(Double.parseDouble(service_price));
					s.setService_like(Integer.parseInt(service_like));
					s.setService_comment(Integer.parseInt(service_comment));

					al_services.add(s);

				}



				model.setAl_servicies_detail(al_services);
				Log.e("service result----",""+result);

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

		customGridService1.setAddList(model);

	}

}
