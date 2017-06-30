package com.example.model.model_market;

import java.io.Serializable;

public class Model_getServiceDescriptionMarket implements Serializable{

	private String susername;
	private String suserimage;
	private String  sfollow;
	private String sname;
	private  String sdesc;
	private String stags;
	private Double sprice;
	private String slocation;
	private String surl;
	private String simg;
	private String sstatus;
	private String sdate;



//	private int pid;
	private int ownerid;
	

	public int getOwnerid() {
		return ownerid;
	}

	public void setOwnerid(int ownerid) {
		this.ownerid = ownerid;
	}

	public String getSusername() {
		return susername;
	}

	public void setSusername(String susername) {
		this.susername = susername;
	}



	public String getSuserimage() {
		return suserimage;
	}

	public void setSuserimage(String suserimage) {
		this.suserimage = suserimage;
	}

	public String getSfollow() {
		return sfollow;
	}

	public void setSfollow(String sfollow) {
		this.sfollow = sfollow;
	}



	public String getSname() {
		return sname;
	}

	public void setSname(String sname) {
		this.sname = sname;
	}
	public String getSdesc() {
		return sdesc;
	}

	public void setSdesc(String sdesc) {
		this.sdesc = sdesc;
	}


	public String getStags() {
		return stags;
	}

	public void setStags(String stags) {
		this.stags = stags;
	}

	public Double getSprice() {
		return sprice;
	}

	public void setSprice(Double sprice) {
		this.sprice = sprice;
	}




	public String getSlocation() {
		return slocation;
	}

	public void setSlocation(String slocation) {
		this.slocation = slocation;
	}

	public String getSurl() {
		return surl;
	}

	public void setSurl(String purl) {
		this.surl = surl;
	}
	public String getSimg() {
		return simg;
	}

	public void setSimg(String simg) {
		this.simg = simg;
	}

	public String getSstatus() {
		return sstatus;
	}

	public void setSstatus(String sstatus) {
		this.sstatus= sstatus;
	}


	public String getSdate() {
		return sdate;
	}

	public void setSdate(String sdate) {
		this.sdate =sdate;
	}


}
