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
        String input;

        // Se o ValidLibraries.txt está vazio, assume-se que é a primeira inicialização
        if (libs_on_file.isEmpty()) {
            System.out.println("Não existe nenhuma biblioteca válida existente.");

            while (true) {
                System.out.print("Digite um nome válido para criar uma biblioteca: ");
                input = scanner.nextLine().trim();

                // Ignorar entradas vazias
                if (input.isEmpty()) {
                    System.out.println("Nome inválido. Tente novamente.");
                    continue;
                }

                File dir = new File(input);

                // Se diretório já existe, rejeita
                if (dir.exists()) {
                    System.out.println("Diretório já existe. Por favor, escolha outro nome.");
                } else {
                    boolean success = dir.mkdirs();
                    if (success) {
                        config.addLibrary(input);
                        System.out.println("Biblioteca criada com sucesso: " + input);
                        break;
                    } else {
                    System.out.println("Falha ao criar o diretório. Tente novamente.");
                    }
                }
            }
        } else {
            // Já existem nomes de bibliotecas no ValidLibraries.txt
            // Espera-se que sejam nomes de diretórios válidos
        }

        // Verificar se os nomes em ValidLibraries.txt são válidos (existem e são diretórios)
        config.removeInvalidLibraries();

        // Adicionar os paths das bibliotecas no ValidLibraries.txt ao vetor de bibliotecas
        List<String> validLibPaths = config.getLibrariesNames();
        
        // Adiciona bibliotecas válidas ao programa
        for (String libPath : validLibPaths) {
            //System.out.println(libPath);
            File dir = new File(libPath);
            if (dir.exists() && dir.isDirectory()) {
                Library lib = new Library(libPath, scanner);
                libraries.add(lib);
            } else {
                System.out.println("Aviso: Diretório inválido ignorado: " + libPath);
            }
        }

        // (Opcional) Caso nenhuma biblioteca seja válida após remoção, notificar o usuário
        if (libraries.isEmpty()) {
            System.out.println("Nenhuma biblioteca válida foi encontrada. Por favor, reinicie e crie uma nova.");
            end_loop = true;
        }
        
    }

    public void process_event() {
        if (state == e_states.STARTING) {
            
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
      
        int option = scanner.nextInt();
        scanner.nextLine(); // consumir quebra de linha
      
        switch (option) {
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
        int option = scanner.nextInt();
      
        if (option >= 1 && option <= libraries.size()) {
            library = libraries.get(option - 1);
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
        String input = scanner.nextLine();
      
        File newDir = new File(input);
        if (newDir.exists()) {
            System.out.println("Já existe uma biblioteca com esse nome.");
        } else {
            if (newDir.mkdirs()) {
                Library newLibrary = new Library(input, scanner);
                libraries.add(newLibrary);
                config.addLibrary(input);
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
      
        int option = scanner.nextInt();
      
        switch (option) {
            case 1:
                System.out.println("Você escolheu acessar subdiretório\n");
                selectSubdir();
                break;
            case 2:
                System.out.println("Você escolheu buscar arquivos\n");
                library.searchArchiveByName();
                break;
            case 3:
                System.out.println("Você escolheu trocar biblioteca\n");
                state = e_states.STARTING;
                break;
            case 4:
                System.out.println("Você escolheu deletar a atual biblioteca\n");
                state = e_states.STARTING;
                library.deleteLibrary();       // limpa arquivos e memória
                libraries.remove(library);    // remove da lista global
                library = null;               // reseta o ponteiro atual
                config.removeInvalidLibraries();
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
        scanner.nextLine(); // consumir quebra de linha
        String input = scanner.nextLine();
      
        if (input.isEmpty()) {
            System.out.println("Entrada vazia. Digite um número válido.");
            return;
        }
        int num_input;
        try {
            num_input = Integer.parseInt(input);
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
        library.listArchivesCurrentDir();
        library.listCollectionsCurrentDir();
        System.out.println("Escolha uma opção:");
        System.out.println("1. Adicionar arquivos");
        System.out.println("2. Editar arquivo");
        System.out.println("3. Deletar arquivo");
        System.out.println("4. Voltar para biblioteca");
        System.out.println("5. Criar coleção");
        System.out.println("6. Empacotar coleção");
        System.out.println("7. Adicionar entrada em coleção");
        System.out.println("8. Remover entrada coleção");

        System.out.print("Opção: ");
      
        int option = scanner.nextInt();
        scanner.nextLine(); // consumir quebra de linha
        String input;
        switch (option) {
            case 1:
                System.out.println("Você escolheu adicionar arquivos\n");
                System.out.print("Digite o caminho do arquivo PDF (ex: pdfs/MeuSlide.pdf): ");
                input = scanner.nextLine();
                library.addEntry(input);
                break;
            case 2:
                System.out.println("Você escolheu editar arquivos\n");
                System.out.print("Digite o nome do arquivo (ex: MeuSlide): ");
                input = scanner.nextLine();
                library.editEntry(input);
                break;
            case 3:
                System.out.println("Você escolheu deletar arquivo\n");
                System.out.print("Digite o nome do arquivo (ex: MeuSlide): ");
                input = scanner.nextLine();
                library.deleteEntry(input);
                break;
            case 4:
                System.out.println("Você escolheu voltar para biblioteca\n");
                state = e_states.LIBRARY;
                break;
            case 5:
                System.out.println("Você escolheu criar coleção\n");
                library.CreateCollection();
                break;
            case 6:
                System.out.println("Você escolheu empacotar coleção\n");
                library.packCollection();
                break;
            case 7:
                System.out.println("Você escolheu adicionar entrada em coleção\n");
                library.addEntryToCollection();
                break;
            case 8:
                System.out.println("Você escolheu remover entrada em coleção\n");
                library.removeEntryFromCollection();
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
