package br.edu.vianna.escola.dao.impl;

import br.edu.vianna.escola.dao.GenericsDAO;
import br.edu.vianna.escola.dao.con.ConnectionFactory;
import br.edu.vianna.escola.model.Aluno;
import br.edu.vianna.escola.model.escola.Disciplina;
import br.edu.vianna.escola.model.escola.Matricula;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MatriculaDAO implements GenericsDAO<Matricula, Integer>{

    private Connection c;

    @Override
    public void inserir(Matricula matricula) throws SQLException, ClassNotFoundException {

        c = ConnectionFactory.getConnection();

        String sql = "INSERT INTO fpoo.matricula\n" +
                "(id_aluno, id_disciplina, nota, numeroFaltas)\n" +
                "VALUES (?, ?, ?, ?);";

        PreparedStatement pst = c.prepareStatement(sql);
        pst.setInt(1, matricula.getAluno().getId());
        pst.setInt(2, matricula.getDisciplina().getIdDisciplina());
        pst.setDouble(3, matricula.getNota());
        pst.setInt(4, matricula.getNumeroFaltas());

        pst.execute();
    }

    @Override
    public void alterar(Matricula matricula) throws SQLException, ClassNotFoundException {

        c = ConnectionFactory.getConnection();

        String sql = "UPDATE fpoo.matricula\n" +
                "SET id_aluno=?, id_disciplina=?, nota=?, numeroFaltas=?;";

        PreparedStatement pst = c.prepareStatement(sql);
        pst.setInt(1, matricula.getAluno().getId());
        pst.setInt(2, matricula.getDisciplina().getIdDisciplina());
        pst.setDouble(3, matricula.getNota());
        pst.setInt(4, matricula.getNumeroFaltas());

        pst.execute();
    }

    @Override
    public void apagar(Matricula matricula) throws SQLException, ClassNotFoundException {

        c = ConnectionFactory.getConnection();

        String sql = "DELETE FROM fpoo.matricula " +
                "WHERE id_aluno=?, id_disciplina=?;";

        PreparedStatement pst = c.prepareStatement(sql);
        pst.setInt(1, matricula.getAluno().getId());
        pst.setInt(2, matricula.getDisciplina().getIdDisciplina());

        pst.execute();
    }

    @Override
    public Matricula buscarUm(Integer id) throws SQLException, ClassNotFoundException {

        c = ConnectionFactory.getConnection();

        String sql = "SELECT id_aluno, id_disciplina, nota, numeroFaltas " +
                "FROM fpoo.matricula " +
                "WHERE id_aluno=?, id_disciplina=?; ";

        PreparedStatement pst = c.prepareStatement(sql);
        pst.setInt(1, id);

        ResultSet rs = pst.executeQuery();

        Matricula m = null;
        if(rs.next()){
            m = new Matricula();
            m.getAluno().setId(rs.getInt("id_aluno"));
            m.getDisciplina().setIdDisciplina(rs.getInt("id_disciplina"));
            m.setNota(rs.getDouble("nota"));
            m.setNumeroFaltas(rs.getInt("numeroFaltas"));
        }
        return m;
    }

    @Override
    public ArrayList<Matricula> buscarTodos() throws SQLException, ClassNotFoundException {

        c = ConnectionFactory.getConnection();

        String sql = "SELECT id_aluno, id_disciplina, nota, numeroFaltas" +
                "FROM fpoo.matricula;";

        PreparedStatement pst = c.prepareStatement(sql);

        ResultSet rs = pst.executeQuery();

        ArrayList<Matricula> listaMatricula = new ArrayList<>();
        while (rs.next()){
            Matricula m = new Matricula(new AlunoDAO().buscarUm(rs.getInt("id_aluno")),
                    new DisciplinaDAO().buscarUm(rs.getInt("id_disciplina")),
                    rs.getDouble("nota"),
                    rs.getInt("numeroFaltas"));

            listaMatricula.add(m);
        }

        return listaMatricula;
    }

    @Override
    public int count() throws SQLException, ClassNotFoundException {

        c = ConnectionFactory.getConnection();

        String sql = "SELECT count(*) as qtde " +
                "FROM fpoo.matricula;";

        PreparedStatement pst = c.prepareStatement(sql);

        ResultSet rs = pst.executeQuery();

        rs.next();

        return rs.getInt("qtde");
    }
}
