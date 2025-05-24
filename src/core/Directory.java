package core;

import core.Entry;

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

    public Directory(String path, Scanner scanner) {
        this.path = path;
        this.scanner = scanner;

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

        // copiar PDF para dentro deste diretório
        Path destPdfPath = Paths.get(path, pdfSrc.getName());
        Files.copy(pdfSrc.toPath(), destPdfPath, StandardCopyOption.REPLACE_EXISTING);

        // criar o Entry
        String relativo = new File(path).toPath()
                            .getFileName().resolve(pdfSrc.getName())
                            .toString();
        Entry entry = new Entry(
        /* tipo */       "Nota",          // ou "Livro"/"Slide" conforme o seu design
        /* filePath */   relativo,
        /* autores */    autores,
        /* titulo */     titulo,
        /* subtitulo */  subtitulo.isEmpty() ? null : subtitulo,
        /* area */       null,            // só para livros
        /* disciplina */ disciplina,
        /* editora */    null,
        /* ano */        ano,
        /* paginas */    0,
        /* instituicao*/ null
        );

        // 3) Salvar JSON
        Gson gson = new Gson();
        String jsonFilename = pdfSrc.getName().replaceAll("\\.pdf$", ".json");
        try (Writer writer = new FileWriter(new File(path, jsonFilename))) {
        gson.toJson(entry, writer);
        }

        // 4) Guardar na memória
        files.add(entry);

        System.out.println("Adicionado: " + pdfSrc.getName()
                        + " e " + jsonFilename + " em " + path);
    }

    public void deleteEntry(String nomeArquivo) {
        // Construir os caminhos dos arquivos
        File pdfFile = new File(path, nomeArquivo + ".pdf");
        File jsonFile = new File(path, nomeArquivo + ".json");
      
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
                return nomeBase.equals(nomeArquivo);
            });
      
            System.out.println("Entrada removida da memória.");
        }
    }   
    
    // Você pode adicionar getters aqui se quiser expor os dados
    public String getPath() {
      return path;
    }

     /**
     * Cria um novo aquivo no diretório
     */
    public void create () {
        
    }
    
    /**
     * Edita um arquivo do direótio
     * 
     * @param file_name nomedo arquivo
     */
    public void edit (String file_name) {
    
    }

    /**
     * Busca um diretório pelo nome
     * 
     * @param file_name nome do diretório
     */
    public void delete (String file_name) {

    }
}
