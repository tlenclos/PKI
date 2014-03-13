package pki;

import java.security.PublicKey;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import pki.utilities.KeypairUtility;

public class Certificate extends Model {
	public String commonName;
	public String country;
	public String stateprovince;
	public String organization;
	public Date date;
	public int revoked;
	public String revokedDate;
	public PublicKey publicKey;
	
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
	
	public void setPublicKeyWithBytes(byte[] bytes)
	{
		this.publicKey = KeypairUtility.getPublicKeyFromEncodedBytes(bytes);
	}
	
	public Certificate()
	{
		this.date = Calendar.getInstance().getTime();
	}

	public Boolean validate() throws Exception {
		return 
			this.validateName(this.commonName)
		;
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
