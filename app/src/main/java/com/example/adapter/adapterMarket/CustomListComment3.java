package com.example.adapter.adapterMarket;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.com.example.zoptal102.Register;
import com.example.fragment.fragmentHomeTab.FragmentHome;
import com.example.fragment.fragmentMarketTab.NewComment3;
import com.example.model.model_market.Json_commentlist3;
import com.example.model.model_market.Model_Comment;
import com.example.url.RegisterUrl;
import com.example.zotal102.yahwahapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


public class CustomListComment3 extends BaseAdapter {

	private Context mContext;
	Model_Comment m;
	TextView txt_name,txt_desc;
	public  static Button btn_cmt;
	String result2;

	public CustomListComment3(Context c, Model_Comment m) {

		mContext = c;
		this.m = m;

	}


	@Override
	public int getCount() {

		return m.getAl_comment().size();

	}


	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View grid;
		LayoutInflater inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		if (convertView == null) {

			grid = new View(mContext);
			grid = inflater.inflate(R.layout.fragment_commentlist, null);


		} else {
			grid = (View) convertView;
		}


		txt_desc= (TextView) grid.findViewById(R.id.desc);
		txt_name = (TextView) grid.findViewById(R.id.name);
		btn_cmt=(Button)grid.findViewById(R.id.dlt);

		txt_name.setText(m.getAl_comment().get(position).getName());
		txt_desc.setText(m.getAl_comment().get(position).getComment());

try {
	if (m.getAl_comment().get(position).getUserid().equals(FragmentHome.userid)) {
		btn_cmt.setVisibility(View.VISIBLE);

	} else {


		btn_cmt.setVisibility(View.INVISIBLE);
	}
}
  catch (IndexOutOfBoundsException e){}
		btn_cmt.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

	           		removeItemFromList(position);
					notifyDataSetChanged();

			}
		});

		if ( position % 2 == 0) {

			grid.setBackgroundResource(R.drawable.shape0);

		}
		else{

			grid.setBackgroundResource(R.drawable.shape0);

		}

		return grid;
	}

	protected void removeItemFromList(final int position) {

		AlertDialog.Builder alert = new AlertDialog.Builder(
				mContext);

		alert.setTitle("Delete");
		alert.setMessage("Do you want delete this item?");
		alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {

				try {
					commentdlt(Json_commentlist3.model_comment.getAl_comment().remove(position).getId());
				}
				catch (Exception e){}

			}
		});
		alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});

		alert.show();

	}




	private void commentdlt(String cid) {


		class Commentdlt extends AsyncTask<String, Void, String> {
			ProgressDialog loading;
			Register ruc = new Register();


			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				loading = new ProgressDialog(mContext);
				loading.setMessage("loading");
				loading.show();
			}

			@Override
			protected void onPostExecute(String s) {
				super.onPostExecute(s);
				loading.dismiss();
				if(result2.equals("true")) {
					Toast.makeText(mContext, "Comment deleted successfully", Toast.LENGTH_LONG).show();

					new Json_commentlist3(mContext,NewComment3.list).execute(CustomGridServiceMarket.sid, "s");

					String val="-1";
					int value=Integer.parseInt(val);
					int tv=Integer.parseInt(String.valueOf(NewComment3.tv_comment.getText()));
					value=value+tv;

					NewComment3.tv_comment.setText(String.valueOf(value));


				}


			}

			@Override
			protected String doInBackground(String... params) {

				HashMap<String, String> data = new HashMap<String, String>();


				data.put("id", params[0]);

				String result1 = ruc.sendPostRequest(RegisterUrl.dlt_comment,data);


				JSONObject jsonObject = null;
				try {
					jsonObject = new JSONObject(result1);



				} catch (JSONException e) {
					e.printStackTrace();
				}

				try {
					result2 = jsonObject.optString("result");
				} catch (Exception e) {
					e.printStackTrace();
				}
		return result2;
			}
		}

		Commentdlt ru = new Commentdlt();
		ru.execute(cid);

	}


}