package com.example.game;

import java.util.Scanner;

import com.example.board.Board;
import com.example.board.BoardImpl;
import com.example.player.Player;
import com.example.result.Result;

public class GameImpl implements Game {

    // TODO:
    // make user to trigger another game (+)
    // make game 9x9 (+)
    // add another player (3 players) (+)

    // make board size dynamic (+)
    // make dynamic number of players (+)
    // TEST EVERYTHING!

    private final Board _board;
    private final Scanner _scanner;
    private Player _currentPlayer;

    public GameImpl() {
        _board = new BoardImpl(3);
        _currentPlayer = Player.getFirstPlayer();
        _scanner = new Scanner(System.in);
    }

    @Override
    public void start() {
        while (true) {
            _board.print();

            System.out.printf("Player %s, please make your move (1 to %d): ", _currentPlayer, _board.getSize() * _board.getSize());
            int position = _scanner.nextInt();
            if (position < 0 || position > _board.getSize() * _board.getSize()) {
                continue;
            }

            Result result = _board.move(position, _currentPlayer);
            switch (result.getType()) {
                case WIN -> {
                    System.out.printf("Player %s wins!", result.getPlayer());
                    if (!isRestart()) return;
                }
                case DRAW -> {
                    System.out.println("It's a draw!");
                    if (!isRestart()) return;
                }
                case PROCEED -> _currentPlayer = _currentPlayer.getNextPlayer();
                case SKIP -> System.out.println("That position is already taken. Please try again.");
            }
        }
    }

    private boolean isRestart() {
        System.out.println("Do you want to restart? (y/n): ");
        String answer = _scanner.next();

        if (answer.equalsIgnoreCase("y")) {
            _board.clear();
            _currentPlayer = Player.getFirstPlayer();
            return true;
        } else if (answer.equalsIgnoreCase("n")) {
            return false;
        } else {
            System.out.println("Invalid input. Please type 'y' for yes or 'n' for no.");
            return isRestart();
        }
    }

}
