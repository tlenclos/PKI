package pki;

public class User extends Model {
	public String firstname;
	public String lastname;
	public String email;
	public String password;
	
	public User(String firstname, String lastname, String email, String password) {
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.password = password;
	}
	
	public Boolean validate() throws Exception {
		return 
			this.validateEmail(this.email)
			&& this.validatePassword(password, password)
			&& this.validateName(this.firstname)
			&& this.validateName(this.lastname)
		;
	}
}
