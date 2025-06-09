package core;

import java.util.ArrayList;
import java.util.List;
import java.lang.String;
import java.util.Scanner;


public class CollectionManager {
    Directory directory;    // Usaremos o directory em questão para acessar
                            // seu respectivo colecoes
    List<Collection> colecoes = new ArrayList<>();
    //private Scanner scanner;
    String path;            // library\subdir\Colecoes

    public CollectionManager(Directory directory, Scanner scanner) {
        this.directory = directory;
        //this.scanner = scanner;
        this.path = (directory.getPath() + "\\Colecoes");
    }

    public void createCollection() {
        
    }
}
