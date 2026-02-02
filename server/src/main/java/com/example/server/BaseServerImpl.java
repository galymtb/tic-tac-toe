package com.example.server;

import jakarta.servlet.http.HttpServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseServerImpl implements BaseServer{

    private final Logger _log = LoggerFactory.getLogger(BaseServerImpl.class);

    private final Server _server;
    private final ServletContextHandler _context;

    public BaseServerImpl(Server server, ServletContextHandler context) {
        _server = server;
        _context = context;
    }

    @Override
    public void start() throws Exception {
        try {
            _server.start();
            _log.info("Server has started successfully! Send boardSize to /init for initializing the game");
            _server.join();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void stop() throws Exception {
        _server.stop();
    }

    @Override
    public void addEndpoint(HttpServlet endpoint, String path) {
        _context.addServlet(new ServletHolder(endpoint), path);
    }

}
