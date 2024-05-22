package br.edu.vianna.escola.dao;

import br.edu.vianna.escola.model.Aluno;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface GenericsDAO<C,K> {

    public void inserir( C obj) throws SQLException, ClassNotFoundException;
    public void alterar( C obj) throws SQLException, ClassNotFoundException;
    public void apagar( C obj) throws SQLException, ClassNotFoundException;
    public C buscarUm( K id) throws SQLException, ClassNotFoundException;
    public ArrayList<C> buscarTodos( ) throws SQLException, ClassNotFoundException;
    public int count( ) throws SQLException, ClassNotFoundException;


}
