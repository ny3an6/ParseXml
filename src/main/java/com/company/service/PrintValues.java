package com.company.service;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class PrintValues {

    public static void getAttribute(NodeList nodeList) {
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) { // либо nodeList.item(i) instanceof Element
                if(((Element) node).hasAttribute("step") && ((Element) node).hasAttribute("name") ){
                    String step = ((Element) node).getAttribute("step").trim().toLowerCase();
                    String name = ((Element) node).getAttribute("name").trim().toLowerCase();
                    if(step.equals("1") && name.equals("гражданство")){
                        NamedNodeMap attribute = node.getAttributes();
                        for(int j = 0; j < attribute.getLength(); j++){
                            Node node1 = attribute.item(j);
                            System.out.println("   2) "+node1.getNodeName()+ " :" + node1.getNodeValue());
                        }
                    }
                }
                if (node.hasChildNodes()) {
                    getAttribute(node.getChildNodes());
                }
            }
        }
    }

    private String normalizeString(String value){
        return value.trim().toLowerCase();
    }
}
