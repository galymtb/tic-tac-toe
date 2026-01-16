package com.example;

import com.example.game.Game;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class TicTacToe {

    private static final Injector injector = Guice.createInjector(new GameModule());

    public static void main(String[] args) {
        Game game = injector.getInstance(Game.class);
        if (game.init()) {
            game.start();
        }
    }

}