package fr.acceis.forum.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.acceis.forum.classes.FilThread;
import fr.acceis.forum.classes.Message;

public class ThreadDAO extends DAO<FilThread> {

	public ThreadDAO(Connection conn) {
		super(conn);
		// TODO Auto-generated constructor stub
	}

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

	@Override
	public boolean delete(FilThread obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(FilThread obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public FilThread find(FilThread obj) throws SQLException, InstantiationException, IllegalAccessException {
		return obj;
	}

	@Override
	public int findbyID(FilThread obj) throws SQLException, InstantiationException, IllegalAccessException {
		PreparedStatement stmt = this.connect.prepareStatement("SELECT ID FROM THREAD WHERE TITLE = ?");
		stmt.setString(1, obj.getTitle());
		ResultSet res = stmt.executeQuery();

		res.next();
		int id = res.getInt(1);

		return id;
	}

	@Override
	public FilThread findByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}
}
