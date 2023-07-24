package com.jump.contact;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.io.*;


public class ContactManagement {
	private static final int NULL = 0;
	 
    static Connection con = DatabaseConnection.getConnection();
    //static String sql = "";
    public static boolean createAccount(String email, String password) // create account 
    {
        try {
            // validation
            if (email == "" || password == "") {
                System.out.println("All Field Required!");
                return false;
            }
            // query
            String sql = "INSERT INTO USER_ACCOUNT (USER_EMAIL, USER_PSW) VALUES (?, ?)";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, email);
            pstmt.setString(2, password);
            pstmt.executeUpdate();
            // Execution
            if (pstmt.executeUpdate() > 0) {
                return true;
            }
            // return
        }
        catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Username Not Available!");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public static boolean loginAccount(String email, String password) // login method
    {
        try {
            // validation
            if (email == "" || password == "") {
                System.out.println("All Field Required!");
                return false;
            }
            // query
            String sql = "select * from USER_ACCOUNT where USER_EMAIL='"
                  + email + "' and USER_PSW=" + password;
            PreparedStatement st
                = con.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            // Execution
            BufferedReader sc = new BufferedReader(
                new InputStreamReader(System.in));
 
            if (rs.next()) {
                // after login menu driven interface method
 
                int ch = 5;
                String contactName;
                String contactEmail;
                int contactNumber;
                int userID;
                while (true) {
                    try {
                        System.out.println("Welcome back, "+ rs.getString("USER_EMAIL"));
                        System.out.println("1)Add Contact");
                        System.out.println("2)Delete Contact");
                        System.out.println("3)Sort Contacts");
                        System.out.println("4)View Contacts");
                        System.out.println("5)View Users");      
                        System.out.println("6)Log out");      
                        

                        System.out.print("Enter Choice:");
                        ch = Integer.parseInt(sc.readLine());
                        if (ch == 1) {
                            System.out.print( "Enter contact name: ");
                            contactName = sc.readLine();
                            System.out.print("Enter contact email: ");
                            contactEmail = sc.readLine();
                            System.out.print("Enter contact phone number: ");
                            contactNumber = Integer.parseInt(sc.readLine());
                            System.out.print("Enter user ID: ");
                            userID = Integer.parseInt(sc.readLine());
                            if(ContactManagement.addContact(contactName, contactEmail, contactNumber, userID)) {
                            	 System.out.println("Contact added successfully!\n");
                            }
                            else {
                                System.out.println("ERR :  Failed:(\n");
                            }
                            
                        }
                        else if (ch == 2) {
                        	System.out.print( "Enter contact name: ");
                        	contactName = sc.readLine();
                        	boolean resp = ContactManagement.deleteContact(contactName);
                        	if(resp == true) {
                        		System.out.println("deleted");
                        		System.out.println("Contact deleted successfully!\n");
                            }
                            else if(resp == false) {
                                 System.out.println("deleted failed:(\n");
                            }
                        }
                        else if (ch == 3) {
                        	ContactManagement.sortContacts();
                        
                        }
                        else if (ch == 4) {
                        	ContactManagement.viewContacts();
                        }
                        else if(ch == 5) {
                        	ContactManagement.viewUsers();
                        }
                        else if(ch == 6) {
                        	// write to new file the user's contacts every time they log out
                        	ContactManagement.writeToFile(email);
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
            // return
            return true;
        }
        catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Username Not Available!");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public static boolean addContact(String contactName, String contactEmail, int contactNumber, int userID)throws SQLException {
    	 try {
             //con.setAutoCommit(false);
             String sql = "INSERT INTO CONTACT (CONTACT_NAME,CONTACT_EMAIL,CONTACT_NUMBER,USER_ID) VALUES (?,?,?,?)";
             PreparedStatement pstmt = con.prepareStatement(sql);
             pstmt.setString(1, contactName);
             pstmt.setString(2, contactEmail);
             pstmt.setInt(3, contactNumber);
             pstmt.setInt(4, userID);
             pstmt.executeUpdate();
             
 
             //con.setSavepoint();
  
             //con.commit();
             return true;
         }
         catch (Exception e) {
             e.printStackTrace();
             //con.rollback();
         }
         // return
         return false;
    	
    }
    public static boolean deleteContact(String contactName)throws SQLException {
    	
		String sql = "DELETE FROM CONTACT WHERE CONTACT_NAME=?";
    	PreparedStatement st= con.prepareStatement(sql);
    	st.setString(1, contactName);
    	st.executeUpdate();
    	 System.out.println(
                 "st.executeUpdate(): " + st.executeUpdate());
    	  
    	return true;
		
    	
    }
    
    public static void sortContacts()throws SQLException {
    	
    	String sql = "SELECT * from CONTACT ORDER by CONTACT_NAME";
    	PreparedStatement st = con.prepareStatement(sql);
    	ResultSet rs = st.executeQuery(sql);
    	System.out.println(
                 "-----------------------------------------------------------");
	     System.out.printf("%12s %10s %10s\n",
	                       "Contact Name", "Contact Email", "Contact Number");
	  
         // Execution
  
         while (rs.next()) {
             System.out.printf("%12s %10s %10d\n",
                   rs.getString("CONTACT_NAME"),
                   rs.getString("CONTACT_EMAIL"),
                   rs.getInt("CONTACT_NUMBER"));
	     }
	     System.out.println(
	         "-----------------------------------------------------------\n");
    }
    
    public static void viewContacts()throws SQLException {
   	 try {
   		 
            // query
            String sql = "select * from CONTACT";
            PreparedStatement st = con.prepareStatement(sql);
 
            ResultSet rs = st.executeQuery(sql);
            System.out.println(
                "-----------------------------------------------------------");
            System.out.printf("%12s %10s %10s\n",
                              "Contact Name", "Contact Email", "Contact Number");
 
            // Execution
 
            while (rs.next()) {
                System.out.printf("%12s %10s %10d\n",
                                  rs.getString("CONTACT_NAME"),
                                  rs.getString("CONTACT_EMAIL"),
                                  rs.getInt("CONTACT_NUMBER"));
            }
            System.out.println(
                "-----------------------------------------------------------\n");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
   }
    public static void viewUsers()throws SQLException {
   	 try {
   		 
            // query
            String sql = "SELECT * FROM USER_ACCOUNT GROUP BY USER_ID, USER_EMAIL;";
            PreparedStatement st = con.prepareStatement(sql);
 
            ResultSet rs = st.executeQuery(sql);
            System.out.println(
                "-----------------------------------------------------------");
            System.out.printf("%12s %10s\n",
                              "User ID", "User Email");
 
            // Execution
 
            while (rs.next()) {
                System.out.printf("%12d %10s\n", rs.getInt("USER_ID"), rs.getString("USER_EMAIL"));
            }
            System.out.println(
                "-----------------------------------------------------------\n");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
   }
   public static void writeToFile(String email) throws IOException, SQLException {
	   try {
		   String sql = "SELECT * FROM CONTACT";
		  // Statement statement = con.createStatement();
		   PreparedStatement statement
           = con.prepareStatement(sql);
	       ResultSet result = statement.executeQuery();
	       String csvFilePath = email+".csv";
	       BufferedWriter fileWriter = new BufferedWriter(new FileWriter(csvFilePath));
	       // write header line containing column names       
           fileWriter.write("contact_name,contact_email,contact_number");
            
           while (result.next()) {
               String contactName = result.getString("contact_name");
               String contactEmail = result.getString("contact_email");
               int contactNumber = result.getInt("contact_number");
                
               String line = String.format("\"%s\",%s,%s",
            		   contactName, contactEmail, contactNumber);
                
               fileWriter.newLine();
               fileWriter.write(line);            
           }
            
           statement.close();
           fileWriter.close();
	   } catch (SQLException e) {
           System.out.println("Datababse error:");
           e.printStackTrace();
       } catch (IOException e) {
           System.out.println("File IO error:");
           e.printStackTrace();
       }
        
	   
   }
}
