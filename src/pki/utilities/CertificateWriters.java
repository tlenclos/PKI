package pki.utilities;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.io.StringWriter;
import java.security.cert.X509Certificate;

import org.bouncycastle.openssl.PEMWriter;

public class CertificateWriters
{
	public static String getPemString(X509Certificate cert){
		StringWriter stringWriter = null;
        PEMWriter pemWriter = null;
        String result = null;
        try {
            stringWriter = new StringWriter();
            pemWriter = new PEMWriter(stringWriter);
            pemWriter.writeObject(cert);
            pemWriter.flush();
            stringWriter.flush();
            result = stringWriter.toString();
        }
        catch(Exception e){
        	e.printStackTrace();
        }
        finally{
        	try{
	            if (pemWriter != null)
	                pemWriter.close();
	            if (stringWriter != null)
	                stringWriter.close();
        	}
        	catch(Exception e){
        		e.printStackTrace();
        	}
        }
        return result;
	}
	
	public static boolean WriteToFile(File file, X509Certificate cert)
	{
		boolean result = false;
		String certificatePem = getPemString(cert);
		if(file.exists())
			file.delete();
		
		try {
			file.createNewFile();
			OutputStream os = new ByteArrayOutputStream();
			FileUtilities.writeStream(os, certificatePem);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
}
