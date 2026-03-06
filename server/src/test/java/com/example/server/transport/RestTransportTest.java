package com.example.server.transport;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import com.example.game.GameState;
import com.example.player.PlayerType;
import com.example.result.ResultType;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RestTransportTest {

    private final RestTransport _testTx;
    private final HttpServletRequest _request;
    private final HttpServletResponse _response;
    private final StringWriter _responseWriter;

    RestTransportTest() throws IOException {
        _request = mock(HttpServletRequest.class);
        _response = mock(HttpServletResponse.class);
        ObjectMapper _mapper = new ObjectMapper();

        _responseWriter = new StringWriter();
        PrintWriter pw = new PrintWriter(_responseWriter);
        when(_response.getWriter()).thenReturn(pw);
        _testTx = new RestTransport(_request, _response, _mapper);
    }

    @Test
    public void testInitialization() {
        verify(_response).setContentType("application/json");
    }

    @Test
    public void testGetParam() {
        when(_request.getParameter("key")).thenReturn("value");
        assertEquals("value", _testTx.getParam("key"));
        verify(_request).getParameter("key");
    }

    @Test
    public void testErrorState() throws IOException {
        _testTx.sendError("some error");
        verify(_response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
        String json = _responseWriter.toString();
        assertTrue(json.contains("\"error\""));
        assertTrue(json.contains("some error"));
    }

    @Test
    public void testSendState() throws IOException {
        GameState okState = new GameState("ok", ResultType.NEXT, 'X', "", true, false);
        _testTx.sendState(okState);
        verify(_response).setStatus(HttpServletResponse.SC_OK);
        String okJson = _responseWriter.toString();
        assertFalse(okJson.isEmpty());

        GameState errState = new GameState("err", ResultType.ERROR, 'X', "", true, false);
        _testTx.sendState(errState);
        verify(_response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
        String errJson = _responseWriter.toString();
        assertFalse(errJson.isEmpty());
    }

    @Test
    public void testGetPlayerType() {
        assertEquals(PlayerType.REST, _testTx.getPlayerType());
    }

}
