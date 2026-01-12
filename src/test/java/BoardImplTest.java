import com.example.board.BoardImpl;
import com.example.player.Player;
import com.example.player.PlayerImpl;
import com.example.result.Result;
import com.example.result.ResultType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class BoardImplTest {

    private final TestBoardImpl _testBoard;

    BoardImplTest() {
        _testBoard = new TestBoardImpl(3);
    }

    @Test
    public void testInitialization() {
        char[][] board = _testBoard.getBoard();
        for (int r = 0; r < _testBoard.getSize(); r++) {
            for (int c = 0; c < _testBoard.getSize(); c++) {
                assertEquals(' ', board[r][c]);
            }
        }
    }

    @Test
    public void testMoveNext() {
        Player player1 = new PlayerImpl('X');

        Result next = _testBoard.move(1, player1);

        char takenPosition = _testBoard.getBoard()[0][0];
        assertEquals('X', takenPosition, "Incorrect character at position after move");

        assertEquals(ResultType.NEXT, next.getType(), "Incorrect result type after move");
        assertNull(next.getPlayer());
    }

    @Test
    public void testMoveSkip() {
        Player player1 = new PlayerImpl('X');
        Player player2 = new PlayerImpl('O');

        _testBoard.move(1, player1);
        Result skip = _testBoard.move(1, player2);

        assertEquals(ResultType.SKIP, skip.getType());
        assertNull(skip.getPlayer());
    }

    @Test
    public void testMoveDraw() {
        Player player1 = new PlayerImpl('X');
        Player player2 = new PlayerImpl('O');

        _testBoard.move(1, player1);
        _testBoard.move(2, player1);
        _testBoard.move(3, player2);
        _testBoard.move(4, player2);
        _testBoard.move(5, player2);
        _testBoard.move(6, player1);
        _testBoard.move(7, player1);
        _testBoard.move(8, player2);
        Result draw = _testBoard.move(9, player1);

        assertEquals(9, _testBoard.getPositionsTaken());
        assertEquals(ResultType.DRAW, draw.getType());
        assertNull(draw.getPlayer());
    }

    @Test
    public void testMoveWin() {
        Player player = new PlayerImpl('X');

        _testBoard.move(1, player);
        _testBoard.move(2, player);
        Result win = _testBoard.move(3, player);

        assertEquals(ResultType.WIN, win.getType());
        assertEquals(player, win.getPlayer());

        _testBoard.clear();

        _testBoard.move(1, player);
        _testBoard.move(4, player);
        win = _testBoard.move(7, player);

        assertEquals(ResultType.WIN, win.getType());
        assertEquals(player, win.getPlayer());

        _testBoard.clear();

        _testBoard.move(1, player);
        _testBoard.move(5, player);
        win = _testBoard.move(9, player);

        assertEquals(ResultType.WIN, win.getType());
        assertEquals(player, win.getPlayer());

        _testBoard.clear();

        _testBoard.move(3, player);
        _testBoard.move(5, player);
        win = _testBoard.move(7, player);

        assertEquals(ResultType.WIN, win.getType());
        assertEquals(player, win.getPlayer());
    }

    public static class TestBoardImpl extends BoardImpl {

        public TestBoardImpl(int size) {
            super(size);
        }

        char[][] getBoard() {
            return super._board;
        }

    }

}
