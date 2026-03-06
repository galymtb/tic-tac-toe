package com.example.player;

import com.example.board.BoardMark;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayerImplTest {

    @Test
    public void testInitialization() {
        Player player = new PlayerImpl(BoardMark.X, PlayerType.REST);

        assertEquals(BoardMark.X.getValue(), player.getName());
        assertEquals(PlayerType.REST, player.getType());
    }

}
