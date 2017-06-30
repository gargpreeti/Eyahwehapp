package com.example.adapter.adapterHome;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.model.model_home.Feed;
import com.example.model.model_home.Model_Home;
import com.example.zotal102.yahwahapp.R;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeStandalonePlayer;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class CustomListAdapter extends BaseAdapter {

	private Context mContext;
	 Model_Home m;
	LayoutInflater inflater;

	public CustomListAdapter(Context c, Model_Home m) {
		mContext = c;
		this.m = m;
		inflater=LayoutInflater.from(mContext);
	}
	public void setAddList(ArrayList<Feed> list)
	{


		this.m.getAl_feed().addAll(list);
		notifyDataSetChanged();


	}
	public ArrayList<Feed> getCurrentList()
	{
		return m.getAl_feed();
	}


	@Override
	public int getCount() {

//		if(m.getAl_feed().size()==0){
//
//			FragmentHome.rel_lay.setVisibility(View.VISIBLE);
//
//		}
//   else if(m.getAl_feed().size()>0){
//
//			FragmentHome.rel_lay.setVisibility(View.INVISIBLE);
//
//		}

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
	public View getView(final int position, View convertView, ViewGroup parent) {
//		View grid;
//		LayoutInflater inflater = (LayoutInflater) mContext
//				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		final ViewHolderhome holder = new ViewHolderhome();
		convertView=inflater.inflate(R.layout.activity_customview, null);



			holder.txt_name = (TextView) convertView.findViewById(R.id.name);
			holder.img1 = (ImageView) convertView.findViewById(R.id.img2);
			holder.txt_desc = (TextView) convertView.findViewById(R.id.desc);
			holder.txt_link = (TextView) convertView.findViewById(R.id.link);
		    holder.videoView= (VideoView) convertView.findViewById(R.id.videoView1);
			holder.iv_yt_logo = (ImageView) convertView.findViewById(R.id.iv_yt_logo);

			holder.youTubeThumbnailView = (YouTubeThumbnailView) convertView.findViewById(R.id.youtube_thumbnail);




		holder.img1.setClickable(false);
		holder.txt_desc.setClickable(false);
		holder.txt_name.setClickable(false);


			holder.txt_name.setText(m.getAl_feed().get(position).getTitle());
			holder.txt_desc.setText(m.getAl_feed().get(position).getDesc());
			holder.txt_link.setText(m.getAl_feed().get(position).getLink());

			if (m.getAl_feed().get(position).getImage().isEmpty()) {
				holder.img1.setVisibility(View.GONE);
			} else {
				holder.img1.setVisibility(View.VISIBLE);
				holder.img1.setClickable(false);
				holder.iv_yt_logo.setVisibility(View.GONE);
				holder.videoView.setVisibility(View.GONE);
				holder.youTubeThumbnailView.setVisibility(View.GONE);
				Picasso.with(mContext).load(m.getAl_feed().get(position).getImage()).into(holder.img1);
			}


			if (m.getAl_feed().get(position).getVideo().isEmpty()) {
				holder.videoView.setVisibility(View.GONE);
			} else {
				holder.videoView.setVisibility(View.VISIBLE);
				holder.youTubeThumbnailView.setVisibility(View.GONE);
				holder.iv_yt_logo.setVisibility(View.GONE);
				holder.img1.setVisibility(View.GONE);
				//Creating MediaController
				MediaController mediaController = new MediaController(mContext);
				mediaController.setAnchorView(holder.videoView);

				//specify the location of media file
				Uri uri = Uri.parse(m.getAl_feed().get(position).getVideo());

				//Setting MediaController and URI, then starting the videoView
				holder.videoView.setMediaController(mediaController);
				holder.videoView.setVideoURI(uri);
				holder.videoView.requestFocus();
				holder.videoView.start();
			}

			if (m.getAl_feed().get(position).getVideourl().isEmpty()) {

//			holder.img1.setVisibility(View.VISIBLE);
				holder.youTubeThumbnailView.setVisibility(View.GONE);
				holder.iv_yt_logo.setVisibility(View.GONE);
//			videoView.setVisibility(View.GONE);

			} else {
				holder.youTubeThumbnailView.setVisibility(View.VISIBLE);
 		        holder.img1.setVisibility(View.GONE);
				holder.iv_yt_logo.setVisibility(View.VISIBLE);
		       holder.videoView.setVisibility(View.GONE);
			}


			final YouTubeThumbnailLoader.OnThumbnailLoadedListener onThumbnailLoadedListener = new YouTubeThumbnailLoader.OnThumbnailLoadedListener() {
				@Override
				public void onThumbnailError(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader.ErrorReason errorReason) {

				}

				@Override
				public void onThumbnailLoaded(YouTubeThumbnailView youTubeThumbnailView, String s) {


					if (m.getAl_feed().get(position).getVideourl().isEmpty()) {

						youTubeThumbnailView.setVisibility(View.GONE);
						holder.iv_yt_logo.setVisibility(View.GONE);

					} else {

						youTubeThumbnailView.setVisibility(View.VISIBLE);
						holder.iv_yt_logo.setVisibility(View.VISIBLE);

					}

				}
			};


			holder.youTubeThumbnailView.initialize("AIzaSyC_e22FoyeQkKV_InIrAaJaJGkL0ehBmI0", new YouTubeThumbnailView.OnInitializedListener() {
				@Override
				public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, final YouTubeThumbnailLoader youTubeThumbnailLoader) {

					youTubeThumbnailLoader.setVideo(m.getAl_feed().get(position).getVideourl());


					//    youTubeThumbnailLoader.setOnThumbnailLoadedListener(onThumbnailLoadedListener);

					youTubeThumbnailLoader.setOnThumbnailLoadedListener(new YouTubeThumbnailLoader.OnThumbnailLoadedListener() {
						@Override
						public void onThumbnailLoaded(YouTubeThumbnailView youTubeThumbnailView, String s) {
							youTubeThumbnailLoader.release();
						}

						@Override
						public void onThumbnailError(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader.ErrorReason errorReason) {

						}
					});

				}


				@Override
				public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {

				}


			});


		holder.iv_yt_logo.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {

					Intent intent = YouTubeStandalonePlayer.createVideoIntent((Activity) mContext, "AIzaSyC_e22FoyeQkKV_InIrAaJaJGkL0ehBmI0", m.getAl_feed().get(position).getVideourl());
					mContext.startActivity(intent);
				}
			});


			holder.youTubeThumbnailView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {

					Intent intent = YouTubeStandalonePlayer.createVideoIntent((Activity) mContext, "AIzaSyC_e22FoyeQkKV_InIrAaJaJGkL0ehBmI0", m.getAl_feed().get(position).getVideourl());
					mContext.startActivity(intent);
				}
			});

	//	m.getAl_feed().get(position).getLink()
			holder.txt_link.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {

					try {
						Intent internetIntent = new Intent(Intent.ACTION_VIEW,
								Uri.parse(m.getAl_feed().get(position).getLink()));
						internetIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						mContext.startActivity(internetIntent);
					}
					catch (ActivityNotFoundException e){


					}
				}
			});



		//notifyDataSetChanged();
//		if ( position % 2 == 0) {
//
//			grid.setBackgroundResource(R.drawable.shape0);
//
//		}
//		else{
//
//		grid.setBackgroundResource(R.drawable.shape0);
//
//	}

		return convertView;
	}


}

class ViewHolderhome {
	TextView txt_name,txt_desc,txt_link;
	ImageView img,img1,type_img;
	YouTubeThumbnailView youTubeThumbnailView;
	VideoView videoView;
	 ImageView iv_yt_logo;

}