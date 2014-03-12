package pki.entities;

import java.io.*;

public class FileUtilities {
	// lit un flux d'éntrée et le convertit en chaine de caractères
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

	public static void writeStream(OutputStream outputStream, String content) {
		PrintWriter printWriter = new PrintWriter(new BufferedOutputStream(outputStream));
		printWriter.print(content);
		printWriter.flush();
		try {
			outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
