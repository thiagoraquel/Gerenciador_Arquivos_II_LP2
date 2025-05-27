package core;

import java.util.List;
import java.io.File;  

public class Entry {
  private String tipo;           
  private String filePath;       
  private List<String> autores;
  private String titulo;
  private String subtitulo;      
  private String disciplina;     
  private int ano;

  public Entry(String tipo, String filePath,
               List<String> autores, String titulo,
               String subtitulo, String disciplina,
               int ano) {
    this.tipo = tipo;
    this.filePath = filePath;
    this.autores = autores;
    this.titulo = titulo;
    this.subtitulo = subtitulo;
    this.disciplina = disciplina;
    this.ano = ano;
  }

  public String getEntryPath() {
    return filePath;
  }

  public String getFileNameBase() {
    return new File(filePath).getName().replaceFirst("\\.pdf$", "");
  }

  // --- Getters e Setters para edição ---
  public List<String> getAutores() { return autores; }
  public void setAutores(List<String> autores) { this.autores = autores; }

  public String getTitulo() { return titulo; }
  public void setTitulo(String titulo) { this.titulo = titulo; }

  public String getSubtitulo() { return subtitulo; }
  public void setSubtitulo(String subtitulo) { this.subtitulo = subtitulo; }

  public String getDisciplina() { return disciplina; }
  public void setDisciplina(String disciplina) { this.disciplina = disciplina; }

  public int getAno() { return ano; }
  public void setAno(int ano) { this.ano = ano; }

  // toString para exibir no terminal
  @Override
  public String toString() {
    return String.format(
      "Entry[%s]\n  Tipo: %s\n  Autores: %s\n  Título: %s\n  Subtítulo: %s\n  Disciplina: %s\n  Ano: %d\n  Caminho: %s\n",
      getFileNameBase(),
      tipo,
      String.join(", ", autores),
      titulo,
      subtitulo == null ? "(nenhum)" : subtitulo,
      disciplina,
      ano,
      filePath
    );
  }
}

