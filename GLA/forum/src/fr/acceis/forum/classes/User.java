package fr.acceis.forum.classes;

public class User {
	private String login;
	private String password;
	private int age;
	private String gender;
	private String city;
	private String photo;
	
	public User(String log, String pw, int a, String sex, String cit, String photo) {
		this.setLogin(log);
		this.setPassword(pw);
		this.setAge(a);
		this.setGender(sex);
		this.setCity(cit);
		this.setPhoto(photo);
	}
	
	public String getLogin() {
		return login;
	}
	public String getPassword() {
		return password;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}
	
	
	
}
