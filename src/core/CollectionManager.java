package core;

import java.util.ArrayList;
import java.util.List;

public class CollectionManager {
    Directory directory;
    List<Collection> colecoes = new ArrayList<>();

    public CollectionManager(Directory directory) {
        this.directory = directory;
        // opcional: carregar coleções persistidas
    }

    public void criarColecao(String tipo, String autor, int max, String nome, List<Entry> entradas) {
        if (entradas.size() > max) {
          System.out.println("Erro: número de entradas excede o limite permitido.");
          return;
        }
    
        Collection nova = new Collection(directory, tipo, autor, max, nome, entradas);
        colecoes.add(nova);
        // opcional: salvar imediatamente em arquivo .bib
    }
}
