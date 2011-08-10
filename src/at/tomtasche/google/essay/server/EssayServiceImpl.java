package at.tomtasche.google.essay.server;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import at.tomtasche.google.essay.client.EssayService;

import com.google.gson.Gson;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class EssayServiceImpl extends RemoteServiceServlet implements EssayService {

    Gson gson = new Gson();


    public String getNextWord(String input) throws NullPointerException {
	if (input.contains("I'm")) {
	    input = input.replaceAll("I'm", "I am");
	}

	InputStreamReader reader = null;
	OutputStreamWriter writer = null;

	try {
	    final URL url = new URL("https://www.googleapis.com/rpc?key=AIzaSyBStAcD4WoVx3l1Cmc8xKvJTm2c0G2eqH4");

	    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	    connection.setRequestMethod("POST");
	    connection.setRequestProperty("Content-Type", "application/json");
	    connection.setRequestProperty("Connection", "close");

	    connection.setDoOutput(true);
	    connection.setDoInput(true);

	    writer = new OutputStreamWriter(connection.getOutputStream());
	    writer.write("{\"method\":\"scribe.textSuggestions.get\",\"id\":\"scribe.textSuggestions.get\",\"params\":{\"query\":\"" + input + "\",\"cp\":42},\"jsonrpc\":\"2.0\",\"key\":\"scribe.textSuggestions.get\",\"apiVersion\":\"v1\"}");
	    writer.flush();

	    reader = new InputStreamReader(connection.getInputStream());

	    BrainPacket packet = gson.fromJson(reader, BrainPacket.class);

	    return packet.getFirstIdea().replaceAll("I'm", "I am");
	} catch (IOException e) {
	    e.printStackTrace();
	} finally {
	    try {
		if (writer != null) writer.close();
	    } catch (IOException e) {}

	    try {
		if (reader != null) reader.close();
	    } catch (IOException e) {}
	}

	return "";
    }
}
