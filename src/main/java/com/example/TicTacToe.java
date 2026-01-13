package com.example;

import java.util.Scanner;

import com.example.game.Game;
import com.example.game.GameImpl;
import com.example.input.InputSourceConsole;
import com.example.input.InputSource;

public class TicTacToe {

    public static void main(String[] args) {
        InputSource inputSource = new InputSourceConsole(new Scanner(System.in));
        Game game = new GameImpl(inputSource);
        if (game.init()) {
            game.start();
        }
    }

}