package com.jump.contact;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//connect database
//public Connection connect() {
// if (connection == null) {
//     try {
//         Class.forName(DATABASE_DRIVER);
//         connection = DriverManager.getConnection(DATABASE_URL, getProperties());
//     } catch (ClassNotFoundException | SQLException e) {
//         // Java 7+
//         e.printStackTrace();
//     }
// }
// return connection;
//}
public class DatabaseConnection {
	static Connection con; // Global Connection Object
    public static Connection getConnection()
    {
        try {
            
            String mysqlJDBCDriver
                = "com.mysql.cj.jdbc.Driver"; //jdbc driver
            String url
                = "jdbc:mysql://127.0.0.1:3306/TEST7"; //mysql url
            String user = "root";        //mysql username
            String pass = "hohoGreat9^^";  //mysql passcode
            Class.forName(mysqlJDBCDriver);
            con = DriverManager.getConnection(url, user,
                                              pass);
        }
        catch (ClassNotFoundException | SQLException e) {
	        e.printStackTrace();
	    }
	 
        return con;
    }
 // disconnect database
    public static void disconnect() {
        if (con != null) {
            try {
                con.close();
                con = null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
