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
import com.example.fragment.fragmentMarketTab.NewComment;
import com.example.model.model_market.Json_AddCart;
import com.example.model.model_market.Json_ProductDescriptionMarket1;
import com.example.model.model_market.Json_like;
import com.example.model.model_market.Model_Market;
import com.example.model.model_market.ProductDetailMarket;
import com.example.zotal102.yahwahapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;




public class CustomGridProductMarket extends BaseAdapter {

       private Context mContext;
    public Model_Market mrkt;
    int checkBoxCounter = 0;
    public static int value = 0;
    public static String pid, p_img;
    public static String totl_like, totl_comment,liked;
    public static String like_value;


    public CustomGridProductMarket(Context mContext, Model_Market mrkt) {

        this.mContext = mContext;
        this.mrkt = mrkt;

    }

    public void setAddList(ArrayList<ProductDetailMarket> list)
    {


        this.mrkt.getAl_product_detail_market().addAll(list);
        notifyDataSetChanged();


    }
    public ArrayList<ProductDetailMarket> getCurrentList()
    {
        return mrkt.getAl_product_detail_market();
    }

    @Override

    public int getCount() {

             return mrkt.getAl_product_detail_market().size();


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
        final ViewHolder holder = new ViewHolder();

        if (convertView == null) {

            grid = new View(mContext);
            grid = inflater.inflate(R.layout.product_grid_market, null);


        } else {
            grid = convertView;
        }

        holder.ch = (CheckBox) grid.findViewById(R.id.check1);
        holder.name1 = (TextView) grid.findViewById(R.id.img_name);
        holder.tv_view = (ImageView) grid.findViewById(R.id.view_txt);
        holder.tv_like = (ImageView) grid.findViewById(R.id.like);
        holder.tv_like1 = (TextView) grid.findViewById(R.id.like_txt1);
        holder.tv_comment = (ImageView) grid.findViewById(R.id.comment);
        holder.tv_comment1 = (TextView) grid.findViewById(R.id.comment_txt);
        holder.tv_cart = (ImageView) grid.findViewById(R.id.cart);
        holder.tv_cart1 = (TextView) grid.findViewById(R.id.cart_txt);
        holder.imageView = (ImageView) grid.findViewById(R.id.img);



        holder.name1.setText(mrkt.getAl_product_detail_market().get(position).getProduct_name());

        holder.tv_comment1.setText(String.valueOf(mrkt.getAl_product_detail_market().get(position).getProduct_comment()));
        holder.tv_like1.setText(String.valueOf(mrkt.getAl_product_detail_market().get(position).getProduct_like()));


        Picasso.with(mContext).load(mrkt.getAl_product_detail_market().get(position).getProduct_image()).resize(1000, 600).into(holder.imageView);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String type = "p";

                pid =  mrkt.getAl_product_detail_market().get(position).getProduct_id();
                new Json_ProductDescriptionMarket1(mContext).execute(pid, FragmentHome.userid, type);


            }
        });


        holder.tv_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pid = mrkt.getAl_product_detail_market().get(position).getProduct_id();

                new Json_AddCart(mContext).execute(FragmentHome.userid, FragmentHome.usertokn, pid, "p", "1");

                String val = "1";
                value = Integer.parseInt(val);

                int tv = Integer.parseInt(String.valueOf(FragmentHome.tv_cart2.getText()));

                value = value + tv;



                FragmentMarket.tv_cart.setText(String.valueOf(value));
                FragmentHome.tv_cart2.setText(String.valueOf(value));

            }
        });


        holder.tv_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String type = "p";

                String pid = mrkt.getAl_product_detail_market().get(position).getProduct_id();

                new Json_ProductDescriptionMarket1(mContext).execute(pid, FragmentHome.userid, type);


            }
        });

        if(mrkt.getAl_product_detail_market().get(position).getProduct_liked().equals("1")){

            holder.ch.isChecked();

        }
        else{


        }

        try {
            holder.ch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton arg0, boolean isChecked) {


                    if (isChecked) {

                        checkBoxCounter++;

                        if (mrkt.getAl_product_detail_market().get(position).getProduct_liked().equals("0")) {

                                     checkBoxCounter++;
                            String val = "1";
                            int value = Integer.parseInt(val);
                            value = value + mrkt.getAl_product_detail_market().get(position).getProduct_like();
                            holder.tv_like1.setText(String.valueOf(value));
                            String type = "p";
                            pid = mrkt.getAl_product_detail_market().get(position).getProduct_id();
                            new Json_like(mContext).execute(pid, FragmentHome.userid, type, "1");

                        }

                    } else {

                        checkBoxCounter--;
                        String st = "0";
                        String val = "-1";
                        int value = Integer.parseInt(val);
                        value = value + Integer.parseInt(String.valueOf(holder.tv_like1.getText()));
                                  holder.tv_like1.setText(String.valueOf(value));


                        String type = "p";
                        pid = mrkt.getAl_product_detail_market().get(position).getProduct_id();
                        new Json_like(mContext).execute(pid, FragmentHome.userid, type, st);

                    }
                    holder.ch.isChecked();
                }

            });

        } catch (IndexOutOfBoundsException e) {


        }



        holder.tv_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                liked=mrkt.getAl_product_detail_market().get(position).getProduct_liked();
                p_img = mrkt.getAl_product_detail_market().get(position).getProduct_image();
                pid = mrkt.getAl_product_detail_market().get(position).getProduct_id();
                       totl_like= String.valueOf(mrkt.getAl_product_detail_market().get(position).getProduct_like());
                totl_comment = String.valueOf(mrkt.getAl_product_detail_market().get(position).getProduct_comment());

                if (holder.ch.isChecked()) {
                    like_value = "1";
                } else {

                    like_value = "0";
                }

                FragmentManager fragmentManager1 = ((FragmentActivity) mContext).getSupportFragmentManager();
                NewComment f=
                        new NewComment();

                fragmentManager1
                        .beginTransaction()
                        .replace(R.id.activity_main_content_fragment,
                                f).addToBackStack(null).commit();


            }
        });



        return grid;
    }

}

class ViewHolder {
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


