package fr.acceis.forum.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import fr.acceis.forum.classes.Message;
import fr.acceis.forum.classes.User;

// TODO: Auto-generated Javadoc
/**
 * The Class MessageDAO.
 */
/**
 * @author solenn
 *
 */
public class MessageDAO extends DAO<Message> {

	/**
	 * Instantiates a new message DAO with connection
	 *
	 * @param conn the conn
	 */
	public MessageDAO(Connection conn) {
		super(conn);
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.acceis.forum.dao.DAO#create(java.lang.Object)
	 */
	@Override
	public boolean create(Message obj) throws SQLException {
		boolean rtrn = false;
		PreparedStatement newMessage = this.connect
				.prepareStatement("INSERT INTO MESSAGE (AUTHOR, CONTENT, IDTHREAD) VALUES (?,?,?)");
		newMessage.setString(1, obj.getAuthor().getLogin());
		newMessage.setString(2, obj.getText());
		newMessage.setInt(3, obj.getIdThread());
		newMessage.executeUpdate();

		PreparedStatement IncrThread = this.connect
				.prepareStatement("UPDATE THREAD SET NBMESSAGES = NBMESSAGES+1 WHERE ID = ?");
		IncrThread.setInt(1, obj.getIdThread());
		IncrThread.executeUpdate();

		rtrn = true;
		return rtrn;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.acceis.forum.dao.DAO#delete(java.lang.Object)
	 */
	@Override
	public boolean delete(Message obj) throws SQLException {
		boolean rtrn = false;
		PreparedStatement stmt = this.connect.prepareStatement("DELETE FROM MESSAGE WHERE AUTHOR = ? AND CONTENT = ?");
		stmt.setString(1, obj.getAuthor().getLogin());
		stmt.setString(2, obj.getText());
		stmt.executeUpdate();

		PreparedStatement IncrThread = this.connect
				.prepareStatement("UPDATE THREAD SET NBMESSAGES = NBMESSAGES-1 WHERE ID = ?");
		IncrThread.setInt(1, obj.getIdThread());
		IncrThread.executeUpdate();

		rtrn = true;
		return rtrn;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.acceis.forum.dao.DAO#update(java.lang.Object)
	 */
	@Override
	public boolean update(Message obj) throws SQLException {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.acceis.forum.dao.DAO#find(java.lang.Object)
	 */
	@Override
	public Message find(Message obj) throws SQLException, InstantiationException, IllegalAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.acceis.forum.dao.DAO#findbyID(java.lang.Object)
	 */
	@Override
	public int findbyID(Message obj) throws SQLException, InstantiationException, IllegalAccessException {
		int ctr = 0;
		PreparedStatement stmt = this.connect.prepareStatement("SELECT COUNT (AUTHOR) FROM MESSAGE WHERE AUTHOR = ?");
		stmt.setString(1, obj.getAuthor().getLogin());
		ResultSet res = stmt.executeQuery();

		res.next();
		ctr = res.getInt(1);

		return ctr;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.acceis.forum.dao.DAO#findByName(java.lang.String)
	 */
	@Override
	public Message findByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Gets the all message.
	 *
	 * @param id the id
	 * @return the all message
	 * @throws SQLException           the SQL exception
	 * @throws InstantiationException the instantiation exception
	 * @throws IllegalAccessException the illegal access exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	public static ArrayList<Message> getAllMessage(int id)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		ArrayList<Message> all = new ArrayList<Message>();
		PreparedStatement stmt = HSQLDBConnection.getConnection()
				.prepareStatement("SELECT * FROM MESSAGE WHERE IDTHREAD = ?");
		stmt.setInt(1, id);
		ResultSet res = stmt.executeQuery();

		while (res.next()) {
			String nameAuth = res.getString("AUTHOR");
			UserDAO DAOUser = new UserDAO(HSQLDBConnection.getConnection());
			User auth = DAOUser.findByName(nameAuth);
			Message tmp = new Message(auth, res.getString("CONTENT"), res.getInt("IDTHREAD"));
			all.add(tmp);
		}
		return all;
	}
}
