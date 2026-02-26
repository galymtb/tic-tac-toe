package com.example.game;

import com.example.result.ResultType;

public class GameState {

    String _message;
    ResultType _result;
    Character _currentPlayer;
    String _board;
    boolean _isInitialized;
    boolean _isFinished;

    public GameState(String message, ResultType result, Character currentPlayer, String board, boolean isInitialized, boolean isFinished) {
        _message = message;
        _result = result;
        _currentPlayer = currentPlayer;
        _board = board;
        _isInitialized = isInitialized;
        _isFinished = isFinished;
    }

    public String getMessage() {
        return _message;
    }

    public void setMessage(String _message) {
        this._message = _message;
    }

    public ResultType getResult() {
        return _result;
    }

    public void setResult(ResultType _result) {
        this._result = _result;
    }

    public Character getCurrentPlayer() {
        return _currentPlayer;
    }

    public void setCurrentPlayer(Character _currentPlayer) {
        this._currentPlayer = _currentPlayer;
    }

    public String getBoard() {
        return _board;
    }

    public void setBoard(String _board) {
        this._board = _board;
    }

    public boolean isInitialized() {
        return _isInitialized;
    }

    public void setInitialized(boolean _isInitialized) {
        this._isInitialized = _isInitialized;
    }

    public boolean isFinished() {
        return _isFinished;
    }

    public void setFinished(boolean _isFinished) {
        this._isFinished = _isFinished;
    }

}
