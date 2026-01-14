package com.example.game;

import java.util.LinkedList;
import java.util.Queue;

import com.example.input.InputSource;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GameImplTest {

    private final TestInputSource _inputSource;
    private final GameImpl _game;

    GameImplTest() {
        _inputSource = new TestInputSource();
        _game = new GameImpl(_inputSource);
    }

    @Test
    public void testInitialization() {
        _inputSource.addNextIntInput(3);
        _inputSource.addNextIntInput(3);
        _game.init();

        assertNotNull(_game);
    }

    @Test
    public void testIteration() {
        // Initialize
        _inputSource.addNextIntInput(3);
        _inputSource.addNextIntInput(3);
        _game.init();

        // Check position is between 1 and 9
        _inputSource.addNextIntInput(0);
        assertTrue(_game.iterate());
        _inputSource.addNextIntInput(10);
        assertTrue(_game.iterate());

        // Make move
        _inputSource.addNextIntInput(1);
        assertTrue(_game.iterate());

        // Input non-digit
        _inputSource.addNextStringInput("1");
        assertTrue(_game.iterate());

        // Check for restart
        _inputSource.addNextIntInput(2);
        _game.iterate();
        _inputSource.addNextIntInput(3);
        _game.iterate();
        _inputSource.addNextIntInput(4);
        _game.iterate();
        _inputSource.addNextIntInput(5);
        _game.iterate();
        _inputSource.addNextIntInput(6);
        _game.iterate();
        _inputSource.addNextIntInput(7);
        _inputSource.addNextStringInput("1");
        _inputSource.addNextStringInput("y");
        _inputSource.addNextIntInput(3);
        _inputSource.addNextIntInput(3);
        assertTrue(_game.iterate());

        // Check for shutdown
        _inputSource.addNextIntInput(1);
        _game.iterate();
        _inputSource.addNextIntInput(1);
        _game.iterate();
        _inputSource.addNextIntInput(2);
        _game.iterate();
        _inputSource.addNextIntInput(3);
        _game.iterate();
        _inputSource.addNextIntInput(4);
        _game.iterate();
        _inputSource.addNextIntInput(5);
        _game.iterate();
        _inputSource.addNextIntInput(6);
        _game.iterate();
        _inputSource.addNextIntInput(9);
        _game.iterate();
        _inputSource.addNextIntInput(7);
        _game.iterate();
        _inputSource.addNextIntInput(8);
        _inputSource.addNextStringInput("n");
        assertFalse(_game.iterate());
    }

    static class TestInputSource implements InputSource {

        private final Queue<IntInput> _nextIntInputs = new LinkedList<>();
        private final Queue<StringInput> _nextStringInputs = new LinkedList<>();


        @Override
        public String next() {
            return _nextStringInputs.poll().getValue();
        }

        @Override
        public int nextInt() {
            return _nextIntInputs.poll().getValue();
        }

        void addNextStringInput(String nextStringInput) {
            _nextStringInputs.add(new StringInput(nextStringInput));
        }

        void addNextIntInput(int nextIntInput) {
            _nextIntInputs.add(new IntInput(nextIntInput));
        }

        void addNextThrowingIntInput() {
            _nextIntInputs.add(new ThrowingIntInput());
        }

        void addNextThrowingStringInput() {
            _nextStringInputs.add(new ThrowingStringInput());
        }

        static class ThrowingIntInput extends IntInput {

            ThrowingIntInput() {
                super(0);
            }

            @Override
            public Integer getValue() {
                throw new RuntimeException("Test exception!");
            }
        }


        static class IntInput implements Input<Integer> {

            private final int _input;

            IntInput(int input) {
                _input = input;
            }

            @Override
            public Integer getValue() {
                return _input;
            }

        }

        static class ThrowingStringInput extends StringInput {

            ThrowingStringInput() {
                super(null);
            }

            @Override
            public String getValue() {
                throw new RuntimeException("Test exception!");
            }

        }

        static class StringInput implements Input<String> {

            private final String _input;

            StringInput(String input) {
                _input = input;
            }

            @Override
            public String getValue() {
                return _input;
            }
        }

        interface Input<T> {

            T getValue();

        }

    }

}
