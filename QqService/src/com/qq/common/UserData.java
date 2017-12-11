/**
 * 用户个人资料
 */

package com.qq.common;

import java.util.Date;

public class UserData implements java.io.Serializable {
	private String sex;
	private Date birthday;
	private int age;
	private String phoneNum;
	private String email;
	private String blood;
	private String sign;
	
	public String getSex() {
		return sex;
	}
	public Date getBirthday() {
		return birthday;
	}
	public int getAge() {
		return age;
	}
	public String getPhoneNum() {
		return phoneNum;
	}
	public String getEmail() {
		return email;
	}
	public String getBlood() {
		return blood;
	}
	public String getSign() {
		return sign;
	}
	
	public void setSex(String sex) {
		this.sex = sex;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setBlood(String blood) {
		this.blood = blood;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	
	
}
