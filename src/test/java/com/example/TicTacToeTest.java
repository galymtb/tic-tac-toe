package com.example;

import com.example.game.Game;
import com.google.inject.Guice;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TicTacToeTest {

    @Test
    public void testInitialization() {
        Game game = Guice.createInjector(new GameModule()).getInstance(Game.class);
        assertNotNull(game);
    }

}
