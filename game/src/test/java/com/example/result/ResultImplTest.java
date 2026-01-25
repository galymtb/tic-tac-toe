package com.example.result;

import com.example.player.Player;
import com.example.player.PlayerImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ResultImplTest {

    @Test
    public void testInitialization() {
        Player player = new PlayerImpl('X');
        Result result = new ResultImpl(ResultType.WIN, player);

        assertEquals(player, result.getPlayer());
        assertEquals(ResultType.WIN, result.getType());
        assertEquals(player.getName(), result.getPlayer().getName());
    }

}
