package com.example;

import java.util.Scanner;

import com.example.game.Game;
import com.example.game.GameImpl;
import com.example.input.InputSource;
import com.example.input.InputSourceConsole;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

public class GameModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(InputSource.class).to(InputSourceConsole.class);
        bind(Game.class).to(GameImpl.class);
    }

    @Provides
    @Singleton
    public Scanner provideScanner() {
        return new Scanner(System.in);
    }
}
