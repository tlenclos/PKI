package pki.utilities;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

public class CertificateReaders {
	
	public static X509Certificate ReadCertificateFromFile(File file) {
		try {
			return ReadCertificateFromInputStream(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public static X509Certificate ReadCertificateFromInputStream(InputStream stream)
	{
		try {
			String pemString = FileUtilities.readStream(stream);

			CertificateFactory cf = CertificateFactory.getInstance("X.509");
			Certificate cert = cf.generateCertificate(new ByteArrayInputStream(pemString.getBytes()));

			return (X509Certificate)cert;
		}
		catch (Exception e){
			e.printStackTrace();
		}
		
		return null;
	}

}
