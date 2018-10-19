package fr.acceis.forum.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.acceis.forum.classes.User;

import fr.acceis.forum.classes.FilThread;
import fr.acceis.forum.classes.Message;
import fr.acceis.forum.dao.DAO;
import fr.acceis.forum.dao.HSQLDBConnection;
import fr.acceis.forum.dao.MessageDAO;
import fr.acceis.forum.dao.ThreadDAO;
import fr.acceis.forum.dao.UserDAO;

// TODO: Auto-generated Javadoc
/**
 * The Class TopicServlet.
 */
/**
 * @author solenn
 *
 */
@SuppressWarnings("serial")
public class TopicServlet extends HttpServlet {

	/** The Constant CHAMP_TITLE. */
	public static final String CHAMP_TITLE = "title";

	/** The Constant CHAMP_CONTENT. */
	public static final String CHAMP_CONTENT = "content";

	/** The Constant ATT_ERREURS. */
	public static final String ATT_ERREURS = "erreurs";

	/** The Constant ATT_RESULTAT. */
	public static final String ATT_RESULTAT = "resultat";

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/jsp/topic.jsp").forward(req, resp);
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
		String title = req.getParameter("title");
		String author = (String) req.getSession().getAttribute("user");
		String content = req.getParameter("content");

		DAO<User> DAOUser = null;
		User auth = null;

		Map<String, String> erreurs = new HashMap<String, String>();
		String resultat = "";
		
		try {
			DAOUser = new UserDAO(HSQLDBConnection.getConnection());
			auth = DAOUser.findByName(author);
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		// not empty login
		try {
			validation(title);
		} catch (Exception e) {
			erreurs.put(CHAMP_TITLE, e.getMessage());
		}

		// not empty password
		try {
			validation(content);
		} catch (Exception e) {
			erreurs.put(CHAMP_CONTENT, e.getMessage());
		}

		// summary
		if (erreurs.isEmpty()) {
			try {

				// create new Thread with a new topic
				FilThread newThread = new FilThread(title, author, 0);

				// connect to database and create user
				DAO<FilThread> DAOThread = new ThreadDAO(HSQLDBConnection.getConnection());
				Boolean isCreated = DAOThread.create(newThread);

				// topic has been post
				if (isCreated) {
					int id = DAOThread.findbyID(newThread);

					// create the first message of the topic
					Message newMessage = new Message(auth, content, id);
					DAO<Message> DAOMessage = new MessageDAO(HSQLDBConnection.getConnection());
					Boolean isPosted = DAOMessage.create(newMessage);

					if (isPosted) {
						resultat = "Topic Created ! Other user can see it and answer to it";
					} else {
						erreurs.put(ATT_RESULTAT, "Cannot add message to topic, please contact admin.");
					}

				} else {
					erreurs.put(ATT_RESULTAT, "Title already taken. Pick another one please.");
				}

			} catch (InstantiationException | IllegalAccessException | SQLException e) {
				erreurs.put(ATT_RESULTAT, "Cannot add thread to databse");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			resultat = "Failed Adding. Please start again.";
		}

		// forward to request
		req.setAttribute(ATT_ERREURS, erreurs);
		req.setAttribute(ATT_RESULTAT, resultat);
		req.getRequestDispatcher("/WEB-INF/jsp/topic.jsp").forward(req, resp);

	}

	/**
	 * Validation.
	 *
	 * @param str the str
	 * @throws Exception the exception
	 */
	private void validation(String str) throws Exception {
		if (str.isEmpty()) {
			throw new Exception("Please fill the field ! ");
		}
		if (str.length() > 4999) {
			throw new Exception("Content too long !");
		}
	}

}
