package com.jump.grade_book_mgt.DaoImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jump.grade_book_mgt.DaoInterface.ClassDao;
import com.jump.grade_book_mgt.Model.Class;
import com.jump.grade_book_mgt.Model.Student;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public class ClassDaoImpl implements ClassDao{

	@Autowired
    private EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Class> getAllClasses() {
		return entityManager.createNativeQuery("SELECT * FROM CLASS", Class.class).getResultList();
	}

	@Override
	public Class findClassById(Integer id) {
		return entityManager.find(Class.class, id);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Class findClassByName(String name) {
		Class theClass = null;
		
		List<Class> res =  entityManager
			.createNativeQuery("SELECT * FROM CLASS WHERE CLASS_NAME = ?", Class.class)
			.setParameter(1, name)
			.getResultList();
		if(res.size() == 1) {
			theClass = res.get(0);
		}
		return theClass;
	}

	@Override
	@Transactional
	public void addClass(Class theClass, Integer teacherId) {
		//entityManager.merge(theClass);
		entityManager.createNativeQuery("INSERT INTO CLASS(CLASS_NAME, HOMEWORK, HOMEWORK_WEIGHT, TEST, TEST_WEIGHT, LAB, LAB_WEIGHT, PROJECT, PROJECT_WEIGHT, PARTICIPATION, PARTICIPATION_WEIGHT, TEACHER_ID) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)")
				.setParameter(1, theClass.getClass_name())
				.setParameter(2, theClass.isHomework())
				.setParameter(3, theClass.getHomeworkWeight())
				.setParameter(4, theClass.isTest())
		.setParameter(5, theClass.getTestWeight())
		.setParameter(6, theClass.isLab())
		.setParameter(7, theClass.getLabWeight())
		.setParameter(8, theClass.isProject())
		.setParameter(9, theClass.getProjectWeight())
		.setParameter(10, theClass.isParticipationConfig())
		.setParameter(11, theClass.getParticipationWeight())
		.setParameter(12, teacherId)
		.executeUpdate();
	}

	@Override
	public void deleteClass(Class theClass) {
		entityManager.remove(theClass);
		
	}

}
