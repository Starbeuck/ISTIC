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
import javax.servlet.http.HttpSession;

import fr.acceis.forum.classes.FilThread;
import fr.acceis.forum.classes.User;
import fr.acceis.forum.dao.DAO;
import fr.acceis.forum.dao.HSQLDBConnection;
import fr.acceis.forum.dao.ThreadDAO;
import fr.acceis.forum.dao.UserDAO;

@SuppressWarnings("serial")
public class LoginServlet extends HttpServlet {
	public static final String CHAMP_LOGIN = "login";
	public static final String CHAMP_PASS = "password";
	public static final String ATT_ERREURS = "erreurs";
	public static final String ATT_RESULTAT = "resultat";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String login = req.getParameter("username");
		String password = req.getParameter("password");
		Map<String, String> erreurs = new HashMap<String, String>();

		// not empty login
		try {
			validation(login);
		} catch (Exception e) {
			erreurs.put(CHAMP_LOGIN, e.getMessage());
		}

		// not empty password
		try {
			validation(password);
		} catch (Exception e) {
			erreurs.put(CHAMP_PASS, e.getMessage());
		}

		// summary
		if (erreurs.isEmpty()) {
			try {
				// create user
				User newUser = new User(login, password);

				// connect to database and create user
				DAO<User> DAOUser = new UserDAO(HSQLDBConnection.getConnection());
				User getUser = DAOUser.find(newUser);

				// user has been created
				if (getUser!= null) {
					String log = getUser.getLogin();
					req.getSession().setAttribute("user", log);
					
					ArrayList<FilThread> ListThread = null;
					try {
						ListThread = ThreadDAO.getAllTread();
					} catch (InstantiationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					req.setAttribute("threads", ListThread);
					req.getRequestDispatcher("/WEB-INF/jsp/threads.jsp").forward(req, resp);
				} else {
					erreurs.put(ATT_RESULTAT, "User doesn't exists or wrong password.");
				}

			} catch (InstantiationException | IllegalAccessException | SQLException e) {
				
			}
		} 

		// forward to request
		req.setAttribute(ATT_ERREURS, erreurs);
		req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, resp);

	}

	private void validation(String str) throws Exception {
		if (str.isEmpty()) {
			throw new Exception("Please fill the field ! ");
		}
	}
}
