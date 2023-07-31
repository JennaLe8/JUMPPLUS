package com.jump.grade_book_mgt.DaoInterface;

import com.jump.grade_book_mgt.Model.Class;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public interface ClassDao {

	List<Class> getAllClasses();
	Class findClassById(Integer id);
	Class findClassByName(String name);
	void addClass(Class theClass, Integer teacherId); 
	void deleteClass(Class theClass);
	

}
