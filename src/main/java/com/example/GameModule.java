package com.example;

import java.util.Scanner;

import com.example.game.Game;
import com.example.game.GameImpl;
import com.example.input.InputSource;
import com.example.input.InputSourceConsole;
import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provides;
import com.google.inject.Singleton;

public class GameModule extends AbstractModule {

    @Provides
    @Singleton
    public Scanner provideScanner() {
        return new Scanner(System.in);
    }

    @Provides
    @Singleton
    @Inject
    public InputSource getInputSource(Scanner scanner) {
        return new InputSourceConsole(scanner);
    }

    @Provides
    @Singleton
    @Inject
    public Game getGame(InputSource inputSource) {
        return new GameImpl(inputSource);
    }

}
