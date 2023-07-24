package com.jump.contact;
import java.util.*;

public class Contact {
	private int contact_id;
	public int getContact_id() {
		return contact_id;
	}
	public void setContact_id(int contact_id) {
		this.contact_id = contact_id;
	}
	private String name;
	private int number;
	private String email;
	public Contact(String name, int number, String email) {
		super();
		this.name = name;
		this.number = number;
		this.email = email;
	}
	public String getName() {
		return name;
	}
	private void setName(String name) {
		this.name = name;
	}
	public int getNumber() {
		return number;
	}
	private void setNumber(int number) {
		this.number = number;
	}
	public String getEmail() {
		return email;
	}
	private void setEmail(String email) {
		this.email = email;
	}
	@Override
	public String toString() {
		return "Model [name=" + name + ", number=" + number + ", email=" + email + "]";
	}
	
	
	
	
	
	
	
	
	

}
