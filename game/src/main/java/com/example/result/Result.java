package com.example.result;

import com.example.player.Player;

public interface Result {

    /**
     * Returns Type of the Result
     */
    ResultType getType();

    /**
     * Returns Player of the Result
     */
    Player getPlayer();

}
