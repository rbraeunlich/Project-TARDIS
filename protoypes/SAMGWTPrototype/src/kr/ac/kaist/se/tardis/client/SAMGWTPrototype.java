package kr.ac.kaist.se.tardis.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class SAMGWTPrototype implements EntryPoint {


	@Override
	public void onModuleLoad() {
		RootPanel rootPanel = RootPanel.get("main");
		Login login = new Login();
		login.setVisible(true);
		final VerticalPanel mainPanel = new VerticalPanel();
		mainPanel .add(login);
		rootPanel.add(mainPanel);

		History.addValueChangeHandler(new ValueChangeHandler<String>() {

			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				RootPanel rootPanel = RootPanel.get("main");
				rootPanel.remove(mainPanel);
				VerticalPanel verticalPanel = new VerticalPanel();
				verticalPanel.add(new Projects());
				rootPanel.add(verticalPanel);
			}
		});
	}

}
