package com.example.server.rest.endpoints;

import java.io.IOException;

import com.example.game.Game;
import com.example.game.GameImpl;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InitEndpoint extends HttpServlet {

    private static final Logger _log = LoggerFactory.getLogger(InitEndpoint.class);

    private static Game _game;

    public InitEndpoint(Game game) {
        _game = game;
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        _log.debug("request: method = {}, URI = {}, from = {}",
                request.getMethod(),
                request.getRequestURI(),
                request.getRemoteAddr());

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);

        String jsonResponse = "{\"status\": \"ok\"}";
        response.getWriter().print(jsonResponse);

        _log.debug("response: status = {}, body = {}",
                response.getStatus(),
                jsonResponse);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int boardSize = Integer.parseInt(request.getParameter("boardSize"));
        _game.init(boardSize);
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);
    }

}
