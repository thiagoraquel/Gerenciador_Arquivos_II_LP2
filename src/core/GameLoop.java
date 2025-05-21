package core;

import core.Library;
import core.Config;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Vector;
import java.io.File;  

/**
 * Implementa a arquitetura Game Loop.
 * 
 * @authors Júlia Guilhermino e Marcos Fontes.
 * @version 1.0
 */
public class GameLoop  {

    // Estados do loop
    private enum e_states{
        STARTING,
        LIBRARY,
        DIRECTORY,
        DELETE,
        QUITTING
    }

    // Operações da Biblioteca (diretório raiz)
    private enum e_op_library {
        OPEN_DIRECTORY,
        SEARCH_FILE,
        ORGANIZATION,
        DELETE,
        QUIT
    }

    // Operações de Arquivos
    private enum e_op_file {
        CREATE,
        DELETE,
        EDIT,
        MOVE, 
        BACK_TO_LIBRARY
    }

    // Atributos 
    e_states state;                             // controle dos estados do loop
    e_op_library op_library;                    // controla as operações de manipulação da biblioteca
    e_op_file op_file;                          // controla as operações de manipulação de arquivos
    boolean end_loop = false;                   // controle do loop
    Vector<Library> libraries = new Vector<>(); // vetor com todas as bibliotecas raiz reconhecidas pelo programa
    Library library;                            // biblioteca que esta sendo acessada
    Config config = new Config();               // configuração de inicialização

    private static final Scanner scanner = new Scanner(System.in); // Entrada do terminal

    // fechar o scanner
    // obs: só fechar quando o programa terminar
    public void close_scanner() {
        scanner.close();
    }
    /**
     * Construtor
    */
    public GameLoop() {
        state = e_states.STARTING;
        libraries.clear();
    }

    public void initialize(){
        // Lê nomes dos diretórios no file
        List<String> libs_on_file = config.getDirectoriesNames();
        // Ver quais bibliotecas estão no arquivo
        config.printLibraries();

        // Se o ValidLibraries.txt está vazio, assume-se que é a primeira inicialização
        // Ou seja, ValidLibraries.txt sempre tem ao menos uma biblioteca, exceto na primeira init
        if (libs_on_file.isEmpty()) {
            System.out.print(
            "Não existe nenhuma biblioteca válida existente\n" + 
            "Digite um nome válido para criar uma biblioteca: ");
            
            String userInput = scanner.nextLine();
            File dir = new File(userInput); 
            
            // Se diretório dado por usuário não existe
            if (!dir.exists()) {
                boolean success = dir.mkdirs();
                // Cria uma nova biblioteca
                if (success) {
                    config.addLibrary(userInput);
                } else {
                    System.out.println("Falha ao criar o diretório.");
                }
            } 

            // Se nome de diretório dado por user existe
            else { 
                // TODO: Nome de primeira biblioteca já existe
                // Perguntar por nome da biblioteca até que user dê um nome que ainda não foi usado
                System.out.println("Diretório já existe, crie um diretório com um nome diferente: ");   
               
            }
        } else {
            // Já existem nomes de bibliotecas no ValidLibraries.txt
            // Espera-se que sejam nomes de diretórios válidos
            // O programa apenas faz bibliotecas válidas no ValidLibraries
            // Ou seja, não deve tratar nada aqui
        }

        // Verificar se os nomes em ValidLibraries.txt são válidos (existem e são diretórios)
        config.removeInvalidLibraries();

        // Adicionar os paths das bibliotecas no ValidLibraries.txt ao vetor de bibliotecas
        List<String> validLibPaths = config.getDirectoriesNames();
        for (String lib : validLibPaths) {
            // File dir = new File(lib);
            //if (dir.exists() && dir.isDirectory()) {
                // libraries.add(new Library(lib));
            //}
        }
    }

    /**
     * Recebe as entradas do usuário
    */
    public void process_event() {
        if (state == e_states.STARTING) {
            // Pede para o usuário informar o nome do repositório que é a biblioteca raiz
        } else if (state == e_states.LIBRARY) {
            // Solicita operação de E_OP_LIBRARY e armazena ela em OP_LIBRARY
        } else if (state == e_states.DIRECTORY) {
            // Solicita operação de E_OP_FILE e armazena ela em OP_FILE
        } else if (state == e_states.DELETE) {
            
        } else if (state == e_states.QUITTING) {
            
        }
    }

    /**
     * Atualiza o estado do sistema
    */
    public void update() {
        if (state == e_states.STARTING) {
            // Verifica se o repositório informado é válido: busca no vector LIBRARY
            // Se existir muda o estado para STARTING
            // Senão não altera o estado
        } else if (state == e_states.LIBRARY) {
            // Verifica se a operação de OP_LIBRARY é válida
            if (op_library == e_op_library.OPEN_DIRECTORY) {
                state = e_states.DIRECTORY;

            } else if (op_library == e_op_library.SEARCH_FILE) {
                // chama método de LIBRARY

            } else if (op_library == e_op_library.ORGANIZATION) {
                // chama método de LIBRARY

            } else if (op_library == e_op_library.DELETE) {
                // exclui a biblioteca atual da lista de bibliotecas

            } else if (op_library == e_op_library.QUIT) {
                state = e_states.QUITTING;
            } else {
                    // Exibe mensagem de erro e não altera o estado
            }
        } else if (state == e_states.DIRECTORY) {
            // Verifica se a operação de OP_DIRECTORY é válida
            if (op_file == e_op_file.CREATE) {
                // chama método de DIRECTORY

            } else if (op_file == e_op_file.EDIT) {
                // chama método de DIRECTORY

            } else if (op_file == e_op_file.MOVE) {
                // chama método de LIBRARY

            } else if (op_file == e_op_file.DELETE) {
                // chama método de DIRECTORY

            } else if (op_file == e_op_file.BACK_TO_LIBRARY) {
                state = e_states.LIBRARY;
            } else {
                    // Exibe mensagem de erro e não altera o estado
            }
        } else if (state == e_states.DELETE) {
            
        } else if (state == e_states.QUITTING) {
            
        }
    }

    /**
     * Renderiza as autualizações do sistema para o usuário
    */
    public void render() {
        if (state == e_states.STARTING) {
            // Se o estado ainda for STARTING significa que o repositório informado foi inválido
            // Exibe mensagem de erro
        } else if (state == e_states.LIBRARY) {
            // Exibe todos os diretórios da biblioteca
            /* Exibe menu de opções:
                1) Entrar em um subdiretório
                2) Buscar arquivos
                3) Alterar organização biblioteca
                4) Deletar
                5) Sair
            */
        } else if (state == e_states.DIRECTORY) {
            // Exibe todos os arquivos de um diretório
            /* Exibe o menu de opções:
                1) Adicionar arquivo
                2) Deletar arquivo
                3) Editar informações do arquivo
                4) Mover arquivo 
                5) Voltar para diretorio raiz
            */
        } else if (state == e_states.DELETE) {
            
        } else if (state == e_states.QUITTING) {
            
        }
    }

    /**
     * Controle do loop
    * 
    * @return variável booleana
    */
    public boolean is_over() {
        return end_loop;
    }
}
