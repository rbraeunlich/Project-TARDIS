package kr.ac.kaist.se.tardis.client;

import java.util.Date;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.SubmitButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class Login extends SimpleContent {

	private VerticalPanel mainPanel = new VerticalPanel();
	private Label labelTitle = new Label("Login");
	private TextBox textBoxMail = new TextBox();
	private TextBox textBoxPassword = new PasswordTextBox();
	private Button buttonLogin = new SubmitButton("Login");
	private Label labelError = new Label();

	public Login() {
		textBoxPassword.setText("Password");
		textBoxMail.setText("email");
		mainPanel.add(labelTitle);
		mainPanel.add(textBoxMail);
		mainPanel.add(textBoxPassword);
		mainPanel.add(buttonLogin);
		mainPanel.add(labelError);
		this.setWidget(mainPanel);

		buttonLogin.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				String mail = textBoxMail.getText();
				String password = textBoxPassword.getText();
				if (mail.equals("admin") && password.equals("admin")) {
					// this ways we change the URL in the browser
					History.newItem("projects", true);
					Long cookieValidity = 1000L * 60 * 1;
					Cookies.setCookie("sid", String.valueOf(Math.random()),
							new Date(System.currentTimeMillis() + cookieValidity), null, "/", false);
				} else {
					labelError.setText("Invalid username and/or password!");
				}

			}
		});
	}
}
