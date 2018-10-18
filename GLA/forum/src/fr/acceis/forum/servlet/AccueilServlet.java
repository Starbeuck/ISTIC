package fr.acceis.forum.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.acceis.forum.classes.FilThread;
import fr.acceis.forum.dao.HSQLDBConnection;
import fr.acceis.forum.dao.ThreadDAO;

// TODO: Auto-generated Javadoc
/**
 * The Class AccueilServlet.
 *
 * @author solenn
 */
@SuppressWarnings("serial")
public class AccueilServlet extends HttpServlet {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ThreadDAO DAOThread = null;
		ArrayList<FilThread> ListThread = null;
		
		//try go get a connection and access to all thread
		try {
			DAOThread = new ThreadDAO(HSQLDBConnection.getConnection());
			ListThread = DAOThread.getAllTread();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		//send params to the requete
		req.setAttribute("threads", ListThread);
		req.getRequestDispatcher("/WEB-INF/jsp/threads.jsp").forward(req, resp);
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
		doGet(req, resp);
	}

}
