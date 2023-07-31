package com.jump.grade_book_mgt.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.jump.grade_book_mgt.DaoInterface.ClassDao;
import com.jump.grade_book_mgt.DaoInterface.StudentDao;
import com.jump.grade_book_mgt.DaoInterface.StudentGradeDao;
import com.jump.grade_book_mgt.DaoInterface.TeacherDao;
import com.jump.grade_book_mgt.Model.Student;
import com.jump.grade_book_mgt.Model.StudentGrade;
import com.jump.grade_book_mgt.Model.Teacher;

import jakarta.transaction.Transactional;

import com.jump.grade_book_mgt.Model.Class;

@Service
@Transactional 
public class GradeBookService {
	@Autowired
    private TeacherDao teacherDao;
	
	@Autowired
	private StudentDao studentDao;
	
	@Autowired
	private StudentGradeDao gradeDao;
	
	@Autowired
	private ClassDao classDao;
	
	public boolean createTeacherAccount(Teacher teacher) // create teacher account 
    {
        try {
            // validation
            if (teacher.getEmai() == "" || teacher.getPassword() == "") {
                System.out.println("All Field Required!");
                return false;
            }
            teacherDao.addTeacher(teacher);
 
            return true;
            
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
	
	public boolean createStudentAccount(Student student)  // create student account 
    {
        try {
            // validation
            if (student.getEmai() == "" || student.getPassword() == "") {
                System.out.println("All Field Required!");
                return false;
            }
            studentDao.addStudent(student);
 
            return true;
            
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
	public boolean loginStudentAccount(String email, String password) {
		try {
			 // validation
            if (email == "" || password == "") {
                System.out.println("All Field Required!");
                return false;
            }
            
            Student student = studentDao.findStudentByEmailAndPassword(email, password);
            
            BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
            
            if (student != null) {
                int ch = 0;
             
                while (true) {
                    try {
                        System.out.println("Welcome back, "+ student.getFirstName() + " " + student.getLastName());;
                        System.out.println("1)View the classes you are attending");
                        System.out.println("2)Log out");      
                        
                        System.out.print("Enter Choice:");
                        ch = Integer.parseInt(sc.readLine());
                        if (ch == 1) {
                        	this.viewClassesForStudent(student);

                        }
                        else if(ch == 2) {
                        	break;
                        }
                        else {
                            System.out.println(
                                "Err : Enter Valid input!\n");
                        }
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }else {
                return false;
            }
            
            return true;
		}catch(Exception e) {
            e.printStackTrace();
        }
		return false;
	}
	public boolean loginTeacherAccount(String email, String password) {
		 try {
	            // validation
	            if (email == "" || password == "") {
	                System.out.println("All Field Required!");
	                return false;
	            }
	            
	            Teacher teacher = teacherDao.findTeacherByEmailAndPassword(email, password);
	            
	            BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
	 
	            if (teacher != null) {
	                int ch = 0;
	             
	                while (true) {
	                    try {
	                        System.out.println("Welcome back, "+ teacher.getFirstName() + " " + teacher.getLastName());;
	                        System.out.println("1)View the classes you are teaching");
	                        System.out.println("2)Create a new class");   
	                        System.out.println("3)Log out");      
	                        
	                        System.out.print("Enter Choice:");
	                        ch = Integer.parseInt(sc.readLine());
	                        if (ch == 1) {
	                        	this.viewClasses(teacher);
    
	                        }
	                        else if (ch == 2) {
	                        	this.addNewClass(teacher);
	                        }
	                        else if(ch == 3) {
	                        	break;
	                        }
	                        else {
	                            System.out.println(
	                                "Err : Enter Valid input!\n");
	                        }
	                    }
	                    catch (Exception e) {
	                        e.printStackTrace();
	                    }
	                }
	            }else {
	                return false;
	            }
	            return true;
	        }
	        catch (Exception e) {
	            e.printStackTrace();
	        }
	        return false;
	}
	
	public void viewClassesForStudent(Student student) {
		List<Class> classes = student.getClasses();
		  System.out.println(
	                "-----------------------------------------------------------");
		  System.out.println("----------------- Classes ----------------------- ");
	      
	      if(classes.size() > 0) {
	    	  int count = 0;
	    	  while(count < classes.size()) {
	    		  System.out.println(classes.get(count).getClass_name());        
	    		  count++;
	    	  }
	    	 
	      }
	}
	
	public void viewClasses(Teacher teacher) {
		List<Class> classes = teacher.getClasses();
		  System.out.println(
	                "-----------------------------------------------------------");
		  System.out.println("----------------- Classes ----------------------- ");
	      
	      if(classes.size() > 0) {
	    	  int count = 0;
	    	  while(count < classes.size()) {
	    		  System.out.println(classes.get(count).getClass_name());        
	    		  count++;
	    	  }
	    	 
	      }
	      this.selectAClassMenu();
	      
	}
	public void selectAClassMenu() {
		int ch = 0;
		BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
		while(true) {
			try {
				System.out.println("1)Select Class");      
				System.out.println("2)Exit this menu");  
				System.out.print("Enter Choice: ");
                ch = Integer.parseInt(sc.readLine());
                if(ch == 1) {
                	int ch2 = 0;
                	System.out.println("Enter class name: ");  
                	String className = sc.readLine();
                	Class theClass = classDao.findClassByName(className);
                	System.out.println("theClass: "+ theClass.getClass_name() + " " + theClass.getTeacher().getTeacher_id());
    				while(true) {
    					System.out.println("1)View individual student grades and Average/Median grade for this class");
        				System.out.println("2)Sort students by alphabetical order and by their grade");
        				System.out.println("3)Update a student’s grade");
        				System.out.println("4)Remove a student from a class");
        				System.out.println("5)Add student to a class");
        				System.out.println("6)Quit");
        				System.out.print("Enter Choice: ");
        				ch2 = Integer.parseInt(sc.readLine());
    					if(ch2 == 1) {
    						this.viewStudents(theClass);
    					}
    					else if(ch2 == 2) {
    						System.out.println("Sort by name or by grade? (enter 'name' or 'grade') ");  
    	                	String sortChoice = sc.readLine();
    	                	if(sortChoice.equalsIgnoreCase("name")) {
    	                		this.sortStudentsByName(theClass);
    	                	}else {
    	                		this.sortStudentsByGrade(theClass);
    	                	}
    					}
    					else if(ch2 == 3) {
    						this.updateStudentGrade(theClass);
    					}
    					else if(ch2 == 4) {
    						this.removeStudentFromClass(theClass);
    					}
    					else if(ch2 == 5) {
    						this.addStudentToClass(theClass);
    					}
    					else if(ch2 == 6) {
    						break;
    					}
    				}
                }
                else if(ch == 2) {
                	break;
                }
                
			}catch(Exception e) {
				e.printStackTrace();
			}
		}

	}
	
	public Double findSum(Double[] grades) {
	    Double sum = 0.0;
	    for (Double value : grades) {
	        sum += value;
	    }
	    return sum;
	}
	public Double findAverage(Double[] grades) {
	    Double sum = findSum(grades);
	    return (double) sum / grades.length;
	}
	/*
	 * public static float median(float[] values) {
        if (values.length == 1) {
            return values[0];
        }
        Arrays.sort(values);
        int middle = values.length / 2;
        if (values.length % 2 == 1) {
            return values[middle];
        } else {
            return (values[middle - 1] + values[middle]) / 2.0f;
        }
    }
	 */
	public Double findMedian(Double[] grades){
		Arrays.sort(grades);
		double median = 0.0;
		if (grades.length % 2 == 0)
		    median = ((double)grades[grades.length/2] + (double)grades[grades.length/2 - 1])/2;
		else {
		    median = (double) grades[grades.length/2];
		}
		return median;
	}
	

	
	public void viewStudents(Class theClass) {
	int num = theClass.getStudents().size();
	Double[] grades = new Double[num];
		 
	  if(!(theClass.getStudents().isEmpty()) ){
	
	  	System.out.println("--------------------------------------------------------------------------------------");
	      System.out.printf("%20s %20s %20s\n",
	                        "Student First Name", "Student Last Name", "Grade");
	 	int count = 0;
	 	while(count < theClass.getStudents().size()) {
	 		Double grade = gradeDao.findGradeByStudentIdAndClassId(theClass.getStudents().get(count).getStudent_id(), theClass.getClass_id());
	 		grades[count] = grade;
	 		System.out.printf("%20s %20s %20f\n",
	 			theClass.getStudents().get(count).getFirstName(),
	 			theClass.getStudents().get(count).getLastName(),
	             grade);
	 		count++;
	 	}
	 	  
	     Double averageGrade = this.findAverage(grades);
	     Double median = this.findMedian(grades);
	     
	     System.out.println("AVERAGE GRADE: " + averageGrade);
	     System.out.println("MEDIAN: " + median);
	     System.out.println("-------------------------------------------------------------------------------------\n");
	
	  }
	  else {
	   	System.out.println("No data to view");
	   }

		
	}
	    
	public void sortStudentsByName(Class theClass){
		List<Student> sortedStudents  = new ArrayList<Student>();
    	sortedStudents = studentDao.sortStudentsByName();
    	
    	
	    int count = 0;
	    if(sortedStudents.size() > 0) {
	    	System.out.println(
	                 "-------------------------------------------------------------------------------------------------");
		    System.out.printf("%20s %20s",
		                       "Student First Name", "Student Last Name");
		  
	    	 while (count < sortedStudents.size()) {
	    		 System.out.println();
	             System.out.printf("%20s %20s\n",
	                   sortedStudents.get(count).getFirstName(),
	                   sortedStudents.get(count).getLastName()
	             );
	             count++;
		     }
		     System.out.println(
		         "---------------------------------------------------------------------------------------------------\n");
	    }
	    else {
	    	System.out.println("No students to view");
	    }
       
    }
	public void sortStudentsByGrade(Class theClass){
		List<StudentGrade> sortedGrades = new ArrayList<StudentGrade>();
    	sortedGrades = gradeDao.sortGradeByClassId(theClass.getClass_id());
  
	  
	    if(sortedGrades.size() > 0) {
	    	System.out.println(
	                 "----------------------------------------------------------------------------------------------------");
		    System.out.printf("%20s %20s %20s\n",
		                       "Student First Name", "Student Last Name", "Grade");
        	int count = 0;
        	while(count < sortedGrades.size()) {
	    		System.out.println();
        		System.out.printf("20s %20s %20f\n",
                    sortedGrades.get(count).getStudent().getFirstName(),
                    sortedGrades.get(count).getStudent().getLastName(),
                    sortedGrades.get(count).getGrade()
                );
        		count++;
        	}
        	 System.out.println(
    		         "---------------------------------------------------------------------------------------------------\n");
    	    
         }
	    else {
	    	System.out.println("No students to view");
	    }
	   
    }
	
	public void updateStudentGrade(Class theClass) {
		System.out.println(" --------------------- Class " + theClass.getClass_name() + "---------------------");
		System.out.println("Based on the weighted grading system you configure for this class, please answer the "
				+ "following questions so student's weight grade will be calculated accordingly "
				+ "and added to the system");
		List<Double> hwGrades = new ArrayList<Double>();
		List<Double> testGrades = new ArrayList<Double>();
		List<Double> labGrades = new ArrayList<Double>();
		List<Double> projectGrades = new ArrayList<Double>();
		Integer studentId;
		Double weightedAverage = 0.0;
		Double participationGrade;
		BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
		try {
			System.out.println("What is the student's ID who you want to update the grade? ");
			studentId = Integer.parseInt(sc.readLine());
			Student student = studentDao.findStudentById(studentId);
			if(theClass.isHomework()) {
				Double grade = 0.0;
				boolean more = false;
				do {
					System.out.println("What is the homework's grade of this student? ");
					grade = Double.parseDouble(sc.readLine());
					hwGrades.add(grade);
					System.out.println("Do you have more grade to enter for this student's homework? (enter 'y' or 'no') ");
					more = Boolean.parseBoolean(sc.readLine());
				}while(more); 
				weightedAverage += this.calculateAverageForOneCategory(hwGrades, theClass.getHomeworkWeight(), hwGrades.size());
			}
			if(theClass.isTest()) {
				Double grade = 0.0;
				boolean more = false;
				do {
					System.out.println("What is the test's grade of this student? ");
					grade = Double.parseDouble(sc.readLine());
					testGrades.add(grade);
					System.out.println("Do you have more grade to enter for this student's test? (enter 'y' or 'no') ");
					more = Boolean.parseBoolean(sc.readLine());
				}while(more); 
				weightedAverage += this.calculateAverageForOneCategory(testGrades, theClass.getTestWeight(), testGrades.size());
			}
			if(theClass.isProject()) {
				Double grade = 0.0;
				boolean more = false;
				do {
					System.out.println("What is the project's grade of this student? ");
					grade = Double.parseDouble(sc.readLine());
					projectGrades.add(grade);
					System.out.println("Do you have more grade to enter for this student's project? (enter 'y' or 'no') ");
					more = Boolean.parseBoolean(sc.readLine());
				}while(more); 
				weightedAverage += this.calculateAverageForOneCategory(projectGrades, theClass.getProjectWeight(), projectGrades.size());

			}
			if(theClass.isLab()) {
				Double grade = 0.0;
				boolean more = false;
				do {
					System.out.println("What is the lab's grade of this student? ");
					grade = Double.parseDouble(sc.readLine());
					labGrades.add(grade);
					System.out.println("Do you have more grade to enter for this student's lab? (enter 'y' or 'no') ");
					more = Boolean.parseBoolean(sc.readLine());
				}while(more); 
				weightedAverage += this.calculateAverageForOneCategory(labGrades, theClass.getLabWeight(), labGrades.size());

			}
			if(theClass.isParticipationConfig()) {
				Double grade = 0.0;
				System.out.println("What is the participation's grade of this student? ");
				grade = Double.parseDouble(sc.readLine());
				participationGrade = grade;
				
				weightedAverage += participationGrade * theClass.getParticipationWeight();
			}
			
			// Add grade to grade db and set student/class record associated with this grade accordingly 
			StudentGrade studentGrade = new StudentGrade();
			studentGrade.setGrade(weightedAverage);
			studentGrade.setClass(theClass);
			studentGrade.setStudent(student);
			gradeDao.addStudentGrade(studentGrade);
			student.getGrades().add(studentGrade);
			theClass.getGrades().add(studentGrade);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
		
		
	}
	public Double calculateAverageForOneCategory(List<Double> grades, Double weight, int num) {
		 Double res = 0.0;
	      
	        for (int i = 0; i < num; i++)
	        {
	        	res = res + grades.get(i) * weight;
	         
	        }
	      
	        return res;
	}
	public boolean removeStudentFromClass(Class theClass) {
		BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
		boolean res = false;
		try {
			System.out.println("What is the student's ID? ");
			Integer studentId = Integer.parseInt(sc.readLine());
			Student student = studentDao.findStudentById(studentId);
			res = student.getClasses().remove(theClass);
			theClass.getStudents().remove(student);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return res;
			
	}
	
	public boolean addStudentToClass(Class theClass) {
		boolean res = false;
		BufferedReader sc = new BufferedReader(
                new InputStreamReader(System.in));
		try {
			System.out.println("What is the student's ID? ");
			Integer studentId = Integer.parseInt(sc.readLine());
			Student student = studentDao.findStudentById(studentId);
			res = student.getClasses().add(theClass);
			theClass.getStudents().add(student);
			res = true;
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return res;
	}
	
	public void addNewClass(Teacher teacher) {
		String ans = "";
		Double weight = 0.0;
		BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
		
		try {
			Class theClass = new Class();
			theClass.setHomework(false);
			theClass.setTest(false);
			theClass.setLab(false);
			theClass.setParticipationConfig(false);
			System.out.println("What is the class's name? ");
			ans = sc.readLine();
			theClass.setClass_name(ans);
			System.out.println("Configuration questions about weighted grading system for this class");
			System.out.println("Does your class need 'Homework' category? (enter 'y' or 'n')");
			ans = sc.readLine();
			if(ans.equalsIgnoreCase("y")) {
				theClass.setHomework(true);
				System.out.println("What is the homework's weight? ");
				weight = Double.parseDouble(sc.readLine());
				theClass.setHomeworkWeight(weight);
			}
			System.out.println("Does your class need 'Test' category? (enter 'y' or 'n')");
			ans = sc.readLine();
			if(ans.equalsIgnoreCase("y")) {
				theClass.setTest(true);
				System.out.println("What is the test's weight? ");
				weight = Double.parseDouble(sc.readLine());
				theClass.setTestWeight(weight);
			}
			System.out.println("Does your class need 'Lab' category? (enter 'y' or 'n')");
			ans = sc.readLine();
			if(ans.equalsIgnoreCase("y")) {
				theClass.setLab(true);
				System.out.println("What is the lab's weight? ");
				weight = Double.parseDouble(sc.readLine());
				theClass.setLabWeight(weight);
			}
			System.out.println("Does your class need 'Project' category? (enter 'y' or 'n')");
			ans = sc.readLine();
			if(ans.equalsIgnoreCase("y")) {
				theClass.setProject(false);
				System.out.println("What is the project's weight? ");
				weight = Double.parseDouble(sc.readLine());
				theClass.setProjectWeight(weight);
			}
			System.out.println("Does your class need 'Participation' category? (enter 'y' or 'n')");
			ans = sc.readLine();
			if(ans.equalsIgnoreCase("y")) {
				theClass.setParticipationConfig(true);
				System.out.println("What is the test's weight? ");
				weight = Double.parseDouble(sc.readLine());
				theClass.setParticipationWeight(weight);
			}
			theClass.setTeacher(teacher);
			teacher.getClasses().add(theClass);
			
			classDao.addClass(theClass, teacher.getTeacher_id());
		}catch(Exception e) {
			e.printStackTrace();
		}

		
	}
}
