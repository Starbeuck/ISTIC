package fr.acceis.forum.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class HSQLDBConnection {
	public final static String[] QUERIES = {
			"CREATE TABLE USERS (ID INT IDENTITY PRIMARY KEY, LOGIN VARCHAR(255), PASSWORD VARCHAR(255))", //Id auto increment
            "ALTER TABLE USERS ADD CONSTRAINT UNIQUE_LOGIN UNIQUE(login)",
            "INSERT INTO USERS (LOGIN,PASSWORD) VALUES('admin', 'admin')",
			"INSERT INTO USERS (LOGIN,PASSWORD) VALUES('pierre', 'pierre')",
			"INSERT INTO USERS (LOGIN,PASSWORD) VALUES('paul', 'paul')",
			"INSERT INTO USERS (LOGIN,PASSWORD) VALUES('jacques', 'jacques')",
			"CREATE TABLE THREAD (ID INT IDENTITY PRIMARY KEY, TITLE VARCHAR(255), AUTHOR VARCHAR(255))",
			"INSERT INTO THREAD (TITLE, AUTHOR) VALUES('coucou jacques', 'jacques')",
			"INSERT INTO THREAD (TITLE, AUTHOR) VALUES('coucou pierre', 'pierre')",
			"INSERT INTO THREAD (TITLE, AUTHOR) VALUES('coucou paul', 'paul')",
			"CREATE TABLE MESSAGE (ID INT IDENTITY PRIMARY KEY, AUTHOR VARCHAR(255), CONTENT VARCHAR(5000), IDTHREAD INT)",
			"ALTER TABLE MESSAGE ADD FOREIGN KEY (IDTHREAD) REFERENCES THREAD(ID)",
			"INSERT INTO MESSAGE (TITLE, AUTHOR) VALUES('jacques', 'hello jaja', 0)",
			"INSERT INTO MESSAGE (TITLE, AUTHOR) VALUES('pierre', 'hello pierrou', 1)",
			"INSERT INTO MESSAGE (TITLE, AUTHOR) VALUES('paul', 'hello polo', 2)",
		};
	
	public static Connection getConnection() throws InstantiationException, IllegalAccessException {
		Connection con = null;

		try {
			Class.forName("org.hsqldb.jdbcDriver").newInstance();
			System.out.println("HSQLDB JDBCDriver Loaded");
			con = DriverManager.getConnection("jdbc:hsqldb:/home/solenn/Documents/GLA/ForumTP/forum/data/basejpa", "SA", "");
			System.out.println("HSQLDB Connection Created");
			
			Statement stmt = con.createStatement();

			for (String query : QUERIES) {
				//stmt.executeUpdate(query);
			}
			
			stmt.close();
	
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
	}
}
