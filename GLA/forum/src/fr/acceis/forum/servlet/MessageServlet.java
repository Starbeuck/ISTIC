package fr.acceis.forum.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

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
 * The Class MessageServlet.
 */
/**
 * @author solenn
 *
 */
@SuppressWarnings("serial")
public class MessageServlet extends HttpServlet {

	/** The Constant CHAMP_TITLE. */
	public static final String CHAMP_TITLE = "title";

	/** The Constant CHAMP_CONTENT. */
	public static final String CHAMP_CONTENT = "content";

	/** The Constant ATT_ERREURS. */
	public static final String ATT_ERREURS = "erreurs";

	/** The Constant ATT_RESULTAT. */
	public static final String ATT_RESULTAT = "resultat";

	/** The title. */
	String title = "";

	/** The author topic. */
	String authorTopic = "";

	/** logger */
	final static Logger logger = Logger.getLogger(MessageServlet.class);
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		title = req.getParameter("title");
		authorTopic = req.getParameter("author");
		req.getRequestDispatcher("/WEB-INF/jsp/message.jsp").forward(req, resp);
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
		String author = (String) req.getSession().getAttribute("user");

		DAO<User> DAOUser = null;
		User auth = null;

		// Get user from auth
		try {
			DAOUser = new UserDAO(HSQLDBConnection.getConnection());
			auth = DAOUser.findByName(author);
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e2) {
			// TODO Auto-generated catch block
			logger.warn("Failed to find author to post the message");
			e2.printStackTrace();
		}

		logger.info(auth.getLogin() + " try to a message");
		String content = req.getParameter("content");

		Map<String, String> erreurs = new HashMap<String, String>();
		String resultat = "";

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
				FilThread newThread = new FilThread(title, authorTopic, 0);

				// connect to database and create user
				DAO<FilThread> DAOThread = new ThreadDAO(HSQLDBConnection.getConnection());
				int id = DAOThread.findbyID(newThread);

				// create the first message of the topic
				Message newMessage = new Message(auth, content, id);

				DAO<Message> DAOMessage = new MessageDAO(HSQLDBConnection.getConnection());
				Boolean isPosted = DAOMessage.create(newMessage);

				if (isPosted) {
					logger.info(auth.getLogin() + " post a message");
					resultat = "New Message Added ! Other users can see it and answer to it";
				} else {
					logger.warn(auth.getLogin() + " failed to post a message");
					erreurs.put(ATT_RESULTAT, "Cannot add message to topic, please contact admin.");
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
		req.setAttribute("title", title);
		req.setAttribute("topic", authorTopic);
		req.getRequestDispatcher("/WEB-INF/jsp/message.jsp").forward(req, resp);

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
