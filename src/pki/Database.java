package pki;

import java.io.InputStream;
import java.security.cert.X509Certificate;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import pki.entities.CRLEntry;
import pki.utilities.CertificateReaders;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

public class Database {
	private Database()
	{}
 
	private static Database _dbInstance;
 
	public static Database getInstance()
	{
		if(_dbInstance == null)
			_dbInstance = new Database();

		return _dbInstance;
	}
	
	public static Connection getConnection() {
    	Connection connexion = null;
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
				tryLoginUser.id = result.getInt("id");
				tryLoginUser.email = result.getString("email");
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
	
	public static CRLEntry[] revokedCertificates()
	{
		Connection dbCon = Database.getConnection();
		
		try {
			
			ArrayList<CRLEntry> entries = new ArrayList<CRLEntry>();
			String sql = "SELECT certificate,revoked_date FROM certificate WHERE revoked = 1";
			PreparedStatement statement = (PreparedStatement) dbCon.prepareStatement(sql);
			ResultSet result = statement.executeQuery();
    		
			while (result.next()) {
				CRLEntry entry = new CRLEntry();
				InputStream blobStream = result.getBlob("certificate").getBinaryStream();
				X509Certificate certificate = CertificateReaders.ReadCertificateFromInputStream(blobStream);
				entry.RevocationDate = Calendar.getInstance().getTime(); // change
				if (certificate != null)
				{
					entry.SerialNumber = certificate.getSerialNumber();
					entries.add(entry);
				}
			}
			
			return entries.toArray(new CRLEntry[0]);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
