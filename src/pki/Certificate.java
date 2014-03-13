package pki;

import java.security.PublicKey;

import pki.utilities.KeypairUtility;

public class Certificate extends Model {
	public String commonName;
	public String country;
	public String stateprovince;
	public String organization;
	public String date;
	public int revoked;
	public String revokedDate;
	public PublicKey publicKey;
	
	public Certificate(String commonName, String country, String stateprovince, String organization, String date, int revoked, String revokedDate, byte[] publicKeyBytes)
	{
		this.commonName = commonName;
		this.country = country;
		this.stateprovince = stateprovince;
		this.organization = organization;
		this.date = date;
		this.revoked = revoked;
		this.revokedDate = revokedDate;
		
		this.setPublicKeyWithBytes(publicKeyBytes);
	}
	
	public void setPublicKeyWithBytes(byte[] bytes)
	{
		this.publicKey = KeypairUtility.getPublicKeyFromEncodedBytes(bytes);
	}
	
	public Certificate() {
		// TODO Auto-generated constructor stub
	}

	public Boolean validate() throws Exception {
		return 
			this.validateName(this.commonName)
		;
	}
}
