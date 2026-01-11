import com.example.board.BoardImpl;
import com.example.player.Player;
import com.example.result.Result;
import com.example.result.ResultType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
    public void testMoveProceed() {
        Result result = _testBoard.move(1, Player.X);

        char takenPosition = _testBoard.getBoard()[0][0];
        assertEquals('X', takenPosition, "Incorrect character at position after move");
        
        assertEquals(ResultType.PROCEED, result.getType());
        assertEquals(null, result.getPlayer());
    }

    @Test
    public void testMoveSkip() {
        _testBoard.move(1, Player.X);
        Result resultSkip = _testBoard.move(1, Player.O);

        assertEquals(ResultType.SKIP, resultSkip.getType());
        assertEquals(null, resultSkip.getPlayer());
    }

    @Test
    public void testMoveDraw() {
        _testBoard.move(1, Player.X);
        _testBoard.move(2, Player.X);
        _testBoard.move(3, Player.O);
        _testBoard.move(4, Player.O);
        _testBoard.move(5, Player.O);
        _testBoard.move(6, Player.X);
        _testBoard.move(7, Player.X);
        _testBoard.move(8, Player.O);
        Result resultDraw = _testBoard.move(9, Player.X);

        assertEquals(ResultType.DRAW, resultDraw.getType());
        assertEquals(null, resultDraw.getPlayer());
    }

    @Test
    public void testMoveWin() {
        _testBoard.move(1, Player.X);
        _testBoard.move(2, Player.X);
        Result resultDraw = _testBoard.move(3, Player.X);

        assertEquals(ResultType.WIN, resultDraw.getType());
        assertEquals(Player.X, resultDraw.getPlayer());
    }

    public static class TestBoardImpl extends BoardImpl {

        public TestBoardImpl(int size) {
            super(size);
        }

        char[][] getBoard() {
            return super._board;
        }

        int getPositionsTaken() {
            return super._positionsTaken;
        }

    }

}
