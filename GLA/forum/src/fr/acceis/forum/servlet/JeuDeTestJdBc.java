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

	public final static String[] QUERIES = { "DROP TABLE USERS IF EXISTS", "DROP TABLE THREAD IF EXISTS",
			"DROP TABLE MESSAGE IF EXISTS",
			"CREATE TABLE USERS (ID INT IDENTITY PRIMARY KEY, LOGIN VARCHAR(255), PASSWORD VARCHAR(255))", // Id auto
																											// increment
			"ALTER TABLE USERS ADD CONSTRAINT UNIQUE_LOGIN UNIQUE(login)",
			"CREATE TABLE THREAD (ID INT IDENTITY PRIMARY KEY, TITLE VARCHAR(255), AUTHOR VARCHAR(255))",
			"INSERT INTO THREAD (TITLE,AUTHOR) VALUES('titi', 'toto')",
			"CREATE TABLE MESSAGE (ID INT IDENTITY PRIMARY KEY, AUTHOR VARCHAR(255), CONTENT VARCHAR(5000), IDTHREAD INT)",
			
			"INSERT INTO USERS (LOGIN,PASSWORD) VALUES('admin', 'admin')",
			"INSERT INTO USERS (LOGIN,PASSWORD) VALUES('pierre', 'pierre')",
			"INSERT INTO USERS (LOGIN,PASSWORD) VALUES('paul', 'paul')",
			"INSERT INTO USERS (LOGIN,PASSWORD) VALUES('jacques', 'jacques')", };

	public static void main(String[] args) throws Exception {
		Class.forName("org.hsqldb.jdbcDriver").newInstance();
		Connection connexion = DriverManager
				.getConnection("jdbc:hsqldb:/home/solenn/Documents/GLA/ForumTP/forum/data/basejpa", "sa", "");
		 Statement stmt = connexion.createStatement();

		 for (String query : QUERIES) {
		 stmt.executeUpdate(query);
		 }

		 ArrayList<FilThread> all = new ArrayList<FilThread>();
			PreparedStatement toto = HSQLDBConnection.getConnection().prepareStatement("SELECT * FROM THREAD");
			ResultSet res = toto.executeQuery();
			while(res.next()) {
				FilThread tmp = new FilThread(res.getString("TITLE"),res.getString("AUTHOR"));
				System.out.println(res.getString("TITLE") + res.getString("AUTHOR"));
				all.add(tmp);
			}

		stmt.close();
		connexion.close();

	}

}
