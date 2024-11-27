package com.example.mipt_5_praktinis;

import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.util.ArrayList;

public class Parser {

    private static final String TAG = "Parser";

    public static Document parseXML(InputStream inputStream) {
        Log.d(TAG, "parseXML method called");
        try {
            javax.xml.parsers.DocumentBuilderFactory factory = javax.xml.parsers.DocumentBuilderFactory.newInstance();
            javax.xml.parsers.DocumentBuilder builder = factory.newDocumentBuilder();
            return builder.parse(inputStream);
        } catch (Exception e) {
            Log.e(TAG, "Error in parseXML: " + e.getMessage(), e);
            return null;
        }
    }

    public static ArrayList<String> extractCurrencyRates(Document document) {
        Log.d(TAG, "extractCurrencyRates method called");
        ArrayList<String> currencyRates = new ArrayList<>();

        try {
            NodeList nodeList = document.getElementsByTagName("item");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                String targetCurrency = getNodeValue(node, "targetCurrency");
                String exchangeRate = getNodeValue(node, "exchangeRate");

                if (targetCurrency != null && exchangeRate != null) {
                    currencyRates.add(targetCurrency + " – " + exchangeRate);
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Error in extractCurrencyRates: " + e.getMessage(), e);
        }

        return currencyRates;
    }

    private static int logCounter = 0;

    private static String getNodeValue(Node node, String tagName) {
        logCounter++;
        // Log every 50th call
        if (logCounter % 50 == 0) {
            Log.d(TAG, "getNodeValue method called for tag: " + tagName);
        }

        try {
            NodeList childNodes = node.getChildNodes();
            for (int i = 0; i < childNodes.getLength(); i++) {
                Node childNode = childNodes.item(i);
                if (childNode.getNodeName().equals(tagName)) {
                    return childNode.getTextContent();
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Error in getNodeValue for tag: " + tagName + " - " + e.getMessage(), e);
        }
        return null;
    }

}
