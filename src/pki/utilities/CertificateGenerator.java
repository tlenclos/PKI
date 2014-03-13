package pki.utilities;

import java.io.ByteArrayInputStream;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.Security;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Date;

import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.cert.X509v3CertificateBuilder;

public class CertificateGenerator {
	
	public final static String defaultAlgorithm = "SHA256withRSA";
	public static KeyPair generateKeys() throws NoSuchAlgorithmException, NoSuchProviderException
	{
		//generate key (RSA)
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA", "BC");
		keyPairGenerator.initialize(1024, new SecureRandom());
	    return keyPairGenerator.generateKeyPair();
	}
	public static X509Certificate genereateCertificate(String issuerCN, Date notBefore, Date notAfter, String subjectName,KeyPair keys)
	{
		return genereateCertificate(defaultAlgorithm, issuerCN, notBefore, notAfter, subjectName, keys);
	}
	
	public static X509Certificate genereateCertificate(String signatureAlgorithm, String issuerCN, Date notBefore, Date notAfter, String subjectName,KeyPair keys)
	{
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		
		// set properties
		BigInteger serialNumber = BigInteger.valueOf(System.currentTimeMillis());
		
	    try {
		    
		    // build
			X509v3CertificateBuilder certBuilder = new X509v3CertificateBuilder(new X500Name(issuerCN), serialNumber, notBefore, notAfter, new X500Name(subjectName), SubjectPublicKeyInfo.getInstance(keys.getPublic().getEncoded()));
			byte[] certBytes = certBuilder.build(new JCESigner(keys.getPrivate(), signatureAlgorithm)).getEncoded();
			CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
			
			X509Certificate cert = (X509Certificate)certificateFactory.generateCertificate(new ByteArrayInputStream(certBytes));
			
			cert.verify(keys.getPublic(), signatureAlgorithm);
			return cert;
	    }
	    catch(Exception e)
	    {
	    	e.printStackTrace();
	    	return null;
	    }		
	}
}
