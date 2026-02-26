package com.example.server;

import jakarta.servlet.http.HttpServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class BaseServerImpl implements BaseServer{

    private final Server _server;
    private final ServletContextHandler _context;

    public BaseServerImpl(Server server, ServletContextHandler context) {
        _server = server;
        _context = context;
    }

    @Override
    public void start() {
        try {
            _server.start();
            _server.join();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void stop() {
        try {
            _server.stop();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addEndpoint(HttpServlet endpoint, String path) {
        _context.addServlet(new ServletHolder(endpoint), path);
    }

}
