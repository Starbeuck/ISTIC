package fr.acceis.forum.classes;

// TODO: Auto-generated Javadoc
/**
 * The Class User.
 *
 * @author solenn
 */
public class User {
	
	/** The login. */
	private String login;
	
	/** The password. */
	private String password;
	
	/** The age. */
	private int age;
	
	/** The gender. */
	private String gender;
	
	/** The city. */
	private String city;
	
	/** The photo. */
	private String photo;
	
	/** The role. */
	private Role role;
	
	/**
	 * Instantiates a new user.
	 *
	 * @param log the log
	 * @param pw the pw
	 * @param a the a
	 * @param sex the sex
	 * @param cit the cit
	 * @param photo the photo
	 * @param role the role
	 */
	public User(String log, String pw, int a, String sex, String cit, String photo, Role role) {
		this.setLogin(log);
		this.setPassword(pw);
		this.setAge(a);
		this.setGender(sex);
		this.setCity(cit);
		this.setPhoto(photo);
		this.setRole(role);
	}
	
	/**
	 * Gets the login.
	 *
	 * @return the login
	 */
	public String getLogin() {
		return login;
	}
	
	/**
	 * Gets the password.
	 *
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * Sets the login.
	 *
	 * @param login the new login
	 */
	public void setLogin(String login) {
		this.login = login;
	}
	
	/**
	 * Sets the password.
	 *
	 * @param password the new password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Gets the city.
	 *
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * Sets the city.
	 *
	 * @param city the new city
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * Gets the gender.
	 *
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * Sets the gender.
	 *
	 * @param gender the new gender
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
	 * Gets the age.
	 *
	 * @return the age
	 */
	public int getAge() {
		return age;
	}

	/**
	 * Sets the age.
	 *
	 * @param age the new age
	 */
	public void setAge(int age) {
		this.age = age;
	}

	/**
	 * Gets the photo.
	 *
	 * @return the photo
	 */
	public String getPhoto() {
		return photo;
	}

	/**
	 * Sets the photo.
	 *
	 * @param photo the new photo
	 */
	public void setPhoto(String photo) {
		this.photo = photo;
	}

	/**
	 * Gets the role.
	 *
	 * @return the role
	 */
	public Role getRole() {
		return role;
	}

	/**
	 * Sets the role.
	 *
	 * @param role the new role
	 */
	public void setRole(Role role) {
		this.role = role;
	}
	
}
