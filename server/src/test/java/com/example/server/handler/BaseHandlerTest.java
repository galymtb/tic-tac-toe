package com.example.server.handler;

import java.io.IOException;

import com.example.board.BoardMark;
import com.example.game.Game;
import com.example.game.GameState;
import com.example.player.PlayerType;
import com.example.result.ResultType;
import com.example.server.transport.Transport;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BaseHandlerTest {

    private Game _game;
    private Transport _transport;
    private BaseHandler _handler;

    BaseHandlerTest() {
        _game = mock(Game.class);
        _transport = mock(Transport.class);
        _handler = new BaseHandler(_game);
    }

    @Test
    public void testInit() throws IOException {
        when(_transport.getParam("boardSize")).thenReturn("3");
        when(_transport.getParam("playerMark")).thenReturn("X");
        when(_transport.getPlayerType()).thenReturn(PlayerType.REST);

        GameState state = new GameState("Game is initialized!", ResultType.NEXT, 'X', "", true, false);
        when(_game.init(3, BoardMark.X, PlayerType.REST)).thenReturn(state);

        _handler.init(_transport);
        verify(_game).init(3, BoardMark.X, PlayerType.REST);
        verify(_transport).sendState(state);
        verify(_transport, never()).sendError(anyString());
    }

    @Test
    public void testInitMissingSizeOrMark() throws IOException {
        when(_transport.getParam("boardSize")).thenReturn("");
        when(_transport.getParam("playerMark")).thenReturn("");
        _handler.init(_transport);
        verify(_transport).sendError("Missing required parameter: boardSize and/or playerMark");
    }

    @Test
    public void testInitInvalidSizeOrMark() throws IOException {
        when(_transport.getParam("boardSize")).thenReturn("0");
        when(_transport.getParam("playerMark")).thenReturn("A");
        _handler.init(_transport);
        verify(_transport).sendError("Invalid boardSize (int) or playerMark (X or O)");
    }

    @Test
    public void testRestart() throws IOException {
        when(_transport.getParam("boardSize")).thenReturn("3");
        when(_transport.getParam("playerMark")).thenReturn("X");
        when(_transport.getPlayerType()).thenReturn(PlayerType.REST);

        GameState state = new GameState("Game is initialized!", ResultType.NEXT, 'X', "", true, false);
        when(_game.restart(3, BoardMark.X, PlayerType.REST)).thenReturn(state);

        _handler.restart(_transport);
        verify(_game).restart(3, BoardMark.X, PlayerType.REST);
        verify(_transport).sendState(state);
        verify(_transport, never()).sendError(anyString());
    }

    @Test
    public void testRestartMissingSizeOrMark() throws IOException {
        when(_transport.getParam("boardSize")).thenReturn("");
        when(_transport.getParam("playerMark")).thenReturn("");
        _handler.restart(_transport);
        verify(_transport).sendError("Missing required parameter: boardSize and/or playerMark");
    }

    @Test
    public void testRestartInvalidSizeOrMark() throws IOException {
        when(_transport.getParam("boardSize")).thenReturn("0");
        when(_transport.getParam("playerMark")).thenReturn("A");
        _handler.restart(_transport);
        verify(_transport).sendError("Invalid boardSize (int) or playerMark (X or O)");
    }

    @Test
    public void testStep() throws IOException {
        when(_transport.getParam("position")).thenReturn("5");
        when(_transport.getPlayerType()).thenReturn(PlayerType.REST);

        GameState state = new GameState("Player O, please make a step (1 to 9): ", ResultType.NEXT, 'O', "", true, false);
        when(_game.step(5, PlayerType.REST)).thenReturn(state);

        _handler.step(_transport);
        verify(_game).step(5, PlayerType.REST);
        verify(_transport).sendState(state);
        verify(_transport, never()).sendError(anyString());
    }

    @Test
    public void testStepMissingPosition() throws IOException {
        when(_transport.getParam("position")).thenReturn("");
        _handler.step(_transport);
        verify(_transport).sendError("Missing required parameter: position");
    }

    @Test
    public void testStepInvalidPosition() throws IOException {
        when(_transport.getParam("position")).thenReturn("A");
        _handler.step(_transport);
        verify(_transport).sendError("Invalid position (int)");
    }

    @Test
    public void testStatus() throws IOException {
        GameState state = new GameState("Game is not started yet", ResultType.ERROR, null, null, false, false);
        when(_game.status()).thenReturn(state);
        _handler.status(_transport);

        verify(_game).status();
        verify(_transport).sendState(state);
        verify(_transport, never()).sendError(anyString());
    }

}
