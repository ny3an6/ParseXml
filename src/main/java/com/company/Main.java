package com.company;

import com.company.service.CreateStructureDocument;
import com.company.service.DbService;
import com.company.models.Documents;
import com.company.service.CreateCollectionService;
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
    private static final String URL = "jdbc:postgresql://172.20.1.30:32771/cabinet";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "postgres";
    private static final String DBDRIVER = "postgresql";
    private static final String FILELOCATION = "src/main/resources/file.xml";

    public static void main(String[] args) {
        try {
            CreateStructureDocument document = new CreateStructureDocument(FILELOCATION);
            ArrayList<Documents> documents = new ArrayList<>();
            CreateCollectionService.getListOfDocuments(document.getFile().getDocumentElement().getChildNodes(), documents);
            // TODO Replace sout -> logger
            System.out.println("First task: ");
            for (Documents doc : documents) {
                System.out.println("   1) "+doc.getValue());
            }

            System.out.println("Second task: ");
            PrintValues.getAttribute(document.getFile().getDocumentElement().getChildNodes());

            System.out.println("Third task: ");
            DbService connectDb = new DbService(URL,USERNAME, PASSWORD, DBDRIVER);
            connectDb.addDocuments(documents);

            // findAll
            for (Documents documents1 : connectDb.findAll()){
                System.out.println("DocumentsFromDb: "+documents1.getValue());
            }

            // findById
            Documents id = connectDb.findById(138);
            System.out.println(id.getId()+"  "+id.getValue());
            connectDb.close();

        } catch (ClassNotFoundException | SQLException | IOException e) {
            e.printStackTrace();
        }




    }
}



