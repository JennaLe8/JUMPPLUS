package com.jump.contact;

public class Account {
	private int account_id;
	public int getAccount_id() {
		return account_id;
	}
	public void setAccount_id(int account_id) {
		this.account_id = account_id;
	}
	private String email;
	private String password;
    private final String regex = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$";
	public Account(String email, String password) {
		super();
		this.email = email;
		this.password = password;
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
	public String getRegex() {
		return regex;
	}
	@Override
	public String toString() {
		return "Account [email=" + email + ", password=" + password + ", regex=" + regex + "]";
	}
    
}
