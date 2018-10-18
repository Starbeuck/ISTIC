package fr.acceis.forum.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.javatuples.Triplet;

import fr.acceis.forum.classes.FilThread;
import fr.acceis.forum.classes.Message;
import fr.acceis.forum.classes.User;
import fr.acceis.forum.dao.DAO;
import fr.acceis.forum.dao.HSQLDBConnection;
import fr.acceis.forum.dao.MessageDAO;
import fr.acceis.forum.dao.ThreadDAO;
import fr.acceis.forum.dao.UserDAO;

// TODO: Auto-generated Javadoc
/**
 * The Class ThreadServlet.
 *
 * @author solenn
 */
@SuppressWarnings("serial")
public class ThreadServlet extends HttpServlet {

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// get title and author of topic
		String title = req.getParameter("title");
		String author = req.getParameter("author");
		req.getSession().setAttribute("currentThread", title);
		req.getSession().setAttribute("currentAuthThread", author);

		// create a template thread to get all the message into thread
		FilThread tmp = new FilThread(title, author, 0);
		DAO<FilThread> DAOThread = null;
		int id = 0;
		ArrayList<Message> ListMessage = null;
		DAO<User> DAOUser = null;

		// get message associated to the id of the topic
		try {
			DAOThread = new ThreadDAO(HSQLDBConnection.getConnection());
			id = DAOThread.findbyID(tmp);
			ListMessage = MessageDAO.getAllMessage(id);
			DAOUser = new UserDAO(HSQLDBConnection.getConnection());
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		req.setAttribute("messages", ListMessage);
		req.getRequestDispatcher("/WEB-INF/jsp/thread.jsp").forward(req, resp);

	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/jsp/thread.jsp").forward(req, resp);

	}
}
