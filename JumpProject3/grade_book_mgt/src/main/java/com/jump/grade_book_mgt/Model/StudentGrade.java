package com.jump.grade_book_mgt.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="STUDENT_GRADE")
public class StudentGrade {
	
	@Id
    @Column(name="STUDENT_GRADE_ID")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer student_grade_id;
	
	@Column(name="GRADE", columnDefinition = "DOUBLE")
	private Double grade;
	
	// FK to classId
	@ManyToOne
    @JoinColumn(name = "CLASS_ID")
    private Class theClass;

	@ManyToOne
    @JoinColumn(name = "STUDENT_ID")
	private Student student;
	
	public StudentGrade() {
		
	}

	public StudentGrade(Double grade, Class theClass) {
		this.grade = grade;
		this.theClass = theClass;
	}

	public Integer getStudent_grade_id() {
		return student_grade_id;
	}

	public void setStudent_grade_id(Integer student_grade_id) {
		this.student_grade_id = student_grade_id;
	}

	public Double getGrade() {
		return grade;
	}

	public void setGrade(Double grade) {
		this.grade = grade;
	}

	public Class getTheClass() {
		return theClass;
	}

	public void setClass(Class theClass) {
		this.theClass = theClass;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}
	
	

}
