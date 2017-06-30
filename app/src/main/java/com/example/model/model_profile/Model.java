package com.example.model.model_profile;

import java.util.ArrayList;

public class Model {

	private String user_id;
	private String user_name;
	private String accesstoken;
	private String user_image;
	private String paypal_id;
	private ArrayList<ProductDetail> al_product_detail = new ArrayList<ProductDetail>();
	private ArrayList<Servicies> al_servicies_detail = new ArrayList<Servicies>();
	private ArrayList<Event> al_event_detail = new ArrayList<Event>();

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getAccesstoken() {
		return accesstoken;
	}

	public void setAccesstoken(String accesstoken) {
		this.accesstoken = accesstoken;
	}

	public String getUser_image() {
		return user_image;
	}

	public void setUser_image(String user_image) {
		this.user_image = user_image;
	}

	public String getPaypal_id() {
		return paypal_id;
	}

	public void setPaypal_id(String paypal_id) {
		this.paypal_id = paypal_id;
	}

	public ArrayList<ProductDetail> getAl_product_detail() {
		return al_product_detail;
	}

	public void setAl_product_detail(ArrayList<ProductDetail> al_product_detail) {
		this.al_product_detail = al_product_detail;
	}

	public ArrayList<Servicies> getAl_servicies_detail() {
		return al_servicies_detail;
	}

	public void setAl_servicies_detail(ArrayList<Servicies> al_servicies_detail) {
		this.al_servicies_detail = al_servicies_detail;
	}

	public ArrayList<Event> getAl_event_detail() {
		return al_event_detail;
	}

	public void setAl_event_detail(ArrayList<Event> al_event_detail) {
		this.al_event_detail = al_event_detail;
	}

}





