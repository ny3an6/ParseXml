package com.company.service;

import com.company.models.Documents;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.Comparator;
import java.util.List;

public class CreateCollection {

    public void getListOfDocuments(NodeList nodeList, List<Documents> documents) {
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) { // nodeList.item(i) instanceof Element
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
                if (node.hasChildNodes()) {
                    getListOfDocuments(node.getChildNodes(), documents);
                }
            }
        }
        // Check for duplicate items of array
        for (int i=0; i<documents.size(); i++) {
            Documents firstPlaceCell = documents.get(i);
            for (int j=i+1; j<documents.size(); j++) {
                Documents secondPlaceCell = documents.get(j);
                if (firstPlaceCell.getValue().equals(secondPlaceCell.getValue())) {
                    documents.remove(firstPlaceCell);
                }
            }
        }
        documents.sort(Comparator.comparing(Documents::getValue));
    }
}
