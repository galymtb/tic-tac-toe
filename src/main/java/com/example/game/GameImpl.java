package com.example.game;

import com.example.board.Board;
import com.example.board.BoardImpl;
import com.example.input.InputSource;
import com.example.player.Player;
import com.example.player.PlayerImpl;
import com.example.result.Result;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class GameImpl implements Game {

    // TODO:
    // make user to trigger another game (+)
    // make game 9x9 (+)
    // add another player (3 players) (+)

    // make board size dynamic (+)
    // make dynamic number of players (+)
    // dependency injection
    // TEST EVERYTHING!

    private Player[] _players;
    private Board _board;
    private final InputSource _inputSource;

    private static final String YES = "y";
    private static final String NO = "n";

    @Inject
    public GameImpl(InputSource inputSource) {
        _inputSource = inputSource;
    }

    @Override
    public boolean init() {
        return initBoard() && initPlayers();
    }

    private boolean initPlayers() {
        System.out.print("Number of players: ");
        int playersNumber;
        try {
            playersNumber = _inputSource.nextInt();
        } catch (Exception e) {
            // retry logic?
            System.out.println("Could not initialize players");
            return false;
        }

        _players = new PlayerImpl[playersNumber];
        fill();
        return true;
    }

    private boolean initBoard() {
        System.out.print("Board size: " );
        int boardSize;
        try {
            boardSize = _inputSource.nextInt();
        } catch (Exception e) {
            // retry logic?
            System.out.println("Could not initialize board");
            return false;
        }

        _board = new BoardImpl(boardSize);
        return true;
    }

    private void fill() {
        for (int i = 0; i < _players.length; i++) {
            _players[i] = new PlayerImpl((char) (i + 'A'));
        }
    }

    private boolean isRestart() {
        System.out.print("Do you want to restart? (y/n): ");
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
                System.out.println("Invalid input. Please type 'y' for yes or 'n' for no.");
                return isRestart();
            }
        }
    }

    @Override
    public void start() {
        while (true) {
            if (!iterate()) {
                break;
            }
        }
    }

    boolean iterate() {

        int idx = _board.getPositionsTaken() % _players.length;
        int area = _board.getSize() * _board.getSize();

        _board.print();

        System.out.printf("Player %s, please make your move (1 to %d): ", _players[idx].getName(), area);

        int position;
        try {
            position = _inputSource.nextInt();
        } catch (Exception e) {
            System.out.println("Input digit, idiot!");
            return true;
        }

        if (position <= 0 || position > area) {
            return true;
        }

        Result result = _board.move(position, _players[idx]);
        switch (result.getType()) {
            case WIN -> {
                _board.print();
                System.out.printf("Player %s wins! ", result.getPlayer().getName());
                return isRestart();
            }
            case DRAW -> {
                _board.print();
                System.out.print("It's a draw! ");
                return isRestart();
            }
            case NEXT -> {}
            case SKIP -> System.out.println("Position is already taken. Please try again.");
        }
        return true;

    }

}
