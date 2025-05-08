// Pacote (opcional)
package nomeDoPacote;

// Importações (opcional)
import java.util.Scanner; // exemplo

// Declaração da classe
public class NomeDaClasse {

    // Atributos (variáveis da classe)
    private int idade;
    public String nome;

    // Construtor
    public NomeDaClasse(String nome, int idade) {
        this.nome = nome;
        this.idade = idade;
    }

    // Métodos (comportamentos da classe)
    public void apresentar() {
        System.out.println("Olá, meu nome é " + nome + " e tenho " + idade + " anos.");
    }

    // Método main (opcional - ponto de entrada do programa)
    public static void main(String[] args) {
        NomeDaClasse pessoa = new NomeDaClasse("João", 25);
        pessoa.apresentar();
    }
}
