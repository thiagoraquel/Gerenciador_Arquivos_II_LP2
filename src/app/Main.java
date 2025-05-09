package app;

import core.GameLoop;

public class Main {
    public static void main(String[] args) {
        GameLoop loop = new GameLoop();
        loop.initialize();
        while (loop.is_over()) {
            loop.process_event();
            loop.update();
            loop.render();
        }
    }
}
