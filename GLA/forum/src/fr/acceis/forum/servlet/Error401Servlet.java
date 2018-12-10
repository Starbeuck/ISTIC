package fr.acceis.forum.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

// TODO: Auto-generated Javadoc
/**
 * The Class Error401Servlet.
 */
/**
 * @author solenn
 *
 */
@SuppressWarnings("serial")
public class Error401Servlet extends HttpServlet {

	/** logger */
	final static Logger logger = Logger.getLogger(Error401Servlet.class);
	
	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		logger.error("Try to get a forbidden resources");
		req.getRequestDispatcher("/WEB-INF/jsp/error401.jsp").forward(req, resp);
	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/jsp/error401.jsp").forward(req, resp);
	}
}
