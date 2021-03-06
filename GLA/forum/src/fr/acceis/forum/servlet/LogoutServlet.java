package fr.acceis.forum.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

// TODO: Auto-generated Javadoc
/**
 * The Class LogoutServlet.
 *
 * @author solenn
 */
@SuppressWarnings("serial")
public class LogoutServlet extends HttpServlet {
	

	/** logger */
	final static Logger logger = Logger.getLogger(LogoutServlet.class);
	
	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		logger.info(req.getSession().getAttribute("user")+" deconnected");
		session.invalidate();
		req.getRequestDispatcher("/WEB-INF/jsp/logout.jsp").forward(req, resp);
	}
}