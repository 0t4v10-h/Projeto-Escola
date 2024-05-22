package br.edu.vianna.escola.model.escola;

import br.edu.vianna.escola.model.Professor;

import java.util.ArrayList;

public class Disciplina {

    private String nome;
    private int cargaHoraria;
    private int ano, semestre;

    private Professor professor;

    private ArrayList<Matricula> matriculas;

    public Disciplina() {
        matriculas = new ArrayList<>();
    }

    public Disciplina(String nome, int cargaHoraria, int ano, int semestre, Professor professor) {
        this.nome = nome;
        this.cargaHoraria = cargaHoraria;
        this.ano = ano;
        this.semestre = semestre;
        this.professor = professor;
        matriculas = new ArrayList<>();
    }

    public ArrayList<Matricula> getMatriculas() {
        return matriculas;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getCargaHoraria() {
        return cargaHoraria;
    }

    public void setCargaHoraria(int cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public int getSemestre() {
        return semestre;
    }

    public void setSemestre(int semestre) {
        this.semestre = semestre;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }
}
