package br.edu.vianna.escola.dao.impl;

import br.edu.vianna.escola.dao.GenericsDAO;
import br.edu.vianna.escola.dao.con.ConnectionFactory;
import br.edu.vianna.escola.model.Professor;
import br.edu.vianna.escola.model.escola.Disciplina;
import br.edu.vianna.escola.model.escola.Matricula;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DisciplinaDAO implements GenericsDAO<Disciplina, Integer> {

    private Connection c;
    @Override
    public void inserir(Disciplina disciplina) throws SQLException, ClassNotFoundException {

        c = ConnectionFactory.getConnection();

        String sql = "INSERT INTO fpoo.disciplina\n" +
                "(nome, iddisciplina, carga_horaria, ano, semestre, id_professor)\n" +
                "VALUES(?, ?, ?, ?, ?, ?);";

        PreparedStatement pst = c.prepareStatement(sql);
        pst.setString(1, disciplina.getNome());
        pst.setInt(2, disciplina.getIdDisciplina());
        pst.setInt(3, disciplina.getCargaHoraria());
        pst.setInt(4, disciplina.getAno());
        pst.setInt(5, disciplina.getSemestre());
        pst.setInt(6, disciplina.getProfessor().getId());

        pst.execute();
    }

    @Override
    public void alterar(Disciplina disciplina) throws SQLException, ClassNotFoundException {

        c = ConnectionFactory.getConnection();

        String sql = "UPDATE fpoo.disciplina\n" +
                "SET nome=?, carga_horaria=?, ano=?, semestre=?, id_professor=?" +
                "WHERE iddisciplina=?;";

        PreparedStatement pst = c.prepareStatement(sql);
        pst.setString(1, disciplina.getNome());
        pst.setInt(2, disciplina.getIdDisciplina());
        pst.setInt(3, disciplina.getCargaHoraria());
        pst.setInt(4, disciplina.getAno());
        pst.setInt(5, disciplina.getSemestre());
        pst.setInt(6, disciplina.getProfessor().getId());

        pst.execute();
    }

    @Override
    public void apagar(Disciplina disciplina) throws SQLException, ClassNotFoundException {

        c = ConnectionFactory.getConnection();

        String sql = "DELETE FROM fpoo.disciplina " +
                "WHERE iddisciplina=?;";

        PreparedStatement pst = c.prepareStatement(sql);
        pst.setInt(1, disciplina.getIdDisciplina());

        pst.execute();
    }

    @Override
    public Disciplina buscarUm(Integer id) throws SQLException, ClassNotFoundException {

        c = ConnectionFactory.getConnection();

        String sql = "SELECT nome, carga_horaria, ano, semestre, id_professor " +
                "FROM fpoo.disciplina " +
                "WHERE iddisciplina=?;";

        PreparedStatement pst = c.prepareStatement(sql);
        pst.setInt(1, id);

        ResultSet rs = pst.executeQuery();

        Disciplina d = null;
        if(rs.next()){
            d = new Disciplina();
            d.setNome(rs.getString("nome"));
            d.setIdDisciplina(rs.getInt("iddisciplina"));
            d.setCargaHoraria(rs.getInt("carga_horaria"));
            d.setAno(rs.getInt("ano"));
            d.setSemestre(rs.getInt("semestre"));
            d.getProfessor().setId(rs.getInt("id_professor"));
        }

        return d;
    }

    @Override
    public ArrayList<Disciplina> buscarTodos() throws SQLException, ClassNotFoundException {

        c = ConnectionFactory.getConnection();

        String sql = "SELECT nome, iddisciplina, carga_horaria, ano, semestre, id_professor " +
                "FROM fpoo.disciplina ;";

        PreparedStatement pst = c.prepareStatement(sql);

        ResultSet rs = pst.executeQuery();

        ArrayList<Disciplina> listaDisciplina = new ArrayList<>();
        while(rs.next()){

            Disciplina d = new Disciplina(rs.getString("nome"), rs.getInt("iddisciplina"), rs.getInt("carga_horaria"), rs.getInt("ano"), rs.getInt("semestre"), new ProfessorDAO().buscarUm(rs.getInt("id_professor")));

            listaDisciplina.add(d);
        }

        return listaDisciplina;
    }

    @Override
    public int count() throws SQLException, ClassNotFoundException {

        c = ConnectionFactory.getConnection();

        String sql = "SELECT count(*) as qtde " +
                "FROM fpoo.disciplina;";

        PreparedStatement pst = c.prepareStatement(sql);

        ResultSet rs = pst.executeQuery();

        rs.next();

        return rs.getInt("qtde");
    }
}
