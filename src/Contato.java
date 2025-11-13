
public class Contato {

    /* atributos privados */
    private String nome;
    private String telefone;

    /* construtor */
    public Contato(String nome, String telefone) {
        this.nome = nome;
        this.telefone = telefone;
    }

    /* getters */
    public String getNome() {
        return this.nome;
    }

    public String getTelefone() {
        return this.telefone;
    }

    /* toString() com formatação usando String.format() */
    @Override
    public String toString() {
        return String.format("Nome: %-30sFone: %s", this.nome, this.telefone);
    }
}