package com.company;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.*;


public class Main {

    public static void main(String[] args) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File("file.xml"));
            Node root = document.getDocumentElement(); // получили первый тег и з нашего xml(корневой элемент)
            ArrayList<Documents> documents = new ArrayList<>(); // Список всех документов
            printElement(root.getChildNodes(), documents);
            documents.sort(Comparator.comparing(Documents::getValue));
            System.out.println("First task :");
            Postgres postgres = new Postgres();
            postgres.connection(documents);
            for (Documents doc : documents) {
                System.out.println("      Документ: "+doc.getValue());
            }

        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }


    }

    private static void printElement(NodeList nodeList, List<Documents> documents) { // nodeList это список всех эл-тов внутри корневого эл-та(order)
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) { // либо nodeList.item(i) instanceof Element
                if (((Element) node).hasAttribute("value")) {
                    String valueCont = ((Element) node).getAttribute("value").trim().toLowerCase();
                    if (valueCont.contains("паспорт") || valueCont.contains("свид") ||
                            valueCont.contains("инн") || valueCont.contains("удостовер") || valueCont.contains("билет")
                            || valueCont.contains("разреш"))
                    {
                        NamedNodeMap attribute = node.getAttributes();
                        documents.add(new Documents(attribute.getNamedItem("value").getNodeValue()));
                    }
                }
                if(((Element) node).hasAttribute("step") && ((Element) node).hasAttribute("name") ){
                    String stepCont = ((Element) node).getAttribute("step").trim().toLowerCase();
                    String nameCont = ((Element) node).getAttribute("name").trim().toLowerCase();
                    if(stepCont.contains("1") && nameCont.contains("гражданство")){
                        NamedNodeMap attribute = node.getAttributes();
                        for(int j = 0; j < attribute.getLength(); j++){
                            Node asd = attribute.item(j);
                            System.out.println("      Аттрибут: "+asd.getNodeName()+ " :" + asd.getNodeValue());
                        }
                    }
                }
                if (node.hasChildNodes()) {
                    printElement(node.getChildNodes(), documents);
                }
            }
        }
    }
}



