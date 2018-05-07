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
import ghook.gson.Label;
import ghook.httpsconnection.GitHubConnection;

@WebServlet("/Listener")
public class Listener extends HttpServlet {
	private static final long serialVersionUID = 1L;
	// THE BELOWS MUST BE CONFIGURED BEFOE STATING SERVICE
	private static final String REPO_ID = "132212948";
	private static final String REPO_NAME = "star-platform";
	private static final String ORG_ID = "38997317";
	private static final String ORG_LOGIN_NAME = "CHSUNSONG";

	private static final String AUTH_TOKEN = "2471398598f63b65e6a8039d5a1a0970e2818bf7";
	private static final String ACCEPT_GET_LABEL = "application/vnd.github.symmetra-preview+json";

	public Listener() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String event_type = request.getHeader("X-GitHub-Event");
		if (event_type == null)
			return;

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

					// 1. get submitted label
					// https://api.github.com/repos/CHSUNSONG/star-platform/labels
					GitHubConnection ghconn = new GitHubConnection(Listener.AUTH_TOKEN, Listener.ACCEPT_GET_LABEL);
					String req_url = "https://api.github.com/repos/" + Listener.ORG_LOGIN_NAME + "/"
							+ Listener.REPO_NAME + "/labels";
					String res = null;
					try {
						res = ghconn.requestGetMethod(req_url);
						gson = new GsonBuilder().create();
						Label labels[] = gson.fromJson(res, Label[].class);
						Label slabel = null;
						for (int i = 0; i < labels.length; i++) {
							if(labels[i].name.equalsIgnoreCase("submitted")){
								slabel = labels[i];
								break;
							}
						}
						
						if(slabel!=null) {
							
						}
					} catch (Exception e) {
					}

					// 2. add a label (submitted) to an issue
					// POST /repos/:owner/:repo/issues/:number/labels
					// [
					// "Label1",
					// "Label2"
					// ]
					// ref#1 : https://developer.github.com/v3/issues/#edit-an-issue
					// ref#2 : https://developer.github.com/v3/issues/labels/#add-labels-to-an-issue

					
					// appendix. Remove a label from an issue
					// DELETE /repos/:owner/:repo/issues/:number/labels/:name
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