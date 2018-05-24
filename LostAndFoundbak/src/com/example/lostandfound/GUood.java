package com.example.lostandfound;

public class GUood {
	String image;
	String uno;
	String gno;
	String name;
	String unno;
	String time;
	String tel;
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public GUood(String image,String uno,String gno,String name,String unno,String time,String tel){
		this.image=image;
		this.unno=unno;
		this.uno=uno;
		this.gno=gno;
		this.name=name;
		this.time=time;
		this.tel=tel;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getUno() {
		return uno;
	}
	public void setUno(String uno) {
		this.uno = uno;
	}
	public String getGno() {
		return gno;
	}
	public void setGno(String gno) {
		this.gno = gno;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUnno() {
		return unno;
	}
	public void setUnno(String unno) {
		this.unno = unno;
	}
	
}
