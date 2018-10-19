package fr.acceis.forum.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;import fr.acceis.forum.classes.Passwords;
import fr.acceis.forum.classes.Role;
import fr.acceis.forum.classes.User;

// TODO: Auto-generated Javadoc
/**
 * The Class UserDAO.
 */
/**
 * @author solenn
 *
 */
public class UserDAO extends DAO<User> {

	/**
	 * Instantiates a new user DAO with connection.
	 *
	 * @param conn the conn
	 */
	public UserDAO(Connection conn) {
		super(conn);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see fr.acceis.forum.dao.DAO#create(java.lang.Object)
	 */
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
					.prepareStatement("INSERT INTO USERS (LOGIN, PASSWORD, AGE, GENDER, CITY, PHOTO, ROLE, SEL) VALUES (?,?,?,?,?,?,?,?)");
			newUser.setString(1, obj.getLogin());
			newUser.setString(2, obj.getPassword());
			newUser.setInt(3, obj.getAge());
			newUser.setString(4, obj.getGender());
			newUser.setString(5, obj.getCity());
			newUser.setString(6, obj.getPhoto());
			newUser.setString(7, "User");
			newUser.setString(8, obj.getSel());
			newUser.executeUpdate();
			rtrn = true;
		}

		// if user already exists, does nothing
		return rtrn;
	}

	/* (non-Javadoc)
	 * @see fr.acceis.forum.dao.DAO#find(java.lang.Object)
	 */
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

	/* (non-Javadoc)
	 * @see fr.acceis.forum.dao.DAO#findByName(java.lang.String)
	 */
	@Override
	public User findByName(String name) throws SQLException, InstantiationException, IllegalAccessException {
		PreparedStatement stmt = this.connect.prepareStatement("SELECT * FROM USERS WHERE LOGIN = ?");
		stmt.setString(1, name);
		ResultSet res = stmt.executeQuery();

		if (!res.next()) {
			return null;
		}

		User user = new User(res.getString("LOGIN"), res.getString("PASSWORD"), res.getInt("AGE"), res.getString("GENDER"),
				res.getString("CITY"), res.getString("PHOTO"), convert(res.getString("ROLE")), res.getString("SEL"));
		
		return user;
	}

	/**
	 * Convert.
	 *
	 * @param role the role
	 * @return the role
	 */
	// converti le role en numéro
	private Role convert(String role) {
		switch (role) {
		case "Invit":
			return Role.Invit;
		case "User":
			return Role.User;
		case "Modo":
			return Role.Modo;
		case "Admin":
			return Role.Admin;
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see fr.acceis.forum.dao.DAO#delete(java.lang.Object)
	 */
	@Override
	public boolean delete(User obj) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see fr.acceis.forum.dao.DAO#update(java.lang.Object)
	 */
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

	/* (non-Javadoc)
	 * @see fr.acceis.forum.dao.DAO#findbyID(java.lang.Object)
	 */
	@Override
	public int findbyID(User obj) throws SQLException, InstantiationException, IllegalAccessException {
		// TODO Auto-generated method stub
		return 0;
	}

}
