package pki;

public class User {
	public int id;
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
	
	public Boolean validate(Model model) throws Exception {
		return
				model.validateEmail(email)
			&& model.validatePassword(password, password)
			&& model.validateName(firstname)
			&& model.validateName(lastname)
		;
	}
}
