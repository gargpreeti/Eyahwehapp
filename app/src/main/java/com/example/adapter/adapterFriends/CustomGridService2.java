package com.example.adapter.adapterFriends;


import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fragment.fragmentFriendsTab.FragmentFriends;
import com.example.fragment.fragmentFriendsTab.Fragment_discover;
import com.example.fragment.fragmentFriendsTab.NewComment_FS;
import com.example.fragment.fragmentFriendsTab.ServiceFragment1;
import com.example.fragment.fragmentHomeTab.FragmentHome;
import com.example.model.model_friends.Json_FriendsDescription3;
import com.example.model.model_friends.Json_ServiceDescriptionFriends;
import com.example.model.model_friends.Model_getFriendsDescription;
import com.example.model.model_market.Json_AddCart;
import com.example.model.model_market.Json_like;
import com.example.zotal102.yahwahapp.R;
import com.squareup.picasso.Picasso;


public class CustomGridService2 extends BaseAdapter {

    private Context mContext;
    Model_getFriendsDescription m;
    public static int value=0;
    int checkBoxCounter = 0;
    public static String like_value;
    public static String sid,s_img;
    public static String totl_like,totl_comment;


    public CustomGridService2(Context c, Model_getFriendsDescription m) {
        mContext = c;
        this.m=m;

    }


    @Override
    public int getCount() {


        if(m.getAl_servicies_detail().size()==0){

            ServiceFragment1.tv_msg.setVisibility(View.VISIBLE);
        }

        else if(m.getAl_servicies_detail().size()>0){

            ServiceFragment1.tv_msg.setVisibility(View.INVISIBLE);

        }
        return m.getAl_servicies_detail().size();
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

        final ViewHolder111 holder=new ViewHolder111();
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {

            grid = new View(mContext);
            grid = inflater.inflate(R.layout.service_grid2, null);
            holder.ch=(CheckBox)grid.findViewById(R.id.check1);
            holder.name2 = (TextView) grid.findViewById(R.id.img_name);
            holder.tv_view = (ImageView)grid.findViewById(R.id.view_txt);
            holder.tv_like1= (TextView) grid.findViewById(R.id.like_txt1);
            holder.tv_comment1 = (TextView) grid.findViewById(R.id.comment_txt);
            holder.imageView = (ImageView)grid.findViewById(R.id.img);
            holder.img_comment = (ImageView)grid.findViewById(R.id.comment);
            holder.img_cart= (ImageView)grid.findViewById(R.id.cart);

            holder.name2.setText(m.getAl_servicies_detail().get(position).getService_name());
            holder.tv_like1.setText(String.valueOf(m.getAl_servicies_detail().get(position).getService_like()));
            holder.tv_comment1.setText(String.valueOf(m.getAl_servicies_detail().get(position).getService_comment()));


            Picasso.with(mContext).load(m.getAl_servicies_detail().get(position).getService_image()).resize(1000, 600).into(holder.imageView);

            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    sid = Json_FriendsDescription3.obj_friendsDescription.getAl_servicies_detail().get(position).getService_id();
                    new Json_ServiceDescriptionFriends(mContext).execute(sid, FragmentHome.userid, "s");

                }
            });


            holder.tv_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                     sid = Json_FriendsDescription3.obj_friendsDescription.getAl_servicies_detail().get(position).getService_id();
                    new Json_ServiceDescriptionFriends(mContext).execute(sid, FragmentHome.userid, "s");


                }
            });

            holder.img_cart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    String sid = Json_FriendsDescription3.obj_friendsDescription.getAl_servicies_detail().get(position).getService_id();

                    new Json_AddCart(mContext).execute(FragmentHome.userid,FragmentHome.usertokn,sid,"s","1");

                    String val="1";
                    value=Integer.parseInt(val);

                    int tv = Integer.parseInt(String.valueOf(Fragment_discover.tv_cart4.getText()));

                    value=value+tv;



                    FragmentFriends.tv_cart3.setText(String.valueOf(value));
                    FragmentHome.tv_cart2.setText(String.valueOf(value));
                    Fragment_discover.tv_cart4.setText(String.valueOf(value));


                }
            });


            holder.ch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton arg0, boolean isChecked) {

                    if (isChecked) {

                        if(Json_FriendsDescription3.obj_friendsDescription.al_servicies_detail.get(position).getService_liked().equals("0")) {

                            checkBoxCounter++;
                            String val = "1";
                            int value = Integer.parseInt(val);
                            value = value + m.getAl_servicies_detail().get(position).getService_like();
                            holder.tv_like1.setText(String.valueOf(value));

                                   String type = "s";
                            String sid = Json_FriendsDescription3.obj_friendsDescription.getAl_servicies_detail().get(position).getService_id();
                            new Json_like(mContext).execute(sid, FragmentHome.userid, type, "1");
                        }
                    } else {


                        checkBoxCounter--;
                        String st = "0";
                        String val = "-1";
                        int value = Integer.parseInt(val);
                        value = value + Integer.parseInt(String.valueOf(holder.tv_like1.getText()));
                        holder.tv_like1.setText(String.valueOf(value));


                        String type = "s";
                        String sid = Json_FriendsDescription3.obj_friendsDescription.getAl_servicies_detail().get(position).getService_id();
                        new Json_like(mContext).execute(sid, FragmentHome.userid, type, st);

                    }

                }

            });


            holder.img_comment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    s_img = Json_FriendsDescription3.obj_friendsDescription.getAl_servicies_detail().get(position).getService_image();
                    sid = Json_FriendsDescription3.obj_friendsDescription.getAl_servicies_detail().get(position).getService_id();
                    totl_like = String.valueOf(Json_FriendsDescription3.obj_friendsDescription.getAl_servicies_detail().get(position).getService_like());
                    totl_comment = String.valueOf(Json_FriendsDescription3.obj_friendsDescription.getAl_servicies_detail().get(position).getService_comment());

                    if(holder.ch.isChecked()){
                        like_value="1";
                    }
                    else{

                        like_value="0";
                    }

                    FragmentManager fragmentManager1 = ((FragmentActivity) mContext).getSupportFragmentManager();
                    NewComment_FS f=
                            new NewComment_FS();

                    fragmentManager1
                            .beginTransaction()
                            .replace(R.id.activity_main_content_fragment,
                                    f).addToBackStack(null).commit();





                }
            });




        } else {
            grid = (View) convertView;
        }



        return grid;
    }

}


class ViewHolder111{

    TextView name2;
    TextView tv_like1;
    ImageView imageView;
    ImageView img_comment;
    TextView tv_comment1;
    ImageView img_cart;
    ImageView tv_view;
    CheckBox ch;

}
