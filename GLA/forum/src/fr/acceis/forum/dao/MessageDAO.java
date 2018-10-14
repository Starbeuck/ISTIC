package fr.acceis.forum.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import fr.acceis.forum.classes.FilThread;
import fr.acceis.forum.classes.Message;

public class MessageDAO extends DAO<Message> {

	public MessageDAO(Connection conn) {
		super(conn);
		// TODO Auto-generated constructor stub
	}
	
	public static ArrayList<Message> getAllMessage(int id) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		ArrayList<Message> all = new ArrayList<Message>();
		PreparedStatement stmt = HSQLDBConnection.getConnection().prepareStatement("SELECT * FROM MESSAGE WHERE IDTHREAD = ?");
		stmt.setInt(1, id);
		ResultSet res = stmt.executeQuery();
		while(res.next()) {
			Message tmp = new Message(res.getString("AUTHOR"),res.getString("CONTENT"), res.getInt("IDTHREAD"));
			all.add(tmp);
		}
		return all;
	}
	@Override
	public boolean create(Message obj) throws SQLException {
		boolean rtrn = false;
		PreparedStatement newMessage = this.connect
				.prepareStatement("INSERT INTO MESSAGE (AUTHOR, CONTENT, IDTHREAD) VALUES (?,?,?)");
		newMessage.setString(1, obj.getAuthor());
		newMessage.setString(2, obj.getText());
		newMessage.setInt(3, obj.getIdThread());
		newMessage.executeUpdate();
		
		PreparedStatement IncrThread = this.connect.prepareStatement("UPDATE THREAD SET NBMESSAGES = NBMESSAGES+1 WHERE ID = ?");
		IncrThread.setInt(1, obj.getIdThread());
		IncrThread.executeUpdate();
		
		rtrn = true;
		return rtrn;
	}

	@Override
	public boolean delete(Message obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Message obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Message find(Message obj) throws SQLException, InstantiationException, IllegalAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int findbyID(Message obj) throws SQLException, InstantiationException, IllegalAccessException {
		// TODO Auto-generated method stub
		return 0;
	}

}
