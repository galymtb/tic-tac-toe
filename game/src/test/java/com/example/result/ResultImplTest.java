package com.example.result;

import com.example.board.BoardMark;
import com.example.player.Player;
import com.example.player.PlayerImpl;
import com.example.player.PlayerType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ResultImplTest {

    @Test
    public void testInitialization() {
        Player player = new PlayerImpl(BoardMark.X, PlayerType.REST);
        Result result = new ResultImpl(ResultType.WIN, player);

        assertEquals(player, result.getPlayer());
        assertEquals(ResultType.WIN, result.getType());
        assertEquals(player.getName(), result.getPlayer().getName());
    }

}
