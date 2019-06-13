package com.company;

import com.company.service.ConnectDb;
import com.company.models.Documents;
import com.company.service.CreateCollection;
import com.company.service.PrintValues;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;


public class Main {
    private static final String url = "jdbc:postgresql://172.20.1.30:32771/cabinet";
    private static final String username = "postgres";
    private static final String password = "postgres";
    private static final String dbDriver = "postgresql";

    public static void main(String[] args) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File("src/main/resources/file.xml"));

            ArrayList<Documents> documents = new ArrayList<>();

            System.out.println("First task: ");
            CreateCollection cc = new CreateCollection();
            cc.getListOfDocuments(document.getDocumentElement().getChildNodes(), documents);
            for (Documents doc : documents) {
                System.out.println("   1) "+doc.getValue());
            }

            System.out.println("Second task: ");
            PrintValues printValues = new PrintValues();
            printValues.getAttribute(document.getDocumentElement().getChildNodes());

            System.out.println("Third task: ");
            ConnectDb connectDb = new ConnectDb(url,username, password, dbDriver);
            connectDb.addDocuments(documents);

            // findAll
            for (Documents documents1 : connectDb.findAll()){
                System.out.println("DocumentsFromDb: "+documents1.getValue());
            }

            // findById
            Documents id = connectDb.findById(138);
            System.out.println(id.getId()+"  "+id.getValue());


        } catch (ParserConfigurationException | IOException | SAXException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }


    }
}



