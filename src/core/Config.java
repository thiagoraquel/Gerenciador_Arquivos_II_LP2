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
  private final String LAST_LIBRARY_FILE = "data/LastLibrary.txt";

  public void saveLastLibrary(String path) {
    try (FileWriter writer = new FileWriter(LAST_LIBRARY_FILE)) {
      writer.write(path);
    } catch (IOException e) {
      System.out.println("Erro ao salvar o path.");
      e.printStackTrace();
    }
  }

  public String readLastLibrary() {
    File file = new File(LAST_LIBRARY_FILE);
    if (!file.exists()) {
      return null;
    }
    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
      return reader.readLine();
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  // Adiciona uma nova biblioteca (sem duplicar)
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

  // Lê todos os diretórios válidos
  public List<String> readValidDirectories() {
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
}
