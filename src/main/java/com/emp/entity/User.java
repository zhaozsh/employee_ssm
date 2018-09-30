package com.emp.entity;

/**
 * 用户表(T_USER)
 * @author zhaozs
 * @data 2018年9月29日
 */
public class User {
	private int uid;
	private String username;
	private String password;
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
