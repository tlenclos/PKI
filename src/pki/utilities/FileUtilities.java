package pki.utilities;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;

public class FileUtilities {
	// lit un flux d'??ntr??e et le convertit en chaine de caract??res
	public static String readStream(InputStream inputStream) {
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
		StringBuilder content = new StringBuilder(512);

		try {
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				content.append(line);
				content.append("\n");
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				inputStream.close();
				bufferedReader.close();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}

		return content.toString();
	}

	public static void writeStream(FileWriter writer, String content) {
		
		try {
			writer.write(content);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
