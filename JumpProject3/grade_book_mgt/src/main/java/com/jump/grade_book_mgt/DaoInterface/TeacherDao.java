package com.jump.grade_book_mgt.DaoInterface;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jump.grade_book_mgt.Model.Teacher;
import com.jump.grade_book_mgt.Model.Class;

@Repository
public interface TeacherDao {
	List<Teacher> getAllTeachers();
	Teacher findTeacherById(Integer id);
	Teacher findTeacherByEmailAndPassword(String email, String password);
	//List<Class> getAllClasses();
	void addTeacher(Teacher teacher);
	void deleteTeacher(Teacher teacher);
}
