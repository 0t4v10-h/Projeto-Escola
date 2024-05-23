package br.edu.vianna.escola;


import br.edu.vianna.escola.Utils.CryptoUtils;
import br.edu.vianna.escola.dao.impl.AlunoDAO;
import br.edu.vianna.escola.dao.impl.MatriculaDAO;
import br.edu.vianna.escola.dao.impl.ProfessorDAO;
import br.edu.vianna.escola.model.Aluno;
import br.edu.vianna.escola.model.Professor;
import br.edu.vianna.escola.model.escola.Matricula;
import br.edu.vianna.escola.model.esp.EEspecializacao;

import java.sql.SQLException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {


        Aluno a = new Aluno(1,"Zezin das Coves","ze@ze.com","ze",
                "123", null,"123456",
                1500);

        try {
            new AlunoDAO().inserir(a);
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Aluno - Não conseguiu salvar no banco: "+e.getMessage());
        }


        Professor p = new Professor(1, "Ze Ruela", "Ze@ruela.com", "ruela", "111", null, EEspecializacao.GRADUACAO, 2, 100.00);

        try{
            new ProfessorDAO().inserir(p);
        }catch (SQLException | ClassNotFoundException e){
            System.out.println("Professor - Não conseguiu salvar no banco: " +e.getMessage());
        }

        Matricula m = new Matricula(1, 3, 25.0, 5);

        try{
            new MatriculaDAO().inserir(m);
        }catch (SQLException | ClassNotFoundException e){
            System.out.println("Matricula - Não consegui salvar no banco: " +e.getMessage());
        }
    }
}