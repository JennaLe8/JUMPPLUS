package com.jump.grade_book_mgt;
import com.jump.grade_book_mgt.Service.*;
import com.jump.grade_book_mgt.Model.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;


@SpringBootApplication
public class GradeBookMgtApplication implements ApplicationRunner{
	static final String regexEmail = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$";
    //static final String regexPassword = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&-+=()])(?=\\\\S+$).{8, 20}$";

	@Autowired
	private GradeBookService service;
	
	//static ApplicationContext context; 
	public static void main(String[] args) throws IOException {
		 
//        context = SpringApplication.run(GradeBookMgtApplication.class, args);
//        service = (GradeBookService) context.getBean("service");
		SpringApplication.run(GradeBookMgtApplication.class, args);
		
	 }
	 public static boolean isValidEmail(String email)
     {
 
         // Compile the ReGex
         Pattern p = Pattern.compile(regexEmail);
  
     
         Matcher m = p.matcher(email);
  
         // Return if the email
         // matched the ReGex
         return m.matches();
     
		
      }

   @Override
   public void run(ApplicationArguments args) throws Exception {
	 BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
        String email = "";
        String password;
        String userType;
        String firstName, lastName = "";
        int ch;
       
        while (true) {
            System.out.println(
                "\n ->||    Welcome to Student Grade Book Application   ||<- \n");
            
            System.out.println("1)Create Account");
            System.out.println("2)Login Account");
            System.out.println("3)Exit Application");
 
            try {
                System.out.print("\n    Enter Input:"); //user input
                ch = Integer.parseInt(sc.readLine());
 
                switch (ch) {
                case 1:
                    try {
                    	System.out.println("Are you a teacher or a student? (enter 'teacher' or 'student)' ");
                    	userType = sc.readLine();
                        System.out.print("Enter Unique email:");
                        email = sc.readLine();
                       
                        if(!GradeBookMgtApplication.isValidEmail(email)) {
                        	System.out.print("\n Email format is not valid. (Valid Ex: abc@gmail.com or abc13@yahoo.com \n"); 
                        	System.out.print("\n Enter again");
                        	ch = Integer.parseInt(sc.readLine());
	                        email = sc.readLine();
                        }
                        System.out.print("Enter New Password:");
                        password = sc.readLine();
                        
                        System.out.print("Enter First Name:");
                        firstName = sc.readLine();
                        
                        System.out.print("Enter Last Name:");
                        lastName = sc.readLine();
                        
                        if(userType.equalsIgnoreCase("teacher")){
                        	Teacher teacher = new Teacher(email, password, lastName, firstName);
                        	if(service.createTeacherAccount(teacher) ){
	                        	  System.out.println("Account Created Successfully!\n");
	                        }
	                        else {
	                            System.out.println(
	                                "Account Creation Failed:(\n");
	                        }
                        }else {
                        	Student student = new Student(email, password, lastName, firstName);
                        	if(service.createStudentAccount(student)) {
	                        	  System.out.println("Account Created Successfully!\n");
	                        }
	                        else {
	                            System.out.println(
	                                "Account Creation Failed:(\n");
	                        }
                        }
                       
                    }
                    catch (Exception e) {
                        System.out.println(
                            " ERR : Enter Valid Data::Insertion Failed!\n");
                        System.out.println("error: " + e);
                    }
                    break;
 
                case 2:
                    try {
                    	System.out.println("Are you a teacher or a student? (enter 'teacher' or 'student') ");
                    	userType = sc.readLine();
                    	System.out.print("Enter  Email:");
                        email = sc.readLine();
                        System.out.print("Enter  Password:");
                        password = sc.readLine();
                    	if(userType.equalsIgnoreCase("teacher")) {
                    		if(service.loginTeacherAccount(email, password)) {
 	                        	 System.out.println("See you later!\n");
 	                        }
 	                        else {
 	                            System.out.println(
 	                                "Login Failed:(\n");
 	                        }
                    	}else {
                    		if(service.loginStudentAccount(email, password)) {
 	                        	 System.out.println("See you later!\n");
 	                        }
 	                        else {
 	                            System.out.println(
 	                                "Login Failed:(\n");
 	                        }
                    	}
                       
                    }
                    catch (Exception e) {
                        System.out.println(
                            " ERR : Enter Valid Data::Login Failed!\n");
                    }
 
                    break;
 
                default:
                    System.out.println("Invalid Entry!\n");
                }
 
                if (ch == 5) {
                    System.out.println(
                        "Exited Successfully!\n\n Thank You!)");
                    break;
                }
            }
            catch (Exception e) {
                System.out.println("Enter Valid Entry!");
            }
        }
        sc.close();
  }
//@Override
//public void run(ApplicationArguments args) throws Exception {
//	// TODO Auto-generated method stub
//	
//}
}
