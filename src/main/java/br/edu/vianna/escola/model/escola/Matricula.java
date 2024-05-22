package br.edu.vianna.escola.model.escola;

import br.edu.vianna.escola.model.Aluno;

public class Matricula {

    private Aluno aluno;

    private Disciplina disciplina;

    private double nota;

    private int numeroFaltas;

    public Matricula() {
    }

    public Matricula(Aluno aluno, Disciplina disciplina, double nota, int numeroFaltas) {
        this.aluno = aluno;
        this.disciplina = disciplina;
        this.nota = nota;
        this.numeroFaltas = numeroFaltas;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }

    public int getNumeroFaltas() {
        return numeroFaltas;
    }

    public void setNumeroFaltas(int numeroFaltas) {
        this.numeroFaltas = numeroFaltas;
    }
}
