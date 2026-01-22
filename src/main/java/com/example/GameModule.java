package com.example;

import java.util.Scanner;

import com.example.server.rest.endpoints.HelloEndpoint;
import com.example.server.rest.RestServer;
import com.example.server.rest.RestServerImpl;
import com.example.game.Game;
import com.example.game.GameImpl;
import com.example.input.InputSource;
import com.example.input.InputSourceConsole;
import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;

public class GameModule extends AbstractModule {

    private static final int SERVER_PORT = 5000;
    private static final String API_PATH = "/api/v1";

    @Provides
    @Singleton
    public Scanner provideScanner() {
        return new Scanner(System.in);
    }

    @Provides
    @Singleton
    @Inject
    public InputSource getInputSource(Scanner scanner) {
        return new InputSourceConsole(scanner);
    }

    @Provides
    @Singleton
    @Inject
    public Game getGame(InputSource inputSource) {
        return new GameImpl(inputSource);
    }

    @Provides
    @Singleton
    public Server provideServer() {
        return new Server(SERVER_PORT);
    }

    @Provides
    @Singleton
    public ServletContextHandler provideHandler(Server server) {
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath(API_PATH);
        server.setHandler(context);
        return context;
    }

    @Provides
    @Singleton
    @Inject
    public RestServer getRestServer(Server jettyServer, ServletContextHandler context) {
        RestServer server = new RestServerImpl(jettyServer, context);
        server.addEndpoint(new HelloEndpoint(), "/hello");
        return server;
    }

}
