import Response.PurchasedItem;
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
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(MockitoJUnitRunner.class)
public class ShoppingTest {

    private Game game = new Game();

    @InjectMocks
    @Spy
    private Shopping shopping;

    @Mock
    Request request;

    @Spy
    Gson gson = new Gson();

    @Before
    public void setUp() throws Exception {
        initMocks(this);
    }

    @Test
    public void start_enoughGoldToBuyAllItems() throws IOException {
        game.setLives(2);
        game.setGold(600);
        game.setLevel(2);
        game.setScore(0);
        game.setHighScore(0);
        game.setTurn(3);
        String allItemsInShop = "[{\"id\":\"iron\",\"name\":\"Iron Plating\",\"cost\":100},{\"id\":\"mtrix\",\"name\":\"Book of Megatricks\",\"cost\":100}]";
        when(request.GETRequest(anyString())).thenReturn(allItemsInShop);
        PurchasedItem purchasedItem1 = new PurchasedItem("true", 500, 2, 2, 3);
        PurchasedItem purchasedItem2 = new PurchasedItem("true", 400, 3, 3, 4);
        when(shopping.getPurchasedItem(anyString(), anyString())).thenReturn(purchasedItem1);
        when(shopping.getPurchasedItem(anyString(), anyString())).thenReturn(purchasedItem2);
        shopping.start(game);
        verify(shopping, times(2)).setNewGameStatistics(any(), any());
    }

    @Test
    public void start_notEnoughGold() throws IOException {
        game.setLives(2);
        game.setGold(0);
        game.setLevel(2);
        game.setScore(0);
        game.setHighScore(0);
        game.setTurn(3);
        String allItemsInShop = "[{\"id\":\"iron\",\"name\":\"Iron Plating\",\"cost\":100},{\"id\":\"mtrix\",\"name\":\"Book of Megatricks\",\"cost\":100}]";
        when(request.GETRequest(anyString())).thenReturn(allItemsInShop);
        PurchasedItem purchasedItem1 = new PurchasedItem("true", 300, 2, 2, 3);
        PurchasedItem purchasedItem2 = new PurchasedItem("true", 100, 3, 3, 4);
        when(shopping.getPurchasedItem(anyString(), anyString())).thenReturn(purchasedItem1);
        when(shopping.getPurchasedItem(anyString(), anyString())).thenReturn(purchasedItem2);
        shopping.start(game);
        verify(shopping, times(0)).setNewGameStatistics(any(), any());
    }

    @Test
    public void start_purchaseOneItemOfTwo() throws IOException {
        game.setLives(2);
        game.setGold(600);
        game.setLevel(2);
        game.setScore(0);
        game.setHighScore(0);
        game.setTurn(3);
        String allItemsInShop = "[{\"id\":\"iron\",\"name\":\"Iron Plating\",\"cost\":600},{\"id\":\"mtrix\",\"name\":\"Book of Megatricks\",\"cost\":100}]";
        when(request.GETRequest(anyString())).thenReturn(allItemsInShop);
        PurchasedItem purchasedItem1 = new PurchasedItem("true", 0, 2, 2, 3);
        when(shopping.getPurchasedItem(anyString(), anyString())).thenReturn(purchasedItem1);
        shopping.start(game);
        verify(shopping, times(1)).setNewGameStatistics(any(), any());
    }


    @Test
    public void setNewGameStatistics() {

        PurchasedItem purchasedItem = new PurchasedItem("true", 0, 1, 2, 3);
        shopping.setNewGameStatistics(game, purchasedItem);
        assertEquals(0, game.getGold());
        assertEquals(1, game.getLives());
        assertEquals(2, game.getLevel());
        assertEquals(3, game.getTurn());
    }

    @Test
    public void getPurchasedItem() throws IOException {
        String purchaseResponse = "{\"shoppingSuccess\":true,\"gold\":28,\"lives\":5,\"level\":5,\"turn\":34}";
        when(request.POSTRequest(anyString())).thenReturn(purchaseResponse);
        PurchasedItem purchasedItem = shopping.getPurchasedItem("", "");
        assertEquals(28, purchasedItem.getGold());
        assertEquals(5, purchasedItem.getLives());
        assertEquals(5, purchasedItem.getLevel());
        assertEquals(34, purchasedItem.getTurn());
    }

    @Test
    public void getItems() throws IOException {
        String allItemsInShop = "[{\"id\":\"iron\",\"name\":\"Iron Plating\",\"cost\":300},{\"id\":\"mtrix\",\"name\":\"Book of Megatricks\",\"cost\":300}]";
        when(request.GETRequest(anyString())).thenReturn(allItemsInShop);
        List<Item> items = shopping.getItems("");
        assertEquals("iron", items.get(0).getId());
        assertEquals(300, items.get(0).getCost());
        assertEquals("mtrix", items.get(1).getId());
        assertEquals(300, items.get(1).getCost());
    }
}