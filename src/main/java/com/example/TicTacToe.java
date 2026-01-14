package com.example;

import com.example.game.Game;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class TicTacToe {

    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new GameModule());
        Game game = injector.getInstance(Game.class);
        if (game.init()) {
            game.start();
        }
    }

}