package Main;

public class User {
	private String name;
	private int userNumber;
	public User() {
		
	}
	public User(String name) {
		this.name = name;
	}
	public User(int userNumber, String name) {
		this.userNumber = userNumber;
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public int getUserNumber() {
		return userNumber;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setUserNumber(int userNumber) {
		this.userNumber = userNumber;
	}
}
