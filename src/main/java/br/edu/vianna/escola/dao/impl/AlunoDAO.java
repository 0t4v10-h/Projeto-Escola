package br.edu.vianna.escola.dao.impl;

import br.edu.vianna.escola.Utils.CryptoUtils;
import br.edu.vianna.escola.dao.GenericsDAO;
import br.edu.vianna.escola.dao.con.ConnectionFactory;
import br.edu.vianna.escola.model.Aluno;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class AlunoDAO implements GenericsDAO<Aluno, Integer> {

    private Connection c;

    @Override
    public void inserir(Aluno aluno) throws SQLException, ClassNotFoundException {
        // 4 passos???
        // connectar
        c = ConnectionFactory.getConnection();
        // comando sql
        String sql = "INSERT INTO fpoo.aluno\n" +
                "(nome, email, login, senha, matricula," +
                " valor_mensalidade)\n" +
                "VALUES(?, ?, ?, ?, ?, ?);";
        //preparar execução
        PreparedStatement pst = c.prepareStatement(sql);
        pst.setString(1, aluno.getNome() );
        pst.setString(2, aluno.getEmail() );
        pst.setString(3, aluno.getLogin() );
        try {
            pst.setString(4, CryptoUtils.md5(aluno.getSenha() ) );
        } catch (NoSuchAlgorithmException e) {
            throw new SQLException("erro ao gerar a senha");
        }
        pst.setString(5, aluno.getMatricula() );
        pst.setDouble(6, aluno.getValorMensalidade() );
        //Execução
        pst.execute();
    }

    @Override
    public void alterar(Aluno aluno) throws SQLException, ClassNotFoundException {
        // 4 passos???
        // connectar
        c = ConnectionFactory.getConnection();
        // comando sql
        String sql = "UPDATE fpoo.aluno\n" +
                "SET nome=?, email=?, login=?, " +
                "senha=?, matricula=?, " +
                "valor_mensalidade=? " +
                "WHERE idaluno=?;";
        //preparar execução
        PreparedStatement pst = c.prepareStatement(sql);
        pst.setString(1, aluno.getNome() );
        pst.setString(2, aluno.getEmail() );
        pst.setString(3, aluno.getLogin() );
        try {
            pst.setString(4, CryptoUtils.md5(aluno.getSenha() ) );
        } catch (NoSuchAlgorithmException e) {
            throw new SQLException("erro ao gerar a senha");
        }
        pst.setString(5, aluno.getMatricula() );
        pst.setDouble(6, aluno.getValorMensalidade() );
        pst.setInt(7, aluno.getId() );
        //Execução
        pst.execute();
    }

    @Override
    public void apagar(Aluno aluno) throws SQLException, ClassNotFoundException {
        // 4 passos???
        // connectar
        c = ConnectionFactory.getConnection();
        // comando sql
        String sql = "DELETE FROM fpoo.aluno " +
                "WHERE idaluno=?;";
        //preparar execução
        PreparedStatement pst = c.prepareStatement(sql);

        pst.setInt(1, aluno.getId() );
        //Execução
        pst.execute();
    }

    @Override
    public Aluno buscarUm(Integer id) throws SQLException, ClassNotFoundException {
        // 5 passos???
        // connectar
        c = ConnectionFactory.getConnection();
        // comando sql
        String sql = "SELECT idaluno, nome, email, login, senha, " +
                "data_ultimo_acesso, matricula, valor_mensalidade" +
                " FROM fpoo.aluno " +
                "WHERE idaluno=?;";
        //preparar execução
        PreparedStatement pst = c.prepareStatement(sql);

        pst.setInt(1, id);
        //Execução
        ResultSet rs = pst.executeQuery();
        // preencher o Objeto
        Aluno a = null;
        if (rs.next()) {
            a = new Aluno();
            a.setId(rs.getInt("idaluno"));
            a.setMatricula(rs.getString("matricula"));
            a.setValorMensalidade(rs.getDouble("valor_mensalidade"));
            a.setNome(rs.getString("nome"));
            a.setEmail(rs.getString("email"));
            a.setLogin(rs.getString("login"));
            a.setSenha(rs.getString("senha"));
            String dt = rs.getString("data_ultimo_acesso");
            //2024-05-01 22:00:00.000
            if (dt != null && !dt.isEmpty() && !dt.isBlank()) {
                LocalDateTime dt1 = LocalDateTime.parse(dt,
                        DateTimeFormatter.ofPattern("YYYY-mm-dd HH:MM"));
                a.setDataUltimoAcesso(dt1);
            }
        }

        return a;
    }

    public Aluno buscarAlunoByLoginAndSenha(String login, String senha) throws SQLException, ClassNotFoundException {

        c = ConnectionFactory.getConnection();
        String sql = "SELECT idaluno, nome, email, login, senha, " +
                "data_ultimo_acesso, matricula, valor_mensalidade" +
                " FROM fpoo.aluno " +
                "WHERE login=? and senha=?;";
        //preparar execução
        PreparedStatement pst = c.prepareStatement(sql);

        pst.setString(1, login);
        try {
            pst.setString(2, CryptoUtils.md5(senha) );
        } catch (NoSuchAlgorithmException e) {
            throw new SQLException("Erro na Criptografia");
        }
        //Execução
        ResultSet rs = pst.executeQuery();
        // preencher o Objeto
        Aluno a = null;
        if (rs.next()) {
            a = new Aluno();
            a.setId(rs.getInt("idaluno"));
            a.setMatricula(rs.getString("matricula"));
            a.setValorMensalidade(rs.getDouble("valor_mensalidade"));
            a.setNome(rs.getString("nome"));
            a.setEmail(rs.getString("email"));
            a.setLogin(rs.getString("login"));
            a.setSenha(rs.getString("senha"));
            String dt = rs.getString("data_ultimo_acesso");
            //2024-05-01 22:00:00.000
            if (dt != null && !dt.isEmpty() && !dt.isBlank()) {
                LocalDateTime dt1 = LocalDateTime.parse(dt,
                        DateTimeFormatter.ofPattern("YYYY-mm-dd HH:MM"));
                a.setDataUltimoAcesso(dt1);
            }
        }

        return a;
    }


    @Override
    public ArrayList<Aluno> buscarTodos() throws SQLException, ClassNotFoundException {
        // 5 passos???
        // connectar
        c = ConnectionFactory.getConnection();
        // comando sql
        String sql = "SELECT idaluno, nome, email, login, senha, " +
                "data_ultimo_acesso, matricula, valor_mensalidade" +
                " FROM fpoo.aluno ";
        //preparar execução
        PreparedStatement pst = c.prepareStatement(sql);

        //Execução
        ResultSet rs = pst.executeQuery();
        // preencher o Objeto
        ArrayList<Aluno> listaAlunos = new ArrayList<>();
        while (rs.next()) {

            String dt = rs.getString("data_ultimo_acesso");
            LocalDateTime dt1 = null;
            if (dt != null && !dt.isEmpty() && !dt.isBlank()) {
                dt1 = LocalDateTime.parse(dt,
                        DateTimeFormatter.ofPattern("YYYY-mm-dd HH:MM"));
            }
            Aluno a = new Aluno(rs.getInt("idaluno"),rs.getString("nome"),
                    rs.getString("email"),rs.getString("login"),
                    rs.getString("senha"),dt1,rs.getString("matricula"),
                    rs.getDouble("valor_mensalidade"));
            listaAlunos.add(a);
        }

        return listaAlunos;
    }

    @Override
    public int count() throws SQLException, ClassNotFoundException {
        // 5 passos???
        // connectar
        c = ConnectionFactory.getConnection();
        // comando sql
        String sql = "SELECT count(*) qtde " +
                " FROM fpoo.aluno ";
        //preparar execução
        PreparedStatement pst = c.prepareStatement(sql);

        //Execução
        ResultSet rs = pst.executeQuery();
        // preencher o Objeto
        rs.next();

        return rs.getInt("qtde");
    }
}