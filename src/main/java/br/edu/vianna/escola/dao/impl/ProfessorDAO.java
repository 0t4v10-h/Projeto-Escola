package br.edu.vianna.escola.dao.impl;

import br.edu.vianna.escola.Utils.CryptoUtils;
import br.edu.vianna.escola.dao.GenericsDAO;
import br.edu.vianna.escola.dao.con.ConnectionFactory;
import br.edu.vianna.escola.model.Aluno;
import br.edu.vianna.escola.model.Professor;
import br.edu.vianna.escola.model.esp.EEspecializacao;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ProfessorDAO implements GenericsDAO<Professor, Integer> {
    private Connection c;

    @Override
    public void inserir(Professor professor) throws SQLException, ClassNotFoundException {

        c = ConnectionFactory.getConnection();

        String sql = "INSERT INTO fpoo.professor\n" +
                "(nome, email, login, senha, especializacao, tempo_casa, valor_hora_aula) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?);";

        PreparedStatement pst = c.prepareStatement(sql);
        pst.setString(1, professor.getNome());
        pst.setString(2, professor.getEmail());
        pst.setString(3, professor.getLogin());
        try{
            pst.setString(4, CryptoUtils.md5(professor.getSenha()));
        }catch (NoSuchAlgorithmException e){
            throw new SQLException("Erro ao gerar a senha!");
        }
        pst.setString(5, professor.getEspecializacao().toString());
        pst.setInt(6, professor.getTempoCasa());
        pst.setDouble(7, professor.getValorHoraAula());

        pst.execute();
    }

    @Override
    public void alterar(Professor professor) throws SQLException, ClassNotFoundException {

        c = ConnectionFactory.getConnection();

        String sql = "UPDATE fpoo.profesoor\n" +
                "SET nome=?, email=?, login=?, " +
                "senha=?, especializacao=?, " +
                "tempo_casa=?, valor_hora_aula=? " +
                "WHERE idprofessor=?;";

        PreparedStatement pst = c.prepareStatement(sql);
        pst.setString(1, professor.getNome());
        pst.setString(2, professor.getEmail());
        pst.setString(3, professor.getLogin());
        try{
            pst.setString(4, CryptoUtils.md5(professor.getSenha()));
        }catch (NoSuchAlgorithmException e){
            throw new SQLException("Erro ao gerar a senha!");
        }
        pst.setString(5, professor.getEspecializacao().name());
        pst.setInt(6, professor.getTempoCasa());
        pst.setDouble(7, professor.getValorHoraAula());
        pst.setInt(8, professor.getId());

        pst.execute();
    }

    @Override
    public void apagar(Professor professor) throws SQLException, ClassNotFoundException {

        c = ConnectionFactory.getConnection();

        String sql = "DELETE FROM fpoo.professor " +
                "WHERE idprofessor=?;";

        PreparedStatement pst = c.prepareStatement(sql);
        pst.setInt(1, professor.getId());

        pst.execute();
    }

    @Override
    public Professor buscarUm(Integer id) throws SQLException, ClassNotFoundException {

        c = ConnectionFactory.getConnection();

        String sql = "SELECT nome, email, login, senha, especializacao, tempo_casa, valor_hora_aula, data_ultimo_acesso" +
                "FROM fpoo.professor " +
                "WHERE idprofessor=?;";

        PreparedStatement pst = c.prepareStatement(sql);
        pst.setInt(1, id);

        ResultSet rs = pst.executeQuery();

        Professor p = null;
        if(rs.next()){
            p = new Professor();
            p.setId(rs.getInt("idprofessor"));
            p.setNome(rs.getString("nome"));
            p.setEmail(rs.getString("email"));
            p.setLogin(rs.getString("login"));
            p.setSenha(rs.getString("senha"));
            p.setEspecializacao(EEspecializacao.valueOf(rs.getString("especializacao")));
            p.setTempoCasa(rs.getInt("tempo_casa"));
            p.setValorHoraAula(rs.getDouble("valor_hora_aula"));
            String dt = rs.getString("data_ultimo_acasso");
            if(dt != null && !dt.isEmpty() && !dt.isBlank()){
                LocalDateTime dt1 = LocalDateTime.parse(dt, DateTimeFormatter.ofPattern("YYYY-mm-dd HH:MM"));
                p.setDataUltimoAcesso(dt1);
            }
        }

        return p;
    }

    public Professor buscarAlunoByLoginAndSenha(String login, String senha) throws SQLException, ClassNotFoundException {

        c = ConnectionFactory.getConnection();
        String sql = "SELECT idprofessor, nome, email, login, senha, especializacao, tempo_casa, valor_hora_aula, data_ultimo_acesso" +
                "FROM fpoo.professor " +
                "WHERE login=? and senha=?;";

        PreparedStatement pst = c.prepareStatement(sql);

        pst.setString(1, login);
        try {
            pst.setString(2, CryptoUtils.md5(senha) );
        } catch (NoSuchAlgorithmException e) {
            throw new SQLException("Erro na Criptografia");
        }

        ResultSet rs = pst.executeQuery();

        Professor p = null;
        if (rs.next()) {
            p = new Professor();
            p.setId(rs.getInt("idprofessor"));
            p.setNome(rs.getString("nome"));
            p.setEmail(rs.getString("email"));
            p.setLogin(rs.getString("login"));
            p.setSenha(rs.getString("senha"));
            String dt = rs.getString("data_ultimo_acesso");
            //2024-05-01 22:00:00.000
            if (dt != null && !dt.isEmpty() && !dt.isBlank()) {
                LocalDateTime dt1 = LocalDateTime.parse(dt.substring(0,16),
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                p.setDataUltimoAcesso(dt1);
            }
            p.setEspecializacao(EEspecializacao.valueOf("especializacao"));
            p.setTempoCasa(rs.getInt("tempo_casa"));
            p.setValorHoraAula(rs.getDouble("valor_hora_aula"));
        }

        return p;
    }

    @Override
    public ArrayList<Professor> buscarTodos() throws SQLException, ClassNotFoundException {

        c =ConnectionFactory.getConnection();

        String sql = "SELECT idprofessor, nome, email, login, " +
                "senha, especializacao, tempo_casa, " +
                "valor_hora_aula, data_ultimo_acesso " +
                "FROM fpoo.professor;";

        PreparedStatement pst = c.prepareStatement(sql);

        ResultSet rs = pst.executeQuery();

        ArrayList<Professor> listaProfessores = new ArrayList<>();
        while(rs.next()){
            String dt = rs.getString("data_ultimo_acesso");
            LocalDateTime dt1 = null;
            if(dt != null && !dt.isEmpty() && !dt.isBlank()){
                dt1 = LocalDateTime.parse(dt, DateTimeFormatter.ofPattern("YYYY-mm-dd HH:MM"));
            }
            Professor p = new Professor(rs.getInt("idprofessor"),
                    rs.getString("nome"),
                    rs.getString("email"),
                    rs.getString("login"),
                    rs.getString("senha"),
                    dt1,
                    EEspecializacao.valueOf(rs.getString("especializacao")),
                    rs.getInt("tempo_casa"),
                    rs.getDouble("valor_hora_aula"));

            listaProfessores.add(p);
        }

        return listaProfessores;
    }

    @Override
    public int count() throws SQLException, ClassNotFoundException {

        c = ConnectionFactory.getConnection();

        String sql = "SELECT count(*) as qtde " +
                "FROM fpoo.professor;";

        PreparedStatement pst = c.prepareStatement(sql);

        ResultSet rs = pst.executeQuery();

        rs.next();

        return rs.getInt("qtde");
    }
}
