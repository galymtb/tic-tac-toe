package com.example.board;

import com.example.player.Player;
import com.example.result.Result;

public interface Board {

    /**
     * Moves player to position
     */
    Result move(int position, Player player);

    /**
     * Gets area of the board
     */
    int getArea();

    /**
     * Gets board in String
     */
    String boardToString();

}
