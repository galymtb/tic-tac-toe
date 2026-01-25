package com.example.server.ws.websockets;

import java.io.IOException;

import com.example.server.rest.endpoints.HelloEndpoint;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.eclipse.jetty.websocket.server.JettyWebSocketServlet;
import org.eclipse.jetty.websocket.server.JettyWebSocketServletFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelloWebSocket extends JettyWebSocketServlet {

    private static final Logger _log = LoggerFactory.getLogger(HelloWebSocket.class);

    @Override
    protected void configure(JettyWebSocketServletFactory factory) {
        factory.register(WebSocketImpl.class);
    }

    @WebSocket
    public static class WebSocketImpl {

        @OnWebSocketConnect
        public void onOpen(Session session) {
            _log.debug("connected: {}", session.getRemoteAddress());
        }

        @OnWebSocketMessage
        public void onMessage(String message, Session session) throws IOException {
            _log.info("received: " + message);
            session.getRemote().sendString(message);
        }

        @OnWebSocketClose
        public void onClose(Session session) {
            _log.debug("disconnected: {}", session.getRemoteAddress());
        }

    }
}
