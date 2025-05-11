package app;

import core.GameLoop;

public class Main {
    public static void main(String[] args) {
        GameLoop loop = new GameLoop();
        
        if (args.length == 0) { // dois casos aqui: ou é a primeira execução (config vazio) e não foi dado path pra criar um diretório
                                // Ou é depois da primeira execução e usuário quer acessar a última biblioteca
            loop.initialize();
        } else if (args.length > 0) { //casos: primeira execução e path para criar um diretório (config vazio)
            String path = args[0];
            loop.initialize(path); 
            System.out.println("Path given by terminal: " + path);
        } else {
            // TODO: Inicialização sem argumentos
            // Futuramente, pode carregar o path salvo anteriormente.
            // Por enquanto, exige argumento via terminal para definir o diretório da biblioteca.
            System.out.println("Path wasn't given by terminal, using path at Config.txt.");
            return;
        }

        // Comentado para testes
        /*while (loop.is_over()) {
            loop.process_event();
            loop.update();
            loop.render();
        }*/
        loop.close_scanner();
    }
}
