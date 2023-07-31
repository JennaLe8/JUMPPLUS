package com.jump.grade_book_mgt.DaoImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jump.grade_book_mgt.DaoInterface.StudentDao;
import com.jump.grade_book_mgt.Model.Student;
import com.jump.grade_book_mgt.Model.StudentGrade;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public class StudentDaoImpl implements StudentDao{
	
	@Autowired
    private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	@Override
	public List<Student> getAllStudents() {
		return entityManager.createNativeQuery("SELECT * FROM STUDENT", Student.class).getResultList();
		
	}

	@Override
	public Student findStudentById(Integer id) {
		return entityManager.find(Student.class, id);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public Student findStudentByEmailAndPassword(String email, String password) {
		Student student = null;
	
		List<Student> res =  entityManager
			.createNativeQuery("SELECT * FROM STUDENT WHERE EMAIL = ? AND PSW = ?", Student.class)
			.setParameter(1, email)
			.setParameter(2, password)
			.getResultList();
		if(res.size() == 1) {
			student = res.get(0);
		}
		return student;
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public List<Student> sortStudentsByName() {
		return entityManager.createNativeQuery("SELECT * FROM STUDENT ORDER BY FIRST_NAME, LAST_NAME", Student.class)
					.getResultList();
	}
	@Override
	public void addStudent(Student theStudent) {
//		entityManager
//			.createNativeQuery("INSERT INTO STUDENT(EMAIL,PSW,FIRST_NAME,LAST_NAME) "
//					+ "VALUES(?,?,?,?)", Student.class)
//			.setParameter(1, theStudent.getEmai())
//			.setParameter(2, theStudent.getPassword())
//			.setParameter(3, theStudent.getFirstName())
//			.setParameter(4, theStudent.getLastName())
//			.executeUpdate();
		entityManager.persist(theStudent);
	}

	@Override
	public void deleteStudent(Student theStudent) {
		entityManager.remove(theStudent);
		
	}


}
