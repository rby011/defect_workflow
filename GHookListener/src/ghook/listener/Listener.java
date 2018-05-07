package ghook.listener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import ghook.gson.EventCardCreated;

@WebServlet("/Listener")
public class Listener extends HttpServlet {
	private static final long serialVersionUID = 1L;
	// THE BELOWS MUST BE CONFIGURED BEFOE STATING SERVICE
	private static final String REPO_ID = "132212948";
	private static final String REPO_NAME = "star-platform";
	private static final String ORG_ID = "38997317";
	private static final String ORG_LOGIN_NAME = "CHSUNSONG";

	public Listener() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String event_type = request.getHeader("X-GitHub-Event");
		String payload = null;
		if (event_type.equalsIgnoreCase("project_card")) {
			payload = readBody(request);

			Gson gson = new GsonBuilder().create();
			EventCardCreated card_created = gson.fromJson(payload, EventCardCreated.class);

			if (card_created.action.equalsIgnoreCase("created")) {
				// how to get project id ?
				String orgid = card_created.organization.id;
				String repoid = card_created.repository.id;
				if (orgid.equalsIgnoreCase(Listener.ORG_ID) && repoid.equalsIgnoreCase(Listener.REPO_ID)) {
					String issue_url = card_created.project_card.content_url;
					// attach label

				}
			}

		} else if (event_type.equalsIgnoreCase("issue")) {
			payload = readBody(request);

		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	public synchronized static String readBody(HttpServletRequest request) throws IOException {
		BufferedReader input = new BufferedReader(new InputStreamReader(request.getInputStream()));
		StringBuilder builder = new StringBuilder();
		String buffer;
		while ((buffer = input.readLine()) != null) {
			if (builder.length() > 0) {
				builder.append("\n");
			}
			builder.append(buffer);
		}
		return builder.toString();
	}

}