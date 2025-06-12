package core;

import java.io.File;
import java.io.IOException;
import java.lang.String;
import java.util.Scanner;
import java.util.Vector;

/**
 * Diretório raiz so sistema de gerencimaneto de arquivos.
 * 
 * @authors Júlia Guilhermino, Marcos Fontes.
 * @version 1.0
 */


 public class Library {
    Vector<Directory> directories = new Vector<>();
    String path;            // Caminho do diretório raiz do sistema de gerenciamento
    Scanner scanner;
    Directory SubDir;       // Subdiretório atual/em uso da biblioteca
  
    public Library(String path, Scanner scanner) {
        this.path = path;
        this.scanner = scanner;
    
        // Criar os subdiretórios se ainda não existirem
        String[] subdirs = { "Livros", "NotasDeAulas", "Slides" };
        for (String dirName : subdirs) {
            File subdir = new File(path, dirName);
            if (!subdir.exists()) {
                subdir.mkdirs(); // cria diretório (e pais, se necessário)
            }
    
            // Criar subdiretório "Colecoes" dentro de cada um
            File colecoesDir = new File(subdir, "Colecoes");
            if (!colecoesDir.exists()) {
                colecoesDir.mkdirs();
            }
        }
    
        // Carregar os diretórios da biblioteca
        File folder = new File(path);
        File[] files = folder.listFiles();
    
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    directories.add(new Directory(file.getPath(), scanner));
                }
            }
        }
    }
  
    public void CreateCollection() {
        SubDir.createCollection();
    }

    public void searchArchiveByName() {
        System.out.print("Digite o nome do arquivo (ex: MeuSlide.pdf): ");
        scanner.nextLine();
        String nomeArquivo = scanner.nextLine().trim();
      
        if (!nomeArquivo.toLowerCase().endsWith(".pdf")) {
            System.out.println("Erro: O nome do arquivo deve terminar com \".pdf\".");
            return;
        }
      
        boolean encontrado = false;
      
        for (Directory dir : directories) {
            for (Entry entry : dir.files) {
                File f = new File(entry.getEntryPath());
                if (f.getName().equalsIgnoreCase(nomeArquivo)) {
                System.out.println("Arquivo encontrado no subdiretório: " + dir.path);
                System.out.println(entry);
                encontrado = true;
                break;
                }
            }
            if (encontrado) break;
        }
      
        if (!encontrado) {
            System.out.println("Arquivo \"" + nomeArquivo + "\" não encontrado.");
        }
    }      

    public void listAllArchives() {
        if (directories.isEmpty()) {
            System.out.println("Nenhum subdiretório na biblioteca.");
            return;
        }
      
        System.out.println("=== Arquivos em todos os subdiretórios ===");
        for (Directory dir : directories) {
            System.out.println("Subdiretório: " + dir.path);
            if (dir.files.isEmpty()) {
                System.out.println("  (Nenhum arquivo)");
            } else {
                for (Entry entry : dir.files) {
                System.out.println("  - " + entry.getFileNameBase());
                }
            }
        }
    }
    
    public void listCollectionsCurrentDir(){
        SubDir.listCollectionsCurrentDir();
    }

    // Função alternativa para listar arquivos (caso precise)
    public void listArchivesCurrentDir() {
        if (SubDir == null) {
            System.out.println("Nenhum subdiretório selecionado.");
            return;
        }
        
        System.out.println("=== Arquivos no subdiretório atual: " + SubDir.path + " ===");
        if (SubDir.files.isEmpty()) {
            System.out.println("(Nenhum arquivo)");
        } else {
            for (Entry entry : SubDir.files) {
            System.out.println("- " + entry.getFileNameBase());
            }
        }
    }
      
     /**
   * Para delegar a criação de uma nova Entry, basta escolher
   * qual subdiretório usar e chamar o addEntry dele.
   */
    public void addEntry(String pdfSourcePath) {
        if (SubDir == null) {
            System.err.println("Nenhum subdiretório selecionado. Use setCurrentDir() antes de adicionar uma entry.");
            return;
        }
    
        try {
            SubDir.addEntry(pdfSourcePath);
        } catch (IOException e) {
            System.err.println("Falha ao adicionar entry: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    // Fazer tratamento de exceções aqui
    public void deleteEntry(String entryName){
        SubDir.deleteEntry(entryName);
    }

    public void editEntry(String entryName) {
        try {
          SubDir.editEntry(entryName);
        } catch (IOException e) {
          System.err.println("Erro ao editar entry: " + e.getMessage());
          e.printStackTrace(); // ou apenas logar, ou nada
        }
      }

    public void setCurrentSubdir(String subdirName) {
        for (Directory d : directories) {
          String dirName = new File(d.getPath()).getName();
          if (dirName.equals(subdirName)) {
            SubDir = d;
            System.out.println("Subdiretório atual definido para: " + dirName);
            return;  // importante sair do método aqui
          }
        }
        System.err.println("Subdiretório não encontrado: " + subdirName);
    }

    public void deleteLibrary() {
        File rootDir = new File(path);
      
        if (!rootDir.exists()) {
            System.out.println("Diretório já não existe no sistema de arquivos.");
        } else {
            deleteRecursive(rootDir);
            System.out.println("Diretório e conteúdos apagados com sucesso: " + path);
        }
      
        // Limpar os dados em memória
        for (Directory dir : directories) {
            dir.clear(); // limpa os arquivos do diretório
        }
      
        directories.clear();
        SubDir = null;
        path = null; // opcional, se quiser invalidar completamente a instância
    }
    
    private void deleteRecursive(File file) {
        if (file.isDirectory()) {
            for (File sub : file.listFiles()) {
                deleteRecursive(sub);
            }
        }
        file.delete();
    }
      
    
    public String getPath() {
        return path;
    }

    public Vector<String> getDirectoriesPaths() {
        Vector<String> paths = new Vector<>();
        for (Directory dir : directories) {
          paths.add(dir.getPath());
        }
        return paths;
    }

    public Vector<Directory> getDirectories(){
        return directories;
    }
}

 