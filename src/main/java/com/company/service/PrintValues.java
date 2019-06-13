package com.company.service;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class PrintValues {

    public void getAttribute(NodeList nodeList) {
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) { // либо nodeList.item(i) instanceof Element
                if(((Element) node).hasAttribute("step") && ((Element) node).hasAttribute("name") ){
                    String stepCont = ((Element) node).getAttribute("step").trim().toLowerCase();
                    String nameCont = ((Element) node).getAttribute("name").trim().toLowerCase();
                    if(stepCont.contains("1") && nameCont.contains("гражданство")){
                        NamedNodeMap attribute = node.getAttributes();
                        for(int j = 0; j < attribute.getLength(); j++){
                            Node asd = attribute.item(j);
                            System.out.println("   2) "+asd.getNodeName()+ " :" + asd.getNodeValue());
                        }
                    }
                }
                if (node.hasChildNodes()) {
                    getAttribute(node.getChildNodes());
                }
            }
        }
    }
}
