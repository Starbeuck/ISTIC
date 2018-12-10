package fr.acceis.forum.servlet;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.acceis.forum.classes.Message;
import fr.acceis.forum.classes.Rank;
import fr.acceis.forum.classes.User;
import fr.acceis.forum.dao.DAO;
import fr.acceis.forum.dao.HSQLDBConnection;
import fr.acceis.forum.dao.MessageDAO;
import fr.acceis.forum.dao.UserDAO;

import org.apache.commons.fileupload.*;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

// TODO: Auto-generated Javadoc
/**
 * The Class UploadServlet.
 */
/**
 * @author solenn
 *
 */
@SuppressWarnings("serial")
public class UploadServlet extends HttpServlet {

	/** The Constant ATT_ERREURS. */
	public static final String ATT_ERREURS = "erreurs";

	/** The Constant ATT_RESULTAT. */
	public static final String ATT_RESULTAT = "resultat";

	/** The get user. */
	User getUser = null;

	// upload settings
	private static final int MEMORY_THRESHOLD = 1024 * 1024 * 3; // 3MB
	private static final int MAX_FILE_SIZE = 1024 * 1024 * 40; // 40MB

	/** The Constant MAX_REQUEST_SIZE. */
	private static final int MAX_REQUEST_SIZE = 1024 * 1024 * 50; // 50MB

	/** logger */
	final static Logger logger = Logger.getLogger(UploadServlet.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String author = (String) req.getSession().getAttribute("user");

		// looking for the user profil into the database
		DAO<User> DAOUser = null;

		DAO<Message> DAOMessage = null;

		int nbMessages = 0;
		try {
			DAOUser = new UserDAO(HSQLDBConnection.getConnection());
			getUser = DAOUser.findByName(author);

			// get message post by the user
			Message tmpMess = new Message(getUser, "", 0);

			DAOMessage = new MessageDAO(HSQLDBConnection.getConnection());
			nbMessages = DAOMessage.findbyID(tmpMess);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// get rank according to number of message post
		Rank rank = new Rank(nbMessages);

		req.setAttribute("photo", getUser.getPhoto());
		req.setAttribute("getUser", getUser);
		req.setAttribute("nbMess", nbMessages);
		req.setAttribute("rank", rank);
		req.getRequestDispatcher("/WEB-INF/jsp/upload.jsp").forward(req, resp);
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
		Map<String, String> erreurs = new HashMap<String, String>();
		String resultat = "";
		// Check that we have a file upload request
		boolean isMultipart = ServletFileUpload.isMultipartContent(req);

		if (!isMultipart) {
			return;
		}

		// Create a factory for disk-based file items
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// Sets the directory used to temporarily store files that are larger
		// than the configured size threshold. We use temporary directory for
		// java
		factory.setRepository(new File(System.getProperty("java.io.tmpdir")));

		// Create a new file upload handler
		ServletFileUpload upload = new ServletFileUpload(factory);

		try {
			// Parse the request
			List<FileItem> items = upload.parseRequest(req);
			Iterator<FileItem> iter = items.iterator();
			while (iter.hasNext()) {
				FileItem item = (FileItem) iter.next();

				if (item.getName().isEmpty()) {
					erreurs.put(ATT_RESULTAT, "Please add a picture !");
				} else if (!item.isFormField()) {
					String fileName = new File(item.getName()).getName();
					if (!checkExtension(fileName)) {
						logger.error(getUser.getLogin() + " try to add a file with wrong extensions");
						erreurs.put(ATT_RESULTAT, "Wrong extension ! (only .png .jpeg .jpg)");
					} else {
						File test = new File("/home/solenn/Documents/GLA/ForumTP/forum/WebContent/fichiers/imgs/Avatar"
								+ File.separator + fileName);
						item.write(test);
						getUser.setPhoto("fichiers/imgs/Avatar/" + fileName);

						// upload image in database
						DAO<User> DAOUser = new UserDAO(HSQLDBConnection.getConnection());
						DAOUser.update(getUser);
						logger.info(getUser.getLogin() + " changes is picture");
						resultat = "Here your new picture ! ";
					}
				}
			}

			// forward to request
			req.setAttribute(ATT_ERREURS, erreurs);
			req.setAttribute(ATT_RESULTAT, resultat);
			doGet(req, resp);

		} catch (FileUploadException ex) {
			throw new ServletException(ex);
		} catch (Exception ex) {
			throw new ServletException(ex);
		}
	}

	/**
	 * Check extension.
	 *
	 * @param name the name
	 * @return true, if successful
	 */
	public boolean checkExtension(String name) {
		// check extention of file send
		String format = "none";
		int indexLast = name.indexOf(".");
		if (indexLast > 0) {
			format = name.substring(indexLast + 1);
			format = format.toLowerCase();
		}

		System.out.println(format);
		if (!(format.contentEquals("png") || format.contentEquals("jpg") || format.contentEquals("jpeg"))) {
			return false;
		}
		return true;

	}

}
