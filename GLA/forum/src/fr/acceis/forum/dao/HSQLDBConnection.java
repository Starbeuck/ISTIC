package fr.acceis.forum.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// TODO: Auto-generated Javadoc
/**
 * The Class HSQLDBConnection.
 */
/**
 * @author solenn
 *
 */
public class HSQLDBConnection {
	
	/** The Constant QUERIES. */
	public final static String[] QUERIES = {
			"CREATE TABLE USERS (ID INT IDENTITY PRIMARY KEY, LOGIN VARCHAR(255), PASSWORD VARCHAR(255), AGE INT, GENDER VARCHAR(10), CITY VARCHAR(50))", // increment
			"ALTER TABLE USERS ADD CONSTRAINT UNIQUE_LOGIN UNIQUE(login)",
			"INSERT INTO USERS (LOGIN,PASSWORD,AGE,GENDER,CITY) VALUES('admin', 'admin', '25', 'MALE', 'Paris')",
			"INSERT INTO USERS (LOGIN,PASSWORD,AGE,GENDER,CITY) VALUES('pierre', 'pierre', '18', 'MALE', 'Rennes')",
			"INSERT INTO USERS (LOGIN,PASSWORD,AGE,GENDER,CITY) VALUES('paul', 'paul', '16', 'MALE', 'Brest')",
			"INSERT INTO USERS (LOGIN,PASSWORD,AGE,GENDER,CITY) VALUES('jacques', 'jacques', '55', 'MALE', 'Marseille')",
			"CREATE TABLE THREAD (ID INT IDENTITY PRIMARY KEY, TITLE VARCHAR(255), AUTHOR VARCHAR(255))",
			"INSERT INTO THREAD (TITLE, AUTHOR) VALUES('coucou jacques', 'jacques')",
			"INSERT INTO THREAD (TITLE, AUTHOR) VALUES('coucou pierre', 'pierre')",
			"INSERT INTO THREAD (TITLE, AUTHOR) VALUES('coucou paul', 'paul')",
			"CREATE TABLE MESSAGE (ID INT IDENTITY PRIMARY KEY, AUTHOR VARCHAR(255), CONTENT VARCHAR(5000), IDTHREAD INT)",
			"ALTER TABLE MESSAGE ADD FOREIGN KEY (IDTHREAD) REFERENCES THREAD(ID)",
			"INSERT INTO MESSAGE (TITLE, AUTHOR) VALUES('jacques', 'hello jaja', 0)",
			"INSERT INTO MESSAGE (TITLE, AUTHOR) VALUES('pierre', 'hello pierrou', 1)",
			"INSERT INTO MESSAGE (TITLE, AUTHOR) VALUES('paul', 'hello polo', 2)", };

	/** The con. */
	static Connection con = null;
	
	/**
	 * Gets the connection.
	 *
	 * @return the connection
	 * @throws InstantiationException the instantiation exception
	 * @throws IllegalAccessException the illegal access exception
	 * @throws ClassNotFoundException the class not found exception
	 * @throws SQLException the SQL exception
	 */
	public static Connection getConnection()
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		Class.forName("org.sqlite.JDBC");
		
		//check if connection exist or not - singleton
		if (con == null) {
			con = DriverManager
					.getConnection("jdbc:sqlite:/home/solenn/Documents/GLA/ForumTP/forum/data/db/forum.db", "sa", "");
			System.out.println("CREATE NEW CONNECTION HSQLDB");
		} else {
			System.out.println("USING EXISTING CONNECTION HSQLDB");
		}
		return con;
	}

}
