package fr.acceis.forum.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.acceis.forum.classes.FilThread;
import fr.acceis.forum.classes.Message;
import fr.acceis.forum.dao.DAO;
import fr.acceis.forum.dao.HSQLDBConnection;
import fr.acceis.forum.dao.MessageDAO;
import fr.acceis.forum.dao.ThreadDAO;

@SuppressWarnings("serial")
public class MessageServlet extends HttpServlet {
	public static final String CHAMP_TITLE = "title";
	public static final String CHAMP_CONTENT = "content";
	public static final String ATT_ERREURS = "erreurs";
	public static final String ATT_RESULTAT = "resultat";
	String title = "";
	String authorTopic = "";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		title = req.getParameter("title");
		authorTopic = req.getParameter("author");
		System.out.println(authorTopic);
		req.getRequestDispatcher("/WEB-INF/jsp/message.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String author = (String) req.getSession().getAttribute("user");
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
				FilThread newThread = new FilThread(title, authorTopic);

				// connect to database and create user
				DAO<FilThread> DAOThread = new ThreadDAO(HSQLDBConnection.getConnection());
				int id = DAOThread.findbyID(newThread);

				// create the first message of the topic
				Message newMessage = new Message(author, content, id);
				
				DAO<Message> DAOMessage = new MessageDAO(HSQLDBConnection.getConnection());
				Boolean isPosted = DAOMessage.create(newMessage);

				if (isPosted) {
					resultat = "New Message Added ! Other users can see it and answer to it";
				} else {
					erreurs.put(ATT_RESULTAT, "Cannot add message to topic, please contact admin.");
				}

			} catch (InstantiationException | IllegalAccessException | SQLException e) {
				erreurs.put(ATT_RESULTAT, "Cannot add thread to databse");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else

		{
			resultat = "Failed Adding. Please start again.";
		}

		// forward to request
		req.setAttribute(ATT_ERREURS, erreurs);
		req.setAttribute(ATT_RESULTAT, resultat);
		req.setAttribute("title", title);
		req.setAttribute("topic", authorTopic);
		req.getRequestDispatcher("/WEB-INF/jsp/message.jsp").forward(req, resp);

	}

	private void validation(String str) throws Exception {
		if (str.isEmpty()) {
			throw new Exception("Please fill the field ! ");
		}
		if (str.length() > 4999) {
			throw new Exception("Content too long !");
		}
	}
}
