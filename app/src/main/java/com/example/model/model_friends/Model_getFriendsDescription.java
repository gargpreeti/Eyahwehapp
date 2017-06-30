package com.example.model.model_friends;

import com.example.model.model_profile.Event;
import com.example.model.model_profile.ProductDetail;
import com.example.model.model_profile.Servicies;

import java.io.Serializable;
import java.util.ArrayList;

public class Model_getFriendsDescription implements Serializable{

	private String username;
	private String userimage;
	private String  follow;


	public ArrayList<ProductDetail> al_product_detail = new ArrayList<ProductDetail>();
	public ArrayList<Servicies> al_servicies_detail = new ArrayList<Servicies>();
	public  ArrayList<Event> al_event_detail = new ArrayList<Event>();

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}



	public String getUserimage() {
		return userimage;
	}

	public void setUserimage(String userimage) {
		this.userimage = userimage;
	}

	public String getFollow() {
		return follow;
	}

	public void setFollow(String follow) {
		this.follow = follow;
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
