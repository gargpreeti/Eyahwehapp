package com.example.adapter.adapterHome;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fragment.fragmentHomeTab.FragmentHome;
import com.example.model.model_home.Model_Home;
import com.example.zotal102.yahwahapp.R;
import com.squareup.picasso.Picasso;


public class CustomListAdapterbckup extends BaseAdapter {

	private Context mContext;
	 Model_Home m;
	TextView txt_name,txt_desc;
	ImageView img,img1,type_img;

	public CustomListAdapterbckup(Context c, Model_Home m) {
		mContext = c;
		this.m = m;

	}


	@Override
	public int getCount() {

		if(m.getAl_feed().size()==0){

			FragmentHome.rel_lay.setVisibility(View.VISIBLE);

		}
   else if(m.getAl_feed().size()>0){

			FragmentHome.rel_lay.setVisibility(View.INVISIBLE);

		}

			return m.getAl_feed().size();

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
			grid = inflater.inflate(R.layout.activity_customview, null);

			img = (ImageView) grid.findViewById(R.id.img);
			img1 = (ImageView) grid.findViewById(R.id.img2);
			txt_desc= (TextView) grid.findViewById(R.id.desc);
			txt_name = (TextView) grid.findViewById(R.id.name);

			type_img=(ImageView)grid.findViewById(R.id.typeimg);

			if(m.getAl_feed().get(position).getType().equals("p")){

				type_img.setImageResource(R.drawable.icon01);

			}

			if(m.getAl_feed().get(position).getType().equals("e")){

				type_img.setImageResource(R.drawable.icon03);

			}

			if(m.getAl_feed().get(position).getType().equals("s")){

				type_img.setImageResource(R.drawable.icon02);

			}
			txt_name.setText(m.getAl_feed().get(position).getUsername());
			txt_desc.setText(m.getAl_feed().get(position).getName());


			Picasso.with(mContext).load(m.getAl_feed().get(position).getImage()).into(img1);

		} else {
			grid = (View) convertView;
		}
		if ( position % 2 == 0) {

			grid.setBackgroundResource(R.drawable.shape0);

		}
		else{

		grid.setBackgroundResource(R.drawable.shape0);

	}

		return grid;
	}

}