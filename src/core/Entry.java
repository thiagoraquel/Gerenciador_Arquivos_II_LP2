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
  private String subtitulo;      // opcional
  @SuppressWarnings("unused")
  private String area;           // só para livro
  @SuppressWarnings("unused")
  private String disciplina;     // para notas e slides
  @SuppressWarnings("unused")
  private String editora;        // opcional para livro
  @SuppressWarnings("unused")
  private int ano;
  @SuppressWarnings("unused")
  private int paginas;           // opcional
  @SuppressWarnings("unused")
  private String instituicao;    // opcional para notas e slides

  public Entry(String tipo, String filePath,
               List<String> autores, String titulo,
               String subtitulo, String area,
               String disciplina, String editora,
               int ano, int paginas, String instituicao) {
    this.tipo = tipo;
    this.filePath = filePath;
    this.autores = autores;
    this.titulo = titulo;
    this.subtitulo = subtitulo;
    this.area = area;
    this.disciplina = disciplina;
    this.editora = editora;
    this.ano = ano;
    this.paginas = paginas;
    this.instituicao = instituicao;
  }
  public String getEntryPath(){
    return filePath;
  }
}
