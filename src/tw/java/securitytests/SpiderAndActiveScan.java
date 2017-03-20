package tw.java.securitytests;

import org.zaproxy.clientapi.core.ApiResponse;
import org.zaproxy.clientapi.core.ApiResponseElement;
import org.zaproxy.clientapi.core.ClientApi;
import org.zaproxy.clientapi.core.ClientApiException;

/**
 * A simple example showing how to use the API to spider and active scan a site and then retrieve and print out the alerts.
 * <p>
 * ZAP must be running on the specified host and port for this script to work
 */
public class SpiderAndActiveScan {

	private static final String ZAP_ADDRESS = "localhost";
	private static final int ZAP_PORT = 8090;
	private static final String ZAP_API_KEY = null; // Change this if you have set the apikey in ZAP via Options / API

	private static final String TARGET = "http://localhost:8080/bodgeit/";

	public static void main(String[] args) {

		ClientApi api = new ClientApi(ZAP_ADDRESS, ZAP_PORT, ZAP_API_KEY);
		try {

			startSpidering(api);
			startActiveScan(api);
			generateXMLReport(api);

		} catch (Exception e) {
			System.out.println("Exception : " + e.getMessage());
			e.printStackTrace();
		}
	}

	private static void generateXMLReport(ClientApi api)
			throws ClientApiException {
		System.out.println("Alerts:");
		System.out.println(new String(api.core.xmlreport()));
	}

	private static void startSpidering(ClientApi api)
			throws ClientApiException, InterruptedException {
		// Start spidering the target
		System.out.println("Spider : " + TARGET);
		// It's not necessary to pass the ZAP API key again, already set when creating the ClientApi.
		ApiResponse resp = api.spider.scan(TARGET, null, null, null, null);
		String scanid;
		int progress;

		// The scan now returns a scan id to support concurrent scanning
		scanid = ((ApiResponseElement) resp).getValue();

		// Poll the status until it completes
		while (true) {
			Thread.sleep(1000);
			progress = Integer.parseInt(((ApiResponseElement) api.spider.status(scanid)).getValue());
			System.out.println("Spider progress : " + progress + "%");
			if (progress >= 100) {
				break;
			}
		}
		System.out.println("Spider complete");

		// Give the passive scanner a chance to complete
		Thread.sleep(2000);
	}

	private static void startActiveScan(ClientApi api)
			throws ClientApiException, InterruptedException {
		ApiResponse resp;
		String scanid;
		int progress;
		System.out.println("Active scan : " + TARGET);
		resp = api.ascan.scan(TARGET, "True", "False", null, null, null);

		// The scan now returns a scan id to support concurrent scanning
		scanid = ((ApiResponseElement) resp).getValue();

		// Poll the status until it completes
		while (true) {
			Thread.sleep(5000);
			progress = Integer.parseInt(((ApiResponseElement) api.ascan.status(scanid)).getValue());
			System.out.println("Active Scan progress : " + progress + "%");
			if (progress >= 100) {
				break;
			}
		}
		System.out.println("Active Scan complete");
	}

}