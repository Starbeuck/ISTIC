package fr.acceis.forum.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.acceis.forum.classes.Passwords;
import fr.acceis.forum.classes.Role;
import fr.acceis.forum.classes.User;
import fr.acceis.forum.dao.DAO;
import fr.acceis.forum.dao.HSQLDBConnection;
import fr.acceis.forum.dao.UserDAO;

// TODO: Auto-generated Javadoc
/**
 * The Class SignUpServlet.
 */
/**
 * @author solenn
 *
 */
@SuppressWarnings("serial")
public class SignUpServlet extends HttpServlet {
	
	/** The Constant CHAMP_LOGIN. */
	public static final String CHAMP_LOGIN = "login";
	
	/** The Constant CHAMP_PASS. */
	public static final String CHAMP_PASS = "password";
	
	/** The Constant CHAMP_AGE. */
	public static final String CHAMP_AGE = "age";
	
	/** The Constant CHAMP_GENDER. */
	public static final String CHAMP_GENDER = "gender";
	
	/** The Constant CHAMP_CITY. */
	public static final String CHAMP_CITY = "city";
	
	/** The Constant ATT_ERREURS. */
	public static final String ATT_ERREURS = "erreurs";
	
	/** The Constant ATT_RESULTAT. */
	public static final String ATT_RESULTAT = "resultat";
	
	/** The Constant ATT_ALREADY. */
	public static final String ATT_ALREADY = "already";

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/jsp/signup.jsp").forward(req, resp);
	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String login = req.getParameter("login");
		String password = req.getParameter("password");
		String tmpAge = req.getParameter("age");
		String gender = req.getParameter("gender");
		String city = req.getParameter("city");

		Map<String, String> erreurs = new HashMap<String, String>();
		String resultat = "";

		int age = Integer.valueOf(tmpAge);

		// not empty age or invalid age
		try {
			validationAge(age);
		} catch (Exception e) {
			erreurs.put(CHAMP_AGE, e.getMessage());
		}

		// summary
		if (erreurs.isEmpty()) {
			try {
				
				// pick a picture
				String photo = "";
				if (gender.equals("Male")) {
					photo = "fichiers/imgs/SuperMan.png";
				} else {
					photo = "fichiers/imgs/WonderWoman.png";
				}

				Passwords pass = new Passwords(password, Passwords.getNextSalt());
				// create user
				User newUser = new User(login, pass.getPassword(), age, gender, city, photo, Role.User, pass.getSalt());
				
				// connect to database and create user
				DAO<User> DAOUser = new UserDAO(HSQLDBConnection.getConnection());
				Boolean isCreated = DAOUser.create(newUser);

				// user has been created
				if (isCreated) {
					resultat = "Success Subscription. You can connect into login.";
				} else {
					erreurs.put(ATT_ALREADY, "User already exists ! ");
				}

			} catch (Exception e) {
				erreurs.put(ATT_ALREADY, "Cannot add user to database");
			}

		} else {
			resultat = "Failed Subscription. Please start again.";
		}

		// forward to request
		req.setAttribute(ATT_ERREURS, erreurs);
		req.setAttribute(ATT_RESULTAT, resultat);
		req.getRequestDispatcher("/WEB-INF/jsp/signup.jsp").forward(req, resp);

	}

	/**
	 * Validation age.
	 *
	 * @param str the str
	 * @throws Exception the exception
	 */
	private void validationAge(int str) throws Exception {
		if (str < 18) {
			throw new Exception("You are too young !");
		}
	}

}
