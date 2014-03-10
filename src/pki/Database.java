package pki;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
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
    	} finally {
    	    if ( connexion != null ) {
    	    	System.out.println("Connected to database");
    	    }
    	}
    	
		return connexion;
	}
	
	public static User loginUser(String email, String password) {
		User tryLoginUser = new User(null, null, email, password);
		// TODO fetch user in DB and validate
		
		return tryLoginUser;
	}
	
}
