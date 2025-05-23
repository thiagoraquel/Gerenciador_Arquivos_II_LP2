package core;

import core.Entry;

import java.io.File;
import java.io.FileReader;
import java.lang.String;
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
    String path;

    public Directory(String path) {
        this.path = path;
        System.out.println("Path do Subdiretório: " + path);

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
