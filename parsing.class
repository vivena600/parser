package org.example;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class parsing {

    public static void downloadCsv(String fileUrl, String localFilename) throws IOException {
        HttpURLConnection httpConn = (HttpURLConnection) new URL(fileUrl).openConnection();
        httpConn.addRequestProperty("User-Agent", "Mozilla/4.76");
        try (InputStream inputStream = httpConn.getInputStream();
             FileOutputStream outputStream = new FileOutputStream(new File(localFilename))) {

            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            System.out.println("CSV file downloaded successfully: " + localFilename);
        }
    }

    public static Document parseCsv(String localFilename) throws IOException, CsvException, ParserConfigurationException, TransformerException {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.newDocument();

        Element rootElement = doc.createElement("san_objects");
        doc.appendChild(rootElement);

        try (CSVReader csvReader = new CSVReader(new FileReader(localFilename))) {
            List<String[]> records = csvReader.readAll();
            String[] headers = records.get(0);
            for (int i = 1; i < records.size(); i++) {
                String[] row = records.get(i);

                Element sanObject = doc.createElement("san_object");
                rootElement.appendChild(sanObject);

                String id = row[1];
                String type = row[0];
                String extid = row[2];
                String dateStart = row[8];
                String dateEnd = row[9];
                String lastUpdate = ""; // Пустое значение для last_update
                StringBuilder valText = new StringBuilder();

                valText.append(row[0]).append(",");
                for (int j = 2; j <= 7; j++) {
                    valText.append(row[j]).append(",");
                }
                valText.append(row[10]);

                createElementWithValue(doc, sanObject, "id", id);
                createElementWithValue(doc, sanObject, "type-aka", type);
                createElementWithValue(doc, sanObject, "extid", extid);
                createElementWithValue(doc, sanObject, "date_start", dateStart);
                createElementWithValue(doc, sanObject, "date_end", dateEnd);
                createElementWithValue(doc, sanObject, "last_update", lastUpdate);

                Element values = doc.createElement("values");
                sanObject.appendChild(values);

                Element value = doc.createElement("value");
                values.appendChild(value);

                createElementWithValue(doc, value, "number", String.valueOf(i - 1));
                createElementWithValue(doc, value, "typeid", "1"); // примерное значение для typeid
                createElementWithValue(doc, value, "val_text", valText.toString());
            }
        }
        return doc;
    }

    public static void createElementWithValue(Document doc, Element parentElement, String tagName, String textContent) {
        Element element = doc.createElement(tagName);
        element.appendChild(doc.createTextNode(textContent));
        parentElement.appendChild(element);
    }
}

