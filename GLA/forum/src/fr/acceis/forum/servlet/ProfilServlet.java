package fr.acceis.forum.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.acceis.forum.classes.Message;
import fr.acceis.forum.classes.Rank;
import fr.acceis.forum.classes.User;
import fr.acceis.forum.dao.DAO;
import fr.acceis.forum.dao.HSQLDBConnection;
import fr.acceis.forum.dao.MessageDAO;
import fr.acceis.forum.dao.UserDAO;

// TODO: Auto-generated Javadoc
/**
 * The Class ProfilServlet.
 */
/**
 * @author solenn
 *
 */
@SuppressWarnings("serial")
public class ProfilServlet extends HttpServlet {

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String name = req.getParameter("author");

		// looking for the user profil into the database
		DAO<User> DAOUser = null;
		User getUser = null;
		int nbMessages = 0;
		
		try {
			//get user 
			DAOUser = new UserDAO(HSQLDBConnection.getConnection());
			getUser = DAOUser.findByName(name);
			
			//get nb nombre message
			Message tmpMess = new Message(getUser, "", 0);
			DAO<Message> DAOMessage = null;
			DAOMessage = new MessageDAO(HSQLDBConnection.getConnection());
			nbMessages = DAOMessage.findbyID(tmpMess);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//get rank according to number of message post
		Rank rank = new Rank(nbMessages);
		
		//forward attributs
		req.setAttribute("getUser", getUser);
		req.setAttribute("nbMess", nbMessages);
		req.setAttribute("rank", rank);
		req.getRequestDispatcher("/WEB-INF/jsp/profil.jsp").forward(req, resp);
	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/jsp/profil.jsp").forward(req, resp);
	}

}
