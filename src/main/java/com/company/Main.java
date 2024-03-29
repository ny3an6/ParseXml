package com.company;

import com.company.service.CreateStructureDocument;
import com.company.service.DbService;
import com.company.models.Documents;
import com.company.service.CreateCollectionService;
import com.company.service.ValuesService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Logger;


public class Main {
    private static final String URL_H2 = "jdbc:h2:mem:xmldocuments";
    private static final String DB_DRIVER_H2 = "h2";
    private static final String FILE_LOCATION = "src/main/resources/file.xml";
    private static final Logger logger = Logger.getGlobal();


    public static void main(String[] args) {
        try {
            CreateStructureDocument document = new CreateStructureDocument(FILE_LOCATION);
            ArrayList<Documents> documents = new ArrayList<>();
            CreateCollectionService.getListOfDocuments(document.getFile().getDocumentElement().getChildNodes(), documents);


            System.out.println("First task: ");
            for (Documents doc : documents) {
                logger.info("   1) "+doc.getValue());
            }

            System.out.println("Second task: ");
            ValuesService.print(document.getFile().getDocumentElement().getChildNodes());

            System.out.println("Third task: ");
            DbService connectDb = new DbService(URL_H2, DB_DRIVER_H2);
            connectDb.addDocuments(documents);

            // findAll
            for (Documents documents1 : connectDb.findAll()){
                System.out.println("DocumentsFromDb: "+documents1.getId()+" "+documents1.getValue());
            }

            // findById
            Documents id = connectDb.findById(4);
            System.out.println(id.getId()+"  "+id.getValue());
            connectDb.close();

        } catch (ClassNotFoundException | SQLException | IOException e) {
            e.printStackTrace();
        }




    }
}



