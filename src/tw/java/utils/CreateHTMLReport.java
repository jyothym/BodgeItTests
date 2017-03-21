package tw.java.utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class CreateHTMLReport {
	static String fileName = new SimpleDateFormat("ddMMMyy_hhmm").format(new Date());
	private static String FILENAME = System.getProperty("user.dir") + "/target/htmlReports/"+ fileName + ".html";

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