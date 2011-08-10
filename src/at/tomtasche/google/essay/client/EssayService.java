package at.tomtasche.google.essay.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("essay")
public interface EssayService extends RemoteService {
    String getNextWord(String name) throws NullPointerException;
}
