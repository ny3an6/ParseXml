package com.company.service;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.logging.Logger;


public class ValuesService {
    private static final Logger logger2 = Logger.getLogger("com.company.service");

    public static void print(NodeList nodeList) {
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) { // либо nodeList.item(i) instanceof Element
                Element element = (Element) node;
                if(element.hasAttribute("step") && element.hasAttribute("name") ){
                    String step = element.getAttribute("step");
                    String name = element.getAttribute("name");
                    if(normalizeString(step).equals("1") && normalizeString(name).equals("гражданство")){
                        NamedNodeMap attribute = node.getAttributes();
                        for(int j = 0; j < attribute.getLength(); j++){
                            Node node1 = attribute.item(j);
                            logger2.info("   2) " + node1.getNodeName() + " :" + node1.getNodeValue());
                        }
                    }
                }
                if (node.hasChildNodes()) {
                    print(node.getChildNodes());
                }
            }
        }
    }

    private static String normalizeString(String value){
        return value.trim().toLowerCase();
    }
}
