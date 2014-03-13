package pki.entities;

import java.io.ByteArrayInputStream;
import java.io.File;
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

import pki.utilities.CertificateGenerator;
import pki.utilities.CertificateReaders;
import pki.utilities.CertificateWriters;
import pki.utilities.JCESigner;
import pki.utilities.KeypairUtility;

public class CA
{
	private X509Certificate _certificate;
	private KeyPair _keyPair;
	private static CA _instance;
	private static final String CA_DATA_SAVE_PATH = "/CA/";
	
	// singleton
	public static CA getInstance()
	{
		if(_instance == null)
			_instance = new CA();
		
		return _instance;
	}
	
	// self-signed certificate
	public void generateSelfSignedCACertificate()
	{
		// set properties
		String signatureAlgorithm = "SHA256withRSA";
		String issuer = "CN=Le CA";
		Date dateOfIssue = new Date(System.currentTimeMillis() - 24 * 60 * 60 * 1000); // yesterday
	    Date dateOfExpiry = new Date(System.currentTimeMillis() + 2 * 365 * 24 * 60 * 60 * 1000);// 2 years
	    
	    try {
	    	//generate key (RSA)
		    _keyPair = CertificateGenerator.generateKeys();
		    
		    // build
			_certificate = CertificateGenerator.genereateCertificate(signatureAlgorithm, issuer, dateOfIssue, dateOfExpiry, issuer, _keyPair);	
			
			this.saveCertificateAndPrivateKey();
		    
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String toPem()
	{
		if(_certificate != null)
			return CertificateWriters.getPemString(_certificate);
		
		return null;
	}
	
	// private
	private CA()
	{
		this.loadCertificateAndPrivateKey();
	}
	
	private void saveCertificateAndPrivateKey()
	{
		if(_certificate != null)
		{
			try{
				CertificateWriters.WriteToFile(new File(CA_DATA_SAVE_PATH+"cert.pem"), _certificate);
			}
			catch (Exception e){
				e.printStackTrace();
			}
		}
		
		if(_keyPair != null)
		{
			try{
				KeypairUtility.SaveKeyPair(CA_DATA_SAVE_PATH, _keyPair);
			}
			catch (Exception e){
				e.printStackTrace();
			}
		}
	}
	
	private void loadCertificateAndPrivateKey()
	{
		// load data
		try {
			_keyPair = KeypairUtility.LoadKeyPair(CA_DATA_SAVE_PATH, "DSA");
			_certificate = CertificateReaders.ReadCertificateFromFile(new File(CA_DATA_SAVE_PATH+"cert.pem"));			
		}
		catch (Exception e) {
			_keyPair = null;
			_certificate = null;
		}
		
		if(_certificate == null)
		{
			this.generateSelfSignedCACertificate();
			this.saveCertificateAndPrivateKey();
		}
	}
}
