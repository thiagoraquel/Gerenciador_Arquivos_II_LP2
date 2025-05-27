package core;

import java.util.List;
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

    String str_input;                         // Entrada de string do terminal
    int num_input;                            // Entrada de número do terminal
    public static final Scanner scanner = new Scanner(System.in); // Scanner do terminal

    /**
     * Construtor
    */
    public GameLoop() {
        state = e_states.STARTING;
        libraries.clear();
    }

    public void initialize(){
        List<String> libs_on_file = config.getLibrariesNames();

        // Se o ValidLibraries.txt está vazio, assume-se que é a primeira inicialização
        // Ou seja, ValidLibraries.txt sempre tem ao menos uma biblioteca, exceto na primeira init
        if (libs_on_file.isEmpty()) {
            System.out.print(
            "Não existe nenhuma biblioteca válida existente\n" + 
            "Digite um nome válido para criar uma biblioteca: ");
            
            str_input = scanner.nextLine();
            File dir = new File(str_input); 
            
            // Se diretório dado por usuário não existe
            if (!dir.exists()) {
                boolean success = dir.mkdirs();
                // Cria uma nova biblioteca
                if (success) {
                    config.addLibrary(str_input);
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

            // Dá erro aqui caso as bibliotecas tenham sido deletadas manualmente e o programa comece, pois os nomes da bibliotecas
            // ainda estão no ValidLibraries.txt
        }

        // Verificar se os nomes em ValidLibraries.txt são válidos (existem e são diretórios)
        config.removeInvalidLibraries();

        // Adicionar os paths das bibliotecas no ValidLibraries.txt ao vetor de bibliotecas
        List<String> validLibPaths = config.getLibrariesNames();
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
        switch (state) {
            case STARTING:
                startingState();
                break;
            case LIBRARY:
                libraryState();
                break;
            case DIRECTORY:
                directoryState();
                break;
            case QUITTING:
                break;
        }
    }
      
    private void startingState() {
        System.out.print("\n");
        config.printLibraries();
        System.out.print("\n");
      
        System.out.println("Escolha uma opção:");
        System.out.println("1. Selecionar biblioteca existente");
        System.out.println("2. Criar nova biblioteca");
        System.out.print("Opção: ");
      
        num_input = scanner.nextInt();
      
        switch (num_input) {
            case 1:
                selectLibrary();
                break;
            case 2:
                createLibrary();
                break;
            default:
                System.out.println("Opção inválida.\n");
        }
    }
      
    private void selectLibrary() {
        System.out.println("Você escolheu selecionar biblioteca existente.\n");
        System.out.println("Escolha uma das bibliotecas existentes:");
      
        for (int i = 1; i <= libraries.size(); i++) {
            Library lib = libraries.get(i - 1);
            System.out.println(i + " - " + lib.getPath());
        }
      
        System.out.print("\nDigite o número da biblioteca: ");
        num_input = scanner.nextInt();
      
        if (num_input >= 1 && num_input <= libraries.size()) {
            library = libraries.get(num_input - 1);
            System.out.println("Biblioteca selecionada: " + library.getPath() + "\n");
            state = e_states.LIBRARY;
        } else {
            System.out.println("Índice inválido.");
        }
    }
      
    private void createLibrary() {
        System.out.println("Você escolheu criar nova biblioteca.\n");
        System.out.print("Digite o nome da nova biblioteca (sem espaços): ");
        scanner.nextLine(); // consumir quebra de linha
        str_input = scanner.nextLine();
      
        File newDir = new File(str_input);
        if (newDir.exists()) {
            System.out.println("Já existe uma biblioteca com esse nome.");
        } else {
            if (newDir.mkdirs()) {
                Library newLibrary = new Library(str_input, scanner);
                libraries.add(newLibrary);
                config.addLibrary(str_input);
                System.out.println("Biblioteca criada com sucesso: " + newLibrary.getPath());
            } else {
                System.out.println("Erro ao criar diretório da nova biblioteca.");
            }
        }
    }
      
    private void libraryState() {
        System.out.println("Path da biblioteca:");
        System.out.println(library.getPath() + "\n");
      
        System.out.println("Paths dos subdiretórios:");
        for (String dirPath : library.getDirectoriesPaths()) {
            System.out.println("- " + dirPath);
        }
        System.out.print("\n");
      
        System.out.println("Escolha uma opção:");
        System.out.println("1. Acessar Subdiretório");
        System.out.println("2. Buscar Arquivos");
        System.out.println("3. Trocar Biblioteca");
        System.out.println("4. Deletar Biblioteca");
        System.out.println("5. Sair");
        System.out.print("Opção: ");
      
        num_input = scanner.nextInt();
      
        switch (num_input) {
            case 1:
                System.out.println("Você escolheu acessar subdiretório\n");
                selectSubdir();
                break;
            case 2:
                System.out.println("Você escolheu buscar arquivos\n");
                break;
            case 3:
                System.out.println("Você escolheu trocar biblioteca\n");
                state = e_states.STARTING;
                break;
            case 4:
                System.out.println("Você escolheu deletar biblioteca\n");
                op_library = e_op_library.DELETE;
                break;
            case 5:
                System.out.println("Você escolheu sair do programa\n");
                state = e_states.QUITTING;
                break;
            default:
                System.out.println("Opção inválida\n");
        }
    }
      
    private void selectSubdir() {
        Vector<Directory> dirs = library.getDirectories();
        if (dirs.isEmpty()) {
            System.out.println("Nenhum subdiretório encontrado.");
            return;
        }
      
        System.out.println("Escolha um subdiretório:");
        for (int i = 0; i < dirs.size(); i++) {
            String name = new File(dirs.get(i).getPath()).getName();
            System.out.printf("  %d) %s\n", i + 1, name);
        }
      
        System.out.print("Número da opção: ");
        scanner.nextLine(); // consumir \n do nextInt anterior
        str_input = scanner.nextLine();
      
        if (str_input.isEmpty()) {
            System.out.println("Entrada vazia. Digite um número válido.");
            return;
        }
      
        try {
            num_input = Integer.parseInt(str_input);
        } catch (NumberFormatException e) {
            System.out.println("Formato inválido. Digite um número.");
            return;
        }
      
        if (num_input >= 1 && num_input <= dirs.size()) {
            Directory currentSubdir = dirs.get(num_input - 1);
            library.setCurrentSubdir(new File(currentSubdir.getPath()).getName());
            state = e_states.DIRECTORY;
        } else {
            System.out.println("Opção inválida.");
        }
    }
      
    private void directoryState() {
        System.out.println("Escolha uma opção:");
        System.out.println("1. Adicionar arquivos");
        System.out.println("2. Editar arquivo");
        System.out.println("3. Deletar arquivo");
        System.out.println("4. Voltar para biblioteca");
        System.out.print("Opção: ");
      
        num_input = scanner.nextInt();
        scanner.nextLine(); // consumir quebra de linha
      
        switch (num_input) {
            case 1:
                System.out.println("Você escolheu adicionar arquivos\n");
                System.out.print("Digite o caminho do arquivo PDF (ex: pdfs/MeuSlide.pdf): ");
                str_input = scanner.nextLine();
                library.addEntry(str_input);
                break;
            case 2:
                System.out.println("Você escolheu editar arquivos\n");
                System.out.print("Digite o nome do arquivo (ex: MeuSlide): ");
                str_input = scanner.nextLine();
                library.editEntry(str_input);
                break;
            case 3:
                System.out.println("Você escolheu deletar arquivo\n");
                System.out.print("Digite o nome do arquivo (ex: MeuSlide): ");
                str_input = scanner.nextLine();
                library.deleteEntry(str_input);
                break;
            case 4:
                System.out.println("Você escolheu voltar para biblioteca\n");
                state = e_states.LIBRARY;
                break;
            default:
                System.out.println("Opção inválida.\n");
        }
    }      

    public boolean is_over() {
        return end_loop;
    }

    // fechar o scanner
    // obs: só fechar quando o programa terminar
    public void close_scanner() {
        scanner.close();
    }
}
