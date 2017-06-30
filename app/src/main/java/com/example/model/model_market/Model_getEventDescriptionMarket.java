package com.example.model.model_market;

import java.io.Serializable;

public class Model_getEventDescriptionMarket implements Serializable{

	private String pusername;
	private String puserimage;
	private String  pfollow;
	private String pname;
	private  String pdesc;
	private String ptags;
	private Double pprice;
	private String plocation;
	private String purl;
	private String pimg;
	private String pstatus;
	private String pdate;
	private String sdate;
	private String edate;
	private String stime;
	private String etime;



//	private int pid;
	private int ownerid;
	

	public int getOwnerid() {
		return ownerid;
	}

	public void setOwnerid(int ownerid) {
		this.ownerid = ownerid;
	}

	public String getPusername() {
		return pusername;
	}

	public void setPusername(String pusername) {
		this.pusername = pusername;
	}



	public String getPuserimage() {
		return puserimage;
	}

	public void setPuserimage(String puserimage) {
		this.puserimage = puserimage;
	}

	public String getPfollow() {
		return pfollow;
	}

	public void setPfollow(String pfollow) {
		this.pfollow = pfollow;
	}



	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}
	public String getPdesc() {
		return pdesc;
	}

	public void setPdesc(String pdesc) {
		this.pdesc = pdesc;
	}


	public String getPtags() {
		return ptags;
	}

	public void setPtags(String ptags) {
		this.ptags = ptags;
	}

	public Double getPprice() {
		return pprice;
	}

	public void setPprice(Double pprice) {
		this.pprice = pprice;
	}




	public String getPlocation() {
		return plocation;
	}

	public void setPlocation(String plocation) {
		this.plocation = plocation;
	}

	public String getPurl() {
		return purl;
	}

	public void setPurl(String purl) {
		this.purl = purl;
	}
	public String getPimg() {
		return pimg;
	}

	public void setPimg(String pimg) {
		this.pimg = pimg;
	}

	public String getPstatus() {
		return pstatus;
	}

	public void setPstatus(String pstatus) {
		this.pstatus= pstatus;
	}


	public String getSdate() {
		return sdate;
	}

	public void setSdate(String sdate) {
		this.sdate =sdate;
	}


	public String getEdate() {
		return edate;
	}

	public void setEdate(String edate) {
		this.edate =edate;
	}

	public String getStime() {
		return stime;
	}

	public void setStime(String stime) {
		this.stime =stime;
	}


	public String getEtime() {
		return etime;
	}

	public void setEtime(String etime) {
		this.etime =etime;
	}

	public String getPdate() {
		return pdate;
	}

	public void setPdate(String pdate) {
		this.pdate = pdate;
	}



}
