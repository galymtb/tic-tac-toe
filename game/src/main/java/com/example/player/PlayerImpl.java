package com.example.player;

import com.example.board.BoardMark;

public class PlayerImpl implements Player{

    private final BoardMark _name;
    private final PlayerType _type;

    public PlayerImpl(BoardMark name, PlayerType type) {
        _name = name;
        _type = type;
    }

    @Override
    public char getName() {
        return _name.getValue();
    }

    @Override
    public PlayerType getType() {
        return _type;
    }

}
