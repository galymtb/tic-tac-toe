package com.example;

import java.io.IOException;
import java.util.Properties;

import com.example.server.BaseServer;
import com.example.server.BaseServerImpl;
import com.example.server.handler.BaseHandler;
import com.example.server.rest.InitEndpoint;
import com.example.game.Game;
import com.example.game.GameImpl;
import com.example.server.rest.RestartEndpoint;
import com.example.server.rest.StatusEndpoint;
import com.example.server.rest.StepEndpoint;
import com.example.server.ws.BaseWebSocket;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import com.google.inject.name.Names;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.websocket.server.config.JettyWebSocketServletContainerInitializer;

public class GameModule extends AbstractModule {

    @Override
    protected void configure() {
        Properties properties = new Properties();
        try {
            properties.load(getClass().getClassLoader().getResourceAsStream("config.properties"));
            Names.bindProperties(binder(), properties);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Provides
    @Singleton
    public Game getGame() {
        return new GameImpl();
    }

    @Provides
    @Singleton
    public BaseHandler provideHandler(Game game) {
        return new BaseHandler(game);
    }

    @Provides
    @Singleton
    public ObjectMapper provideObjectMapper() {
        return new ObjectMapper();
    }

    @Provides
    @Singleton
    public Server provideServer(@Named("SERVER_PORT") String port) {
        return new Server(Integer.parseInt(port));
    }

    @Provides
    @Singleton
    public ServletContextHandler contextHandler(Server server, @Named("SERVER_PATH") String path) {
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath(path);
        server.setHandler(context);
        return context;
    }

    @Provides
    @Singleton
    public BaseServer getServer(Server jettyServer,
                                ServletContextHandler context,
                                BaseHandler handler,
                                ObjectMapper mapper,
                                @Named("REST_API_PATH") String restPath,
                                @Named("WS_PATH") String wsPath) {

        BaseServer server = new BaseServerImpl(jettyServer, context);
        // REST
        server.addEndpoint(new InitEndpoint(handler, mapper), restPath + "/init");
        server.addEndpoint(new StepEndpoint(handler, mapper), restPath + "/step");
        server.addEndpoint(new StatusEndpoint(handler, mapper), restPath + "/status");
        server.addEndpoint(new RestartEndpoint(handler, mapper), restPath + "/restart");

        // WS
        JettyWebSocketServletContainerInitializer.configure(context, null);
        server.addEndpoint(new BaseWebSocket(handler, mapper), wsPath);
        return server;
    }

}
