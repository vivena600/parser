package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class parsingHtml {
    //Турция Измир, L2

    public static void downloadHtml(String fileUrl, String localFilename) throws IOException {
        Document doc = Jsoup.connect(fileUrl).get();
        String htmlContent = doc.html();

        try (FileWriter writer = new FileWriter(localFilename)) {
            writer.write(htmlContent);
            System.out.println("HTML file downloaded successfully: " + localFilename);
        }
    }

    public static org.w3c.dom.Document parseHtml(File htmlFile) throws IOException, ParserConfigurationException {
        Document htmlDoc = Jsoup.parse(htmlFile, "UTF-8");

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        DOMImplementation domImpl = dBuilder.getDOMImplementation();
        org.w3c.dom.Document xmlDoc = domImpl.createDocument(null, "san_objects", null);
        Node rootElement = xmlDoc.getDocumentElement();

        Elements nodes = htmlDoc.select("td.tailSTxt");
        int index = 0;
        for (Element node : nodes) {
            if (!node.text().startsWith("2.")) {
                continue;
            }

            Elements items = node.select("tr");
            for (Element item : items) {
                org.w3c.dom.Element sanObject = xmlDoc.createElement("san_object");
                rootElement.appendChild(sanObject);

                String number = String.valueOf(index++);
                String id = item.select("td.sProvP1No").text();
                String text = item.select("td.sProvP1").text().trim().replaceAll("[;.]", "");

                createElementWithValue(xmlDoc, sanObject, "id", id);
                createElementWithValue(xmlDoc, sanObject, "type-aka", "sanction");
                createElementWithValue(xmlDoc, sanObject, "extid", "");
                createElementWithValue(xmlDoc, sanObject, "date_start", "");
                createElementWithValue(xmlDoc, sanObject, "date_end", "");
                createElementWithValue(xmlDoc, sanObject, "last_update", "");

                org.w3c.dom.Element values = xmlDoc.createElement("values");
                sanObject.appendChild(values);

                org.w3c.dom.Element value = xmlDoc.createElement("value");
                values.appendChild(value);

                createElementWithValue(xmlDoc, value, "number", number);
                createElementWithValue(xmlDoc, value, "typeid", "1");
                createElementWithValue(xmlDoc, value, "val_text", text);
            }
        }

        return xmlDoc;
    }

    private static void createElementWithValue(org.w3c.dom.Document doc, org.w3c.dom.Element parentElement, String tagName, String textContent) {
        org.w3c.dom.Element element = doc.createElement(tagName);
        element.appendChild(doc.createTextNode(textContent));
        parentElement.appendChild(element);
    }

    public static void saveXmlDocument(org.w3c.dom.Document doc, String filePath) throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(filePath));
        transformer.transform(source, result);
    }
}
