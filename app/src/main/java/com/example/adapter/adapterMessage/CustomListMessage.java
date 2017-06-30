package com.example.adapter.adapterMessage;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fragment.fragmentMessageTab.FragmentMessage;
import com.example.model.model_message.Model_Msg;
import com.example.zotal102.yahwahapp.R;
import com.squareup.picasso.Picasso;


public class CustomListMessage extends BaseAdapter {

	public static String othername;
	private Context mContext;
	public static Model_Msg m;
	TextView txt_name, txt_msg, txt_dt;
	ImageView img;

	public CustomListMessage(Context c, Model_Msg m) {
		mContext = c;
		this.m = m;

	}

	@Override
	public int getCount() {

		if (m.getAl_msg_detail().size() == 0) {

			FragmentMessage.tv_msg.setVisibility(View.VISIBLE);

		} else if (m.getAl_msg_detail().size() > 0) {

			FragmentMessage.tv_msg.setVisibility(View.INVISIBLE);
		}
		return m.getAl_msg_detail().size();
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
	public View getView(int position, View convertView, ViewGroup parent) {

		//View grid;

		if (convertView == null) {
				LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = inflater.inflate(R.layout.custom_list_msg, parent, false);
			}

     		img = (ImageView) convertView.findViewById(R.id.user_img);
			txt_name = (TextView) convertView.findViewById(R.id.name);
			txt_dt = (TextView) convertView.findViewById(R.id.date1);
			txt_msg = (TextView) convertView.findViewById(R.id.msg);

			txt_name.setText(m.getAl_msg_detail().get(position).getOtheruser());
			txt_msg.setText(Html.fromHtml(m.getAl_msg_detail().get(position).getMsg()));

			txt_dt.setText(m.getAl_msg_detail().get(position).getMsg_date());
			othername = m.getAl_msg_detail().get(position).getOtheruser();

			Picasso.with(mContext).load(m.getAl_msg_detail().get(position).getOtheruserimage()).into(img);

		return convertView;

	}
}