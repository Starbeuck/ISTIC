package fr.acceis.forum.servlet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import fr.acceis.forum.classes.FilThread;
import fr.acceis.forum.classes.Message;
import fr.acceis.forum.classes.User;
import fr.acceis.forum.dao.HSQLDBConnection;

public class JeuDeTestJdBc {

	public final static String[] QUERIES_TEST = { "DROP TABLE USERS IF EXISTS", "DROP TABLE THREAD IF EXISTS",
			"DROP TABLE MESSAGE IF EXISTS",
			"CREATE TABLE USERS (ID INT IDENTITY PRIMARY KEY, LOGIN VARCHAR(255), PASSWORD VARCHAR(255), AGE INT, GENDER VARCHAR(10), CITY VARCHAR(50))", // increment
			"ALTER TABLE USERS ADD CONSTRAINT UNIQUE_LOGIN UNIQUE(login)",
			"INSERT INTO USERS (LOGIN,PASSWORD,AGE,GENDER,CITY) VALUES('admin', 'admin', '25', 'MALE', 'Paris')",
			"INSERT INTO USERS (LOGIN,PASSWORD,AGE,GENDER,CITY) VALUES('pierre', 'pierre', '18', 'MALE', 'Rennes')",
			"INSERT INTO USERS (LOGIN,PASSWORD,AGE,GENDER,CITY) VALUES('paul', 'paul', '16', 'MALE', 'Brest')",
			"INSERT INTO USERS (LOGIN,PASSWORD,AGE,GENDER,CITY) VALUES('jacques', 'jacques', '55', 'MALE', 'Marseille')",
			"CREATE TABLE THREAD (ID INT IDENTITY PRIMARY KEY, TITLE VARCHAR(255), AUTHOR VARCHAR(255), NBMESSAGES INT)",
			"INSERT INTO THREAD (TITLE, AUTHOR, NBMESSAGES) VALUES('coucou jacques', 'jacques', '1')",
			"INSERT INTO THREAD (TITLE, AUTHOR, NBMESSAGES) VALUES('coucou pierre', 'pierre', '1')",
			"INSERT INTO THREAD (TITLE, AUTHOR, NBMESSAGES) VALUES('coucou paul', 'paul','1')",
			"CREATE TABLE MESSAGE (ID INT IDENTITY PRIMARY KEY, AUTHOR VARCHAR(255), CONTENT VARCHAR(5000), IDTHREAD INT)",
			"ALTER TABLE MESSAGE ADD FOREIGN KEY (IDTHREAD) REFERENCES THREAD(ID)",
			"INSERT INTO MESSAGE (AUTHOR, CONTENT, IDTHREAD) VALUES('jacques', 'hello jaja', '0')",
			"INSERT INTO MESSAGE (AUTHOR, CONTENT, IDTHREAD) VALUES('pierre', 'hello pierrou', '1')",
			"INSERT INTO MESSAGE (AUTHOR, CONTENT, IDTHREAD) VALUES('paul', 'hello polo', '2')", };

	public static void main(String[] args) throws Exception {
		Class.forName("org.hsqldb.jdbcDriver").newInstance();
		Connection connexion = DriverManager
				.getConnection("jdbc:hsqldb:/home/solenn/Documents/GLA/ForumTP/forum/data/basejpa", "sa", "");
		Statement stmt = connexion.createStatement();

		for (String query : QUERIES_TEST) {
			// stmt.executeUpdate(query);
		}

		Message tmpMess = new Message("Axi", "", 0);
		PreparedStatement test = connexion.prepareStatement("SELECT COUNT (AUTHOR) FROM MESSAGE WHERE AUTHOR = ?");
		test.setString(1, tmpMess.getAuthor());
		ResultSet res = test.executeQuery();

		res.next();
		int ctr = res.getInt(1);
		System.out.println(ctr);
		stmt.close();
		connexion.close();

	}

}
