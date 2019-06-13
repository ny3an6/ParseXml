package com.company.service;

import com.company.models.Documents;

import java.io.Closeable;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ConnectDb implements Closeable {
    private String url;
    private String username;
    private String password;
    private Connection connection;

    private static final String SQL_INSERT_DOCUMENTS = "insert into xmldocuments(name) values (?)";
    private static final String SQL_SELECT_ALL = "select * from xmldocuments";
    private static final String SQL_SELECT_BY_ID = "select * from xmldocuments where id = ?";

    public ConnectDb(String url, String username, String password, String dbDriver) throws ClassNotFoundException, SQLException {
        this.url = url;
        this.username = username;
        this.password = password;
        this.connection = DriverManager.getConnection(url, username, password);
        Class.forName("org." + dbDriver + ".Driver");
    }

    public void addDocuments(List<Documents> documents) {
        try {
            for (Documents doc : documents) {
                PreparedStatement ps = connection.prepareStatement(SQL_INSERT_DOCUMENTS);
                System.out.println("   3) " + doc.getValue());
                ps.setString(1, doc.getValue());
                ps.executeUpdate();
                ps.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public List<Documents> findAll() {
        try {
            List<Documents> documentsFromDb = new ArrayList<>();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL);
            while (resultSet.next()) {
                String name = resultSet.getString("name");

                Documents documentFromDb = new Documents(name);

                documentsFromDb.add(documentFromDb);
            }
            return documentsFromDb;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    public Documents findById(Integer id) {
        try {
            Documents documentId = new Documents();
            PreparedStatement pr = connection.prepareStatement(SQL_SELECT_BY_ID);
            pr.setInt(1, id);
            ResultSet resultSet = pr.executeQuery();
            if (resultSet.next()) {
                Integer idDoc = resultSet.getInt("id");
                String name = resultSet.getString("name");
                return new Documents(idDoc, name);
            }
           return documentId;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }


    @Override
    public void close() throws IOException {

    }
}


// закрытие потоков




