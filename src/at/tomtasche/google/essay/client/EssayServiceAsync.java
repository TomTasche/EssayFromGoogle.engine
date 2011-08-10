package at.tomtasche.google.essay.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface EssayServiceAsync {
    void getNextWord(String input, AsyncCallback<String> callback) throws NullPointerException;
}
