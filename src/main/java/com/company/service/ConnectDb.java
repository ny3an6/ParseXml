package com.company.service;

import com.company.models.Documents;

import java.io.Closeable;
import java.io.IOException;
import java.sql.*;
import java.util.List;

public class ConnectDb implements Closeable {
    private String url;
    private String username;
    private String password;

    private static final String SQL_INSERT_DOCUMENTS =  "insert into xmldocuments(name) values (?)";

    public ConnectDb(String url, String username, String password, String dbDriver) throws ClassNotFoundException {

        this.url = url;
        this.username = username;
        this.password = password;
        Class.forName("org." + dbDriver + ".Driver");
    }

    public void addDocuments(List<Documents> documents) {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            for (Documents doc : documents) {
                PreparedStatement ps = connection.prepareStatement(SQL_INSERT_DOCUMENTS);
                System.out.println("   3) "+doc.getValue());
                ps.setString(1, doc.getValue());
                ps.executeUpdate();
                ps.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
        @Override
        public void close() throws IOException {

    }
}


// закрытие потоков




