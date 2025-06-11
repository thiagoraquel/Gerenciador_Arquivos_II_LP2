package core;

import java.util.ArrayList;
import java.util.List;
import java.lang.String;
import java.util.Scanner;
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
import java.io.File;
import java.io.PrintWriter;
import java.io.IOException;

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
    }

    public void createCollection(String type, String author, String name, int max_entrys, List<Entry> entrys) {
        Collection nova = new Collection(type, author, name, max_entrys, entrys);
        colecoes.add(nova);
        System.out.println("Coleção \"" + name + "\" criada com sucesso com " + entrys.size() + " entrada(s).");      
        saveAsBib(nova, path);
    }

    public void saveAsBib(Collection collection, String outputPath) {
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
            writer.println("  note = {" + entry.getSubject() + "}");
            writer.println("}\n");
            }

            writer.close();
            System.out.println("Arquivo BibTeX salvo em: " + file.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Erro ao salvar arquivo .bib: " + e.getMessage());
        }
    }

}
