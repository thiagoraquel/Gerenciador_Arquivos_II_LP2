package core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.String;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;
import com.google.gson.Gson;

/**
 * Sobdiretório de arquivos PDF
 * 
 * @authors Júlia Guilhermino, Marcos Fontes.
 * @version 1.0
 */

public class Directory {
    Vector<Entry> files = new Vector<>();
    private Scanner scanner;
    String path;
    CollectionManager collectionManager;

    public Directory(String path, Scanner scanner) {
        this.path = path;
        this.scanner = scanner;
        
        // Collection Manager tem que ser o diretório em questão (e não o Colecoes)
        // Pois ele realizará operações em ambos
        this.collectionManager = new CollectionManager(this, scanner); // passa o próprio diretório

        File folder = new File(path);
        File[] fileList = folder.listFiles();

        if (fileList != null) {
            for (File file : fileList) {
                if (file.isFile() && file.getName().endsWith(".json")) {
                    try {
                        Gson gson = new Gson();
                        FileReader reader = new FileReader(file);
                        Entry entry = gson.fromJson(reader, Entry.class);
                        reader.close();

                        files.add(entry);
                    } catch (Exception e) {
                        System.err.println("Erro ao ler JSON: " + file.getName());
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void clear() {
        files.clear();
        scanner = null;
        path = null;
    }  

    public void createCollection(){

        String type_entry = new File(path).getName();

        // 1) Perguntar dados ao usuário
        System.out.print("Autor(es) (separe por vírgula): ");
        List<String> autores = Arrays.asList(scanner.nextLine().split("\\s*,\\s*"));

        System.out.print("Nome da Coleção: ");
        String name = scanner.nextLine();

        System.out.print("Máximo de entradas: ");
        int max_entrys = scanner.nextInt();

        scanner.nextLine(); // limpar o \n deixado pelo nextInt()

        // 2) Listar entradas numeradas
        System.out.println("\nEntradas disponíveis:");
        int i = 1;
        for (Entry entry : files) {
            System.out.println(i + ") - " + entry.getFileNameBase());
            i++;
        }

        // 3) Permitir seleção
        System.out.print("\nDigite os números das entradas desejadas (separe por vírgula): ");
        String[] escolhas = scanner.nextLine().split("\\s*,\\s*");

        Vector<Entry> selecionadas = new Vector<>();
        for (String escolha : escolhas) {
            if (selecionadas.size() >= max_entrys) {
                System.out.println("Número máximo de entradas atingido (" + max_entrys + ").");
                break;
            }
          
            try {
                int index = Integer.parseInt(escolha) - 1;
                if (index >= 0 && index < files.size()) {
                    Entry entrada = files.get(index);
                    if (!selecionadas.contains(entrada)) {
                    selecionadas.add(entrada);
                    } else {
                    System.out.println("Entrada " + (index + 1) + " já foi selecionada.");
                    }
                } else {
                    System.out.println("Número inválido: " + (index + 1));
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida: " + escolha);
            }
        }
    }
   /**
    * Copia o PDF para este diretório e cria o arquivo JSON com os metadados.
    * @param pdfSourcePath caminho completo para o PDF de origem
    * @throws IOException se falhar na cópia ou escrita
    */
    public void addEntry(String pdfSourcePath) throws IOException {
        // 1) Perguntar dados ao usuário
        System.out.print("Autor(es) (separe por vírgula): ");
        List<String> autores = Arrays.asList(scanner.nextLine().split("\\s*,\\s*"));

        System.out.print("Título: ");
        String titulo = scanner.nextLine();

        System.out.print("Subtítulo (ou ENTER para nenhum): ");
        String subtitulo = scanner.nextLine();

        System.out.print("Disciplina: ");
        String disciplina = scanner.nextLine();

        System.out.print("Ano: ");
        int ano = Integer.parseInt(scanner.nextLine());

        // 2) Montar nome e destinos
        File pdfSrc = new File(pdfSourcePath);
        if (!pdfSrc.exists() || !pdfSrc.isFile()) {
        throw new FileNotFoundException("PDF não encontrado: " + pdfSourcePath);
        }

        // Copia PDF para dentro deste diretório
        Path destPdfPath = Paths.get(path, pdfSrc.getName());
        Files.copy(pdfSrc.toPath(), destPdfPath, StandardCopyOption.REPLACE_EXISTING);

        // Criar o Entry

        // relativo é o caminho relativo ao diretório atual (path)
        String relativo = new File(path).toPath()
                            .getFileName().resolve(pdfSrc.getName())
                            .toString();

        String tipo = new File(path).getName(); // nome da pasta (ex: "Livros", "NotasDeAulas")
        
        Entry entry = new Entry(
        /* tipo */       tipo,
        /* filePath */   relativo,
        /* autores */    autores,
        /* titulo */     titulo,
        /* subtitulo */  subtitulo.isEmpty() ? null : subtitulo,
        /* disciplina */ disciplina,
        /* ano */        ano
        );

        // Salvar JSON
        Gson gson = new Gson();
        String jsonFilename = pdfSrc.getName().replaceAll("\\.pdf$", ".json");
        try (Writer writer = new FileWriter(new File(path, jsonFilename))) {
            gson.toJson(entry, writer);
        }

        // Guardar na memória
        files.add(entry);
    }

    public void deleteEntry(String entryName) {
        // Construir os caminhos dos arquivos
        File pdfFile = new File(path, entryName + ".pdf");
        File jsonFile = new File(path, entryName + ".json");
      
        // Deletar arquivos físicos
        boolean pdfDeletado = false;
        boolean jsonDeletado = false;
      
        if (pdfFile.exists()) {
            pdfDeletado = pdfFile.delete();
        }
      
        if (jsonFile.exists()) {
            jsonDeletado = jsonFile.delete();
        }
      
        if (!pdfDeletado && !jsonDeletado) {
            System.out.println("Nenhum arquivo foi deletado. Verifique o nome.");
        } else {
            System.out.println("Arquivos deletados:");
            if (pdfDeletado) System.out.println("- " + pdfFile.getName());
            if (jsonDeletado) System.out.println("- " + jsonFile.getName());
      
                // Remover do vetor files
                files.removeIf(entry -> {
                String entryPath = entry.getEntryPath(); // precisa de um getter
                String nomeBase = new File(entryPath).getName().replace(".pdf", "");
                return nomeBase.equals(entryName);
            });
      
            System.out.println("Entrada removida da memória.");
        }
    }   

    public void editEntry(String entryName) throws IOException {
        // 1) Encontrar o Entry
        Entry alvo = null;
        for (Entry e : files) {
            if (e.getFileNameBase().equals(entryName)) {
                alvo = e;
                break;
            }
        }
        if (alvo == null) {
            System.out.println("Entry não encontrada: " + entryName);
          return;
        }
      
        System.out.println("=== Editando entry: " + entryName + " ===");
      
        // 2) Perguntar campos, mostrando valor atual e aceitando ENTER para manter
        // Autores
        System.out.print("Autores atuais (" + String.join(", ", alvo.getAuthors()) + "): ");
        String line = scanner.nextLine().trim();
        if (!line.isEmpty()) {
            List<String> novos = Arrays.asList(line.split("\\s*,\\s*"));
            alvo.setAuthors(novos);
        }
      
        // Título
        System.out.print("Título atual (" + alvo.getTitle() + "): ");
        line = scanner.nextLine().trim();
        if (!line.isEmpty()) {
            alvo.setTitle(line);
        }
      
        // Subtítulo
        String atualSub = alvo.getSubtitle() == null ? "" : alvo.getSubtitle();
        System.out.print("Subtítulo atual (" + (atualSub.isEmpty() ? "nenhum" : atualSub) + "): ");
        line = scanner.nextLine().trim();
        if (!line.isEmpty()) {
            alvo.setSubtitle(line);
        }
      
        // Disciplina
        System.out.print("Disciplina atual (" + alvo.getSubject() + "): ");
        line = scanner.nextLine().trim();
        if (!line.isEmpty()) {
            alvo.setSubject(line);
        }
      
        // Ano
        System.out.print("Ano atual (" + alvo.getYear() + "): ");
        line = scanner.nextLine().trim();
        if (!line.isEmpty()) {
            try {
                alvo.setYear(Integer.parseInt(line));
            } catch (NumberFormatException ex) {
                System.out.println("Formato de ano inválido. Mantendo valor anterior.");
            }
        }
      
        // 3) Salvar JSON atualizado
        Gson gson = new Gson();
        String jsonFilename = entryName + ".json";
        try (Writer writer = new FileWriter(new File(path, jsonFilename))) {
            gson.toJson(alvo, writer);
        }
      
        // 4) Mostrar no terminal o Entry atualizado
        System.out.println("\n=== Entry atualizado ===");
        System.out.println(alvo);
    }      
    
    public String getPath() {
      return path;
    }
}
