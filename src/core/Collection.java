package core;

import java.util.List;
import java.lang.String;

public class Collection {
    String type;
    List<Entry> entradas;
    int maxEntradas;
    String nome;
    String autor;
    String tipo;
  
    public Collection(String tipo, String autor, String nome, int maxEntradas, List<Entry> entradas) {
      this.tipo = tipo;
      this.autor = autor;
      this.nome = nome;
      this.maxEntradas = maxEntradas;
  
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
