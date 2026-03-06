package com.example.server.transport;

import java.io.IOException;

import com.example.game.GameState;
import com.example.player.PlayerType;

public interface Transport {

    String getParam(String name);

    void sendError(String message) throws IOException;

    void sendState(GameState state) throws IOException;

    PlayerType getPlayerType();
}
