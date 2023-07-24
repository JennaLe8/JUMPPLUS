package com.jump.application;
import com.jump.contact.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.*;

public class Application {
	static final String regexEmail = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$";
    //static final String regexPassword = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&-+=()])(?=\\\\S+$).{8, 20}$";
    
	public static void main(String args[]) 
	        throws IOException
	    {
	 
	        BufferedReader sc = new BufferedReader(
	            new InputStreamReader(System.in));
	        String email = "";
	        String password;
	        int ch;
	       
	        while (true) {
	            System.out.println(
	                "\n ->||    Welcome to Contact Management Application   ||<- \n");
	            System.out.println("1)Create Account");
	            System.out.println("2)Login Account");
	            System.out.println("3)Exit Application");
	 
	            try {
	                System.out.print("\n    Enter Input:"); //user input
	                ch = Integer.parseInt(sc.readLine());
	 
	                switch (ch) {
	                case 1:
	                    try {
	                        System.out.print("Enter Unique email:");
	                        email = sc.readLine();
	                        if(!Application.isValidEmail(email)) {
	                        	System.out.print("\n Email format is not valid. (Valid Ex: abc@gmail.com or abc13@yahoo.com \n"); 
	                        	System.out.print("\n Enter again");
	                        	ch = Integer.parseInt(sc.readLine());
		                        email = sc.readLine();
	                        }
	                        System.out.print("Enter New Password:");
	                        password = sc.readLine();
	                 
	                        if(ContactManagement.createAccount(email, password)) {
	                        	  System.out.println("Account Created Successfully!\n");
	                        }
	                        else {
	                            System.out.println(
	                                "Account Creation Failed:(\n");
	                        }
	                    }
	                    catch (Exception e) {
	                        System.out.println(
	                            " ERR : Enter Valid Data::Insertion Failed!\n");
	                    }
	                    break;
	 
	                case 2:
	                    try {
	                        System.out.print("Enter  Email:");
	                        email = sc.readLine();
	                        System.out.print("Enter  Password:");
	                        password = sc.readLine();
	                        
	                        if(ContactManagement.loginAccount(email, password)) {
	                        	 System.out.println("See you later!\n");
	                        }
	                        else {
	                            System.out.println(
	                                "Login Failed:(\n");
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
	 public static boolean isValidEmail(String email)
     {
 
         // Compile the ReGex
         Pattern p = Pattern.compile(regexEmail);
  
     
         Matcher m = p.matcher(email);
  
         // Return if the email
         // matched the ReGex
         return m.matches();
     }
}
