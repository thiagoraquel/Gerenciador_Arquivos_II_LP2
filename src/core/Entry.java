package core;

import java.util.List;
import java.io.File;  

public class Entry {
  private String type;           
  private String entryPath;       
  private List<String> authors;
  private String title;
  private String subtitle;      
  private String subject;     
  private int year;

  public Entry(String type, String entryPath,
               List<String> authors, String title,
               String subtitle, String subject,
               int year) {
    this.type = type;
    this.entryPath = entryPath;
    this.authors = authors;
    this.title = title;
    this.subtitle = subtitle;
    this.subject = subject;
    this.year = year;
  }

  public String getEntryPath() {
    return entryPath;
  }

  public String getFileNameBase() {
    if (entryPath == null) return null;
    String name = new File(entryPath).getName();
    return name.replaceFirst("(?i)\\.pdf$", "");
  }
  

  // --- Getters e Setters para edição ---
  public List<String> getAuthors() { return authors; }
  public void setAuthors(List<String> autores) { this.authors = autores; }

  public String getType() { return type; }
  public void getType(String type) { this.type = type; }

  public String getTitle() { return title; }
  public void setTitle(String titulo) { this.title = titulo; }

  public String getSubtitle() { return subtitle; }
  public void setSubtitle(String subtitulo) { this.subtitle = subtitulo; }

  public String getSubject() { return subject; }
  public void setSubject(String disciplina) { this.subject = disciplina; }

  public int getYear() { return year; }
  public void setYear(int ano) { this.year = ano; }

  // toString para exibir no terminal
  @Override
  public String toString() {
    return String.format(
      "Entry[%s]\n  Tipo: %s\n  Autores: %s\n  Título: %s\n  Subtítulo: %s\n  Disciplina: %s\n  Ano: %d\n  Caminho: %s\n",
      getFileNameBase(),
      type,
      String.join(", ", authors),
      title,
      subtitle == null ? "(nenhum)" : subtitle,
      subject,
      year,
      entryPath
    );
  }
}

