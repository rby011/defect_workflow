package ghook.httpsconnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;

import ghook.listener.Listener;

public class GitHubConnection {
	private static final String REPO_NAME = "star-platform";
	private static final String ORG_LOGIN_NAME = "CHSUNSONG";
	private static final String AUTH_TOKEN = "2471398598f63b65e6a8039d5a1a0970e2818bf7";
	private static final String ACCEPT_GET_LABEL = "application/vnd.github.symmetra-preview+json";

	public static void main(String args[]) {
		GitHubConnection conn = new GitHubConnection(AUTH_TOKEN, ACCEPT_GET_LABEL);
		String req_url = "https://api.github.com/repos/" + GitHubConnection.ORG_LOGIN_NAME + "/"
				+ GitHubConnection.REPO_NAME + "/labels";

		try {
			String res = conn.requestGetMethod(req_url);

			System.out.println(res);
		} catch (KeyManagementException | NoSuchAlgorithmException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	String token = null;
	String accept = null;

	public GitHubConnection(String token, String accept) {
		this.token = "token " + token;
		this.accept = accept;
	}

	public String requestPostMethod(String urlString) {
		
		
		return null;
	}

	public String requestGetMethod(String urlString)
			throws IOException, NoSuchAlgorithmException, KeyManagementException {
		URL url = new URL(urlString);
		/*
		 * 1. CONNECTION SETTING
		 */
		HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
		conn.setRequestProperty("Authorization", this.token);
		conn.setRequestProperty("Accept", this.accept);
		conn.setRequestMethod("GET");
		conn.setDoOutput(true);
		conn.setDoInput(true);
		conn.setHostnameVerifier(new HostnameVerifier() {
			@Override
			public boolean verify(String hostname, SSLSession session) {
				// Ignore host name verification. It always returns true.
				return true;
			}
		});
		SSLContext context = SSLContext.getInstance("TLS");
		context.init(null, null, null); // No validation for now
		conn.setSSLSocketFactory(context.getSocketFactory());

		/*
		 * 2. CONNECT TO URL
		 */
		conn.setInstanceFollowRedirects(true);
		// conn.connect();
		int code = conn.getResponseCode();
		if (code != 200)
			return null;
		/*
		 * 3. READ RESPONSE STREAM
		 */
		BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String line = null;
		StringBuffer sbuf = new StringBuffer();
		while ((line = reader.readLine()) != null) {
			sbuf.append(line);
			sbuf.append("\n");
		}
		reader.close();

		return sbuf.toString();
	}
}
