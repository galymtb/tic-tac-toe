package com.example.result;

import com.example.player.Player;

public class ResultImpl implements Result {

    private final ResultType _type;
    private final Player _player;

    public ResultImpl(ResultType type, Player player) {
        _type = type;
        _player = player;
    }

    @Override
    public ResultType getType() {
        return _type;
    }

    @Override
    public Player getPlayer() {
        return _player;
    }

}
