package com.jump.grade_book_mgt.DaoInterface;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jump.grade_book_mgt.Model.Student;

@Repository
public interface StudentDao {
	List<Student> getAllStudents();
	Student findStudentById(Integer id);
	Student findStudentByEmailAndPassword(String email, String password);
	//Student findStudentByGradeIdAndClassId(Integer gradeId, Integer classId);
	List<Student> sortStudentsByName();
	void addStudent(Student theStudent); 
	void deleteStudent(Student theStudent);
}
