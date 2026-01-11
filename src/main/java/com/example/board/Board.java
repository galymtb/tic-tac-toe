package com.example.board;

import com.example.player.Player;
import com.example.result.Result;

public interface Board {

    /**
     * Prints the board
     */
    void print();

    /**
     * Clears the board
     */
    void clear();

    /**
     * Moves player to position
     */
    Result move(int position, Player player);

    /**
     * Get size of the board
     */
    int getSize();
}
