import Response.PurchasedItem;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class ShoppingTest {

    @Mock
    Request request;

    @Spy
    Gson gson = new Gson();

    @Test
    public void setNewGameStatistics() {
        Shopping shopping = new Shopping();
        Game game = new Game();
        PurchasedItem purchasedItem = new PurchasedItem("true", 0, 1, 2, 3);
        shopping.setNewGameStatistics(game, purchasedItem);
        assertEquals(0, game.getGold());
        assertEquals(1, game.getLives());
        assertEquals(2, game.getLevel());
        assertEquals(3, game.getTurn());
    }

    @Test
    public void getPurchasedItem() throws IOException {
        PurchasedItem purchasedItem = new PurchasedItem();
        String purchaseResponse = "{\"shoppingSuccess\":true,\"gold\":28,\"lives\":5,\"level\":5,\"turn\":34}";
        doReturn(purchaseResponse).when(request).POSTRequest("");
        gson.fromJson(purchaseResponse, purchasedItem.getClass());
        assertEquals(28, purchasedItem.getGold());
    }
}