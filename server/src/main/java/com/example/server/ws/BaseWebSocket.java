package com.example.server.ws;

import java.io.IOException;
import java.time.Duration;

import com.example.server.handler.BaseHandler;
import com.example.server.transport.WsTransport;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.eclipse.jetty.websocket.server.JettyWebSocketServlet;
import org.eclipse.jetty.websocket.server.JettyWebSocketServletFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseWebSocket extends JettyWebSocketServlet {

    private static final Logger _log = LoggerFactory.getLogger(BaseWebSocket.class);

    private static BaseHandler _handler = null;
    private static ObjectMapper _mapper = null;

    public BaseWebSocket(BaseHandler handler, ObjectMapper mapper) {
        _handler = handler;
        _mapper = mapper;
    }

    @Override
    protected void configure(JettyWebSocketServletFactory factory) {
        factory.register(WebSocketImpl.class);
    }

    @WebSocket
    public static class WebSocketImpl {

        @OnWebSocketConnect
        public void onOpen(Session session) {
            session.setIdleTimeout(Duration.ofMinutes(10));
            _log.info("connected: {}", session.getRemoteAddress());
        }

        @OnWebSocketMessage
        public void onMessage(String message, Session session) throws IOException {
            _log.info("received: {}", message);

            if (message == null || message.isBlank()) {
                new WsTransport(new String[0], session, _mapper).sendError("Empty message");
                return;
            }

            String[] parts = message.split(":");
            String action = parts.length > 0 ? parts[0].toLowerCase() : "";

            WsTransport tx = new WsTransport(parts, session, _mapper);

            switch (action) {
                case "init" -> _handler.init(tx);
                case "restart" -> _handler.restart(tx);
                case "step" -> _handler.step(tx);
                case "status" -> _handler.status(tx);
                default -> tx.sendError("Invalid action. Expected one of: init, restart, step, status");
            }
        }

        @OnWebSocketClose
        public void onClose(Session session) {
            _log.info("disconnected: {}", session.getRemoteAddress());
        }

    }

}
