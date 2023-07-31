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
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="CLASS")
public class Class {
	@Id
    @Column(name="CLASS_ID")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer class_id;
	
	@Column(name="CLASS_NAME", columnDefinition = "VARCHAR(100) DEFAULT NULL")
	private String class_name;

	
	@OneToMany(mappedBy="theClass")
	private List<StudentGrade> grades;
	
	// FK to Teacher
	@ManyToOne
    @JoinColumn(name = "TEACHER_ID")
    private Teacher teacher;
	
	@Column(name="HOMEWORK", columnDefinition = "BOOLEAN")
    private boolean homework;
        	
	@Column(name="HOMEWORK_WEIGHT", columnDefinition = "DOUBLE")
	private Double homeworkWeight = 0.0;
	
	@Column(name="TEST", columnDefinition = "BOOLEAN")
	private boolean test;
			
	@Column(name="TEST_WEIGHT", columnDefinition = "DOUBLE")
	private Double testWeight = 0.0;
	
	@Column(name="LAB", columnDefinition = "BOOLEAN")
	private boolean lab;
			
	@Column(name="LAB_WEIGHT", columnDefinition = "DOUBLE")
	private Double labWeight = 0.0;
	
	@Column(name="PROJECT", columnDefinition = "BOOLEAN")
	private boolean project;
		
	@Column(name="PROJECT_WEIGHT", columnDefinition = "DOUBLE")
	private Double projectWeight = 0.0;
	
	@Column(name="PARTICIPATION", columnDefinition = "BOOLEAN")
	private boolean participationConfig;
			
	@Column(name="PARTICIPATION_WEIGHT", columnDefinition = "DOUBLE")
	private Double participationWeight = 0.0;
	
	@ManyToMany(mappedBy = "classes", cascade = { CascadeType.ALL })
    private List<Student> students = new ArrayList<Student>();

	public Class() {
		
	}

	public Class(String class_name, Teacher teacher) {
		this.class_name = class_name;
		this.teacher = teacher;
	}

	public Integer getClass_id() {
		return class_id;
	}

	public void setClass_id(Integer class_id) {
		this.class_id = class_id;
	}

	public String getClass_name() {
		return class_name;
	}

	public void setClass_name(String class_name) {
		this.class_name = class_name;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public List<Student> getStudents() {
		return students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}

	public boolean isHomework() {
		return homework;
	}

	public void setHomework(boolean homework) {
		this.homework = homework;
	}

	public boolean isTest() {
		return test;
	}

	public void setTest(boolean test) {
		this.test = test;
	}

	public boolean isLab() {
		return lab;
	}

	public void setLab(boolean lab) {
		this.lab = lab;
	}

	public boolean isParticipationConfig() {
		return participationConfig;
	}

	public void setParticipationConfig(boolean participationConfig) {
		this.participationConfig = participationConfig;
	}

	public Double getHomeworkWeight() {
		return homeworkWeight;
	}

	public void setHomeworkWeight(Double homeworkWeight) {
		this.homeworkWeight = homeworkWeight;
	}

	public Double getTestWeight() {
		return testWeight;
	}

	public void setTestWeight(Double testWeight) {
		this.testWeight = testWeight;
	}

	public Double getLabWeight() {
		return labWeight;
	}

	public void setLabWeight(Double labWeight) {
		this.labWeight = labWeight;
	}
	

	public Double getParticipationWeight() {
		return participationWeight;
	}

	public void setParticipationWeight(Double participationWeight) {
		this.participationWeight = participationWeight;
	}
	
	
	public boolean isProject() {
		return project;
	}

	public void setProject(boolean project) {
		this.project = project;
	}

	
	public Double getProjectWeight() {
		return projectWeight;
	}

	public void setProjectWeight(Double projectWeight) {
		this.projectWeight = projectWeight;
	}

	public List<StudentGrade> getGrades() {
		return grades;
	}

	public void setGrades(List<StudentGrade> grades) {
		this.grades = grades;
	}
	
}
