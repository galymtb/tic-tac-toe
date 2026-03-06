package com.example.game;

import com.example.board.BoardMark;
import com.example.player.PlayerType;
import com.example.result.ResultType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GameImplTest {

    private final TestGameImpl _testGame;

    GameImplTest() {
        _testGame = new TestGameImpl();
    }

    @Test
    public void testInitialization() {
        GameState state = _testGame.status();

        assertEquals("Game is not started yet",  state.getMessage());
        assertEquals(ResultType.ERROR,  state.getResult());
        assertNull(state.getCurrentPlayer());
        assertNull(state.getBoard());
        assertFalse(state.isInitialized());
        assertFalse(state.isFinished());
    }

    @Test
    public void testInitInvalidBoardSize() {
        GameState state = _testGame.init(0, BoardMark.X, PlayerType.REST);

        assertEquals("Board size must be greater than zero!",  state.getMessage());
        assertEquals(ResultType.ERROR,  state.getResult());
        assertNull(state.getCurrentPlayer());
        assertNull(state.getBoard());
        assertFalse(state.isInitialized());
        assertFalse(state.isFinished());
    }

    @Test
    public void testInitAlreadyInitialized() {
        _testGame.init(3, BoardMark.X, PlayerType.REST);
        GameState state = _testGame.init(3, BoardMark.X, PlayerType.REST);

        assertEquals("Game is already initialized!",  state.getMessage());
        assertEquals(ResultType.ERROR,  state.getResult());
        assertNotNull(state.getCurrentPlayer());
        assertNotNull(state.getBoard());
        assertTrue(state.isInitialized());
        assertFalse(state.isFinished());
    }

    @Test
    public void testInitSuccess() {
        GameState state = _testGame.init(3, BoardMark.X, PlayerType.REST);

        assertEquals("Game is initialized!",  state.getMessage());
        assertEquals(ResultType.NEXT,  state.getResult());
        assertNotNull(state.getCurrentPlayer());
        assertNotNull(state.getBoard());
        assertTrue(state.isInitialized());
        assertFalse(state.isFinished());
    }

    @Test
    public void testStepNotInitialized() {
        GameState state = _testGame.step(3, PlayerType.REST);

        assertEquals("Game is not initialized!",  state.getMessage());
        assertEquals(ResultType.ERROR,  state.getResult());
        assertNull(state.getCurrentPlayer());
        assertNull(state.getBoard());
        assertFalse(state.isInitialized());
        assertFalse(state.isFinished());
    }

    @Test
    public void testStepAlreadyFinished() {
        _testGame.init(3, BoardMark.X, PlayerType.REST);
        _testGame.step(1, PlayerType.REST);
        _testGame.step(2, PlayerType.WS);
        _testGame.step(4, PlayerType.REST);
        _testGame.step(5, PlayerType.WS);
        _testGame.step(7, PlayerType.REST);
        GameState state = _testGame.step(8, PlayerType.WS);

        assertEquals("Game is already finished!",  state.getMessage());
        assertEquals(ResultType.ERROR,  state.getResult());
        assertNull(state.getCurrentPlayer());
        assertNotNull(state.getBoard());
        assertTrue(state.isInitialized());
        assertTrue(state.isFinished());
    }

    @Test
    public void testStepNotPlayerTurn() {
        _testGame.init(3, BoardMark.X, PlayerType.REST);
        _testGame.step(1, PlayerType.REST);
        GameState state = _testGame.step(2, PlayerType.REST);

        assertEquals("Not your turn! Wait for O to move!",  state.getMessage());
        assertEquals(ResultType.ERROR,  state.getResult());
        assertNotNull(state.getCurrentPlayer());
        assertNotNull(state.getBoard());
        assertTrue(state.isInitialized());
        assertFalse(state.isFinished());
    }

    @Test
    public void testStepWrongInput() {
        _testGame.init(3, BoardMark.X, PlayerType.REST);
        GameState state = _testGame.step(0, PlayerType.REST);

        assertEquals("Wrong input. Try again!",  state.getMessage());
        assertEquals(ResultType.ERROR,  state.getResult());
        assertNotNull(state.getCurrentPlayer());
        assertNotNull(state.getBoard());
        assertTrue(state.isInitialized());
        assertFalse(state.isFinished());
    }

    @Test
    public void testRestartNotInitialized() {
        GameState state = _testGame.restart(3, BoardMark.X, PlayerType.REST);

        assertEquals("Game is not initialized!",  state.getMessage());
        assertEquals(ResultType.ERROR,  state.getResult());
        assertNull(state.getCurrentPlayer());
        assertNull(state.getBoard());
        assertFalse(state.isInitialized());
        assertFalse(state.isFinished());
    }

    @Test
    public void testRestartSuccess() {
        _testGame.init(3, BoardMark.X, PlayerType.REST);
        GameState state = _testGame.restart(3, BoardMark.X, PlayerType.REST);

        assertEquals("Game is initialized!",  state.getMessage());
        assertEquals(ResultType.NEXT,  state.getResult());
        assertNotNull(state.getCurrentPlayer());
        assertNotNull(state.getBoard());
        assertTrue(state.isInitialized());
        assertFalse(state.isFinished());
    }

    public static class TestGameImpl extends GameImpl {

        public TestGameImpl() {
            super();
        }

    }

}
