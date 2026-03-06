package com.example.game;

import com.example.board.BoardMark;
import com.example.player.Player;
import com.example.player.PlayerImpl;
import com.example.player.PlayerType;
import com.example.result.ResultType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GameStateTest {

    @Test
    public void testInitialization() {
        GameState state = new GameState("Game is not started yet", ResultType.ERROR, null, null, false, false);

        Player player = new PlayerImpl(BoardMark.X, PlayerType.REST);

        state.setMessage("TEST");
        state.setResult(ResultType.NEXT);
        state.setCurrentPlayer(player.getName());
        state.setBoard("\n-------------\n|   |   | X |\n-------------\n|   | X |   |\n-------------\n| X |   |   |\n-------------");
        state.setInitialized(true);
        state.setFinished(false);

        assertEquals("TEST", state.getMessage());
        assertEquals(ResultType.NEXT, state.getResult());
        assertEquals(player.getName(), state.getCurrentPlayer());
        assertEquals("\n-------------\n|   |   | X |\n-------------\n|   | X |   |\n-------------\n| X |   |   |\n-------------", state.getBoard());
        assertTrue(state.isInitialized());
        assertFalse(state.isFinished());
    }

}
