package com.example.input;

import java.util.Scanner;

public class InputSourceConsole implements InputSource {

    private final Scanner _scanner;

    public InputSourceConsole(Scanner scanner) {
        _scanner = scanner;
    }

    @Override
    public String next() {
        return _scanner.next();
    }

    @Override
    public int nextInt() {
        return _scanner.nextInt();
    }

}
