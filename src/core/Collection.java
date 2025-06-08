package core;

import java.util.List;
import java.lang.String;

public class Collection {
    Directory directory;
    List<Entry> entradas;
    int maxEntradas;
    String nome;
    String autor;
    String tipo;
  
    public Collection(Directory directory, String tipo, String autor, int maxEntradas, String nome, List<Entry> entradas) {
      this.directory = directory;
      this.tipo = tipo;
      this.autor = autor;
      this.maxEntradas = maxEntradas;
      this.nome = nome;
  
      if (entradas.size() > maxEntradas) {
        throw new IllegalArgumentException("Entradas excedem o limite da coleção.");
      }
  
      this.entradas = entradas;
    }
  
    public List<Entry> getEntradas() {
      return entradas;
    }
  
    public String getNome() {
      return nome;
    }
  
}
