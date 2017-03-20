package tw.java.utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;


public class CreateHTMLReport {
	static String fileName = new SimpleDateFormat("ddMMMyy_hhmm").format(new Date(0));
	private static final String FILENAME = System.getProperty("user.dir") + "/target/htmlReports/"+ fileName + ".html";
	
	public void generateHTMLFile(String content) {
		BufferedWriter bw = null;
		FileWriter fw = null;

		try {

			fw = new FileWriter(FILENAME);
			bw = new BufferedWriter(fw);
			bw.write(content);

		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			try {

				if (bw != null)
					bw.close();

				if (fw != null)
					fw.close();

			} catch (IOException ex) {

				ex.printStackTrace();

			}

		}

	}

}