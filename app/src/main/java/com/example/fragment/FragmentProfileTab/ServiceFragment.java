package com.example.fragment.FragmentProfileTab;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adapter.adapterProfile.CustomGridService1;
import com.example.com.example.zoptal102.Register;
import com.example.fragment.fragmentHomeTab.FragmentHome;
import com.example.model.model_profile.Json_GetDataService;
import com.example.url.RegisterUrl;
import com.example.zotal102.yahwahapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


public class ServiceFragment extends Fragment {

    public Frag_AddService frag_serviceProfile;

    ListView grid1;
    View view;
    String result2="";
    JSONObject jsonObject;
    Button btn_add;
    String s;

    public static String sid;
    public static TextView tv_msg;

    CustomGridService1 customGridservice1;
    ScrollView scrollView4;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.fragment_service, container, false);

        grid1 = (ListView) view.findViewById(R.id.gridView1);
        scrollView4=(ScrollView)view.findViewById(R.id.scrollView);
        tv_msg=(TextView)view.findViewById(R.id.tv_msg);


        customGridservice1	=new CustomGridService1(getActivity(), Json_GetDataService.model);
        grid1.setAdapter(customGridservice1);

        new Json_GetDataService(getActivity(), customGridservice1).execute(FragmentHome.userid, FragmentHome.usertokn, "1");


        frag_serviceProfile = new Frag_AddService();
        btn_add=(Button)view.findViewById(R.id.add);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fragmentManager = getFragmentManager();

                fragmentManager
                        .beginTransaction()
                        .replace(R.id.activity_main_content_fragment,
                                frag_serviceProfile).addToBackStack(null).commit();

            }
        });



        return view;
    }

    // method to remove list item
    protected void removeItemFromList(int position) {
        final int deletePosition = position;

        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

        alert.setTitle("Delete");
        alert.setMessage("Do you want delete this item?");
        alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

try {
    servicedlt(FragmentHome.userid, Json_GetDataService.model.getAl_servicies_detail().get(deletePosition).getService_id());
}
catch (IndexOutOfBoundsException e){}
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



    private void servicedlt(final String userid,final String sid) {

        class Servicedlt extends AsyncTask<String, Void, String> {

            ProgressDialog loading;
            Register ruc = new Register();


            @Override
            protected void onPreExecute() {
                super.onPreExecute();

               loading = new ProgressDialog(getActivity());
                loading.setMessage("loading");
                loading.show();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                if(result2.equals("true")) {
                    Toast.makeText(getActivity(), "Service deleted successfully", Toast.LENGTH_LONG).show();

                          grid1.setAdapter(customGridservice1);

                }
                else{

                    Toast.makeText(getActivity(), "Something went wrong, please try again!", Toast.LENGTH_LONG).show();

                }

            }

            @Override
            protected String doInBackground(String... params) {

                HashMap<String, String> data = new HashMap<String, String>();
                data.put("user_id", params[0]);
                data.put("id", params[1]);

                String result1 = ruc.sendPostRequest(RegisterUrl.service_dlt, data);


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


                return result2;
            }
        }

        Servicedlt ru = new Servicedlt();
        ru.execute(userid, sid);

    }


}












