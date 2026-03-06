package com.example.server.transport;

import java.io.IOException;
import java.util.Map;

import com.example.game.GameState;
import com.example.player.PlayerType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.jetty.websocket.api.Session;

public class WsTransport implements Transport {

    private final String[] _parts;
    private final Session _session;
    private final ObjectMapper _mapper;

    public WsTransport(String[] parts, Session session, ObjectMapper mapper) {
        _parts = parts;
        _session = session;
        _mapper = mapper;
    }

    @Override
    public String getParam(String name) {
        return switch (name) {
            case "boardSize", "position" -> _parts.length > 1 ? _parts[1] : null;
            case "playerMark" -> _parts.length > 2 ? _parts[2] : null;
            default -> null;
        };
    }

    @Override
    public void sendError(String message) throws IOException {
        String json = _mapper.writeValueAsString(Map.of("error", message));
        _session.getRemote().sendString(json);
    }

    @Override
    public void sendState(GameState state) throws IOException {
        String json = _mapper.writeValueAsString(state);
        _session.getRemote().sendString(json);
    }

    @Override
    public PlayerType getPlayerType() {
        return PlayerType.WS;
    }

}
