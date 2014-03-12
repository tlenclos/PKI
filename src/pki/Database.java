package pki;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

public class Database {
	private Database()
	{}
 
	private static Database INSTANCE = new Database();
 
	public static Database getInstance()
	{	
		return INSTANCE;
	}
	
	public static Connection getConnection() {
    	Connection connexion = null;
    	Statement statement = null;
		ResultSet result = null;
    	
    	try {
    		DriverManager.registerDriver(new com.mysql.jdbc.Driver ());
    	    connexion = (Connection) DriverManager.getConnection( Config.dbUrl, Config.dbUsername, Config.dbPassword );
    	} catch ( SQLException e ) {
    		System.out.println(e);
    	}
    	
		return connexion;
	}
	
	public static User loginUser(String email, String password) {
		User tryLoginUser = new User(null, null, email, password);
		Connection dbCon = Database.getConnection();
		
		try {
			String sql = "SELECT id, firstname, lastname, email, password FROM users WHERE email = ? AND password = MD5(?)";
			PreparedStatement statement = (PreparedStatement) dbCon.prepareStatement(sql);
    		statement.setString(1, email);
    		statement.setString(2, password);
    		
			ResultSet result = statement.executeQuery();
	    		
			while (result.next()) {
				tryLoginUser.firstname = result.getString("firstname");
				tryLoginUser.lastname = result.getString("lastname");
				
				return tryLoginUser;
			}
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}

		return null;
	}
	
}
