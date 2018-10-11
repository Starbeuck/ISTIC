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
import fr.acceis.forum.dao.DAO;
import fr.acceis.forum.dao.HSQLDBConnection;
import fr.acceis.forum.dao.UserDAO;

@SuppressWarnings("serial")
public class SignUpServlet extends HttpServlet {
	public static final String CHAMP_LOGIN = "login";
	public static final String CHAMP_PASS = "password";
	public static final String ATT_ERREURS = "erreurs";
	public static final String ATT_RESULTAT = "resultat";
	public static final String ATT_ALREADY = "already";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/jsp/signup.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String login = req.getParameter("login");
		String password = req.getParameter("password");
		Map<String, String> erreurs = new HashMap<String, String>();
		String resultat = "";

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
				//create user
				User newUser = new User(login, password);

				//connect to database and create user
				DAO<User> DAOUser = new UserDAO(HSQLDBConnection.getConnection());
				Boolean isCreated = DAOUser.create(newUser);
 
				// user has been created
				if (isCreated) {
					resultat = "Success Subscription. You can connect into login.";
				} else {
					erreurs.put(ATT_ALREADY, "User already exists ! ");
				}

			} catch (Exception e) {
				erreurs.put(ATT_ALREADY, "Cannot add user to databse");
			}

		} else {
			resultat = "Failed Subscription. Please start again.";
		}

		// forward to request
		req.setAttribute(ATT_ERREURS, erreurs);
		req.setAttribute(ATT_RESULTAT, resultat);
		req.getRequestDispatcher("/WEB-INF/jsp/signup.jsp").forward(req, resp);

	}

	private void validation(String str) throws Exception {
		if (str.isEmpty()) {
			throw new Exception("Please fill the field ! ");
		}
	}

}
