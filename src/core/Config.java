package core;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Config {
  private final String VALID_LIBS_FILE = "data/ValidLibraries.txt";

  // Adiciona uma nova biblioteca (sem duplicar) em ValidLibraries.txt
  public void addLibrary(String path) {
    if (libraryExists(path)) return;

    try (BufferedWriter writer = new BufferedWriter(new FileWriter(VALID_LIBS_FILE, true))) {
      writer.write(path);
      writer.newLine();
    } catch (IOException e) {
      System.out.println("Erro ao adicionar biblioteca.");
      e.printStackTrace();
    }
  }

  // Verifica se a biblioteca já existe no arquivo
  public boolean libraryExists(String path) {
    File file = new File(VALID_LIBS_FILE);
    if (!file.exists()) {
      return false;
    }

    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
      String line;
      while ((line = reader.readLine()) != null) {
        if (line.equals(path)) {
          return true;
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    return false;
  }

  // Retorna o nome das bibliotecas válidas
  public List<String> getLibrariesNames() {
    List<String> directories = new ArrayList<>();
    File file = new File(VALID_LIBS_FILE);
    if (!file.exists()) {
      return directories;
    }

    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
      String line;
      while ((line = reader.readLine()) != null) {
        directories.add(line);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    return directories;
  }

  // Imprime todas as bibliotecas registradas no arquivo
  public void printLibraries() {
    List<String> libraries = getLibrariesNames();

    if (libraries.isEmpty()) {
      System.out.println("Nenhuma biblioteca registrada.");
      return;
    }

    System.out.println("Bibliotecas registradas:");
    for (String lib : libraries) {
      System.out.println("- " + lib);
    }
  }

  // Remove de ValidLibraires.txt as bibliotecas que não existem
  public void removeInvalidLibraries() {
    List<String> allLibs = getLibrariesNames();
    List<String> validLibs = new ArrayList<>();
  
    for (String lib : allLibs) {
      File dir = new File(lib);
      if (dir.exists() && dir.isDirectory()) {
        validLibs.add(lib);
      } else {
        System.out.println("Removendo biblioteca inválida: " + lib);
      }
    }
  
    // Sobrescreve o arquivo com apenas os diretórios válidos
    try (BufferedWriter writer = new BufferedWriter(new FileWriter("data/ValidLibraries.txt"))) {
      for (String lib : validLibs) {
        writer.write(lib);
        writer.newLine();
      }
    } catch (IOException e) {
      System.out.println("Erro ao atualizar bibliotecas válidas.");
      e.printStackTrace();
    }
  }
}
