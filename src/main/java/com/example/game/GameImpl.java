package com.example.game;

import com.example.board.Board;
import com.example.board.BoardImpl;
import com.example.input.InputSource;
import com.example.player.Player;
import com.example.player.PlayerImpl;
import com.example.result.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameImpl implements Game {

    // TODO:
    // make user to trigger another game (+)
    // make game 9x9 (+)
    // add another player (3 players) (+)
    // make board size dynamic (+)
    // make dynamic number of players (+)
    // dependency injection (+)
    // logger (logback) (+)
    // TEST EVERYTHING!

    private static final Logger _logger = LoggerFactory.getLogger(GameImpl.class);
    private static final String YES = "y";
    private static final String NO = "n";

    private final InputSource _inputSource;
    private Player[] _players;
    private Board _board;

    public GameImpl(InputSource inputSource) {
        _inputSource = inputSource;
    }

    @Override
    public boolean init() {
        return initBoard() && initPlayers();
    }

    private boolean initPlayers() {
        _logger.info("Number of players: ");
        int playersNumber = getValidInt();
        _players = new PlayerImpl[playersNumber];
        fill();
        return true;
    }

    private boolean initBoard() {
        _logger.info("Board size: " );
        int boardSize = getValidInt();
        _board = new BoardImpl(boardSize);
        return true;
    }

    private int getValidInt() {
        while (true) {
            try {
                return _inputSource.nextInt();
            } catch (Exception  e) {
                _logger.error("Input digit, idiot!");
                _inputSource.next();
            }
        }
    }

    private void fill() {
        for (int i = 0; i < _players.length; i++) {
            _players[i] = new PlayerImpl((char) (i + 'A'));
        }
    }

    private boolean isRestart() {
        _logger.info("Do you want to restart? (y/n): ");
        String answer = _inputSource.next();

        switch (answer) {
            case YES -> {
                _board.clear();
                return init();
            }
            case NO -> {
                return false;
            }
            default -> {
                _logger.error("Invalid input. Please type 'y' for yes or 'n' for no.");
                return isRestart();
            }
        }
    }

    @Override
    public void start() {
        while (iterate());
    }

    boolean iterate() {

        int idx = _board.getPositionsTaken() % _players.length;
        int area = _board.getSize() * _board.getSize();

        _board.print();

        _logger.info("Player {}, please make your move (1 to {}): ", _players[idx].getName(), area);

        int position = getValidInt();

        if (position <= 0 || position > area) {
            return true;
        }

        Result result = _board.move(position, _players[idx]);
        switch (result.getType()) {
            case WIN -> {
                _board.print();
                _logger.info("Player {} wins! ", result.getPlayer().getName());
                return isRestart();
            }
            case DRAW -> {
                _board.print();
                _logger.info("It's a draw!");
                return isRestart();
            }
            case NEXT -> {}
            case SKIP -> _logger.info("Position is already taken. Please try again.");
        }
        return true;

    }

}
