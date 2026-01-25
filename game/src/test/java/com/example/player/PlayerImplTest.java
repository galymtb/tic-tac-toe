package com.example.player;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayerImplTest {

    @Test
    public void testInitialization() {
        Player player = new PlayerImpl('X');

        assertEquals('X', player.getName());
    }

}
