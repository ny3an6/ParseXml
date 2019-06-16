package com.company.service;

import com.company.models.Documents;
import org.w3c.dom.*;
import java.util.Comparator;
import java.util.List;

public class CreateCollectionService {

    public static void getListOfDocuments(NodeList nodeList, List<Documents> documents) {
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {// nodeList.item(i) instanceof Element
                    Element element = (Element) node;
                    if (element.hasAttribute("name") && element.hasAttribute("fullname")) {
                        String name = element.getAttribute("name");
                        String fullName = element.getAttribute("fullname");
                        if (normalizeString(name).equals("вид_док") && normalizeString(fullName).equals("вид документа")) {
                            NodeList childNode = element.getElementsByTagName("par_list");
                            for (int j = 0; j < childNode.getLength(); j++) {
                                NamedNodeMap attribute = childNode.item(j).getAttributes();
                                documents.add(new Documents(attribute.getNamedItem("value").getNodeValue()));
                            }
                        }
                    }
                    if (node.hasChildNodes()) {
                        getListOfDocuments(node.getChildNodes(), documents);
                    }
                }
            }
            // Check for duplicate items of array
            for (int i = 0; i < documents.size(); i++) {
                Documents firstPlaceCell = documents.get(i);
                for (int j = i + 1; j < documents.size(); j++) {
                    Documents secondPlaceCell = documents.get(j);
                    if (firstPlaceCell.getValue().equals(secondPlaceCell.getValue())) {
                        documents.remove(firstPlaceCell);
                    }
                }
            }
            documents.sort(Comparator.comparing(Documents::getValue));
    }

    private static String normalizeString(String value){
        return value.trim().toLowerCase();
    }
}


