package fr.acceis.forum.servlet;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Random;

import fr.acceis.forum.classes.Passwords;

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
			"CREATE TABLE IF NOT EXISTS USERS (ID INTEGER PRIMARY KEY, LOGIN VARCHAR(50) UNIQUE, PASSWORD VARCHAR(100), AGE INT, GENDER VARCHAR(10), CITY TEXT, PHOTO VARCHAR(100), ROLE VARCHAR(20), SEL VARCHAR(500))",
			"INSERT INTO USERS (LOGIN,PASSWORD,AGE,GENDER,CITY, PHOTO, ROLE, SEL) VALUES('admin', '2a48ff486f392b6813ef6d98b1d7b7ccfa2876eb465f97f340d91c221b371904', '25', 'MALE', 'Paris', 'fichiers/imgs/Avatar/SuperMan.png', 'Admin', '4e06b16abd821564658d160907a36821')",
			"INSERT INTO USERS (LOGIN,PASSWORD,AGE,GENDER,CITY, PHOTO, ROLE, SEL) VALUES('pierre', 'e4a64af0ea73813a205ae1c02744452ce544f18ccdc5de312612d844a87111c4', '18', 'MALE', 'Rennes', 'fichiers/imgs/Avatar/SuperMan.png', 'User', 'a8c174106ab059db7796f4c5fd2a5965')",
			"INSERT INTO USERS (LOGIN,PASSWORD,AGE,GENDER,CITY, PHOTO, ROLE, SEL) VALUES('paul', '6170d6623d6353aa655b798d777ee0510eff81e29437409e5ddf47af6eab4baa', '16', 'MALE', 'Brest', 'fichiers/imgs/Avatar/SuperMan.png', 'User', '482938d1bbfe57fe2d6e38b17c5951e0')",
			"INSERT INTO USERS (LOGIN,PASSWORD,AGE,GENDER,CITY, PHOTO, ROLE, SEL) VALUES('jacques', '9bc1e6991263d385ed43e297b5c0be3cd5672d71af5513f3e6ebfb0be088a76b', '55', 'MALE', 'Marseille', 'fichiers/imgs/Avatar/SuperMan.png', 'User','a604824d9d99f7bf615bfe277c8bed76')",
			"CREATE TABLE IF NOT EXISTS THREAD (ID INTEGER PRIMARY KEY, TITLE VARCHAR(100), AUTHOR VARCHAR(255), NBMESSAGES INT)",
			"INSERT INTO THREAD (TITLE, AUTHOR, NBMESSAGES) VALUES('coucou jacques', 'jacques', '1')",
			"INSERT INTO THREAD (TITLE, AUTHOR, NBMESSAGES) VALUES('coucou pierre', 'pierre', '1')",
			"INSERT INTO THREAD (TITLE, AUTHOR, NBMESSAGES) VALUES('coucou paul', 'paul','1')",
			"CREATE TABLE IF NOT EXISTS MESSAGE (ID INTEGER PRIMARY KEY, AUTHOR TEXT, CONTENT VARCHAR(5000), IDTHREAD INT, FOREIGN KEY (IDTHREAD) REFERENCES THREAD (ID) )",
			"INSERT INTO MESSAGE (AUTHOR, CONTENT, IDTHREAD) VALUES('jacques', 'hello jaja', '1')",
			"INSERT INTO MESSAGE (AUTHOR, CONTENT, IDTHREAD) VALUES('pierre', 'hello pierrou', '2')",
			"INSERT INTO MESSAGE (AUTHOR, CONTENT, IDTHREAD) VALUES('paul', 'hello polo', '3')", };

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
			//System.out.println(query);
			//blop.executeUpdate(query);
		}


		blop.close();
		con.close();

	}

}
