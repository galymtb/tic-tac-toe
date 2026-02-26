package com.example.server.handler;

import java.io.IOException;

import com.example.board.BoardMark;
import com.example.game.Game;
import com.example.game.GameState;
import com.example.server.controller.Transport;

public class BaseHandler {

    private final Game _game;

    public BaseHandler(Game game) {
        this._game = game;
    }

    public void init(Transport tx) throws IOException {
        String boardSizeParam = tx.getParam("boardSize");
        String playerMarkParam = tx.getParam("playerMark");

        if (boardSizeParam == null || boardSizeParam.isBlank() || playerMarkParam == null || playerMarkParam.isBlank()) {
            tx.sendError("Missing required parameter: boardSize and/or playerMark");
            return;
        }

        int boardSize;
        BoardMark playerMark;
        try {
            boardSize = Integer.parseInt(boardSizeParam);
            playerMark = BoardMark.valueOf(playerMarkParam);
        } catch (Exception e) {
            tx.sendError("Invalid boardSize (int) or playerMark (X or O)");
            return;
        }

        GameState state = _game.init(boardSize, playerMark, tx.getPlayerType());
        tx.sendState(state);
    }

    public void restart(Transport tx) throws IOException {
        String boardSizeParam = tx.getParam("boardSize");
        String playerMarkParam = tx.getParam("playerMark");

        if (boardSizeParam == null || boardSizeParam.isBlank() || playerMarkParam == null || playerMarkParam.isBlank()) {
            tx.sendError("Missing required parameter: boardSize and/or playerMark");
            return;
        }

        int boardSize;
        BoardMark playerMark;
        try {
            boardSize = Integer.parseInt(boardSizeParam);
            playerMark = BoardMark.valueOf(playerMarkParam);
        } catch (Exception e) {
            tx.sendError("Invalid boardSize (int) or playerMark (X or O)");
            return;
        }

        GameState state = _game.restart(boardSize, playerMark, tx.getPlayerType());
        tx.sendState(state);
    }

    public void step(Transport tx) throws IOException {
        String positionParam = tx.getParam("position");

        if (positionParam == null || positionParam.isBlank()) {
            tx.sendError("Missing required parameter: position");
            return;
        }

        int position;
        try {
            position = Integer.parseInt(positionParam);
        } catch (NumberFormatException e) {
            tx.sendError("Invalid position (int)");
            return;
        }

        GameState state = _game.step(position, tx.getPlayerType());
        tx.sendState(state);
    }

    public void status(Transport tx) throws IOException {
        GameState state = _game.status();
        tx.sendState(state);
    }
}
