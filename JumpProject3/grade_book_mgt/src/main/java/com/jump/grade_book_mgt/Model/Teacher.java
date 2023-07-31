package com.jump.grade_book_mgt.Model;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.transaction.Transactional;

@Entity
@Table(name="TEACHER")
public class Teacher {
	@Id
    @Column(name="TEACHER_ID")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer teacher_id;
	
	@Column(name="EMAIL", unique=true, columnDefinition = "VARCHAR(100) DEFAULT NULL")
	private String emai;
	
	@Column(name="PSW", unique=true, columnDefinition = "VARCHAR(100) DEFAULT NULL")
	private String password;
	
	@Column(name="LAST_NAME", columnDefinition = "VARCHAR(100) DEFAULT NULL")
	private String lastName;
	
	@Column(name="FIRST_NAME", columnDefinition = "VARCHAR(100) DEFAULT NULL")
	private String firstName;
	
	@OneToMany(mappedBy="teacher")
	private List<Class> classes = new ArrayList<Class>();
	
	
    public Teacher() {
    	
    }

	public Teacher(String emai, String password, String lastName, String firstName) {
		this.emai = emai;
		this.password = password;
		this.lastName = lastName;
		this.firstName = firstName;
	}

	public Integer getTeacher_id() {
		return teacher_id;
	}

	public void setTeacher_id(Integer teacher_id) {
		this.teacher_id = teacher_id;
	}

	public String getEmai() {
		return emai;
	}

	public void setEmai(String emai) {
		this.emai = emai;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public List<Class> getClasses() {
		return classes;
	}

	public void setClasses(List<Class> classes) {
		this.classes = classes;
	}

	@Override
	public String toString() {
		return "Teacher [teacher_id=" + teacher_id + ", emai=" + emai + ", password=" + password + ", lastName="
				+ lastName + ", firstName=" + firstName + "]";
	}
    
    
	
	
	
	
	

}
