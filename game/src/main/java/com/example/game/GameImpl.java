package com.example.game;

import com.example.board.Board;
import com.example.board.BoardImpl;
import com.example.player.Player;
import com.example.player.PlayerImpl;
import com.example.result.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameImpl implements Game {

    private static final Logger _logger = LoggerFactory.getLogger(GameImpl.class);

    // Preload all static (for performance)
    // public static void load() {}

    private Player[] _players;
    private Board _board;
    private boolean _isGameOver = false;

    public GameImpl() {
    }

    @Override
    public void init(int boardSize) {
        initPlayers();
        initBoard(boardSize);
        _board.clear();
        _board.print();
        _logger.info("Player {}, please send position to /step (1 to {}): ",
                _players[_board.getPositionsTaken() % _players.length].getName(),
                _board.getArea());
    }

    private void initPlayers() {
        int playersNumber = 2;
        _logger.info("Number of players: {}", playersNumber);
        _players = new PlayerImpl[playersNumber];
        fill();
    }

    private void initBoard(int boardSize) {
        _logger.info("Board size: {}", boardSize);
        _board = new BoardImpl(boardSize);
    }

    private void fill() {
        for (int i = 0; i < _players.length; i++) {
            _players[i] = new PlayerImpl((char) (i + 'A'));
        }
    }

    public void step(int position) {
        if (_isGameOver) {
            _logger.warn("Game is finished! Please restart the game using /init");
            return;
        }
        if (position <= 0 || position > _board.getArea()) {
            _logger.info("Wrong input. Try again.");
            return;
        }
        Result result = _board.move(position, _players[_board.getPositionsTaken() % _players.length]);
        _board.print();
        switch (result.getType()) {
            case WIN -> {
                _logger.info("Player {} won! Please restart the game using /init", result.getPlayer().getName());
                _isGameOver = true;
            }
            case DRAW -> {
                _logger.info("Draw! Please restart the game using /init");
                _isGameOver = true;
            }
            case NEXT -> _logger.info("Player {}, please send position to /step (1 to {}): ",
                    _players[_board.getPositionsTaken() % _players.length].getName(),
                    _board.getArea());
            case SKIP -> _logger.info("Position is taken. Try again.");
        }
    }

}

