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

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import fr.acceis.forum.classes.FilThread;
import fr.acceis.forum.classes.Passwords;
import fr.acceis.forum.classes.User;
import fr.acceis.forum.dao.DAO;
import fr.acceis.forum.dao.HSQLDBConnection;
import fr.acceis.forum.dao.ThreadDAO;
import fr.acceis.forum.dao.UserDAO;

// TODO: Auto-generated Javadoc
/**
 * The Class LoginServlet.
 */
/**
 * @author solenn
 *
 */
@SuppressWarnings("serial")
public class LoginServlet extends HttpServlet {

	/** The Constant CHAMP_LOGIN. */
	public static final String CHAMP_LOGIN = "login";

	/** The Constant CHAMP_PASS. */
	public static final String CHAMP_PASS = "password";

	/** The Constant ATT_ERREURS. */
	public static final String ATT_ERREURS = "erreurs";

	/** The Constant ATT_RESULTAT. */
	public static final String ATT_RESULTAT = "resultat";

	/** logger */
	final static Logger logger = Logger.getLogger(LoginServlet.class);
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, resp);
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
		String login = req.getParameter("username");
		String password = req.getParameter("password");
		Map<String, String> erreurs = new HashMap<String, String>();

		// summary
		if (erreurs.isEmpty()) {
			try {
				// connect to database and create user
				DAO<User> DAOUser = new UserDAO(HSQLDBConnection.getConnection());

				User real = DAOUser.findByName(login);
				logger.info(real.getLogin() + " try to connect");
				
				Passwords pass = new Passwords(real.getLogin(), real.getSel());
				
				String realPass = real.getPassword();
				String PassPass = pass.getPassword();
				
				if (realPass.equals(PassPass)) {
					logger.info(real.getLogin() + " is connected");
					
					// set cookie session
					req.getSession().setAttribute("user", real.getLogin());
					req.getSession().setAttribute("password", PassPass);
					req.getSession().setAttribute("role", real.getRole());

					// getting all thread to display on the welcome page
					ThreadDAO DAOThread = null;
					ArrayList<FilThread> ListThread = null;
					try {
						DAOThread = new ThreadDAO(HSQLDBConnection.getConnection());
						ListThread = DAOThread.getAllTread();
					} catch (InstantiationException | IllegalAccessException | ClassNotFoundException
							| SQLException e1) {
						// TODO Auto-generated catch block
						logger.error("Failed to load database");
						e1.printStackTrace();
					}

					// forward to request
					req.setAttribute("threads", ListThread);
					req.getRequestDispatcher("/WEB-INF/jsp/threads.jsp").forward(req, resp);
				} else {
					logger.warn(real.getLogin() + " failed to log in");
					erreurs.put(ATT_RESULTAT, "Wrong information connexion.");
				}
			} catch (InstantiationException | IllegalAccessException | SQLException | ClassNotFoundException e) {
			}
		}

		// forward to request
		req.setAttribute(ATT_ERREURS, erreurs);
		doGet(req, resp);
	}
}