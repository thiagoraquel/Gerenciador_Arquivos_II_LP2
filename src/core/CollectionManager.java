package core;

import java.util.ArrayList;
import java.util.List;
import java.lang.String;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.zip.*;

public class CollectionManager {
    Directory directory;    // Usaremos o directory em questão para acessar
                            // seu respectivo colecoes
    List<Collection> colecoes = new ArrayList<>();
    //private Scanner scanner;
    String path;            // library\subdir\Colecoes

    public CollectionManager(Directory directory, Scanner scanner) {
        this.directory = directory;
        //this.scanner = scanner;
        this.path = (directory.getPath() + "\\Colecoes");
        loadCollectionsFromPath();
    }

    public void createCollection(String type, String author, String name, int max_entrys, List<Entry> entrys) {
        Collection nova = new Collection(type, author, name, max_entrys, entrys);
        colecoes.add(nova);
        System.out.println("Coleção \"" + name + "\" criada com sucesso com " + entrys.size() + " entrada(s).");      
        
        String outputPath = Paths.get(path, name + ".bib").toString();
        saveAsBib(nova, outputPath);
    }

    public void saveAsBib(Collection collection, String outputPath) {
        System.out.println("Salvando em: " + outputPath);
        try {
            File file = new File(outputPath);
            PrintWriter writer = new PrintWriter(file);

            for (Entry entry : collection.getEntradas()) {
            String id = entry.getFileNameBase(); // nome base do arquivo sem .pdf

            writer.println("@" + entry.getType() + "{" + id + ",");
            writer.println("  author = {" + String.join(" and ", entry.getAuthors()) + "},");
            writer.println("  title = {" + entry.getTitle() + 
                (entry.getSubtitle() != null && !entry.getSubtitle().isEmpty() ? ": " + entry.getSubtitle() : "") + "},");
            writer.println("  year = {" + entry.getYear() + "},");
            writer.println("  subject = {" + entry.getSubject() + "}");
            writer.println("}\n");
            }

            writer.close();
            System.out.println("Arquivo BibTeX salvo em: " + file.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Erro ao salvar arquivo .bib: " + e.getMessage());
        }
    }

    public void loadCollectionsFromPath() {
        colecoes.clear();

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(path), "*.bib")) {
            for (Path entryPath : stream) {
                Collection col = parseBibFileToCollection(entryPath);
                if (col != null) {
                    colecoes.add(col);
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler arquivos .bib: " + e.getMessage());
        }
    }

    private Collection parseBibFileToCollection(Path bibPath) {
        List<Entry> entries = new ArrayList<>();
        String tipo = null;  // pega do primeiro entry (supondo que todos são do mesmo tipo)
        String autorColecao = "desconhecido"; // opcional, pode ser ajustado
    
        try (Scanner scanner = new Scanner(bibPath)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
    
                if (line.startsWith("@")) {
                    // Exemplo: @Livros{MeuSlide,
                    int tipoStart = 1;
                    int tipoEnd = line.indexOf('{');
                    if (tipoEnd > tipoStart) {
                        tipo = line.substring(tipoStart, tipoEnd).trim();
                    }
    
                    int idStart = tipoEnd + 1;
                    int idEnd = line.indexOf(',', idStart);
                    String id = line.substring(idStart, idEnd).trim();
    
                    // Agora ler os campos até a linha que fecha a entrada
                    String author = null, title = null, subtitle = null, subject = null;
                    int year = 0;
    
                    while (scanner.hasNextLine()) {
                        String fieldLine = scanner.nextLine().trim();
                        if (fieldLine.equals("}")) break;
    
                        int eq = fieldLine.indexOf('=');
                        if (eq < 0) continue;
    
                        String fieldName = fieldLine.substring(0, eq).trim().toLowerCase();
                        String value = fieldLine.substring(eq + 1).trim();
                        value = value.replaceAll(",$", "").replaceAll("[{}]", "").trim();
    
                        switch (fieldName) {
                            case "author":
                                author = value;
                                break;
                            case "title":
                                if (value.contains(": ")) {
                                    String[] parts = value.split(": ", 2);
                                    title = parts[0];
                                    subtitle = parts[1];
                                } else {
                                    title = value;
                                    subtitle = "";
                                }
                                break;
                            case "year":
                                try {
                                    year = Integer.parseInt(value);
                                } catch (NumberFormatException e) {
                                    year = 0; // default se não puder parsear
                                }
                                break;
                            case "subject":
                                subject = value;
                                break;
                        }
                    }
    
                    List<String> authors = new ArrayList<>();
                    if (author != null) {
                        for (String a : author.split(" and ")) {
                            authors.add(a.trim());
                        }
                    }
    
                    Entry e = new Entry(tipo, id, authors, title, subtitle, subject, year);
                    entries.add(e);
                }
            }
    
            String nomeColecao = bibPath.getFileName().toString().replaceFirst("[.][^.]+$", "");
            int maxEntradas = entries.size();
    
            return new Collection(tipo != null ? tipo : "default", autorColecao, nomeColecao, maxEntradas, entries);
    
        } catch (IOException e) {
            System.err.println("Erro ao ler arquivo " + bibPath + ": " + e.getMessage());
            return null;
        }
    }

    /**
     * Cria um arquivo .zip com os PDFs de uma coleção.
     *
     * @param collection A coleção cujas entradas serão empacotadas.
     * @param outputPath Caminho do diretório onde o .zip será criado.
     * @param zipFileName Nome do arquivo .zip (sem extensão).
     * @throws IOException Se ocorrer erro durante a escrita do arquivo.
     */
    public void zipCollection(Collection collection, String outputPath, String zipFileName) throws IOException {
        // Certifica-se que o diretório existe
        Files.createDirectories(Paths.get(outputPath));

        String zipFullPath = outputPath + File.separator + zipFileName + ".zip";

        try (FileOutputStream fos = new FileOutputStream(zipFullPath);
            ZipOutputStream zos = new ZipOutputStream(fos)) {

                for (Entry entry : collection.getEntradas()) {
                    String path = entry.getEntryPath();
                    File pdfFile = new File(path); // converte o caminho em um objeto File
                
                    if (pdfFile.exists()) {
                        addFileToZip(pdfFile, zos);
                    }
                }
        }
    }

    private void addFileToZip(File file, ZipOutputStream zos) throws IOException {
        try (FileInputStream fis = new FileInputStream(file)) {
            ZipEntry zipEntry = new ZipEntry(file.getName());
            zos.putNextEntry(zipEntry);

            byte[] buffer = new byte[4096];
            int len;
            while ((len = fis.read(buffer)) > 0) {
                zos.write(buffer, 0, len);
            }

            zos.closeEntry();
        }
    }

    public void listCollections() {
        if (colecoes.isEmpty()) {
            System.out.println("Nenhuma coleção encontrada.");
            return;
        }
    
        System.out.println("Coleções disponíveis:");
        for (Collection c : colecoes) {
            System.out.println("- " + c.getNome());
        }
    }
    
    public List<Collection> getCollections(){
        return colecoes;
    }
}
