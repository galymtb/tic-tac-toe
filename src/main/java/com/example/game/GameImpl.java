package com.example.game;

import java.util.Scanner;

import com.example.board.Board;
import com.example.board.BoardImpl;
import com.example.player.Player;
import com.example.player.PlayerImpl;
import com.example.result.Result;

public class GameImpl implements Game {

    // TODO:
    // make user to trigger another game (+)
    // make game 9x9 (+)
    // add another player (3 players) (+)

    // make board size dynamic (+)
    // make dynamic number of players (+)
    // TEST EVERYTHING!

    private final Player[] _players;
    private final Scanner _scanner;
    private final Board _board;

    private static final String YES = "y";
    private static final String NO = "n";

    public GameImpl() {
        _scanner = new Scanner(System.in);

        System.out.print("Number of players: ");
        _players = new PlayerImpl[_scanner.nextInt()];
        fill();

        System.out.print("Board size: ");
        _board = new BoardImpl(_scanner.nextInt());
    }

    private void fill() {
        for (int i = 0; i < _players.length; i++) {
            _players[i] = new PlayerImpl((char) (i + 'A'));
        }
    }

    private boolean isRestart() {
        System.out.print("Do you want to restart? (y/n): ");
        String answer = _scanner.next();

        switch (answer) {
            case YES -> {
                _board.clear();
                return true;
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
            _board.print();

            System.out.printf("Player %s, please make your move (1 to %d): ", _players[_board.getPositionsTaken() % _players.length].getName(), _board.getSize() * _board.getSize());
            int position = _scanner.nextInt();
            if (position < 0 || position > _board.getSize() * _board.getSize()) continue;

            Result result = _board.move(position, _players[_board.getPositionsTaken() % _players.length]);
            switch (result.getType()) {
                case WIN -> {
                    _board.print();
                    System.out.printf("Player %s wins! ", result.getPlayer().getName());
                    if (!isRestart()) return;
                }
                case DRAW -> {
                    _board.print();
                    System.out.print("It's a draw! ");
                    if (!isRestart()) return;
                }
                case NEXT -> {}
                case SKIP -> System.out.println("Position is already taken. Please try again.");
            }
        }
    }

}
