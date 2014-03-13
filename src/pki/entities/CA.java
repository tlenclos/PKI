package pki.entities;

import java.io.File;
import java.security.KeyPair;
import java.security.cert.CRL;
import java.security.cert.CRLSelector;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Date;

import pki.utilities.CertificateGenerator;
import pki.utilities.CertificateReaders;
import pki.utilities.CertificateWriters;
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
		String issuer = "CN=Le CA";
		Date dateOfIssue = new Date(System.currentTimeMillis() - 24 * 60 * 60 * 1000); // yesterday
	    Date dateOfExpiry = new Date(System.currentTimeMillis() + 2 * 365 * 24 * 60 * 60 * 1000);// 2 years
	    
	    try {
	    	//generate key (RSA)
		    _keyPair = CertificateGenerator.generateKeys();
		    
		    // build
			_certificate = CertificateGenerator.generateCertificate(dateOfIssue, dateOfExpiry, issuer,_keyPair, null,null);	
			
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
	
	public CRL generateCRL()
	{
		return null;
	}
	
	public X509Certificate getCertificate()
	{
		return _certificate;
	}
	
	public KeyPair getKeyPair()
	{
		return _keyPair;
	}
}
