package com.example.board;

public enum BoardMark {

    X('X'),
    O('O'),
    EMPTY(' ');

    private final char _value;

    BoardMark(char value) {
        _value = value;
    }

    public char getValue() {
        return _value;
    }

}
