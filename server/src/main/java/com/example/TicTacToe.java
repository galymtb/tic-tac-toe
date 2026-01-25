package com.example;

import com.example.game.Game;
import com.example.server.BaseServer;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class TicTacToe {

    // TODO:
    // make user to trigger another game (+)
    // make game 9x9 (+)
    // add another player (3 players) (+)
    // make board size dynamic (+)
    // make dynamic number of players (+)
    // dependency injection (+)
    // logger (logback) (+)
    // Jetty API Endpoints (+)
    // Test Endpoints (+)
    // WebSockets (running in parallel with REST) (+)
    // Logger formatting (+)
    // Configuration (PORT, ENDPOINT PATHS) (+)
    // Dockerize (+)

    // TEST EVERYTHING!

    private static final Injector _injector = Guice.createInjector(new GameModule());

    public static void main(String[] args) {
        Game game = _injector.getInstance(Game.class);
        BaseServer server = _injector.getInstance(BaseServer.class);
        try {
            server.start();
        } catch (Exception ignore) {
        }

        if (game.init()) {
            game.start();
        }
    }

}