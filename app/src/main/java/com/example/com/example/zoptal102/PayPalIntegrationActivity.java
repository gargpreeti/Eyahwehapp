package com.example.com.example.zoptal102;


import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Toast;

import com.example.adapter.adapterMarket.CustomCart;
import com.example.fragment.fragmentHomeTab.FragmentHome;
import com.example.fragment.fragmentMarketTab.Fragment_cart;
import com.example.model.model_market.Json_SendOrder;
import com.example.model.model_market.Json_ShowCartTotal;
import com.example.url.RegisterUrl;
import com.example.zotal102.yahwahapp.R;
import com.paypal.android.MEP.PayPal;
import com.paypal.android.MEP.PayPalActivity;
import com.paypal.android.MEP.PayPalAdvancedPayment;
import com.paypal.android.MEP.PayPalReceiverDetails;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PayPalIntegrationActivity extends ActionBarActivity {

	public  List<String> seller_id;

	EditText txt_name,txt_pincode,txt_address,txt_landmrk,txt_city,txt_state,txt_phn;
	JSONObject jsonObject;
	String result2;
	ImageView img_bck;
	Button paynow;
	private PayPal mPayPal;
	String MyPREFERENCES = "MyPrefs1";
	SharedPreferences sharedpreferences1;
	String usertokn, userid;
	String name1 = "nameKey1";
	String tokn = "toknKey";
	String uaddress,uname,upincode,ulandmark,ucity,ustate,uphonenum;
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_pay_pal_integration);


		sharedpreferences1 =getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
		if (sharedpreferences1.contains(tokn)) {

			usertokn = sharedpreferences1.getString(tokn, "");
			userid = sharedpreferences1.getString(name1, "");

		}


		txt_name=(EditText)findViewById(R.id.ed_name);
		txt_pincode=(EditText)findViewById(R.id.ed_pincode);
		txt_address=(EditText)findViewById(R.id.ed_address);
		txt_landmrk=(EditText)findViewById(R.id.ed_landmrk);
		txt_city=(EditText)findViewById(R.id.ed_city);
		txt_state=(EditText)findViewById(R.id.ed_state);
		txt_phn=(EditText)findViewById(R.id.ed_phn);
		paynow=(Button)findViewById(R.id.paynow);
		img_bck=(ImageView)findViewById(R.id.back);

		showaddress(userid,usertokn);


		img_bck.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {


				FragmentManager fragmentManager = getSupportFragmentManager();

				fragmentManager
						.beginTransaction()
						.replace(R.id.activity_main_content_fragment,
								new Fragment_cart()).addToBackStack(null).commit();

				finish();


			}
		});


		mPayPal=PayPal.initWithAppID(this,Constants.PAYPAL_APP_ID,PayPal.ENV_LIVE);


		if(isReadStorageAllowed()) {


			initUI();
		}
		else{

			requestStoragePermission();
		}

	}


	private void initUI() {

		LayoutParams params = new
				LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);

		params.addRule(RelativeLayout.CENTER_IN_PARENT);

		params.bottomMargin = 10;


		paynow.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {


				String name = txt_name.getText().toString().trim();
				String address = txt_address.getText().toString().trim();
				String city = txt_city.getText().toString().trim();


				if (name.equals("") || address.equals("") || city.equals("")) {
					Toast.makeText(PayPalIntegrationActivity.this, "Name,Address,City should not be empty", Toast.LENGTH_LONG).show();


				}

				else {

					payWithPaypal();
				}

			}
		});
	}

		private void payWithPaypal() {

			double pricePrimary = 0.0;
		    Double admin_price=0.0;

		seller_id=new ArrayList<String>();
		PayPalAdvancedPayment payment = new PayPalAdvancedPayment();

		payment.setCurrencyType("USD");

	    pricePrimary=Double.parseDouble((String) Fragment_cart.tv_total.getText());

			Log.e("total payment---",""+pricePrimary);

		BigDecimal bigDecimalPrimary = new BigDecimal(pricePrimary);
		PayPalReceiverDetails receiverPrimary = new PayPalReceiverDetails();


		receiverPrimary.setRecipient(CustomCart.m.getAl_cart().get(0).getAdmin_id());

			Log.e("adminid----",""+CustomCart.m.getAl_cart().get(0).getAdmin_id());

		receiverPrimary.setMerchantName("eyahweh-store");

		receiverPrimary.setSubtotal(bigDecimalPrimary);
		receiverPrimary.setIsPrimary(true);

		payment.getReceivers().add(receiverPrimary);

		for (int i=0;i<CustomCart.mrchntlist.size();i++) {


			Double item_price=0.0;
			Double Tprice = 0.0;
			Double Seller_Price=0.0;

			for (int j = 0; j < CustomCart.m.getAl_cart().size(); j++) {

				if (CustomCart.m.getAl_cart().get(j).getMrchntid().equals(CustomCart.mrchntlist.get(i).toString())) {

					//Log.e("item price---",""+CustomCart.m.getAl_cart().get(j).getItem_price());
					Double itmprice=(Double.parseDouble(CustomCart.m.getAl_cart().get(j).getItem_price())*6/100);
				//	Log.e("txprice---",""+itmprice);
					Double ttprice=(Double.parseDouble(CustomCart.m.getAl_cart().get(j).getItem_price()))+itmprice;
					Double cnts=Double.parseDouble(String.valueOf(0.30));
					Log.e("cnts----",""+cnts);
				    Tprice +=ttprice;

					item_price = Tprice / 10;
					item_price=item_price+cnts;


			}

			}
			Log.e("Total seller price before to pay admin---",""+Tprice);
			Log.e("admin price---",""+item_price);

			Seller_Price=Tprice-item_price;
			Log.e("After pay admin Seller_Price",""+Seller_Price);
			PayPalReceiverDetails receiver0;
			receiver0 = new PayPalReceiverDetails();
			BigDecimal bigDecimalSecondary = new BigDecimal(Seller_Price);
			receiver0.setSubtotal(bigDecimalSecondary);
			receiver0.setRecipient((String) CustomCart.mrchntlist.get(i));// add correct object of email of receiver
			Log.e("mrchants id---",""+(String) CustomCart.mrchntlist.get(i));

			if(CustomCart.m.getAl_cart().get(0).getAdmin_id().equals(CustomCart.mrchntlist.get(i))){

			}
			else {

				payment.getReceivers().add(receiver0);
			}

		}
			Log.e("paymnt----",""+payment);
		Intent checkoutIntent = PayPal.getInstance().checkout(payment,PayPalIntegrationActivity.this);
		startActivityForResult(checkoutIntent, 1);


	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		switch(resultCode) {
			case Activity.RESULT_OK:
				String payKey =
						data.getStringExtra(PayPalActivity.EXTRA_PAY_KEY);
				Toast.makeText(this,"Payment Successful",Toast.LENGTH_LONG).show();

				String name=txt_name.getText().toString().trim();
				String pincode=txt_pincode.getText().toString().trim();
				String address=txt_address.getText().toString().trim();
				String landmark=txt_landmrk.getText().toString().trim();
				String city=txt_city.getText().toString().trim();
				String state=txt_state.getText().toString().trim();
				String phn=txt_phn.getText().toString().trim();

				String orderqty=String.valueOf(Fragment_cart.tv_cart1.getText());
				String totalprice= (String) Fragment_cart.tv_total.getText();

		        StringBuilder amtvalue = new StringBuilder();
				StringBuilder proid=new StringBuilder();
				StringBuilder sellid=new StringBuilder();
				StringBuilder productqty=new StringBuilder();
				StringBuilder protype=new StringBuilder();

				for ( int i = 0; i< CustomCart.productamout.size(); i++) {
					//append the value into the builder
					amtvalue.append(CustomCart.productamout.get(i));

					//if the value is not the last element of the list
					//then append the comma(,) as well
					if ( i != CustomCart.productamout.size()-1){
						amtvalue.append(", ");
					}
				}

				for ( int i = 0; i< CustomCart.productid.size(); i++) {
					//append the value into the builder
					proid.append(CustomCart.productid.get(i));

					//if the value is not the last element of the list
					//then append the comma(,) as well
					if ( i != CustomCart.productid.size()-1){
						proid.append(",");
					}
				}


				for ( int i = 0; i< CustomCart.sellerid.size(); i++) {
					//append the value into the builder
					sellid.append(CustomCart.sellerid.get(i));

					//if the value is not the last element of the list
					//then append the comma(,) as well
					if ( i != CustomCart.sellerid.size()-1){
						sellid.append(",");
					}
				}





				for ( int i = 0; i< CustomCart.proqty.size(); i++) {
					//append the value into the builder
					productqty.append(CustomCart.proqty.get(i));

					//if the value is not the last element of the list
					//then append the comma(,) as well
					if ( i != CustomCart.productid.size()-1){
						productqty.append(",");
					}
				}

				for ( int i = 0; i< CustomCart.producttype.size(); i++) {
					//append the value into the builder
					protype.append(CustomCart.producttype.get(i));

					//if the value is not the last element of the list
					//then append the comma(,) as well
					if ( i != CustomCart.productid.size()-1){
						protype.append(",");
					}
				}


				new Json_SendOrder(PayPalIntegrationActivity.this).execute(FragmentHome.userid, FragmentHome.usertokn, totalprice, orderqty, "PayPal", proid.toString(), amtvalue.toString(), productqty.toString(), protype.toString(),name, pincode,address,landmark, city, state, phn,sellid.toString());

				Json_ShowCartTotal.totalcart=0;
				Itemdlt(FragmentHome.userid, FragmentHome.usertokn, "");

				FragmentHome.tv_cart2.setText("0");
				Fragment_cart.tv_cart1.setText("0");

				Intent i = new Intent(PayPalIntegrationActivity.this, MainTabActivity.class);
				startActivity(i);

				break;
			case Activity.RESULT_CANCELED:
				Toast.makeText(this,"Payment Cancel",Toast.LENGTH_LONG).show();

				Intent intent = new Intent(PayPalIntegrationActivity.this, MainTabActivity.class);
				startActivity(intent);

				break;
			case PayPalActivity.RESULT_FAILURE:
				Toast.makeText(this,"Payment Failed",Toast.LENGTH_LONG).show();
				String errorID =
						data.getStringExtra(PayPalActivity.EXTRA_PAYMENT_INFO);
				String errorID1 =
						data.getStringExtra(PayPalActivity.EXTRA_CORRELATION_ID);

				String errorMessage =
						data.getStringExtra(PayPalActivity.EXTRA_ERROR_MESSAGE);

				Log.e("error msg---",""+errorMessage);
				Log.e("errorID1---",""+errorID1);
				Log.e("errorID---",""+errorID);
						break;
		}

	}
	private void Itemdlt(final String userid, final String usertokn,final String  pid) {



		class Productdlt extends AsyncTask<String, Void, String> {
			ProgressDialog loading;
			Register ruc = new Register();


			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				loading = new ProgressDialog(PayPalIntegrationActivity.this);
				loading.setMessage("loading");
				loading.show();
			}

			@Override
			protected void onPostExecute(String s) {
				super.onPostExecute(s);
				loading.dismiss();
				if(result2.equals("true")) {

					FragmentHome.tv_cart2.setText("0");
						Fragment_cart.tv_cart1.setText("0");

				}
				else{


				}

			}

			@Override
			protected String doInBackground(String... params) {

				HashMap<String, String> data = new HashMap<String, String>();
				data.put("user_id", params[0]);
				data.put("access_token", params[1]);
				data.put("item_id", params[2]);

				String result1 = ruc.sendPostRequest(RegisterUrl.dltcart, data);


				try {
					jsonObject = new JSONObject(result1);


				} catch (JSONException e) {
					e.printStackTrace();
				}

				try {
					result2 = jsonObject.optString("result");
				} catch (Exception e) {
					e.printStackTrace();
				}


				return result2;
			}
		}

		Productdlt ru = new Productdlt();
		ru.execute(userid, usertokn, String.valueOf(pid));

	}

	//We are calling this method to check the permission status
	private boolean isReadStorageAllowed() {
		//Getting the permission status
		int result = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);

		//If permission is granted returning true
		if (result == PackageManager.PERMISSION_GRANTED) {
			Log.e("alredy granted---","yes");
			return true;
		}
		else {
			Log.e("no alredy granted---","no");
			//If permission is not granted returning false
			return false;
		}
	}

	//Requesting permission
	private void requestStoragePermission(){

		if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_PHONE_STATE)){
			//If the user has denied the permission previously your code will come to this block
			//Here you can explain why you need this permission
			//Explain here why you need this permission
		}

		//And finally ask for the permission
		ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_PHONE_STATE},22);
	}
	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

		//Checking the request code of our request
		if(requestCode == 22){

			//If permission is granted
			if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

				//Displaying a toast
			//  Toast.makeText(this,"Permission granted now you can read the storage",Toast.LENGTH_LONG).show();

				initUI();

			}
			else{
				//Displaying another toast if permission is not granted
				Toast.makeText(this,"Oops you just denied the permission",Toast.LENGTH_LONG).show();
			}
		}
	}


	private void showaddress(String userid,final String usertokn) {

		class RegisterUser extends AsyncTask<String, Void, String> {
			ProgressDialog loading;
			Register ruc = new Register();


			@Override
			protected void onPreExecute() {
				super.onPreExecute();

			}

			@Override
			protected void onPostExecute(String s) {
				super.onPostExecute(s);

		        txt_name.setText(uname);
				txt_address.setText(uaddress);
				txt_city.setText(ucity);
				txt_landmrk.setText(ulandmark);
				txt_phn.setText(uphonenum);
				txt_pincode.setText(upincode);
				txt_state.setText(ustate);

			}

			@Override
			protected String doInBackground(String... params) {

				HashMap<String, String> data = new HashMap<String,String>();
				data.put("user_id", params[0]);
				data.put("access_token", params[1]);

				Log.e("userid",""+params[0]);
				Log.e("access_token",""+params[1]);
				String result1 = ruc.sendPostRequest(RegisterUrl.showaddress,data);
				Log.e("address result----",""+result1);

				try {
					jsonObject = new JSONObject(result1);

					JSONArray ary_products = jsonObject.getJSONArray("address");
					for (int i = 0; i < ary_products.length(); i++) {
						JSONObject obj = ary_products.getJSONObject(i);
						 uaddress = obj.getString("address");
					uname = obj.getString("name");
					 upincode = obj.getString("pincode");
					 ulandmark = obj.getString("landmark");
					 ucity = obj.getString("city");
						ustate= obj.getString("state");
				uphonenum = obj.getString("phonenum");
					}





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


				return  result2;
			}
		}

		RegisterUser ru = new RegisterUser();
		ru.execute(userid, usertokn);

	}






}
