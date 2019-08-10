import Response.SolvedMessage;
import com.google.gson.Gson;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Spy;

import java.io.IOException;

import static org.mockito.Mockito.doReturn;

public class SolvingMessagesTest {

    @InjectMocks
    @Spy
    Request request;

    @Spy
    Gson gson;

    @Test
    public void getSolvedMessage() throws IOException {

        SolvingMessages solvingMessages = new SolvingMessages();

        String solveResponse = "{\"success\":false,\"lives\":0,\"gold\":17,\"score\":2967,\"highScore\":19474,\"turn\":93,\"message\":\"You were defeated on your last mission!\"}";
        doReturn(solveResponse).when(request).POSTRequest("");
        solvingMessages.getSolvedMessage("", "");
        gson.fromJson(solveResponse, SolvedMessage.class);
    }
}