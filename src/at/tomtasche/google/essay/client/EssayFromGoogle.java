package at.tomtasche.google.essay.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class EssayFromGoogle implements EntryPoint {

    final EssayServiceAsync essayService = GWT.create(EssayService.class);

    Label essay;
    Button sendButton;
    TextBox beginning;

    public void onModuleLoad() {
	beginning = new TextBox();
	beginning.setText("How should I start my essay? I don't know ");

	EventHandler handler = new EventHandler();

	sendButton = new Button("Send");
	sendButton.addClickHandler(handler);
	sendButton.addKeyUpHandler(handler);

	essay = new Label();
	essay.setWidth("100%");
	essay.setHeight("100%");

	RootPanel.get("beginningContainer").add(beginning);
	RootPanel.get("buttonContainer").add(sendButton);
	RootPanel.get("essayContainer").add(essay);

	beginning.setFocus(true);
	beginning.selectAll();
    }

    class EventHandler implements KeyUpHandler, ClickHandler {

	@Override
	public void onClick(ClickEvent event) {
	    essay.setText(beginning.getText());

	    sendToServer();
	}

	public void onKeyUp(KeyUpEvent event) {
	    if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
		essay.setText(beginning.getText());

		sendToServer();
	    }
	}


	private void sendToServer() {
	    sendButton.setEnabled(false);

	    if (essay.getText().isEmpty()) {
		essay.setText("tomtasche.at is the best site I've ever seen! I'm visiting it ");
	    }

	    String text = essay.getText();

	    if (text.length() > 40)
		text = text.substring(text.length() - 40);

	    essayService.getNextWord(text, new AsyncCallback<String>() {

		public void onFailure(Throwable caught) {
		    final DialogBox dialogBox = new DialogBox();
		    dialogBox.setText("Google needs your help");
		    dialogBox.setAnimationEnabled(true);
		    final Button okButton = new Button("Write on...");
		    VerticalPanel dialogVPanel = new VerticalPanel();
		    final TextBox input = new TextBox();
		    input.setText("Enter the next word / letter for Google");
		    input.setFocus(true);
		    input.selectAll();
		    dialogVPanel.add(input);
		    dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
		    dialogVPanel.add(okButton);
		    dialogBox.setWidget(dialogVPanel);

		    okButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
			    dialogBox.hide();

			    essay.setText(essay.getText() + " " + input.getText());

			    sendToServer();
			}
		    });
		    okButton.addKeyUpHandler(new KeyUpHandler() {

			@Override
			public void onKeyUp(KeyUpEvent event) {
			    if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
				dialogBox.hide();

				essay.setText(essay.getText() + " " + input.getText());

				sendToServer();
			    }
			}
		    });
		    dialogBox.center();
		    dialogBox.show();
		}

		public void onSuccess(String result) {
		    essay.setText(essay.getText() + result);

		    sendToServer();
		}
	    });
	}
    }
}
