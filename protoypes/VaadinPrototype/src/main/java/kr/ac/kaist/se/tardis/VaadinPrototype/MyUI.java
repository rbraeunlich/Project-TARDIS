package kr.ac.kaist.se.tardis.VaadinPrototype;

import java.util.Date;
import java.util.Random;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 * This UI is the application entry point. A UI may either represent a browser
 * window (or tab) or some part of a html page where a Vaadin application is
 * embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is
 * intended to be overridden to add component to the user interface and
 * initialize non-component functionality.
 */
@Theme("mytheme")
@Widgetset("kr.ac.kaist.se.tardis.VaadinPrototype.MyAppWidgetset")
public class MyUI extends UI {

	private static final long serialVersionUID = 1L;

	@Override
	protected void init(VaadinRequest vaadinRequest) {
		final VerticalLayout layout = new VerticalLayout();

		final TextField name = new TextField("Username:");
		PasswordField password = new PasswordField("Password:");
		Button button = new Button("Login");
		Label wrongLoginLabel = new Label();
		wrongLoginLabel.setVisible(false);
		button.addClickListener(e -> {
			if(name.getValue().equals("admin") && password.getValue().equals("admin")){
				wrongLoginLabel.setVisible(false);
				layout.removeAllComponents();
				VaadinService.getCurrentRequest().getWrappedSession().setAttribute("uid", new Random(new Date().getTime()));
				layout.addComponent(new Label("Thanks " + name.getValue() + ", it works!"));
			} else{
				wrongLoginLabel.setVisible(true);
				wrongLoginLabel.setCaption("Username or password wrong!");
			}
		});

		layout.addComponents(name, password, button, wrongLoginLabel);
		layout.setMargin(true);
		layout.setSpacing(true);

		setContent(layout);
	}

	@WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
	@VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
	public static class MyUIServlet extends VaadinServlet {
	}
}
