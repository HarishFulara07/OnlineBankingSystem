package com.project;

//Step 1: Use interfaces from java.sql package 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCMySQLConnection {
	//static reference to itself
	private static JDBCMySQLConnection instance = new JDBCMySQLConnection();
	/*
	* "jdbc:" -  Required for all databases
	* "mysql" - Any Relational Database. In this case, it is mysql
	* "localhost" - is the name of the server hosting your database
	* "3306" - is the default port number for MySQL, which can be omitted if not changed to any other number.
	* "online_banking_system" - is the MySQL database name 
	*/
	public static final String URL = "jdbc:mysql://localhost:3306/online_banking_system?autoReconnect=true&useSSL=false";
	//change this username to your database username
	public static final String USER = "root";
	//change this password to your database password
	public static final String PASSWORD = "Harish@1234";
	public static final String DRIVER_CLASS = "com.mysql.jdbc.Driver"; 
	
	//private constructor
	private JDBCMySQLConnection() {
		try {
			//Step 2: Load MySQL Java driver
			Class.forName(DRIVER_CLASS);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private Connection createConnection() {

		Connection connection = null;
		try {
			//Step 3: Establish Java MySQL connection
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
		} catch (SQLException e) {
			System.out.println("ERROR: Unable to Connect to Database.");
		}
		return connection;
	}	
	
	public static Connection getConnection() {
		return instance.createConnection();
	}
}