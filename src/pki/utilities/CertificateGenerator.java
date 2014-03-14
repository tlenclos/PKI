package pki.utilities;

import java.io.ByteArrayInputStream;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.cert.CertificateFactory;
import java.security.cert.X509CRL;
import java.security.cert.X509Certificate;
import java.util.Calendar;
import java.util.Date;

import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.BasicConstraints;
import org.bouncycastle.asn1.x509.CRLDistPoint;
import org.bouncycastle.asn1.x509.DistributionPoint;
import org.bouncycastle.asn1.x509.DistributionPointName;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.asn1.x509.GeneralName;
import org.bouncycastle.asn1.x509.GeneralNames;
import org.bouncycastle.asn1.x509.IssuingDistributionPoint;
import org.bouncycastle.asn1.x509.ReasonFlags;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.asn1.x509.X509Name;
import org.bouncycastle.cert.X509v2CRLBuilder;
import org.bouncycastle.cert.X509v3CertificateBuilder;

import pki.entities.CRLEntry;

public class CertificateGenerator
{	
	private static boolean _init = false;
	
	public static void init()
	{
		if(!_init)
			Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
	}
	
	public final static String defaultAlgorithm = "SHA256withRSA";
	public static KeyPair generateKeys() throws NoSuchAlgorithmException, NoSuchProviderException
	{
		init();
		//generate key (RSA)
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA", "BC");
		keyPairGenerator.initialize(1024, new SecureRandom());
	    return keyPairGenerator.generateKeyPair();
	}
	public static X509Certificate generateCertificate(Date notBefore, Date notAfter, String subjectName,KeyPair keys, X509Certificate issuerCertificate, PrivateKey issuerPrivateKey)
	{
		return generateCertificate(defaultAlgorithm, notBefore, notAfter, subjectName, keys,issuerCertificate,issuerPrivateKey);
	}
	
	public static X509Certificate generateCertificate(String signatureAlgorithm, Date notBefore, Date notAfter, String subjectName, KeyPair keys, X509Certificate issuerCertificate, PrivateKey issuerPrivateKey)
	{		
		init();
		// set properties
		BigInteger serialNumber = BigInteger.valueOf(System.currentTimeMillis());
		
	    try {
		    boolean selfSigned = issuerCertificate == null;
	    	String issuerCN = !selfSigned ? issuerCertificate.getSubjectX500Principal().getName() : subjectName; 
		    PrivateKey privateKey = issuerPrivateKey != null ? issuerPrivateKey : keys.getPrivate();
	    	// build
			X509v3CertificateBuilder certBuilder = new X509v3CertificateBuilder(new X500Name(issuerCN), serialNumber, notBefore, notAfter, new X500Name(subjectName), SubjectPublicKeyInfo.getInstance(keys.getPublic().getEncoded()));
			if (selfSigned)
			{
				GeneralName generalName = new GeneralName(GeneralName.uniformResourceIdentifier,"http://localhost:8080/PKI/crl.crl");
				GeneralNames generalNames = new GeneralNames(generalName);
				DistributionPointName distPointOne = new DistributionPointName(generalNames);
				
				DistributionPoint[] distPoints = new DistributionPoint[] { new DistributionPoint(distPointOne,null,null) };
				 
				certBuilder.addExtension(Extension.cRLDistributionPoints, false, new CRLDistPoint(distPoints));
				certBuilder.addExtension(Extension.basicConstraints, true, new BasicConstraints(true));
			}
			else
			{
				GeneralName generalName = new GeneralName(GeneralName.uniformResourceIdentifier,"http://localhost:8080/PKI/crl.crl");
				GeneralNames generalNames = new GeneralNames(generalName);
				DistributionPointName distPointOne = new DistributionPointName(generalNames);
				
				DistributionPoint[] distPoints = new DistributionPoint[] { new DistributionPoint(distPointOne,null,null) };
				 
				certBuilder.addExtension(Extension.cRLDistributionPoints, false, new CRLDistPoint(distPoints));
				
			}
			byte[] certBytes = certBuilder.build(new JCESigner(privateKey, signatureAlgorithm)).getEncoded();
			CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
			
			X509Certificate cert = (X509Certificate)certificateFactory.generateCertificate(new ByteArrayInputStream(certBytes));
			
			return cert;
	    }
	    catch(Exception e)
	    {
	    	e.printStackTrace();
	    	return null;
	    }		
	}

	public static X509CRL generateCRL(X509Certificate issuerCertificate, PrivateKey issuerPrivateKey, CRLEntry[] crlEntries )
	{
		init();
		try
		{
			X509v2CRLBuilder crlBuilder = new X509v2CRLBuilder(new X500Name(issuerCertificate.getSubjectDN().getName()),Calendar.getInstance().getTime());
			for(CRLEntry entry : crlEntries)
				crlBuilder.addCRLEntry(entry.SerialNumber, entry.RevocationDate,0);
					
			byte[] crlBytes = crlBuilder.build(new JCESigner(issuerPrivateKey, defaultAlgorithm)).getEncoded();		
			CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
			X509CRL crl =  (X509CRL)certificateFactory.generateCRL(new ByteArrayInputStream(crlBytes));
			
			return crl;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
}
