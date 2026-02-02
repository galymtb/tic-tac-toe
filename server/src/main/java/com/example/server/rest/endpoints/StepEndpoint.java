package com.example.server.rest.endpoints;

import java.io.IOException;

import com.example.game.Game;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class StepEndpoint extends HttpServlet {

    private static Game _game;

    public StepEndpoint(Game game) {
        _game = game;
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int position = Integer.parseInt(request.getParameter("position"));
        _game.step(position);
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);
    }

}
