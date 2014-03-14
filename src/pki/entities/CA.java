package pki.entities;

import java.io.File;
import java.security.KeyPair;
import java.security.cert.X509CRL;
import java.security.cert.X509Certificate;
import java.util.Date;

import pki.Database;
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
		//truncate certificate
		Database.resetCertificates();
		
		// set properties
		String issuer = "CN=Le CA";
		Date dateOfIssue = new Date(System.currentTimeMillis() - (24 * 60 * 60 * 1000)); // yesterday
	    Date dateOfExpiry = new Date(System.currentTimeMillis() + (365l * 24l * 60l * 60l * 1000l));// 1 year
	    
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
			CertificateGenerator.init();
			_keyPair = KeypairUtility.LoadKeyPair(CA_DATA_SAVE_PATH, "RSA");
			_certificate = CertificateReaders.ReadCertificateFromFile(new File(CA_DATA_SAVE_PATH+"cert.pem"));			
		}
		catch (Exception e) {
			_keyPair = null;
			_certificate = null;
			e.printStackTrace();
		}
		
		if(_certificate == null)
		{
			this.generateSelfSignedCACertificate();
			this.saveCertificateAndPrivateKey();
		}
	}
	
	public X509CRL generateCRL()
	{
		//get revoked
		CRLEntry[] entries = Database.revokedCertificates();
		X509CRL crl = CertificateGenerator.generateCRL(_certificate, _keyPair.getPrivate(), entries);
	
		
		CertificateWriters.WriteToFile(new File("/CA/crl.crl"), crl);
		
		return crl;
	}
	
	public boolean validateCertificate(X509Certificate certificate)
	{
		try
		{
			if (certificate == null)
				return false;

			certificate.verify(_keyPair.getPublic());

			X509CRL crl = generateCRL();
			if (crl == null)
				return true;

			return !crl.isRevoked(certificate);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
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
