package app;

import core.GameLoop;

public class Main {
    public static void main(String[] args) {
        GameLoop loop = new GameLoop();
        
        if (args.length == 0) {
            loop.initialize();
        } else {
            System.out.println(
            "O Programa não aceita outros argumentos na linha de comando além da execução do programa\n" +
            "Execução do programa esperada: java -cp build app.Main"
            );
            return;
        }

        while (!loop.is_over()) {
            loop.process_event();
            loop.update();
            loop.render();
        }
        loop.close_scanner();
    }
}
