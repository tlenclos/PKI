package pki;

public class Certificate extends Model {
	public String commonName;
	public String country;
	public String stateprovince;
	public String organization;
	public String date;
	public int revoked;
	public String revokedDate;
	
	public Certificate(String commonName, String country, String stateprovince, String organization, String date, int revoked, String revokedDate) {
		this.commonName = commonName;
		this.country = country;
		this.stateprovince = stateprovince;
		this.organization = organization;
		this.date = date;
		this.revoked = revoked;
		this.revokedDate = revokedDate;
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
