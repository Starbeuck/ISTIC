package fr.acceis.forum.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import fr.acceis.forum.classes.FilThread;

// TODO: Auto-generated Javadoc
/**
 * The Class ThreadDAO.
 *
 * @author solenn
 */
public class ThreadDAO extends DAO<FilThread> {

	/**
	 * Instantiates a new thread DAO with connection.
	 *
	 * @param conn the conn
	 */
	public ThreadDAO(Connection conn) {
		super(conn);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Gets the all tread.
	 *
	 * @return the all tread
	 * @throws SQLException the SQL exception
	 * @throws InstantiationException the instantiation exception
	 * @throws IllegalAccessException the illegal access exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	public ArrayList<FilThread> getAllTread()
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		ArrayList<FilThread> all = new ArrayList<FilThread>();
		PreparedStatement stmt = this.connect.prepareStatement("SELECT * FROM THREAD");
		ResultSet res = stmt.executeQuery();
		while (res.next()) {
			FilThread tmp = new FilThread(res.getString("TITLE"), res.getString("AUTHOR"), res.getInt("NBMESSAGES"));
			all.add(tmp);
		}
		return all;
	}

	/* (non-Javadoc)
	 * @see fr.acceis.forum.dao.DAO#create(java.lang.Object)
	 */
	@Override
	public boolean create(FilThread obj) throws InstantiationException, IllegalAccessException, SQLException {
		boolean rtrn = false;
		PreparedStatement stmt = this.connect.prepareStatement("SELECT COUNT (TITLE) FROM THREAD WHERE TITLE = ?");
		stmt.setString(1, obj.getTitle());
		ResultSet res = stmt.executeQuery();

		res.next();
		int ctr = res.getInt(1);

		// create new topic is title is free
		if (ctr == 0) {
			PreparedStatement newUser = this.connect
					.prepareStatement("INSERT INTO THREAD (TITLE, AUTHOR, NBMESSAGES) VALUES (?,?, ?)");
			newUser.setString(1, obj.getTitle());
			newUser.setString(2, obj.getAuthor());
			newUser.setInt(3, obj.getNbMessage());
			newUser.executeUpdate();
			rtrn = true;
		}

		// if user already exists, does nothing
		return rtrn;
	}

	/* (non-Javadoc)
	 * @see fr.acceis.forum.dao.DAO#delete(java.lang.Object)
	 */
	@Override
	public boolean delete(FilThread obj) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see fr.acceis.forum.dao.DAO#update(java.lang.Object)
	 */
	@Override
	public boolean update(FilThread obj) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see fr.acceis.forum.dao.DAO#find(java.lang.Object)
	 */
	@Override
	public FilThread find(FilThread obj) throws SQLException, InstantiationException, IllegalAccessException {
		return obj;
	}

	/* (non-Javadoc)
	 * @see fr.acceis.forum.dao.DAO#findbyID(java.lang.Object)
	 */
	@Override
	public int findbyID(FilThread obj) throws SQLException, InstantiationException, IllegalAccessException {
		PreparedStatement stmt = this.connect.prepareStatement("SELECT ID FROM THREAD WHERE TITLE = ?");
		stmt.setString(1, obj.getTitle());
		ResultSet res = stmt.executeQuery();

		res.next();
		int id = res.getInt(1);

		return id;
	}

	/* (non-Javadoc)
	 * @see fr.acceis.forum.dao.DAO#findByName(java.lang.String)
	 */
	@Override
	public FilThread findByName(String name) throws SQLException {
		PreparedStatement stmt = this.connect.prepareStatement("SELECT * FROM THREAD WHERE TITLE = ?");
		stmt.setString(1, name);
		ResultSet res = stmt.executeQuery();
		FilThread rtrn = null;
		while (res.next()) {
			rtrn = new FilThread(res.getString("TITLE"), res.getString("AUTHOR"), res.getInt("NBMESSAGES"));
		}
		
		// TODO Auto-generated method stub
		return rtrn;
	}
}
