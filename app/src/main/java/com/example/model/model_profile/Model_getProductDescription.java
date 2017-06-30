package com.example.model.model_profile;

import java.io.Serializable;

public class Model_getProductDescription  implements Serializable{

	private String pname;
	private String pimage;
	private String ptags;
	private String psatus;
	private String pdescription;
	private String pdate;
	private String purl;
	private Double pprice;
	private String plocation;
	private int pid;

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	public String getPimage() {
		return pimage;
	}

	public void setPimage(String pimage) {
		this.pimage = pimage;
	}

	public String getPtags() {
		return ptags;
	}

	public void setPtags(String ptags) {
		this.ptags = ptags;
	}

	public String getPsatus() {
		return psatus;
	}

	public void setPsatus(String psatus) {
		this.psatus = psatus;
	}

	public String getPdescription() {
		return pdescription;
	}

	public void setPdescription(String pdescription) {
		this.pdescription = pdescription;
	}

	public String getPdate() {
		return pdate;
	}

	public void setPdate(String pdate) {
		this.pdate = pdate;
	}

	public String getPurl() {
		return purl;
	}

	public void setPurl(String purl) {
		this.purl = purl;
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

}
