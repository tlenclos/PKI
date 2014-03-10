package pki.entities;

import java.io.ByteArrayInputStream;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Date;

import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.cert.X509v3CertificateBuilder;

import pki.utilities.JCESigner;

public class CA
{
	// self-signed certificate
	public static X509Certificate generateSelfSignedCACertificate()
	{
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		
		// set properties
		String signatureAlgorithm = "SHA256withRSA";
		String issuer = "CN=Le CA";
		Date dateOfIssue = new Date(System.currentTimeMillis() - 24 * 60 * 60 * 1000); // yesterday
	    Date dateOfExpiry = new Date(System.currentTimeMillis() + 2 * 365 * 24 * 60 * 60 * 1000);// 2 years
	    BigInteger serialNumber = BigInteger.valueOf(System.currentTimeMillis());
		
	    try {
	    	//generate key (RSA)
			KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA", "BC");
			keyPairGenerator.initialize(1024, new SecureRandom());
		    KeyPair keyPair = keyPairGenerator.generateKeyPair();
		    PrivateKey privateKey = keyPair.getPrivate();
		    PublicKey publicKey = keyPair.getPublic();
		    
		    // build
			X509v3CertificateBuilder certBuilder = new X509v3CertificateBuilder(new X500Name(issuer), serialNumber, dateOfIssue, dateOfExpiry, new X500Name(issuer), SubjectPublicKeyInfo.getInstance(publicKey.getEncoded()));
			byte[] certBytes = certBuilder.build(new JCESigner(privateKey, signatureAlgorithm)).getEncoded();
			CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
			X509Certificate certificate = (X509Certificate)certificateFactory.generateCertificate(new ByteArrayInputStream(certBytes));
			
			return certificate;
		    
		} catch (Exception e) {
			e.printStackTrace();
		}
	    
	    return null;
	}	
}
