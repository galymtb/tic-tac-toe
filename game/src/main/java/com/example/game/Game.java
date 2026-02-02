package com.example.game;

public interface Game {

    /**
     * Initializes game
     */
    void init(int boardSize);

    /**
     * Starts game
     */
    void step(int position);

}
