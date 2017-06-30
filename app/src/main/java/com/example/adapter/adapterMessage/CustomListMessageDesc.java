package com.example.adapter.adapterMessage;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fragment.fragmentHomeTab.FragmentHome;
import com.example.model.model_message.Model_MsgDesc;
import com.example.zotal102.yahwahapp.R;


public class CustomListMessageDesc extends BaseAdapter {

	private Context mContext;
	public static  Model_MsgDesc m;
	TextView txt_msg,txt_msgdesc;
	ImageView img;
	private int activeHex;


	public CustomListMessageDesc(Context c, Model_MsgDesc m) {
		mContext = c;
		this.m = m;

	}


	@Override
	public int getCount() {
		return m.getAl_msg_detaildesc().size();
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
		View grid;
		LayoutInflater inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		if (convertView == null) {

			grid = new View(mContext);


			if(m.getAl_msg_detaildesc().get(position).getSender().equals(FragmentHome.userid))
			  grid = inflater.inflate(R.layout.custom_msg_desc_right, null);
				else
				grid = inflater.inflate(R.layout.custom_msg_desc, null);

		} else {
			grid = (View) convertView;
		}

		img = (ImageView) grid.findViewById(R.id.user_img);
		txt_msgdesc= (TextView) grid.findViewById(R.id.msg1);
		txt_msg= (TextView) grid.findViewById(R.id.msg);

		txt_msgdesc.setText(m.getAl_msg_detaildesc().get(position).getMsg());
		activeHex = Color.parseColor("#FBF9F9");
		if ( position % 2 == 0) {

			   grid.setBackgroundColor(activeHex);

		}
		else{
			         grid.setBackgroundColor(activeHex);


		}



		return grid;
	}

}