package com.jump.grade_book_mgt.DaoImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jump.grade_book_mgt.DaoInterface.StudentGradeDao;
import com.jump.grade_book_mgt.Model.Student;
import com.jump.grade_book_mgt.Model.StudentGrade;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public class StudentGradeDaoImpl implements StudentGradeDao{

	@Autowired
    private EntityManager entityManager;
	
	@Override
	public List<StudentGrade> getAllStudentGrades() {
		return entityManager.createQuery("SELECT * FROM STUDENT_GRADE", StudentGrade.class).getResultList();

	}

	@Override
	public StudentGrade findStudentGradeById(Integer id) {
		return entityManager.find(StudentGrade.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Double findStudentGradeByStudentId(Integer studentId) {
		Double grade = 0.0;
		
		List<StudentGrade> res =  entityManager
			.createNativeQuery("SELECT * FROM STUDENT_GRADE WHERE STUDENT_ID = ? ", StudentGrade.class)
			.setParameter(1, studentId)
			.getResultList();
		if(res.size() == 1) {
			grade = res.get(0).getGrade();
		}
		return grade;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Double findGradeByStudentIdAndClassId(Integer studentId, Integer classId)
	{
		Double grade = 0.0;
		
		List<StudentGrade> res =  entityManager
			.createNativeQuery("SELECT * FROM STUDENT_GRADE WHERE STUDENT_ID = ? AND CLASS_ID = ?", StudentGrade.class)
			.setParameter(1, studentId)
			.setParameter(2, classId)
			.getResultList();
		if(res.size() == 1) {
			grade = res.get(0).getGrade();
		}
		return grade;
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<StudentGrade> sortGradeByClassId(Integer classId){
		return entityManager.createNativeQuery("SELECT * FROM STUDENT_GRADE WHERE CLASS_ID = ? "
				+ "ORDER BY GRADE", Student.class)
				.setParameter(1, classId)
				.getResultList();
	}
	
	@Override
	public void addStudentGrade(StudentGrade grade) {
		/*
		 * // Add grade to grade db and set student/class record associated with this grade accordingly 
			StudentGrade studentGrade = new StudentGrade();
			studentGrade.setGrade(weightedAverage);
			studentGrade.setClass(theClass);
			gradeDao.addStudentGrade(studentGrade);
			student.getGrades().add(studentGrade);
			theClass.getGrades().add(studentGrade);
		 */
		entityManager
		.createNativeQuery("INSERT INTO STUDENT_GRADE(GRADE,CLASS_ID, STUDENT_ID) "
				+ "VALUES(?,?, ?)", Student.class)
		.setParameter(1, grade.getGrade())
		.setParameter(2, grade.getTheClass().getClass_id())
		.setParameter(3, grade.getStudent().getStudent_id())
		.executeUpdate();

	}

	@Override
	public void updateStudentGradeByByStudentId(Integer studentId, StudentGrade newGrade) {
//		entityManager
//		.createNativeQuery("INSERT INTO STUDENT(EMAIL,PSW,FIRST_NAME,LAST_NAME) "
//				+ "VALUES(?,?,?,?)", Student.class)
//		.setParameter(1, theStudent.getEmai())
//		.setParameter(2, theStudent.getPassword())
//		.setParameter(3, theStudent.getFirstName())
//		.setParameter(4, theStudent.getLastName())
//		.executeUpdate();
		
		entityManager
			.createNativeQuery("UPDATE STUDENT_GRADE SET GRADE = ?"
					+ "WHERE STUDENT_ID=?", StudentGrade.class)
					.setParameter(1, newGrade)
					.setParameter(2, studentId)
					.executeUpdate();
			
	}

	@Override
	public void deleteStudentGrade(StudentGrade grade) {
		entityManager.remove(grade);
		
	}

}
