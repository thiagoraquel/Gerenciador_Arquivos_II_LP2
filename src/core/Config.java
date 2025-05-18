package core;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.IOException;

public class Config {
  private final String FILE_PATH = "data/Config.txt";

  public void saveConfig(String path) {
    try (FileWriter writer = new FileWriter(FILE_PATH)) {
      writer.write(path);
    } catch (IOException e) {
      System.out.println("Erro ao salvar o path.");
      e.printStackTrace();
    }
  }

  public String readConfig() {
    File file = new File(FILE_PATH);
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
}
