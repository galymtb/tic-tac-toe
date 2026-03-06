package com.example.server.transport;

import java.io.IOException;

import com.example.game.GameState;
import com.example.player.PlayerType;
import com.example.result.ResultType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.jetty.websocket.api.RemoteEndpoint;
import org.eclipse.jetty.websocket.api.Session;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class WsTransportTest {

    private final Session _session;
    private final RemoteEndpoint _remote;
    private final ObjectMapper _mapper;

    WsTransportTest() {
        _session = mock(Session.class);
        _remote = mock(RemoteEndpoint.class);
        when(_session.getRemote()).thenReturn(_remote);
        _mapper = new ObjectMapper();
    }

    @Test
    public void testGetParam() {
        String[] parts = new String[] {"init", "3", "X"};
        WsTransport t = new WsTransport(parts, _session, _mapper);
        assertEquals("3", t.getParam("boardSize"));
        assertEquals("X", t.getParam("playerMark"));

        parts = new String[] {"init"};
        t = new WsTransport(parts, _session, _mapper);
        assertNull(t.getParam("boardSize"));
        assertNull(t.getParam("playerMark"));
        assertNull(t.getParam(""));
    }

    @Test
    public void testErrorState() throws IOException {
        String[] parts = new String[] {"init"};
        WsTransport t = new WsTransport(parts, _session, _mapper);
        t.sendError("some error");
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(_remote).sendString(captor.capture());
        String json = captor.getValue();
        assertTrue(json.contains("\"error\""));
        assertTrue(json.contains("some error"));
    }

    @Test
    public void testSendState() throws IOException {
        String[] parts = new String[] {"init"};
        WsTransport t = new WsTransport(parts, _session, _mapper);
        GameState okState = new GameState("ok", ResultType.NEXT, 'X', "", true, false);
        t.sendState(okState);
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(_remote).sendString(captor.capture());
        String okJson = captor.getValue();
        assertFalse(okJson.isEmpty());
    }

    @Test
    public void testGetPlayerType() {
        WsTransport t = new WsTransport(new String[] {"some"}, _session, _mapper);
        assertEquals(PlayerType.WS, t.getPlayerType());
    }

}
