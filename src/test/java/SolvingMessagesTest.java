import Response.SolvedMessage;
import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(MockitoJUnitRunner.class)
public class SolvingMessagesTest {

    private SolvedMessage solvedMessage;
    private List<Message> messages;
    private Game game = new Game();

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
    public void start_allMessagesAreSolved() throws IOException {
        game.setLives(3);
        game.setGold(200);
        game.setScore(20);
        game.setTurn(2);
        String allMessages = "[{\"adId\":\"NTBgSPQ7\",\"message\":\"\",\"reward\":22,\"expiresIn\":1,\"probability\":\"Walk in the park\"},{\"adId\":\"5i5zX5BC\",\"message\":\"\",\"reward\":116,\"expiresIn\":2,\"probability\":\"Piece of cake\"}]";
        when(request.GETRequest(anyString())).thenReturn(allMessages);
        SolvedMessage solvedMessage1 = new SolvedMessage(true, 3, 200, 20, 0, 2, "");
        SolvedMessage solvedMessage2 = new SolvedMessage(true, 3, 200, 20, 0, 2, "");
        when(solvingMessages.getSolvedMessage(anyString(), anyString())).thenReturn(solvedMessage1);
        when(solvingMessages.getSolvedMessage(anyString(), anyString())).thenReturn(solvedMessage2);
        solvingMessages.start(game);
        verify(solvingMessages, times(2)).setNewGameStatistics(any(), any());

    }

    @Test
    public void getSolvedMessage() throws IOException {
        String solveResponse = "{\"success\":false,\"lives\":0,\"gold\":17,\"score\":2967,\"highScore\":19474,\"turn\":93,\"message\":\"You were defeated on your last mission!\"}";
        when(request.POSTRequest(anyString())).thenReturn(solveResponse);
        solvedMessage = solvingMessages.getSolvedMessage("", "");
        assertEquals(17, solvedMessage.getGold());
        assertEquals(19474, solvedMessage.getHighScore());
        assertEquals(0, solvedMessage.getLives());
        assertEquals(2967, solvedMessage.getScore());
        assertEquals(93, solvedMessage.getTurn());
    }


    @Test
    public void getMessages() throws IOException {
        String allMessages = "[{\"adId\":\"NTBgSPQ7\",\"message\":\"\",\"reward\":22,\"expiresIn\":1,\"probability\":\"Hmmm....\"},{\"adId\":\"5i5zX5BC\",\"message\":\"\",\"reward\":116,\"expiresIn\":2,\"probability\":\"Piece of cake\"}]";
        when(request.GETRequest(anyString())).thenReturn(allMessages);
        messages = solvingMessages.getMessages("");
        assertEquals("NTBgSPQ7", messages.get(0).getAdId());
        assertEquals("Hmmm....", messages.get(0).getProbability());
        assertEquals(1, messages.get(0).getExpiresIn());
        assertEquals("5i5zX5BC", messages.get(1).getAdId());
        assertEquals("Piece of cake", messages.get(1).getProbability());
        assertEquals(2, messages.get(1).getExpiresIn());

    }

}