package kr.ac.woosuk.computereng.models;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class UserDTO {

	private String id;
	private String email;
	private String password;
	private int job;
	private String gender;
	private String introduction;
	private Date createDate;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getJob() {
		return job;
	}
	public void setJob(int job) {
		this.job = job;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	public String getFormatDate() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY/MM/dd HH:mm:ss");
		dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+9"));
		return dateFormat.format(this.createDate);
	}


}
