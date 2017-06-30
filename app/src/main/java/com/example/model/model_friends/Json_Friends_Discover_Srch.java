package com.example.model.model_friends;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.GridView;

import com.example.adapter.adapterFriends.CustomGridFriends;
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

public class Json_Friends_Discover_Srch extends AsyncTask<String, String, String> {

	ProgressDialog loading;

	public static Model_Discover model_discover;
	GridView grid;
	public static List<String> list1 = new ArrayList<String>();
	Context context;
	String user_name;

	String result;
	public Json_Friends_Discover_Srch(Context context, GridView grid) {
		// TODO Auto-generated constructor stub
		this.grid = grid;
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
				RegisterUrl.friends_discover);

		try {
			// Add your data
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("user_id", params[0]));
			nameValuePairs.add(new BasicNameValuePair("search", params[1]));
			nameValuePairs.add(new BasicNameValuePair("filter_type", params[2]));
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			// Execute HTTP Post Request
			HttpResponse response = httpclient.execute(httppost);

			HttpEntity entity = response.getEntity();
			if (entity != null) {
		    result= EntityUtils.toString(entity);

				JSONObject main_obj = new JSONObject(result);


				model_discover=new Model_Discover();
				ArrayList<FriendsDetail> al_friendsdetail = new ArrayList<FriendsDetail>();

				JSONArray ary_products = main_obj.getJSONArray("users");
				for (int i = 0; i < ary_products.length(); i++) {
					JSONObject obj = ary_products.getJSONObject(i);
					String userid = obj.getString("id");
			     	user_name = obj.getString("username");
					String user_image = obj.getString("image");

					list1.add(user_name);
					FriendsDetail frnds = new FriendsDetail();
					frnds.setUser_id(userid);
					frnds.setUser_name(user_name);
					frnds.setUser_image(user_image);


					al_friendsdetail.add(frnds);

				}


				model_discover.setAl_friends_detail(al_friendsdetail);



			}

		} catch (Exception e) {
			Log.e("==+Error===", "Error===" + e);

		}

		return result;
	}

	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		loading.dismiss();


		grid.setAdapter(new CustomGridFriends(context, model_discover));

	}

}
