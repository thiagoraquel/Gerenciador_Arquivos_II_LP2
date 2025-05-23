package core;

import utils.Database;
import core.Directory;
import java.io.File;  

import java.lang.String;
import java.util.Vector;

/**
 * Diretório raiz so sistema de gerencimaneto de arquivos.
 * 
 * @authors Júlia Guilhermino, Marcos Fontes.
 * @version 1.0
 */


public class Library {
    // TODO: atributos
    Vector<Directory> directories = new Vector<>();
    String path;           // Caminho do diretório raiz do sistema de gerenciamento

    public Library(String path) {
        this.path = path;
      
        // Criar os subdiretórios se ainda não existirem
        String[] subdirs = { "Livros", "NotasDeAulas", "Slides" };
        for (String dirName : subdirs) {
            File subdir = new File(path, dirName);
            if (!subdir.exists()) {
                subdir.mkdirs(); // cria diretório (e pais, se necessário)
            }
        }
      
        // Carregar os diretórios da biblioteca
        File folder = new File(path);
        File[] files = folder.listFiles();
      
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    directories.add(new Directory(file.getPath()));
                }
            }
        }
    }
    
    public String getPath() {
        return path;
    }

    public Vector<String> getDirectoriesPaths() {
        Vector<String> paths = new Vector<>();
        for (Directory dir : directories) {
          paths.add(dir.getPath()); // supondo que a classe Directory tem um método getPath()
        }
        return paths;
      }

    /**
     * Organiza os arquivos em diretórios por tipo ou nome de autores
     * 
     * @param type forma de organização dos diretórios
     */
    public void organize (String type) {
        
    }
    
    /**
     * Busca um arquivo pelo nome nos diretórios
     * 
     * @param file_name nomedo arquivo
     * @return path do arquivo
     */
    public String search_file (String file_name) {
        return "";
    }

    /**
     * Busca um diretório pelo nome
     * 
     * @param file_directory nome do diretório
     * @return path do diretório
     */
    public String search_directory (String directory_name) {
        return "";
    }

}

 