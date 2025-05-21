package core;

import java.util.List;

public class Entry {
  String tipo;           // Livro, Nota, Slide
  String filePath;       // Path relativo na biblioteca
  List<String> autores;
  String titulo;
  String subtitulo;     // opcional
  String area;          // só para livro
  String disciplina;    // para notas e slides
  String editora;       // opcional livro
  int ano;
  int paginas;          // opcional
  String instituicao;   // opcional notas e slides

  // construtores, getters, setters
}