package fr.acceis.forum.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
 * The Class RemoveServlet.
 */
/**
 * @author solenn
 *
 */
@SuppressWarnings("serial")
public class RemoveServlet extends HttpServlet {

	/** The name. */
	String name;

	/** The content. */
	String content;

	/** The photo. */
	String photo;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		name = req.getParameter("author");
		content = req.getParameter("content");
		photo = req.getParameter("photo");
		req.setAttribute("photo", photo);
		req.setAttribute("content", content);
		req.setAttribute("author", name);
		req.getRequestDispatcher("/WEB-INF/jsp/remove.jsp").forward(req, resp);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String password = req.getParameter("password");
		String currenThread = (String) req.getSession().getAttribute("currentThread");
		String currenAuthThread = (String) req.getSession().getAttribute("currentAuthThread");
		String passSession = (String) req.getSession().getAttribute("password");
		Map<String, String> erreurs = new HashMap<String, String>();
		String resultat = "";

		DAO<Message> DAOMessage = null;
		DAO<User> DAOUser = null;
		DAO<FilThread> DAOThread = null;
		User user = null;
		FilThread tmpThread = null;
		int id = 0;

		if (passSession.equals(password)) {
			try {
				DAOUser = new UserDAO(HSQLDBConnection.getConnection());
				user = DAOUser.findByName(name);

				DAOThread = new ThreadDAO(HSQLDBConnection.getConnection());
				tmpThread = DAOThread.findByName(currenThread);
				id = DAOThread.findbyID(tmpThread);
				
				Message tmp = new Message(user, content, id);
				DAOMessage = new MessageDAO(HSQLDBConnection.getConnection());
				DAOMessage.delete(tmp);

				resp.sendRedirect("/forum/home");
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			// forward to requet
			erreurs.put("resultat", "Wrong PassWord");
			req.setAttribute("erreurs", erreurs);
			req.setAttribute("photo", photo);
			req.setAttribute("content", content);
			req.setAttribute("author", name);
			req.getRequestDispatcher("/WEB-INF/jsp/remove.jsp").forward(req, resp);

		}

	}
}
