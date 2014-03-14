package pki;

import java.security.PublicKey;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import pki.utilities.KeypairUtility;

public class Certificate {
	private int id;
	private String commonName;
	private String country;
	private String stateprovince;
	private String organization;
	private Date date;
	private int revoked;
	private String revokedDate;
	private PublicKey publicKey;
	
	public Certificate(String commonName, String country, String stateprovince, String organization, String date, int revoked, String revokedDate, byte[] publicKeyBytes)
	{
		this.commonName = commonName;
		this.country = country;
		this.stateprovince = stateprovince;
		this.organization = organization;
		this.revoked = revoked;
		this.revokedDate = revokedDate;
		
		this.date = Calendar.getInstance().getTime();
		this.setPublicKeyWithBytes(publicKeyBytes);
	}
	
	public Certificate(HttpServletRequest request)
	{
		this.commonName = request.getParameter("commonName");
		this.country = request.getParameter("country");
		this.stateprovince = request.getParameter("stateprovince");
		this.organization = request.getParameter("organization");
		
		this.date = Calendar.getInstance().getTime();
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCommonName() {
		return commonName;
	}

	public void setCommonName(String commonName) {
		this.commonName = commonName;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getStateprovince() {
		return stateprovince;
	}

	public void setStateprovince(String stateprovince) {
		this.stateprovince = stateprovince;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getRevoked() {
		return revoked;
	}

	public void setRevoked(int revoked) {
		this.revoked = revoked;
	}

	public String getRevokedDate() {
		return revokedDate;
	}

	public void setRevokedDate(String revokedDate) {
		this.revokedDate = revokedDate;
	}

	public PublicKey getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(PublicKey publicKey) {
		this.publicKey = publicKey;
	}

	public void setPublicKeyWithBytes(byte[] bytes)
	{
		this.publicKey = KeypairUtility.getPublicKeyFromEncodedBytes(bytes);
	}
	
	public Certificate()
	{
		this.date = Calendar.getInstance().getTime();
	}

	public Boolean validate(Model model) throws Exception {
		return
			model.validateName(this.commonName)
			&& model.validatePublicKey(this.publicKey)
		;
	}
	
   public static Certificate mapWithDatabase( ResultSet resultSet ) throws SQLException {
        Certificate certificate = new Certificate();
        certificate.id = resultSet.getInt("id");
        certificate.commonName = resultSet.getString("common_name");
        certificate.country = resultSet.getString("country");
        certificate.stateprovince = resultSet.getString("stateprovince");
        certificate.organization = resultSet.getString("organization");
        certificate.revoked = resultSet.getInt("revoked");
		
        return certificate;
    }
	
	public void setDataForFieldAndValue(String field, String value)
	{
		if(field.equals("commonName"))
			this.commonName = value;
		else if(field.equals("country"))
			this.country = value;
		else if(field.equals("stateprovince"))
			this.stateprovince = value;
		else if(field.equals("organization"))
			this.organization = value;		
	}
	
	
}
