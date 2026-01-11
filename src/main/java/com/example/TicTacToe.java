package com.example;

import com.example.game.Game;
import com.example.game.GameImpl;

public class TicTacToe {

    public static void main(String[] args) {
        Game game = new GameImpl();
        game.start();
    }

}