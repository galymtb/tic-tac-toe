package com.example.board;

import com.example.player.Player;
import com.example.result.Result;
import com.example.result.ResultImpl;
import com.example.result.ResultType;

public class BoardImpl implements Board {

    protected final char[][] _board;
    protected int _positionsTaken;
    protected int _size;

    public BoardImpl(int size) {
        _board = new char[size][size];
        _positionsTaken = 0;
        _size = size;
        fill();
    }

    private void fill() {
        for (int r = 0; r < _size; r++) {
            for (int c = 0; c < _size; c++) {
                _board[r][c] = ' ';
            }
        }
    }

    private boolean isTaken(int row, int col) {
        return (_board[row][col] != ' ');
    }

    private boolean isWin(Player player) {

        for (int i = 0; i < _size; i++) {
            boolean rowWin = true;
            boolean colWin = true;

            for (int j = 0; j < _size; j++) {
                if (!(_board[i][j] == player.getName())) {
                    rowWin = false;
                }
                if (!(_board[j][i] == player.getName())) {
                    colWin = false;
                }
            }

            if (rowWin || colWin) {
                return true;
            }
        }

        boolean leftDiagonalWin = true;
        boolean rightDiagonalWin = true;

        for (int i = 0; i < _size; i++) {
            if (!(_board[i][i] == player.getName())) {
                leftDiagonalWin = false;
            }
            if (!(_board[i][_size - 1 - i] == player.getName())) {
                rightDiagonalWin = false;
            }
        }

        return leftDiagonalWin || rightDiagonalWin;

    }

    private boolean isDraw() {
        return _positionsTaken == _size * _size;
    }

    @Override
    public void print() {
        String line = "-".repeat(_size * 4 + 1);
        System.out.println(line);
        for (int r = 0; r < _size; r++) {
            for (int c = 0; c < _size; c++) {
                System.out.print("| " + _board[r][c] + " ");
            }
            System.out.println("|\n" + line);
        }
    }

    @Override
    public void clear() {
        _positionsTaken = 0;
        fill();
    }

    @Override
    public Result move(int position, Player player) {
        for (int r = 0; r < _size; r++) {
            for (int c = 0; c < _size; c++) {
                if ((r * _size) + c + 1 == position) {
                    if (isTaken(r, c)) {
                        return new ResultImpl(ResultType.SKIP, null);
                    }
                    _board[r][c] = player.getName();
                    if (isWin(player)) {
                        return new ResultImpl(ResultType.WIN, player);
                    }
                    break;
                }
            }
        }
        _positionsTaken++;
        if (isDraw()) {
            return new ResultImpl(ResultType.DRAW, null);
        }
        return new ResultImpl(ResultType.NEXT, null);
    }

    @Override
    public int getSize() {
        return _size;
    }

    @Override
    public int getPositionsTaken() {
        return _positionsTaken;
    }

}
