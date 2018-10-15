package fr.acceis.forum.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.acceis.forum.classes.FilThread;
import fr.acceis.forum.classes.Message;
import fr.acceis.forum.classes.Rank;
import fr.acceis.forum.classes.User;
import fr.acceis.forum.dao.DAO;
import fr.acceis.forum.dao.HSQLDBConnection;
import fr.acceis.forum.dao.MessageDAO;
import fr.acceis.forum.dao.ThreadDAO;
import fr.acceis.forum.dao.UserDAO;

@SuppressWarnings("serial")
public class ProfilServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String name = req.getParameter("author");

		// looking for the user profil into the database
		DAO<User> DAOUser = null;
		try {
			DAOUser = new UserDAO(HSQLDBConnection.getConnection());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		User getUser = null;
		try {
			getUser = DAOUser.findByName(name);
		} catch (InstantiationException | IllegalAccessException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// pick a picture
		if (getUser.getGender().equals("Male")) {
			req.setAttribute("photo", "fichiers/imgs/SuperMan.png");
		} else {
			req.setAttribute("photo", "fichiers/imgs/WonderWoman.png");
		}

		// get message post by the user

		Message tmpMess = new Message(getUser.getLogin(), "", 0);
		DAO<Message> DAOMessage = null;
		try {
			DAOMessage = new MessageDAO(HSQLDBConnection.getConnection());
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int nbMessages = 0;
		try {
			nbMessages = DAOMessage.findbyID(tmpMess);
			System.out.println(nbMessages);
		} catch (InstantiationException | IllegalAccessException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//get rank according to number of message post
		Rank rank = new Rank(nbMessages);
		
		req.setAttribute("getUser", getUser);
		req.setAttribute("nbMess", nbMessages);
		req.setAttribute("rank", rank);
		req.getRequestDispatcher("/WEB-INF/jsp/profil.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/jsp/profil.jsp").forward(req, resp);
	}

}
