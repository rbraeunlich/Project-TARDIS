package kr.ac.kaist.se.tardis.client;

import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

import kr.ac.kaist.se.tardis.json.JokeRequest;

public class Projects extends SimpleContent {

	private static final String JSON_URL = "http://api.icndb.com/jokes/random";

	private final VerticalPanel mainPanel = new VerticalPanel();
	private Label projectsLabel = new Label("Projects");
	private Label jokeLabel = new Label();
	private Label errorLabel = new Label();
	
	public Projects() {
		mainPanel.add(projectsLabel);
		errorLabel.setVisible(false);
		mainPanel.add(jokeLabel);
		mainPanel.add(errorLabel);
		this.add(mainPanel);
		
		try {
			new RequestBuilder(RequestBuilder.GET, JSON_URL).sendRequest(null, new RequestCallback() {

				@Override
				public void onResponseReceived(Request request, Response response) {
					if (response.getStatusCode() == 200) {
						JokeRequest safeEval = JsonUtils.unsafeEval(response.getText());
						jokeLabel.setText(safeEval.getValue().getJoke());
					} else {
						displayError(response.getStatusCode() + " " + response.getStatusText());
					}
				}

				@Override
				public void onError(Request request, Throwable exception) {
					displayError("Couldn't receive JSON.");
				}
			});
		} catch (RequestException e) {
			displayError(e.getMessage());
		}
	}

	protected void displayError(String string) {
		errorLabel.setText(string);
		errorLabel.setVisible(true);
	}
}
