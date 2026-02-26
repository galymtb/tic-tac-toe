package com.example;

import com.example.server.BaseServer;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class TicTacToe {

    // Preload all static (for performance)
    // public static void load() {}

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
    // Shadow Containerization (fat jar) (+)
    // Integration tests (+)
    // Request, response models (+)
    // New APIs (REST and WS) (+)
    // Method refactor (+)
    // Common handlers (+)
    // TEST EVERYTHING!!!

    private static final Injector _injector = Guice.createInjector(new GameModule());

    public static void main(String[] args) {
        BaseServer server = _injector.getInstance(BaseServer.class);
        server.start();
    }

}