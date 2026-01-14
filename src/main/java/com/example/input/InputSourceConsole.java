package com.example.input;

import java.util.Scanner;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class InputSourceConsole implements InputSource {

    private final Scanner _scanner;

    @Inject
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
