import com.example.player.Player;
import com.example.result.ResultImpl;
import com.example.result.ResultType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ResultImplTest {

    @Test
    public void testInitialization() {
        ResultImpl result = new ResultImpl(ResultType.WIN, Player.X);

        assertEquals(ResultType.WIN, result.getType());
        assertEquals(Player.X, result.getPlayer());
    }

}
