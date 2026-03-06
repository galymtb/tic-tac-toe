package com.example.game;

import com.example.board.Board;
import com.example.board.BoardImpl;
import com.example.board.BoardMark;
import com.example.player.Player;
import com.example.player.PlayerImpl;
import com.example.player.PlayerType;
import com.example.result.Result;
import com.example.result.ResultType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameImpl implements Game {

    private static final Logger _logger = LoggerFactory.getLogger(GameImpl.class);

    private Player _firstPlayer;
    private Player _secondPlayer;
    private Player _currentPlayer;

    private Board _board;

    private boolean _isInitialized;
    private boolean _isFinished;

    private final GameState _gameState;

    public GameImpl() {
        _isInitialized = false;
        _isFinished = false;
        _gameState = new GameState("Game is not started yet", ResultType.ERROR, null, null, false, false);
    }

    private GameState setState(String msg, ResultType resultType, Character currentPlayer, String boardStr) {
        _gameState.setMessage(msg);
        _gameState.setResult(resultType);
        _gameState.setCurrentPlayer(currentPlayer);
        _gameState.setBoard(boardStr);
        _gameState.setInitialized(_isInitialized);
        _gameState.setFinished(_isFinished);
        return _gameState;
    }

    private GameState errorState(String msg, Character currentPlayer, String boardStr) {
        _logger.warn(msg);
        return new GameState(msg, ResultType.ERROR, currentPlayer, boardStr,  _isInitialized, _isFinished);
    }

    private GameState infoState(String msg, ResultType resultType, Character currentPlayer, String boardStr) {
        _logger.info(msg);
        _logger.info(boardStr);
        return setState(msg, resultType, currentPlayer, boardStr);
    }

    private void setupPlayers(BoardMark firstMark, PlayerType firstType) {
        _firstPlayer = new PlayerImpl(firstMark, firstType);
        BoardMark secondMark = (firstMark == BoardMark.X) ? BoardMark.O : BoardMark.X;
        PlayerType secondType = (firstType == PlayerType.REST) ? PlayerType.WS : PlayerType.REST;
        _secondPlayer = new PlayerImpl(secondMark, secondType);
        _currentPlayer = _firstPlayer;
    }

    @Override
    public synchronized GameState init(int boardSize, BoardMark firstPlayerMark, PlayerType firstPlayerType) {
        if (boardSize <= 0) {
            return errorState("Board size must be greater than zero!", null, null);
        }
        if (_isInitialized) {
            String msg = "Game is already initialized!";
            return errorState(msg, _currentPlayer.getName(), _board.boardToString());
        }

        _isInitialized = true;
        setupPlayers(firstPlayerMark, firstPlayerType);
        _board = new BoardImpl(boardSize);

        String msg = "Game is initialized!";
        return infoState(msg, ResultType.NEXT, _currentPlayer.getName(), _board.boardToString());
    }

    @Override
    public synchronized GameState step(int position, PlayerType playerType) {
        if (!_isInitialized) {
            return errorState("Game is not initialized!", null, null);
        }
        if (_isFinished) {
            return errorState("Game is already finished!", null, _board.boardToString());
        }
        if (_currentPlayer.getType() != playerType) {
            String msg = "Not your turn! Wait for " + _currentPlayer.getName() + " to move!";
            return errorState(msg, _currentPlayer.getName(), _board.boardToString());
        }
        if (position <= 0 || position > _board.getArea()) {
            return errorState("Wrong input. Try again!", _currentPlayer.getName(), _board.boardToString());
        }

        Result result = _board.move(position, _currentPlayer);
        String boardStr = _board.boardToString();

        switch (result.getType()) {
            case WIN -> {
                _isFinished = true;
                String winMsg = String.format("Player %s won!", _currentPlayer.getName());
                return infoState(winMsg, result.getType(), null, boardStr);
            }
            case DRAW -> {
                _isFinished = true;
                String drawMsg = "Draw!";
                return infoState(drawMsg, result.getType(), null, boardStr);
            }
            case NEXT -> {
                _currentPlayer = (_currentPlayer == _firstPlayer) ? _secondPlayer : _firstPlayer;
                String nextMsg = String.format("Player %s, please make a step (1 to %d): ", _currentPlayer.getName(), _board.getArea());
                return infoState(nextMsg, result.getType(), _currentPlayer.getName(), boardStr);
            }
            case SKIP -> {
                String skipMsg = "Position already taken. Try again.";
                return infoState(skipMsg, result.getType(), _currentPlayer.getName(), boardStr);
            }
            default -> {
                String unknownMsg = "Unknown result";
                return infoState(unknownMsg, result.getType(), _currentPlayer.getName(), boardStr);
            }
        }
    }

    @Override
    public synchronized GameState status() {
        return _gameState;
    }

    @Override
    public synchronized GameState restart(int boardSize, BoardMark boardMark, PlayerType playerType) {
        if (!_isInitialized) {
            return errorState("Game is not initialized!", null, null);
        }
        _isInitialized = false;
        _isFinished = false;
        return init(boardSize, boardMark, playerType);
    }

}
