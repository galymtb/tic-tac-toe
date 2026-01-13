package com.example.player;

public class PlayerImpl implements Player{

    private final char _name;

    public PlayerImpl(char name) {
        _name = name;
    }

    @Override
    public char getName() {
        return _name;
    }

}
