package fr.acceis.forum.servlet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

// TODO: Auto-generated Javadoc
/**
 * The Class JeuDeTestJdBc.
 */
/**
 * @author solenn
 *
 */
public class JeuDeTestJdBc {

	/** The Constant QUERIES_TEST_HSQLDB. */
	public final static String[] QUERIES_TEST_HSQLDB = { "DROP TABLE USERS IF EXISTS", "DROP TABLE THREAD IF EXISTS",
			"DROP TABLE MESSAGE IF EXISTS",
			"CREATE TABLE USERS (ID INT IDENTITY PRIMARY KEY, LOGIN VARCHAR(255), PASSWORD VARCHAR(255), AGE INT, GENDER VARCHAR(10), CITY VARCHAR(50), PHOTO VARCHAR(100))", // increment
			"ALTER TABLE USERS ADD CONSTRAINT UNIQUE_LOGIN UNIQUE(login)",
			"INSERT INTO USERS (LOGIN,PASSWORD,AGE,GENDER,CITY) VALUES('admin', 'admin', '25', 'MALE', 'Paris', 'fichiers/imgs/Avatar/SuperMan.png')",
			"INSERT INTO USERS (LOGIN,PASSWORD,AGE,GENDER,CITY) VALUES('pierre', 'pierre', '18', 'MALE', 'Rennes', 'fichiers/imgs/Avatar/SuperMan.png')",
			"INSERT INTO USERS (LOGIN,PASSWORD,AGE,GENDER,CITY) VALUES('paul', 'paul', '16', 'MALE', 'Brest', 'fichiers/imgs/Avatar/SuperMan.png')",
			"INSERT INTO USERS (LOGIN,PASSWORD,AGE,GENDER,CITY) VALUES('jacques', 'jacques', '55', 'MALE', 'Marseille', 'fichiers/imgs/Avatar/SuperMan.png')",
			"CREATE TABLE THREAD (ID INT IDENTITY PRIMARY KEY, TITLE VARCHAR(255), AUTHOR VARCHAR(255), NBMESSAGES INT)",
			"INSERT INTO THREAD (TITLE, AUTHOR, NBMESSAGES) VALUES('coucou jacques', 'jacques', '1')",
			"INSERT INTO THREAD (TITLE, AUTHOR, NBMESSAGES) VALUES('coucou pierre', 'pierre', '1')",
			"INSERT INTO THREAD (TITLE, AUTHOR, NBMESSAGES) VALUES('coucou paul', 'paul','1')",
			"CREATE TABLE MESSAGE (ID INT IDENTITY PRIMARY KEY, AUTHOR VARCHAR(255), CONTENT VARCHAR(5000), IDTHREAD INT)",
			"ALTER TABLE MESSAGE ADD FOREIGN KEY (IDTHREAD) REFERENCES THREAD(ID)",
			"INSERT INTO MESSAGE (AUTHOR, CONTENT, IDTHREAD) VALUES('jacques', 'hello jaja', '0')",
			"INSERT INTO MESSAGE (AUTHOR, CONTENT, IDTHREAD) VALUES('pierre', 'hello pierrou', '1')",
			"INSERT INTO MESSAGE (AUTHOR, CONTENT, IDTHREAD) VALUES('paul', 'hello polo', '2')", };

	/** The Constant QUERIES_TEST_SQLITE. */
	public final static String[] QUERIES_TEST_SQLITE = {
			"CREATE TABLE IF NOT EXISTS USERS (ID INTEGER PRIMARY KEY, LOGIN VARCHAR(50) UNIQUE, PASSWORD VARCHAR(100), AGE INT, GENDER VARCHAR(10), CITY TEXT, PHOTO VARCHAR(100), ROLE VARCHAR(20))",
			"INSERT INTO USERS (LOGIN,PASSWORD,AGE,GENDER,CITY, PHOTO, ROLE) VALUES('admin', 'admin', '25', 'MALE', 'Paris', 'fichiers/imgs/Avatar/SuperMan.png', 'Admin')",
			"INSERT INTO USERS (LOGIN,PASSWORD,AGE,GENDER,CITY, PHOTO, ROLE) VALUES('pierre', 'pierre', '18', 'MALE', 'Rennes', 'fichiers/imgs/Avatar/SuperMan.png', 'User')",
			"INSERT INTO USERS (LOGIN,PASSWORD,AGE,GENDER,CITY, PHOTO, ROLE) VALUES('paul', 'paul', '16', 'MALE', 'Brest', 'fichiers/imgs/Avatar/SuperMan.png', 'User')",
			"INSERT INTO USERS (LOGIN,PASSWORD,AGE,GENDER,CITY, PHOTO, ROLE) VALUES('jacques', 'jacques', '55', 'MALE', 'Marseille', 'fichiers/imgs/Avatar/SuperMan.png', 'User')",
			"CREATE TABLE IF NOT EXISTS THREAD (ID INTEGER PRIMARY KEY, TITLE VARCHAR(100), AUTHOR VARCHAR(255), NBMESSAGES INT)",
			"INSERT INTO THREAD (TITLE, AUTHOR, NBMESSAGES) VALUES('coucou jacques', 'jacques', '1')",
			"INSERT INTO THREAD (TITLE, AUTHOR, NBMESSAGES) VALUES('coucou pierre', 'pierre', '1')",
			"INSERT INTO THREAD (TITLE, AUTHOR, NBMESSAGES) VALUES('coucou paul', 'paul','1')",
			"CREATE TABLE IF NOT EXISTS MESSAGE (ID INTEGER PRIMARY KEY, AUTHOR TEXT, CONTENT VARCHAR(5000), IDTHREAD INT, FOREIGN KEY (IDTHREAD) REFERENCES THREAD (ID) )",
			"INSERT INTO MESSAGE (AUTHOR, CONTENT, IDTHREAD) VALUES('jacques', 'hello jaja', '1')",
			"INSERT INTO MESSAGE (AUTHOR, CONTENT, IDTHREAD) VALUES('pierre', 'hello pierrou', '2')",
			"INSERT INTO MESSAGE (AUTHOR, CONTENT, IDTHREAD) VALUES('paul', 'hello polo', '3')", 			
	};

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 * @throws Exception the exception
	 */
	public static void main(String[] args) throws Exception {
		Class.forName("org.sqlite.JDBC");
		// Connection connexion =
		// DriverManager.getConnection("jdbc:hsqldb:/home/solenn/Documents/GLA/ForumTP/forum/data/basejpa",
		// "sa", "");
		Connection con = DriverManager
				.getConnection("jdbc:sqlite:/home/solenn/Documents/GLA/ForumTP/forum/data/db/forum.db", "sa", "");
		Statement blop = con.createStatement();

		for (String query : QUERIES_TEST_SQLITE) {
		//	System.out.println(query);
			blop.executeUpdate(query);
		}

		blop.close();
		con.close();

	}

}
