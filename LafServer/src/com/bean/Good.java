package com.bean;

public class Good {
	int gno;
	String name;
	String time;
	String describe;
	int uno;
	String type;
	String imagname;
	String imag;
	String adress;
	public String getAdress() {
		return adress;
	}
	public void setAdress(String adress) {
		this.adress = adress;
	}
	public String getImag() {
		return imag;
	}
	public void setImag(String imag) {
		this.imag = imag;
	}
	public int getGno() {
		return gno;
	}
	public void setGno(int gno) {
		this.gno = gno;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	public int getUno() {
		return uno;
	}
	public void setUno(int uno) {
		this.uno = uno;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getImagname() {
		return imagname;
	}
	public void setImagname(String imagname) {
		this.imagname = imagname;
	}
	public boolean isClaimstate() {
		return claimstate;
	}
	public void setClaimstate(boolean claimstate) {
		this.claimstate = claimstate;
	}
	boolean claimstate;
}
