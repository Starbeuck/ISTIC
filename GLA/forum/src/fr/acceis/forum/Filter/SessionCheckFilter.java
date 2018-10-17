package fr.acceis.forum.Filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.LogRecord;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.acceis.forum.classes.Role;

public class SessionCheckFilter implements Filter {
	final String ACCES_LOGIN = "/login";
	final String ACCES_MESSAGE = "/message";
	final String ACCES_PROFIL = "/profil.jsp";
	final String ACCES_THREAD = "/thread";
	final String ACCES_UPLOAD = "/upload";
	final String ACCES_REMOVE = "/remove";

	Map<String, Integer> urlAccess;
	final boolean[][] Rules = {
			// Invit, User, Modo, Admin
			{ true, true, true, true }, // read
			{ false, true, true, true }, // add message
			{ false, true, true, true }, // see profil
			{ false, false, true, true } // delete msg
	};

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		urlAccess = new HashMap<>();
		urlAccess.put(ACCES_LOGIN, 0);
		urlAccess.put(ACCES_MESSAGE, 1);
		urlAccess.put(ACCES_PROFIL, 1);
		urlAccess.put(ACCES_THREAD, 1);
		urlAccess.put(ACCES_UPLOAD, 1);
		urlAccess.put(ACCES_REMOVE, 2);
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse rep = (HttpServletResponse) response;

		// get URL pass into the filter
		String URL = req.getServletPath();
		if (URL.substring(0, 1).equals("/")) {
			System.out.println(URL);
			URL = URL.substring(1);
		}

		// get attribut name if user connected
		String name = (String) req.getSession().getAttribute("user");
		Role role = (Role) req.getSession().getAttribute("role");

		if (name == null) {
			System.out.println("SessionCheck non connecté, salut !");
			req.setAttribute("URL", URL);
			request.getRequestDispatcher(ACCES_LOGIN).forward(request, response);
		} else {
			Integer right = urlAccess.get(URL);
			int intRight = convert(role);
			if (right == null) {
				System.out.println("URL non reconnu " + URL);
			} else {
				System.out.println("SessionCheck connecté ! On passe à la suite " + URL);

				if (!Rules[right][intRight]) {
					req.getRequestDispatcher("/WEB-INF/jsp/error401.jsp").forward(req, rep);
					return;
				}
			}
		}
		System.out.println("TOUT S'EST BIEN PASSE !  " + URL);
		chain.doFilter(req, rep);

	}

	// converti le role en numéro
	private int convert(Role role) {
		switch (role) {
		case Invit:
			return 0;
		case User:
			return 1;
		case Modo:
			return 2;
		case Admin:
			return 3;
		default:
			return 50;
		}
	}

	@Override
	public void destroy() {
	}

	public boolean isLoggable(LogRecord record) {
		// TODO Auto-generated method stub
		return false;
	}
}
