package com.jump.grade_book_mgt.DaoInterface;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jump.grade_book_mgt.Model.StudentGrade;

@Repository
public interface StudentGradeDao {
	List<StudentGrade> getAllStudentGrades();
	StudentGrade findStudentGradeById(Integer id);
	Double findStudentGradeByStudentId(Integer studentId);
	Double findGradeByStudentIdAndClassId(Integer studentId, Integer classId);
	List<StudentGrade> sortGradeByClassId(Integer classId);
	void addStudentGrade(StudentGrade grade); 
	void updateStudentGradeByByStudentId(Integer studentId, StudentGrade newGrade);
	void deleteStudentGrade(StudentGrade grade);

}
