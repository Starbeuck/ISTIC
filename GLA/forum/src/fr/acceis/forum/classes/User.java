package fr.acceis.forum.classes;

public class User {
	private String login;
	private String password;
	private int age;
	private String gender;
	private String city;
	
	public User(String log, String pw, int a, String sex, String cit) {
		this.setLogin(log);
		this.setPassword(pw);
		this.setAge(a);
		this.setGender(sex);
		this.setCity(cit);
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
	
	
	
}
