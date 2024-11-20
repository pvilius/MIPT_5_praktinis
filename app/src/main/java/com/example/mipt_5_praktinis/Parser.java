package com.example.mipt_5_praktinis;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.util.ArrayList;

public class Parser {

    public static Document parseXML(InputStream inputStream) {
        try {
            javax.xml.parsers.DocumentBuilderFactory factory = javax.xml.parsers.DocumentBuilderFactory.newInstance();
            javax.xml.parsers.DocumentBuilder builder = factory.newDocumentBuilder();
            return builder.parse(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ArrayList<String> extractCurrencyRates(Document document) {
        ArrayList<String> currencyRates = new ArrayList<>();

        NodeList nodeList = document.getElementsByTagName("item");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            String targetCurrency = getNodeValue(node, "targetCurrency");
            String exchangeRate = getNodeValue(node, "exchangeRate");

            if (targetCurrency != null && exchangeRate != null) {
                currencyRates.add(targetCurrency + " – " + exchangeRate);
            }
        }
        return currencyRates;
    }

    private static String getNodeValue(Node node, String tagName) {
        NodeList childNodes = node.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node childNode = childNodes.item(i);
            if (childNode.getNodeName().equals(tagName)) {
                return childNode.getTextContent();
            }
        }
        return null;
    }
}