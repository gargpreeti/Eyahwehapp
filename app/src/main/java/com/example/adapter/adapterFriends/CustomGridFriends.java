package com.example.adapter.adapterFriends;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fragment.fragmentHomeTab.FragmentHome;
import com.example.model.model_friends.FriendsDetail;
import com.example.model.model_friends.Json_FriendsDescription;
import com.example.model.model_friends.Model_Discover;
import com.example.zotal102.yahwahapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class CustomGridFriends extends BaseAdapter {

    public static String otherid;
    private Context mContext;
    public  Model_Discover md;
    TextView name;
    GridView grid1;
    ImageView imageView;

   public CustomGridFriends(Context c, Model_Discover md) {
        mContext = c;
        this.md = md;

    }

    public void setAddList(ArrayList<FriendsDetail> list)
    {
        this.md.getAl_friends_detail().addAll(list);

        notifyDataSetChanged();
    }

    public ArrayList<FriendsDetail> getCurrentList()
    {
        return md.getAl_friends_detail();
    }




    @Override
    public int getCount(){


        return md.getAl_friends_detail().size();
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
            grid = inflater.inflate(R.layout.row_grid, null);

            grid1 = (GridView) grid.findViewById(R.id.gridView1);

        } else {
            grid =convertView;
        }
        name = (TextView) grid.findViewById(R.id.name);
        imageView = (ImageView) grid.findViewById(R.id. user_image);
        name.setText(md.getAl_friends_detail().get(position).getUser_name());

        Picasso.with(mContext).load(md.getAl_friends_detail().get(position).getUser_image()).into(imageView);


           imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                otherid= md.getAl_friends_detail().get(position).getUserid();
                Log.e("otherid--",""+otherid);
                Log.e("userid--",""+FragmentHome.userid);
                Log.e("usertokn--",""+FragmentHome.usertokn);
            new Json_FriendsDescription(mContext).execute(FragmentHome.userid,FragmentHome.usertokn,otherid);


            }
        });
        return grid;
    }
}