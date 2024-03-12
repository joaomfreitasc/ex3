package model;

public class Matricula {
    private String nome;
    private int idade;
    private String curso;
    private int matricula;
    
    public Matricula() {
        this.nome = "";
        this.idade = -1;
        this.curso = "";
        this.matricula = -1;
    }
    
    public Matricula(String nome, int idade, String curso, int matricula) {
        this.nome = nome;
        this.idade = idade;
        this.curso = curso;
        this.matricula = matricula;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public int getMatricula() {
        return matricula;
    }

    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }

    @Override
    public String toString() {
        return "Matricula [nome=" + nome + ", idade=" + idade + ", curso=" + curso + ", matricula=" + matricula + "]";
    }    
}