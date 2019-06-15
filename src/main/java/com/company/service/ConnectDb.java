package com.company.service;

import com.company.models.Documents;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConnectDb implements AutoCloseable{
    private Connection connection;

    private static final String SQL_INSERT_DOCUMENTS = "insert into xmldocuments(name) values (?)";
    private static final String SQL_SELECT_ALL = "select * from xmldocuments";
    private static final String SQL_SELECT_BY_ID = "select * from xmldocuments where id = ?";

    public ConnectDb(String url, String username, String password, String dbDriver) throws ClassNotFoundException, SQLException {
        this.connection = DriverManager.getConnection(url, username, password);
        Class.forName("org." + dbDriver + ".Driver");
    }

    public void addDocuments(List<Documents> documents) {
        try (PreparedStatement ps = connection.prepareStatement(SQL_INSERT_DOCUMENTS)){
            for (Documents doc : documents) {
                System.out.println("   3) " + doc.getValue());
                ps.setString(1, doc.getValue());
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public List<Documents> findAll() {
        try(Statement statement = connection.createStatement()) {
            List<Documents> documentsFromDb = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL);
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                Documents documentFromDb = new Documents(name);
                documentsFromDb.add(documentFromDb);
            }
            resultSet.close();
            statement.close();
            return documentsFromDb;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    public Documents findById(Integer id) {
        try(PreparedStatement pr = connection.prepareStatement(SQL_SELECT_BY_ID)) {
            Documents documentId = null;
            pr.setInt(1, id);
            ResultSet resultSet = pr.executeQuery();
            if (resultSet.next()) {
                Integer idDoc = resultSet.getInt("id");
                String name = resultSet.getString("name");
                documentId = new Documents(idDoc, name);
            }
            resultSet.close();
            pr.close();
            return documentId;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }


    @Override
    public void close() throws IOException {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}







