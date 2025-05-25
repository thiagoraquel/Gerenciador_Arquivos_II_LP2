package core;

import java.util.List;

public class Entry {
  @SuppressWarnings("unused")
  private String tipo;           // "Livro", "Nota", "Slide"
  @SuppressWarnings("unused")
  private String filePath;       // caminho relativo dentro da Library
  @SuppressWarnings("unused")
  private List<String> autores;
  @SuppressWarnings("unused")
  private String titulo;
  @SuppressWarnings("unused")
  private String subtitulo;      
  @SuppressWarnings("unused")
  private String disciplina;     
  @SuppressWarnings("unused")
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
  public String getEntryPath(){
    return filePath;
  }
}
