package com.example.adapter.adapterMarket;


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

import com.example.fragment.fragmentHomeTab.FragmentHome;
import com.example.fragment.fragmentMarketTab.FragmentMarket;
import com.example.fragment.fragmentMarketTab.NewComment2;
import com.example.model.model_market.EventMarket;
import com.example.model.model_market.Json_AddCart;
import com.example.model.model_market.Json_EventDescriptionMarket;
import com.example.model.model_market.Json_like;
import com.example.model.model_market.Model_Market;
import com.example.zotal102.yahwahapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class CustomGridEventMarket extends BaseAdapter {

    private Context mContext;
   public Model_Market mrkt;
    int checkBoxCounter = 0;
    public static String like_value;
   int value=0;
     public static String eid,eimg;

    public static String totl_like,totl_comment;


    public CustomGridEventMarket(Context mContext, Model_Market mrkt) {
        this.mContext = mContext;
        this.mrkt=mrkt;

    }
    public void setAddList(ArrayList<EventMarket> list)
    {
          this.mrkt.getAl_event_detail_market().addAll(list);
            notifyDataSetChanged();
    }

    public ArrayList<EventMarket> getCurrentList()
    {
        return mrkt.getAl_event_detail_market();
    }

    @Override
    public int getCount() {

      return mrkt.getAl_event_detail_market().size();

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

        final ViewHolderEvent holder2=new ViewHolderEvent();
        if(convertView==null){
            grid = new View(mContext);
            grid = inflater.inflate(R.layout.event_grid_market, null);

        } else {
            grid = convertView;
        }


        holder2.ch=(CheckBox)grid.findViewById(R.id.check);
        holder2.name1 = (TextView)grid.findViewById(R.id.img_name);
        holder2.tv_like= (ImageView)grid.findViewById(R.id.like);
        holder2.tv_view = (ImageView)grid.findViewById(R.id.view_txt);
        holder2. tv_like1 = (TextView) grid.findViewById(R.id.like_txt);
        holder2.tv_comment = (ImageView)grid.findViewById(R.id.comment);
        holder2.tv_comment1 = (TextView)grid.findViewById(R.id.comment_txt);
        holder2.tv_cart= (ImageView)grid.findViewById(R.id.cart);
        holder2.tv_cart1= (TextView)grid.findViewById(R.id.cart_txt);
        holder2.imageView = (ImageView)grid.findViewById(R.id.img);

        holder2.name1.setText(mrkt.getAl_event_detail_market().get(position).getEvent_name());
        holder2.tv_comment1.setText(String.valueOf(mrkt.getAl_event_detail_market().get(position).getEvent_comment()));
        holder2.tv_like1.setText(String.valueOf(mrkt.getAl_event_detail_market().get(position).getEvent_like()));

        Picasso.with(mContext).load(mrkt.getAl_event_detail_market().get(position).getEvent_image()).resize(1000,600).into(holder2.imageView);


        holder2.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String type = "e";
                eid = mrkt.getAl_event_detail_market().get(position).getEvent_id();
                new Json_EventDescriptionMarket(mContext).execute(eid, FragmentHome.userid, type);

            }
        });
        holder2.tv_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String type = "e";

                eid = mrkt.getAl_event_detail_market().get(position).getEvent_id();
                new Json_EventDescriptionMarket(mContext).execute(eid, FragmentHome.userid, type);

            }
        });

        holder2.tv_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                 eid = mrkt.getAl_event_detail_market().get(position).getEvent_id();

                new Json_AddCart(mContext).execute(FragmentHome.userid,FragmentHome.usertokn,eid,"e","1");

                String val="1";
                value=Integer.parseInt(val);
                int tv=Integer.parseInt(String.valueOf(FragmentMarket.tv_cart.getText()));
                value=value+tv;


                FragmentMarket.tv_cart.setText(String.valueOf(value));
                FragmentHome.tv_cart2.setText(String.valueOf(value));


            }
        });



        try {
            holder2.ch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton arg0, boolean isChecked) {
                    // TODO Auto-generated method stub
                    if (isChecked) {

                        if (mrkt.getAl_event_detail_market().get(position).getEvent_liked().equals("0")) {
                            checkBoxCounter++;

                            String val = "1";
                            int value = Integer.parseInt(val);
                            value = value + mrkt.getAl_event_detail_market().get(position).getEvent_like();
                            holder2.tv_like1.setText(String.valueOf(value));

                            String type = "e";
                            String st = "1";

                            eid = mrkt.getAl_event_detail_market().get(position).getEvent_id();

                                new Json_like(mContext).execute(eid, FragmentHome.userid, type, st);

                        }
                    } else {


                        checkBoxCounter--;

                        String st = "0";
                        String val = "-1";
                        int value = Integer.parseInt(val);
                        value = value + Integer.parseInt(String.valueOf( holder2.tv_like1.getText()));
                         holder2.tv_like1.setText(String.valueOf(value));
                  String type = "e";
                            eid = mrkt.getAl_event_detail_market().get(position).getEvent_id();
                            new Json_like(mContext).execute(eid, FragmentHome.userid, type, st);

                    }
                    holder2.ch.isChecked();

                }

            });

        } catch (IndexOutOfBoundsException e){}

        holder2.tv_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                eimg = mrkt.getAl_event_detail_market().get(position).getEvent_image();

                eid = mrkt.getAl_event_detail_market().get(position).getEvent_id();
                totl_like= String.valueOf(mrkt.getAl_event_detail_market().get(position).getEvent_like());
                totl_comment= String.valueOf(mrkt.getAl_event_detail_market().get(position).getEvent_comment());

                if(holder2.ch.isChecked()){
                    like_value="1";
                }
                else{

                    like_value="0";
                }

                FragmentManager fragmentManager1 = ((FragmentActivity) mContext).getSupportFragmentManager();
                NewComment2 f=
                        new NewComment2();

                fragmentManager1
                        .beginTransaction()
                        .replace(R.id.activity_main_content_fragment,
                                f).commit();



            }
        });

        return grid;
    }
}
class ViewHolderEvent {
    TextView name1;
    TextView tv_like1;
    ImageView imageView;
    ImageView tv_like;
    ImageView tv_comment;
    TextView tv_comment1;
    ImageView tv_cart;
    ImageView tv_view;
    TextView tv_cart1;
    CheckBox ch;

}