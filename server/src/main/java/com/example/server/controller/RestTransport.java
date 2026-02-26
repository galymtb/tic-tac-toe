package com.example.server.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import com.example.game.GameState;
import com.example.player.PlayerType;
import com.example.result.ResultType;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class RestTransport implements Transport {

    private final HttpServletRequest _request;
    private final HttpServletResponse _response;
    private final ObjectMapper _mapper;

    public RestTransport(HttpServletRequest request, HttpServletResponse response,  ObjectMapper mapper) {
        _request = request;
        _response = response;
        _response.setContentType("application/json");
        _mapper =  mapper;
    }

    @Override
    public String getParam(String name) {
        return _request.getParameter(name);
    }

    @Override
    public void sendError(String message) throws IOException {
        _response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        try (PrintWriter out = _response.getWriter()) {
            _mapper.writeValue(out, Map.of("error", message));
        }
    }

    @Override
    public void sendState(GameState state) throws IOException {
        int status = state.getResult() == ResultType.ERROR ? HttpServletResponse.SC_BAD_REQUEST : HttpServletResponse.SC_OK;
        _response.setStatus(status);
        try (PrintWriter out = _response.getWriter()) {
            _mapper.writeValue(out, state);
        }
    }

    @Override
    public PlayerType getPlayerType() {
        return PlayerType.REST;
    }

}
