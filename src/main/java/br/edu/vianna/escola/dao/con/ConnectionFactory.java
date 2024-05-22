package br.edu.vianna.escola.dao.con;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {


    public static final String url = "jdbc:mysql://localhost:3306/fpoo";
    public static final String user = "aluno";
    public static final String PASSWORD = "aluno";

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(url,
                    user,
                    PASSWORD);

    }

    public static void main(String[] args) {
        try {
            Connection c = getConnection();
            System.out.println("connectou");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("ERRO");
        }
        //System.out.println(c.getCatalog());

    }


}
