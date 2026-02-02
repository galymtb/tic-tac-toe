package com.example;

import java.io.IOException;
import java.net.URI;
import java.util.Scanner;

import com.example.server.ws.websockets.HelloWebSocket;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.StatusCode;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.eclipse.jetty.websocket.client.ClientUpgradeRequest;
import org.eclipse.jetty.websocket.client.WebSocketClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebsocketTest {

    private static final Logger _log = LoggerFactory.getLogger(HelloWebSocket.class);

    @WebSocket
    public static class WebSocketImpl {

        private static Session _session;

        @OnWebSocketConnect
        public void onOpen(Session session) {
            _session = session;
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
            _session = null;
        }

        public Session getSession() {
            return _session;
        }

    }

    public static void main(String[] args) {
        URI uri = URI.create("ws://localhost:5000/ws/game");
        WebSocketClient client = new WebSocketClient();
        WebSocketImpl socket = new WebSocketImpl();

        try {
            client.start();
            client.connect(socket, uri, new ClientUpgradeRequest()).get();
            Session session = socket.getSession();
            Scanner scanner = new Scanner(System.in);
            while (true) {
                String line = scanner.nextLine();
                if ("exit".equalsIgnoreCase(line.trim())) {
                    session.close(StatusCode.NORMAL, "Client exit");
                    break;
                }
                session.getRemote().sendString(line);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
