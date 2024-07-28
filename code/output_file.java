package org.example;

import org.w3c.dom.Document;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.nio.file.Paths;
import java.util.Scanner;

public class output_file {
    public static void save(Document xmlDoc, String outputXmlFilePath) {

        try {
            // Запись XML документа в файл
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty("indent", "yes");
            DOMSource source = new DOMSource(xmlDoc);
            StreamResult result = new StreamResult(Paths.get(outputXmlFilePath).toFile());

            transformer.transform(source, result);
            System.out.println("Файл успешно сохранен как " + outputXmlFilePath);

            Main.exit_prog(); //завершение работы

        } catch (TransformerException e) {
            e.printStackTrace();
            System.out.println("Ошибка при сохранении файла.");
        }
    }
}
