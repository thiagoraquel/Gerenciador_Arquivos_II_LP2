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
        QUITTING
    }

    // Operações da Biblioteca (diretório raiz)
    private enum e_op_library {
        OPEN_DIRECTORY,
        SEARCH_FILE,
        DELETE
    }

    // Operações de Arquivos
    private enum e_op_file {
        CREATE,
        DELETE,
        EDIT,
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
    String Current_Subdir;                        // Atual subdiretório

    int entrada;                                // Entrada do terminal
    public static final Scanner scanner = new Scanner(System.in); // Scanner do terminal

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
        // config.printLibraries();

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
            // Ou seja, não deve tratar nada aqui (por enquanto)
        }

        // Verificar se os nomes em ValidLibraries.txt são válidos (existem e são diretórios)
        config.removeInvalidLibraries();

        // Adicionar os paths das bibliotecas no ValidLibraries.txt ao vetor de bibliotecas
        List<String> validLibPaths = config.getDirectoriesNames();
        for (String libPath : validLibPaths) {
            Library lib = new Library(libPath, scanner);
            libraries.add(lib);
        }
    }

    public void process_event() {
        if (state == e_states.STARTING) {
            // Pede para o usuário informar o nome do repositório que é a biblioteca raiz
        } else if (state == e_states.QUITTING) {
            end_loop = true;
        }
    }

    public void update() {
        if (state == e_states.STARTING) {

        } else if (state == e_states.LIBRARY) {
            if (op_library == e_op_library.OPEN_DIRECTORY) {
                state = e_states.DIRECTORY;

            //} else if (op_library == e_op_library.DELETE) {
                //state = e_states.QUITTING;
            } else {
                    // Exibe mensagem de erro e não altera o estado
            }
        } else if (state == e_states.DIRECTORY) {
            // Verifica se a operação de OP_DIRECTORY é válida
            if (op_file == e_op_file.CREATE) {
                // chama método de DIRECTORY

            } else if (op_file == e_op_file.BACK_TO_LIBRARY) {
                state = e_states.LIBRARY;
            } else {
                    // Exibe mensagem de erro e não altera o estado
            }
        } else if (state == e_states.QUITTING) {
            
        }
    }

    /**
     * Renderiza as autualizações do sistema para o usuário
    */
    public void render() {
        if (state == e_states.STARTING) {
            config.printLibraries();

            System.out.println("Escolha uma opção:");
            System.out.println("1. Acessar biblioteca existente");
            System.out.println("2. Criar nova biblioteca");
            System.out.print("Opção: ");

            entrada = scanner.nextInt();

            switch (entrada) {
                case 1:
                    System.out.println("Você escolheu acessar biblioteca existente.");
                    System.out.println("Escolha uma das bibliotecas existentes:");

                    for (int i = 0; i < libraries.size(); i++) {
                        Library lib = libraries.get(i);
                        System.out.println(i + " - " + lib.getPath()); // ou lib.toString(), se você sobrescreveu
                    }

                    System.out.print("Digite o número da biblioteca: ");
                    int indice = scanner.nextInt();
                    scanner.nextLine(); // consome o \n

                    if (indice >= 0 && indice < libraries.size()) {
                        library = libraries.get(indice);
                        System.out.println("Biblioteca selecionada: " + library.getPath());
                        // Continue o programa com a biblioteca escolhida
                    } else {
                        System.out.println("Índice inválido.");
                    }

                    state = e_states.LIBRARY;
                    break;
                case 2:
                    System.out.println("Você escolheu criar nova biblioteca.\n");
                    state = e_states.STARTING;
                    break;
                default:
                    System.out.println("Opção inválida.\n");
            }
        } else if (state == e_states.LIBRARY) {
            library = libraries.firstElement();
            System.out.println("Path da biblioteca:");
            System.out.println(library.getPath());

            System.out.println("Paths dos subdiretórios:");
            for (String dirPath : library.getDirectoriesPaths()) {
                System.out.println("- " + dirPath);
            }

            System.out.println("Escolha uma opção:");
            System.out.println("1. Acessar subdiretório");
            System.out.println("2. Buscar Arquivos");
            System.out.println("3. Trocar Biblioteca");
            System.out.println("4. Deletar Biblioteca");
            System.out.println("5. Sair");
            System.out.print("Opção: ");

            entrada = scanner.nextInt();

            switch (entrada) {
                case 1:
                    System.out.println("Você escolheu acessar subdiretório");
                    state = e_states.DIRECTORY;

                    System.out.print("Escolha qual subdiretório você quer acessar:: ");
                    System.out.print("1. Livros");
                    System.out.print("2. NotasDeAulas");
                    System.out.print("3. Slides");

                    System.out.print("Digite o tipo de entrada (Livros, NotasDeAulas ou Slides): ");
                    //String tipoEntrada = scanner.nextLine();
                    break;
                case 2:
                    System.out.println("Você escolheu buscar arquivos");
                    state = e_states.LIBRARY;
                    break;
                case 3:
                    System.out.println("Você escolheu trocar biblioteca");
                    state = e_states.STARTING;
                    break;
                case 4:
                    System.out.println("Você escolheu deletar biblioteca");
                    op_library = e_op_library.DELETE;
                    break;
                case 5:
                    System.out.println("Você escolheu sair do programa.");
                    state = e_states.QUITTING;
                    break;
                default:
                    System.out.println("Opção inválida.\n");
            }

        } else if (state == e_states.DIRECTORY) {
            System.out.println("Escolha uma opção:");
            System.out.println("1. Adicionar arquivos");
            System.out.println("2. Editar arquivo");
            System.out.println("3. Deletar arquivo");
            System.out.println("4. Voltar para biblioteca");
            System.out.print("Opção: ");

            entrada = scanner.nextInt();

            switch (entrada) {
                case 1:
                    System.out.println("Você escolheu adicionar arquivos");

                    System.out.print("Digite o caminho do arquivo PDF (ex: pdfs/MeuSlide.pdf): ");
                    scanner.nextLine(); // consumir a quebra de linha pendente
                    String caminhoPdf = scanner.nextLine();

                    System.out.print("Digite o tipo de entrada (Livros, NotasDeAulas ou Slides): ");
                    String tipoEntrada = scanner.nextLine();

                    if (!tipoEntrada.equals("Livros") &&
                        !tipoEntrada.equals("NotasDeAulas") &&
                        !tipoEntrada.equals("Slides")) {
                    System.out.println("Tipo inválido. Use exatamente: Livros, NotasDeAulas ou Slides.");
                    break;
                    }

                    library.addEntry(caminhoPdf, tipoEntrada);
                    break;
                case 2:
                    System.out.println("Você escolheu editar arquivos");
                    break;
                case 3:
                    System.out.println("Você escolheu deletar arquivo");
                    break;
                case 4:
                    System.out.println("Você escolheu voltar para biblioteca");
                    state = e_states.LIBRARY;
                    break;
                default:
                    System.out.println("Opção inválida.\n");
            }
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
