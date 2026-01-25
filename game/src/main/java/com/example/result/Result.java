package com.example.result;

import com.example.player.Player;

public interface Result {

    /**
     * Returns Type
     */
    ResultType getType();

    /**
     * Returns Player
     */
    Player getPlayer();

}
