package fr.acceis.forum.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
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
@WebServlet(name = "/thread", urlPatterns = { "/thread" })
public class ThreadServlet extends HttpServlet {
	String title = "";
	String author = "";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		title = req.getParameter("title");
		author = req.getParameter("author");

		FilThread tmp = new FilThread(title, author);

		DAO<FilThread> DAOThread = null;
		try {
			DAOThread = new ThreadDAO(HSQLDBConnection.getConnection());
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		int id = 0;
		try {
			id = DAOThread.findbyID(tmp);
		} catch (InstantiationException | IllegalAccessException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		ArrayList<Message> ListMessage = null;

		try {
			ListMessage = MessageDAO.getAllMessage(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		req.setAttribute("messages", ListMessage);
		req.getRequestDispatcher("/WEB-INF/jsp/thread.jsp").forward(req, resp);

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/jsp/thread.jsp").forward(req, resp);

	}
}
