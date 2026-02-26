package com.example.game;

import com.example.board.BoardMark;
import com.example.player.PlayerType;

public interface Game {

    /**
     * Initializes game
     */
    GameState init(int boardSize, BoardMark boardMark, PlayerType playerType);

    /**
     * Makes step
     */
    GameState step(int position, PlayerType playerType);

    /**
     * Restarts game
     */
    GameState restart(int boardSize, BoardMark boardMark, PlayerType playerType);

    /**
     * Gets status
     */
    GameState status();

}
