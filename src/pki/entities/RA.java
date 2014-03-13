package pki.entities;

import java.security.KeyPair;
import java.security.cert.X509Certificate;
import java.util.Date;

import pki.Certificate;
import pki.utilities.CertificateGenerator;

public class RA {
	public static X509Certificate getCertificateWithRequest(Certificate req) {
		CA ca = CA.getInstance();
		X509Certificate caCert = ca.getCertificate();
		KeyPair caKeyPair = ca.getKeyPair();
		
		KeyPair userKeyPair = new KeyPair(req.getPublicKey(), null);
		
		String issuer = "CN="+req.getCommonName();
		Date dateOfIssue = new Date(System.currentTimeMillis() - 24 * 60 * 60 * 1000); // yesterday
	    Date dateOfExpiry = new Date(System.currentTimeMillis() + 2 * 365 * 24 * 60 * 60 * 1000);// 2 years
		
		return CertificateGenerator.generateCertificate(dateOfIssue, dateOfExpiry, issuer, userKeyPair, caCert, caKeyPair.getPrivate());
	}
}
