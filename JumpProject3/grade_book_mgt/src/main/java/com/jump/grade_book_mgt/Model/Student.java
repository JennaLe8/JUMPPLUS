package com.jump.grade_book_mgt.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="STUDENT")
public class Student {
	@Id
    @Column(name="STUDENT_ID")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer student_id;

	@Column(name="EMAIL", unique=true, columnDefinition = "VARCHAR(100) DEFAULT NULL")
	private String emai;
	
	@Column(name="PSW", unique=true, columnDefinition = "VARCHAR(100) DEFAULT NULL")
	private String password;
	
	@Column(name="FIRST_NAME", columnDefinition = "VARCHAR(100) DEFAULT NULL")
	private String firstName;
	
	@ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name = "CLASS_STUDENT",
    			joinColumns = {@JoinColumn(name = "STUDENT_ID")},
    			inverseJoinColumns = {@JoinColumn(name = "CLASS_ID")})
    private List<Class> classes;
	
	@OneToMany(mappedBy="student")
	private List<StudentGrade> grades = new ArrayList<StudentGrade>();
	
	@Column(name="LAST_NAME", columnDefinition = "VARCHAR(100) DEFAULT NULL")
	private String lastName;
	
	public Student() {
		
	}

	public Student(String emai, String password, String lastName, String firstName) {
		this.emai = emai;
		this.password = password;
		this.lastName = lastName;
		this.firstName = firstName;
	}
	
	public List<Class> getClasses() {
		return classes;
	}

	public void setClasses(List<Class> classes) {
		this.classes = classes;
	}

	public Integer getStudent_id() {
		return student_id;
	}

	public void setStudent_id(Integer student_id) {
		this.student_id = student_id;
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

	
	public List<StudentGrade> getGrades() {
		return grades;
	}

	public void setGrades(List<StudentGrade> grades) {
		this.grades = grades;
	}

	@Override
	public String toString() {
		return "Student [student_id=" + student_id + ", emai=" + emai + ", password=" + password + ", lastName="
				+ lastName + ", firstName=" + firstName + "]";
	}
	
	
	
}
