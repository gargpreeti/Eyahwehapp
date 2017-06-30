package com.example.adapter.adapterProfile;

import android.support.v7.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.com.example.zoptal102.Register;
import com.example.fragment.FragmentProfileTab.EventFragment;
import com.example.fragment.fragmentHomeTab.FragmentHome;
import com.example.model.model_profile.Json_EventDescription;
import com.example.model.model_profile.Model;
import com.example.url.RegisterUrl;
import com.example.zotal102.yahwahapp.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


public class CustomGridEvent1 extends BaseAdapter {


     private Context mContext;
     public Model m5;
      public  static String eid;
     String result2="",s;
     int deletePosition;

    public CustomGridEvent1(Context c, Model m5) {
        mContext = c;
        this.m5=m5;

    }

    public void setAddList(Model m5)
    {
         this.m5.getAl_event_detail().addAll(m5.getAl_event_detail());
        this.m5.setAl_event_detail(this.m5.getAl_event_detail());
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {

        if(m5.getAl_event_detail().size()==0){

           EventFragment.tv_msg.setVisibility(View.VISIBLE);
        }
          else if(m5.getAl_event_detail().size()>0){

            EventFragment.tv_msg.setVisibility(View.INVISIBLE);

        }
            return m5.getAl_event_detail().size()/2;
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

        final ViewHolderEvent holder = new ViewHolderEvent();

        if (convertView == null) {

            grid = new View(mContext);
            grid = inflater.inflate(R.layout.customview_event, null);


        }
        else {
            grid = convertView;
        }
        holder.name1 = (TextView) grid.findViewById(R.id.img_name);
        holder.tv_view = (ImageView) grid.findViewById(R.id.view_txt);
        holder.imageView = (ImageView)grid.findViewById(R.id.img);
        holder.name1.setText(m5.getAl_event_detail().get(position).getEvent_name());

        Picasso.with(mContext).load(m5.getAl_event_detail().get(position).getEvent_image()).resize(1100,785).into( holder.imageView);


        holder.tv_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                eid = m5.getAl_event_detail().get(position).getEvent_id();
                new Json_EventDescription(mContext).execute(eid);


            }
        });

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


             eid =m5.getAl_event_detail().get(position).getEvent_id();
                new Json_EventDescription(mContext).execute(eid);



            }
        });


        holder.imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                removeItemFromList(position);


                return true;
            }
        });


        return grid;
    }

    protected void removeItemFromList(int position) {

         deletePosition = position;

        AlertDialog.Builder alert = new AlertDialog.Builder(
                mContext);

        alert.setTitle("Delete");
        alert.setMessage("Do you want delete this item?");
        alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                try {
                    eventdlt(FragmentHome.userid, m5.getAl_event_detail().get(deletePosition).getEvent_id());
                }
                catch (IndexOutOfBoundsException e){

                }

            }
        });
        alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                dialog.dismiss();
            }
        });

        alert.show();

    }




    private void eventdlt(final String userid, final String eid) {



        class Eventdlt extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            Register ruc = new Register();


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = new ProgressDialog(mContext);
                loading.setMessage("loading");
                loading.show();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                if(result2.equals("true")) {
                    Toast.makeText(mContext, "Event deleted successfully", Toast.LENGTH_LONG).show();

                    m5.getAl_event_detail().remove(deletePosition);
                    notifyDataSetChanged();

           }
                else{

                    Toast.makeText(mContext, "Something went wrong, please try again!", Toast.LENGTH_LONG).show();

                }

            }

            @Override
            protected String doInBackground(String... params) {

                HashMap<String, String> data = new HashMap<String, String>();
                data.put("user_id", params[0]);
                data.put("id", params[1]);

                String result1 = ruc.sendPostRequest(RegisterUrl.event_dlt, data);
                JSONObject jsonObject = null;

                try {
                    jsonObject = new JSONObject(result1);

                     } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    if(result2.isEmpty()){

                    }
                    result2 = jsonObject.optString("result");
                } catch (Exception e) {
                    e.printStackTrace();
                }


                Log.e("ree", result1);

                return result2;
            }
        }

        Eventdlt ru = new Eventdlt();
        ru.execute(userid, eid);
    }

}
class ViewHolderEvent {
    TextView name1;
    ImageView imageView;
    ImageView tv_view;

}