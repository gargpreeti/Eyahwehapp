package com.example.adapter.adapterProfile;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.fragment.fragmentFriendsTab.FragmentFriends;
import com.example.fragment.fragmentMessageTab.FragmentMessage;
import com.example.model.model_profile.ModelNotification;
import com.example.zotal102.yahwahapp.R;

import java.util.ArrayList;


public class CustomListNotification extends BaseAdapter {


	 private Context mContext;
	TextView txt_msg;
	RelativeLayout rel;
	ArrayList<ModelNotification> notification;
       String tp;

	public CustomListNotification(Context c,ArrayList<ModelNotification> notification) {
		mContext = c;
		this.notification = notification;

	}

	@Override
	public int getCount() {

	return notification.size();
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
			grid = inflater.inflate(R.layout.custom_notification, null);

		} else {
			grid = (View) convertView;
		}



		txt_msg= (TextView) grid.findViewById(R.id.msg);
		rel=(RelativeLayout)grid.findViewById(R.id.linearLayout1);


		txt_msg.setText(Html.fromHtml(notification.get(position).getMsg()));

		tp=notification.get(position).getType();

		rel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				Log.e("type is----",""+tp);
				if(tp.equals("message")){

					FragmentManager fragmentManager1 = ((FragmentActivity) mContext).getSupportFragmentManager();
					FragmentMessage f=
							new FragmentMessage();

					fragmentManager1
							.beginTransaction()
							.replace(R.id.activity_main_content_fragment,
									f).addToBackStack(null).commit();
				}
				else{

					FragmentManager fragmentManager1 = ((FragmentActivity) mContext).getSupportFragmentManager();
					FragmentFriends f=
							new FragmentFriends();

					fragmentManager1
							.beginTransaction()
							.replace(R.id.activity_main_content_fragment,
									f).commit();

				}

			}
		});


		return grid;
	}

}