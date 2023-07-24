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
          
            return true;
            
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
            	String selectSQL = "select USER_ID from USER_ACCOUNT where USER_EMAIL=?";
    			PreparedStatement pst2= con.prepareStatement(selectSQL);
    			pst2.setString(1,email);
    			ResultSet res = pst2.executeQuery();
    			
    			PreparedStatement pst;
    			int userId=0;
    			if(res.next()){
    				userId = res.getInt("USER_ID");
    				
    			}
                int ch = 5;
                String contactName;
                String contactEmail;
                String contactNumber;
                while (true) {
                    try {
                        System.out.println("Welcome back, "+ rs.getString("USER_EMAIL"));
                        System.out.println("1)Add Contact");
                        System.out.println("2)Delete Contact");
                        System.out.println("3)Update Contact");
                        System.out.println("4)Sort Contacts");
                        System.out.println("5)View Contacts");
                        System.out.println("6)View Users");      
                        System.out.println("7)Log out");      
                        

                        System.out.print("Enter Choice:");
                        ch = Integer.parseInt(sc.readLine());
                        if (ch == 1) {
                            System.out.print( "Enter contact name: ");
                            contactName = sc.readLine();
                            System.out.print("Enter contact email: ");
                            contactEmail = sc.readLine();
                            System.out.print("Enter contact phone number: ");
                            contactNumber = sc.readLine();
                            
                            if(ContactManagement.addContact(contactName, contactEmail, contactNumber, userId)) {
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
                        	System.out.print( "Enter contact name: ");
                        	contactName = sc.readLine();

                        	System.out.print( "What do you want to update? (choose among 'name', 'email', or 'number'): ");
                        	String updateVar = sc.readLine();
                        	
                        	System.out.print( "Enter new value: ");
                        	String updateVal = sc.readLine();
                        	
                        	boolean resp = ContactManagement.updateContact(updateVar, contactName, updateVal);
                        	if(resp == true) {
                        		System.out.println("Contact updated successfully!\n");
                            }
                            else if(resp == false) {
                                 System.out.println("deleted failed:(\n");
                            }
                        }
                        else if (ch == 4) {
                        	ContactManagement.sortContacts(userId);
                        
                        }
                        else if (ch == 5) {
                        	ContactManagement.viewContacts(userId);
                        }
                        else if(ch == 6) {
                        	ContactManagement.viewUsers();
                        }
                        else if(ch == 7) {
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
    
    public static boolean addContact(String contactName, String contactEmail, String contactNumber, int userID)throws SQLException {
    	 try {
             //con.setAutoCommit(false);
    		
             String sql = "INSERT INTO CONTACT (CONTACT_NAME,CONTACT_EMAIL,CONTACT_NUMBER,USER_ID) VALUES (?,?,?,?)";
             PreparedStatement pstmt = con.prepareStatement(sql);
             pstmt.setString(1, contactName);
             pstmt.setString(2, contactEmail);
             pstmt.setString(3, contactNumber);
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
    public static boolean updateContact(String updateVar, String contactName, String updateVal)throws SQLException {
    	String sql = "";
    	String selectSQL = "select CONTACT_ID from CONTACT where CONTACT_NAME=?";
		PreparedStatement st= con.prepareStatement(selectSQL);
		st.setString(1,contactName);
		ResultSet rs = st.executeQuery();
		
		PreparedStatement pst;
		int contactId=0;
		if(rs.next()){
			contactId = rs.getInt("CONTACT_ID");
			
		}
	
    	if(updateVar.equalsIgnoreCase("name")) {
    		sql = "update CONTACT set CONTACT_NAME = ? where CONTACT_ID = ?";
    		pst= con.prepareStatement(sql);
    		pst.setString(1,updateVal);
    		pst.setInt(2, contactId);
    		
        	pst.executeUpdate();
        	String resSQL = "select CONTACT_NAME from CONTACT where CONTACT_ID=?";
        	pst= con.prepareStatement(resSQL);
        	pst.setInt(1, contactId);
        	rs.next();
        
    		
    	}
    	else if(updateVar.equalsIgnoreCase("email")) {
    		sql = "update CONTACT set CONTACT_EMAIL = ? where CONTACT_ID = ?";
    		pst= con.prepareStatement(sql);
    		pst.setString(1,updateVal);
    		pst.setInt(2, contactId);
    		
        	pst.executeUpdate();
    		
    	}
    	else if(updateVar.equalsIgnoreCase("number")) {
    		sql = "update CONTACT set CONTACT_NUMBER = ? where CONTACT_ID = ?";
    		pst= con.prepareStatement(sql);
    		pst.setString(1, updateVal);
    		pst.setInt(2, contactId);
    		
        	pst.executeUpdate();
    		
    	}
    	
    	  
    	return true;
		
    	
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
    
    public static void sortContacts(int userId)throws SQLException {
    	
    	String sql = "SELECT * from CONTACT WHERE USER_ID=? ORDER by CONTACT_NAME";
    	PreparedStatement st = con.prepareStatement(sql);
    	st.setInt(1, userId);
    	ResultSet rs = st.executeQuery();
    	System.out.println(
                 "-----------------------------------------------------------");
	     System.out.printf("%12s %10s %10s\n",
	                       "Contact Name", "Contact Email", "Contact Number");
	  
         // Execution
  
         while (rs.next()) {
             System.out.printf("%12s %10s %10s\n",
                   rs.getString("CONTACT_NAME"),
                   rs.getString("CONTACT_EMAIL"),
                   rs.getString("CONTACT_NUMBER"));
	     }
	     System.out.println(
	         "-----------------------------------------------------------\n");
    }
    
    public static void viewContacts(int userId)throws SQLException {
   	 try {
   		 
            // query
            String sql = "select * from CONTACT where USER_ID=?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, userId);
            ResultSet rs = st.executeQuery();
            System.out.println(
                "-----------------------------------------------------------");
            System.out.printf("%12s %10s %10s\n",
                              "Contact Name", "Contact Email", "Contact Number");
 
            // Execution
 
            while (rs.next()) {
                System.out.printf("%12s %10s %10s\n",
                                  rs.getString("CONTACT_NAME"),
                                  rs.getString("CONTACT_EMAIL"),
                                  rs.getString("CONTACT_NUMBER"));
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
		   //String sql = "";
	    	String selectSQL = "select USER_ID from USER_ACCOUNT where USER_EMAIL=?";
			PreparedStatement st= con.prepareStatement(selectSQL);
			st.setString(1,email);
			ResultSet rs = st.executeQuery();
			
			int userId=0;
			if(rs.next()){
				userId = rs.getInt("USER_ID");
				System.out.println(
		                "USER_ID: " + userId);
			}
		
		   String sql = "SELECT * FROM CONTACT WHERE USER_ID=?";
		   PreparedStatement statement= con.prepareStatement(sql);
		   statement.setInt(1, userId);
		  
	       ResultSet result = statement.executeQuery();
	       String csvFilePath = email+".csv";
	       BufferedWriter fileWriter = new BufferedWriter(new FileWriter(csvFilePath));
	       // write header line containing column names       
           fileWriter.write("contact_name,contact_email,contact_number");
            
           while (result.next()) {
               String contactName = result.getString("contact_name");
               String contactEmail = result.getString("contact_email");
               String contactNumber = result.getString("contact_number");
                
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
