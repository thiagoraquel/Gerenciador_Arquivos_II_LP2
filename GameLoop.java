// Pacote (opcional)
package nomeDoPacote;

// Importações (opcional)
import java.util.Scanner; // exemplo

// Declaração da classe
public GameLoop  {

    // Atributos (variáveis da classe)
    private enum e_states{
        STARTING,
        WELCOME,
        ROOT,
        DIRECTORY,
        DELETE,
        QUITTING;

    }

    int state;
    bool end_loop = false; // controla o loop
    
    public void initialize() {
        
    }

    public void process_event() {
        
    }

    public void update() {
        
    }

    public void render() {
        
    }

    public bool it_over() {
        return end_loop;
    }


    // Construtor
    public NomeDaClasse(String nome, int idade) {
        this.nome = nome;
        this.idade = idade;
    }

    // Métodos (comportamentos da classe)
    public void initialize() {
        
    }

    // Método main (opcional - ponto de entrada do programa)
    public static void main(String[] args) {
        NomeDaClasse pessoa = new NomeDaClasse("João", 25);
        pessoa.apresentar();
    }
}
