package com.example.adapter.adapterFriends;


import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fragment.fragmentFriendsTab.EventFragment1;
import com.example.fragment.fragmentFriendsTab.FragmentFriends;
import com.example.fragment.fragmentFriendsTab.Fragment_discover;
import com.example.fragment.fragmentFriendsTab.NewComment_FE;
import com.example.fragment.fragmentHomeTab.FragmentHome;
import com.example.model.model_friends.Json_EventDescriptionFriends;
import com.example.model.model_friends.Json_FriendsDescription2;
import com.example.model.model_friends.Model_getFriendsDescription;
import com.example.model.model_market.Json_AddCart;
import com.example.model.model_market.Json_like;
import com.example.zotal102.yahwahapp.R;
import com.squareup.picasso.Picasso;


public class CustomGridEvent2 extends BaseAdapter {
    private Context mContext;
    Model_getFriendsDescription m;
    public static int value=0;
    int checkBoxCounter = 0;

    public static String eid,e_img;
    public static  String like_value;

    public static String totl_like,totl_comment;
    public CustomGridEvent2(Context c, Model_getFriendsDescription m) {
        mContext = c;
        this.m=m;

    }


    @Override
    public int getCount() {


        if(m.getAl_event_detail().size()==0){

            EventFragment1.tv_msg.setVisibility(View.VISIBLE);
        }
        else if(m.getAl_event_detail().size()>0){

            EventFragment1.tv_msg.setVisibility(View.INVISIBLE);

        }

        return m.getAl_event_detail().size();
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

        final ViewHolder_FE holder=new ViewHolder_FE();
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {

            grid = new View(mContext);
            grid = inflater.inflate(R.layout.product_grid1, null);
            holder.ch=(CheckBox)grid.findViewById(R.id.check1);
            holder.name2 = (TextView) grid.findViewById(R.id.img_name);
            holder.tv_view = (ImageView)grid.findViewById(R.id.view_txt);
            holder.tv_like1= (TextView) grid.findViewById(R.id.like_txt1);
            holder.tv_comment1 = (TextView) grid.findViewById(R.id.comment_txt);
            holder.imageView = (ImageView)grid.findViewById(R.id.img);
            holder.img_comment = (ImageView)grid.findViewById(R.id.comment);
            holder.img_cart= (ImageView)grid.findViewById(R.id.cart);

            holder.name2.setText(m.getAl_event_detail().get(position).getEvent_name());
            holder.tv_like1.setText(String.valueOf(m.getAl_event_detail().get(position).getEvent_like()));
            holder.tv_comment1.setText(String.valueOf(m.getAl_event_detail().get(position).getEvent_comment()));


            Picasso.with(mContext).load(m.getAl_event_detail().get(position).getEvent_image()).resize(1000, 600).into(holder.imageView);

            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    eid = Json_FriendsDescription2.obj_friendsDescription.getAl_event_detail().get(position).getEvent_id();
                    new Json_EventDescriptionFriends(mContext).execute(eid, FragmentHome.userid, "e");

                }
            });


            holder.tv_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  eid = Json_FriendsDescription2.obj_friendsDescription.getAl_event_detail().get(position).getEvent_id();
                    new Json_EventDescriptionFriends(mContext).execute(eid,FragmentHome.userid,"e");


                }
            });

            holder.img_cart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    String eid = Json_FriendsDescription2.obj_friendsDescription.getAl_event_detail().get(position).getEvent_id();

                    new Json_AddCart(mContext).execute(FragmentHome.userid,FragmentHome.usertokn,eid,"e","1");

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

                        if(Json_FriendsDescription2.obj_friendsDescription.al_event_detail.get(position).getEvent_liked().equals("0")) {

                            checkBoxCounter++;
                            String val = "1";
                            int value = Integer.parseInt(val);
                            value = value + m.getAl_event_detail().get(position).getEvent_like();
                            holder.tv_like1.setText(String.valueOf(value));

                                 String type = "e";
                            String eid = Json_FriendsDescription2.obj_friendsDescription.getAl_event_detail().get(position).getEvent_id();
                            new Json_like(mContext).execute(eid, FragmentHome.userid, type, "1");
                        }
                    } else {

                                     checkBoxCounter--;
                        String st = "0";
                        String val = "-1";
                        int value = Integer.parseInt(val);
                        value = value + Integer.parseInt(String.valueOf(holder.tv_like1.getText()));
                        holder.tv_like1.setText(String.valueOf(value));

                        Log.e("tv_like1", "" + holder.tv_like1.getText());
                        String type = "e";
                        String eid = Json_FriendsDescription2.obj_friendsDescription.getAl_event_detail().get(position).getEvent_id();
                        new Json_like(mContext).execute(eid, FragmentHome.userid, type, st);

                    }

                }

            });

            holder.img_comment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    e_img = Json_FriendsDescription2.obj_friendsDescription.getAl_event_detail().get(position).getEvent_image();
                    eid = Json_FriendsDescription2.obj_friendsDescription.getAl_event_detail().get(position).getEvent_id();
                    totl_like = String.valueOf(Json_FriendsDescription2.obj_friendsDescription.getAl_event_detail().get(position).getEvent_like());
                    totl_comment = String.valueOf(Json_FriendsDescription2.obj_friendsDescription.getAl_event_detail().get(position).getEvent_comment());

                    if(holder.ch.isChecked()){
                        like_value="1";
                    }
                    else{

                        like_value="0";
                    }

                    FragmentManager fragmentManager1 = ((FragmentActivity) mContext).getSupportFragmentManager();
                    NewComment_FE f=
                            new NewComment_FE();

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


class ViewHolder_FE {

    TextView name2;
    TextView tv_like1;
    ImageView imageView;
    ImageView img_comment;
    TextView tv_comment1;
    ImageView img_cart;
    ImageView tv_view;
    CheckBox ch;

}
