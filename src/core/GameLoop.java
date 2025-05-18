package core;

import core.Library;
import core.Config;

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
    private static final Scanner scanner = new Scanner(System.in);

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
    }

    /**
     * Controle do loop
    * 
    * @return variável booleana
    */
    public boolean is_over() {
        return end_loop;
    }

    /**
     * Inicialização sem argumentos no terminal
     */
    public void initialize(){
        String path = config.readConfig(); // Lê texto (path) no config
        if (path == null){  
            
            // Primeira inicialização/diretório inexiste em Config.txt
            // Se o caminho está vazio, assume-se que é a primeira inicialização
            // E pede-se um path
            System.out.println("Config.txt is empty");
            System.out.print("Digite um path válido para criar um diretório: ");
            String userInput = scanner.nextLine();

            File dir = new File(userInput); 
            if (!dir.exists()) {
                boolean success = dir.mkdirs();
                if (success) {
                    System.out.println("Diretório criado com sucesso em: " + userInput);
                    // Atualiza o Config.txt com o novo path
                    config.saveConfig(userInput);
                } else {
                    System.out.println("Falha ao criar o diretório.");
                }
            } else {
                System.out.println("Diretório já existe: " + userInput);   
                config.saveConfig(userInput);
            }
        } else {    // Existe um path em Config.txt
            File dir = new File(path);
            if (dir.exists() && dir.isDirectory()) {
                System.out.println("Path at Config.txt: " + path);
            } else {
                System.out.println("O diretório salvo em Config.txt não existe mais.");
                System.out.print("Digite um path válido para criar um novo diretório: ");
                String userInput = scanner.nextLine();
          
                // obs: este código é o mesmo código do primeiro if da função initialize()
                // Redundância
                File newDir = new File(userInput);
                if (!newDir.exists()) {
                    boolean success = newDir.mkdirs();
                    if (success) {
                        System.out.println("Diretório criado com sucesso em: " + userInput);
                        config.saveConfig(userInput);
                    } else {
                        System.out.println("Falha ao criar o diretório.");
                    }
                } else {
                    System.out.println("Diretório já existe: " + userInput);
                    config.saveConfig(userInput);
                }
            }
        }  
    }

    /**
     * Carrega os diretórios raiz criados (biblioteca)
    */
    public void initialize(String string){
        // Carrega todos os diretórios salvos na pasta DATA no vector LIBRARY
        String path = config.readConfig();
        if (path == null){  // Primeira inicialização/diretório inexiste em Config.txt
            System.out.println("Config.txt is empty");  
        } else if (path != null){
            System.out.println("Path at Config.txt: " + path);
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
}
