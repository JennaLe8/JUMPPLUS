package com.jump.grade_book_mgt.DaoImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jump.grade_book_mgt.DaoInterface.TeacherDao;
import com.jump.grade_book_mgt.Model.Class;
import com.jump.grade_book_mgt.Model.Student;
import com.jump.grade_book_mgt.Model.StudentGrade;
import com.jump.grade_book_mgt.Model.Teacher;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
@Repository
@Transactional
public class TeacherDaoImpl implements TeacherDao{

	@Autowired
    private EntityManager entityManager;

	@Override
	public List<Teacher> getAllTeachers() {
		return entityManager.createQuery("SELECT * FROM TEACHER", Teacher.class).getResultList();
	}

	@Override
	public Teacher findTeacherById(Integer id) {
		return entityManager.find(Teacher.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Teacher findTeacherByEmailAndPassword(String email, String password) {
		Teacher teacher = null;
		
		List<Teacher> res =  entityManager
			.createNativeQuery("SELECT * FROM TEACHER WHERE EMAIL = ? AND PSW = ?", Teacher.class)
			.setParameter(1, email)
			.setParameter(2, password)
			.getResultList();
		if(res.size() == 1) {
			teacher = res.get(0);
		}
		return teacher;
	}

	@Override
	public void addTeacher(Teacher teacher) {
		entityManager.persist(teacher);
	}

	@Override
	public void deleteTeacher(Teacher teacher) {
		entityManager.remove(teacher);
		
	}

}
