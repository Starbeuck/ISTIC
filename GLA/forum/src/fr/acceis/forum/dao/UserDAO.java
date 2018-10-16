package fr.acceis.forum.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import fr.acceis.forum.classes.FilThread;
import fr.acceis.forum.classes.Message;
import fr.acceis.forum.classes.User;

public class UserDAO extends DAO<User> {

	public UserDAO(Connection conn) {
		super(conn);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean create(User obj) throws InstantiationException, IllegalAccessException, SQLException {
		boolean rtrn = false;
		PreparedStatement stmt = this.connect.prepareStatement("SELECT COUNT (login) FROM USERS WHERE login = ?");
		stmt.setString(1, obj.getLogin());
		ResultSet res = stmt.executeQuery();

		res.next();
		int ctr = res.getInt(1);

		// create new user if it doesnt exist
		if (ctr == 0) {
			PreparedStatement newUser = this.connect
					.prepareStatement("INSERT INTO USERS (LOGIN, PASSWORD,AGE,GENDER,CITY) VALUES (?,?,?,?,?)");
			newUser.setString(1, obj.getLogin());
			newUser.setString(2, obj.getPassword());
			newUser.setInt(3, obj.getAge());
			newUser.setString(4, obj.getGender());
			newUser.setString(5, obj.getCity());
			newUser.executeUpdate();
			rtrn = true;
		}

		// if user already exists, does nothing
		return rtrn;
	}

	@Override
	public User find(User obj) throws SQLException, InstantiationException, IllegalAccessException {
		PreparedStatement stmt = this.connect
				.prepareStatement("SELECT LOGIN FROM USERS WHERE LOGIN = ? AND PASSWORD = ?");
		stmt.setString(1, obj.getLogin());
		stmt.setString(2, obj.getPassword());
		ResultSet res = stmt.executeQuery();

		if (!res.next()) {
			return null;
		}
		return obj;
	}

	@Override
	public User findByName(String name) throws SQLException, InstantiationException, IllegalAccessException {
		PreparedStatement stmt = this.connect.prepareStatement("SELECT * FROM USERS WHERE LOGIN = ?");
		stmt.setString(1, name);
		ResultSet res = stmt.executeQuery();

		if (!res.next()) {
			return null;
		}

		User user = new User(res.getString("LOGIN"), "", res.getInt("AGE"), res.getString("GENDER"),
				res.getString("CITY"), res.getString("PHOTO"));
		return user;
	}

	@Override
	public boolean delete(User obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(User obj) throws SQLException {
		boolean rtrn = false;
		PreparedStatement stmt = this.connect.prepareStatement("UPDATE USERS SET PHOTO = ? WHERE LOGIN = ? ");
		stmt.setString(1, obj.getPhoto());
		stmt.setString(2, obj.getLogin());
		stmt.executeUpdate();
		rtrn = true;
		// TODO Auto-generated method stub
		return rtrn;
	}

	@Override
	public int findbyID(User obj) throws SQLException, InstantiationException, IllegalAccessException {
		// TODO Auto-generated method stub
		return 0;
	}

}
