import Response.SolvedMessage;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(MockitoJUnitRunner.class)
public class SolvingMessagesTest {

    private SolvedMessage solvedMessage;

    @InjectMocks
    @Spy
    private SolvingMessages solvingMessages;

    @Mock
    Request request;

    @Spy
    Gson gson;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
    }

    @Test
    public void getSolvedMessage() throws IOException {
        String solveResponse = "{\"success\":false,\"lives\":0,\"gold\":17,\"score\":2967,\"highScore\":19474,\"turn\":93,\"message\":\"You were defeated on your last mission!\"}";
        when(request.POSTRequest(anyString())).thenReturn(solveResponse);
        solvedMessage = solvingMessages.getSolvedMessage("", "");
        Assert.assertEquals(17, solvedMessage.getGold());
        Assert.assertEquals(19474, solvedMessage.getHighScore());
        Assert.assertEquals(0, solvedMessage.getLives());
        Assert.assertEquals(2967, solvedMessage.getScore());
        Assert.assertEquals(93, solvedMessage.getTurn());
    }
}