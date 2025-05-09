package core;

import utils.Database;
import core.Directory;

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

    /**
     * Construtor
     */
    public Library() {}

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

 