package com.example.player;

public enum Player {

    X('X'),
    O('O'),
    W('W');

    private final char _displayName;

    Player(char displayName) {
        _displayName = displayName;
    }

    public static Player getFirstPlayer() {
        return Player.values()[0];
    }

    public char getDisplayName() {
        return _displayName;
    }

    public Player getNextPlayer() {
        int ordinal = this.ordinal();
        Player[] players = Player.values();
        return players[(ordinal + 1) % players.length];
    }

}
