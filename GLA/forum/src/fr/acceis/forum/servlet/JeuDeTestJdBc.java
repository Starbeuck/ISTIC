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
			"INSERT INTO USERS (LOGIN, PASSWORD, AGE,GENDER,CITY, PHOTO, ROLE, SEL) VALUES('admin', '2a48ff486f392b6813ef6d98b1d7b7ccfa2876eb465f97f340d91c221b371904', '25', 'MALE', 'Paris', 'fichiers/imgs/Avatar/SuperMan.png', 'Admin', '4e06b16abd821564658d160907a36821')",
			"INSERT INTO USERS (LOGIN, PASSWORD, AGE,GENDER,CITY, PHOTO, ROLE, SEL) VALUES('pierre', 'e4a64af0ea73813a205ae1c02744452ce544f18ccdc5de312612d844a87111c4', '18', 'MALE', 'Rennes', 'fichiers/imgs/Avatar/SuperMan.png', 'User', 'a8c174106ab059db7796f4c5fd2a5965')",
			"INSERT INTO USERS (LOGIN, PASSWORD, AGE,GENDER,CITY, PHOTO, ROLE, SEL) VALUES('paul', '6170d6623d6353aa655b798d777ee0510eff81e29437409e5ddf47af6eab4baa', '16', 'MALE', 'Brest', 'fichiers/imgs/Avatar/SuperMan.png', 'User', '482938d1bbfe57fe2d6e38b17c5951e0')",
			"INSERT INTO USERS (LOGIN, PASSWORD, AGE,GENDER,CITY, PHOTO, ROLE, SEL) VALUES('jacques', '9bc1e6991263d385ed43e297b5c0be3cd5672d71af5513f3e6ebfb0be088a76b', '55', 'MALE', 'Marseille', 'fichiers/imgs/Avatar/SuperMan.png', 'User','a604824d9d99f7bf615bfe277c8bed76')",
			"INSERT INTO USERS (LOGIN, PASSWORD, AGE,GENDER,CITY, PHOTO, ROLE, SEL) VALUES('soso', '0a6923d8723a3353c01121f4e493e3200ddf9faa2be39d1c502376a43da30cc7', '22', 'FEMALE', 'Bruz', 'fichiers/imgs/Avatar/WonderWoman.png', 'Modo','923564a7e822dd386f52603d8378cf2f')",
			"INSERT INTO USERS (LOGIN, PASSWORD, AGE,GENDER,CITY, PHOTO, ROLE, SEL) VALUES('yves', '5b7f0630aa3ef3817e123a7f87acf0c231ea16cfd247f995d69d313948cbe4b7', '30', 'MALE', 'Rennes', 'fichiers/imgs/Avatar/SuperMan.png', 'User','0fa8a584bd45734e7cb1c11f18e19585')",
			"CREATE TABLE IF NOT EXISTS THREAD (ID INTEGER PRIMARY KEY, TITLE VARCHAR(100), AUTHOR VARCHAR(255), NBMESSAGES INT)",
			"INSERT INTO THREAD (TITLE, AUTHOR, NBMESSAGES) VALUES('Mon arrivee parmi vous !', 'jacques', '5')",
			"INSERT INTO THREAD (TITLE, AUTHOR, NBMESSAGES) VALUES('Je suis nouveau ! ', 'pierre', '3')",
			"INSERT INTO THREAD (TITLE, AUTHOR, NBMESSAGES) VALUES('Ma nouvelle Moto', 'soso','4')",
			"INSERT INTO THREAD (TITLE, AUTHOR, NBMESSAGES) VALUES('La cuisine !', 'paul','3')",
			"INSERT INTO THREAD (TITLE, AUTHOR, NBMESSAGES) VALUES('Reglement', 'admin','2')",
			"CREATE TABLE IF NOT EXISTS MESSAGE (ID INTEGER PRIMARY KEY, AUTHOR TEXT, CONTENT VARCHAR(5000), IDTHREAD INT, FOREIGN KEY (IDTHREAD) REFERENCES THREAD (ID) )",
			"INSERT INTO MESSAGE (AUTHOR, CONTENT, IDTHREAD) VALUES('jacques', 'bonjour, je suis nouveau, je suis Jacques !', '1')",
			"INSERT INTO MESSAGE (AUTHOR, CONTENT, IDTHREAD) VALUES('pierre', 'hello Jacques ! Tu habites ou ?', '1')",
			"INSERT INTO MESSAGE (AUTHOR, CONTENT, IDTHREAD) VALUES('soso', 'Bienvenu parmi nous Jacques ! ', '1')",
			"INSERT INTO MESSAGE (AUTHOR, CONTENT, IDTHREAD) VALUES('jacques', 'J habite a Marseille ! ', '1')",
			"INSERT INTO MESSAGE (AUTHOR, CONTENT, IDTHREAD) VALUES('pierre', 'Oula, je suis de Rennes, c est pas a cote !', '1')",
			"INSERT INTO MESSAGE (AUTHOR, CONTENT, IDTHREAD) VALUES('pierre', 'Bonjour, j espere que je pourrais me faire des copains sur ce forum !', '2')",
			"INSERT INTO MESSAGE (AUTHOR, CONTENT, IDTHREAD) VALUES('admin', 'Hello ! Je n en doute pas !', '2')",
			"INSERT INTO MESSAGE (AUTHOR, CONTENT, IDTHREAD) VALUES('soso', 'Tout a fait d accord ! ', '2')",
			"INSERT INTO MESSAGE (AUTHOR, CONTENT, IDTHREAD) VALUES('soso', 'J ai achete ma nouvelle moto, une GSXF 650 ! ', '3')",
			"INSERT INTO MESSAGE (AUTHOR, CONTENT, IDTHREAD) VALUES('jacques', 'Wow, elle est trop belle !', '3')",
			"INSERT INTO MESSAGE (AUTHOR, CONTENT, IDTHREAD) VALUES('pierre', 'Et beh, la classe !', '3')",
			"INSERT INTO MESSAGE (AUTHOR, CONTENT, IDTHREAD) VALUES('paul', 'J aimerais bien avoir la meme !', '3')",
			"INSERT INTO MESSAGE (AUTHOR, CONTENT, IDTHREAD) VALUES('paul', 'J aimerais bien savoir faire la cuisine ! Des idees ?!', '4')",
			"INSERT INTO MESSAGE (AUTHOR, CONTENT, IDTHREAD) VALUES('admin', 'Owi, fais moi à manger !', '4')",
			"INSERT INTO MESSAGE (AUTHOR, CONTENT, IDTHREAD) VALUES('pierre', 'Regarde Marmitton !', '4')",
			"INSERT INTO MESSAGE (AUTHOR, CONTENT, IDTHREAD) VALUES('admin', 'Vous pourrez poser toutes les questions que vous voulez sur le reglement de ce forum', '5')",
			"INSERT INTO MESSAGE (AUTHOR, CONTENT, IDTHREAD) VALUES('paul', 'Merci !', '5')", };

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
			 System.out.println(query);
			 blop.executeUpdate(query);
		}

		blop.close();
		con.close();

	}

}
