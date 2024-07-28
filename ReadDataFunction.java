package org.example;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.opencsv.exceptions.CsvException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class ReadDataFunction {
    static ArrayList<String> fileData = new ArrayList<>();
    static ArrayList<String> urlData = new ArrayList<>();
    static ArrayList<String> formatData = new ArrayList<>();
    static Document combinedXmlDoc; // Общий XML документ для всех данных
    static Scanner in = new Scanner(System.in);

    public static void findUrl(String directoryPath) throws IOException, ParserConfigurationException, TransformerException, CsvException {
        processDirectory(directoryPath);
        analyzeFormat(fileData, urlData, formatData);
    }

    private static void processDirectory(String directoryPath) throws IOException, ParserConfigurationException, TransformerException, CsvException {
        File directory = new File(directoryPath);

        if (!directory.exists() || !directory.isDirectory()) {
            System.out.println("Directory does not exist or is not a directory: " + directoryPath);
            return;
        }

        processSubdirectory(directory);
    }

    private static void processSubdirectory(File subdirectory) throws IOException, ParserConfigurationException, TransformerException, CsvException {
        File[] filesAndDirs = subdirectory.listFiles();
        if (filesAndDirs != null) {
            for (File fileOrDir : filesAndDirs) {
                if (fileOrDir.isDirectory()) {
                    processSubdirectory(fileOrDir);
                } else if (fileOrDir.isFile() && (fileOrDir.getName().endsWith(".yaml") || fileOrDir.getName().endsWith(".yml"))) {
                    System.out.println("Processing file: " + fileOrDir.getAbsolutePath());
                    parseYamlFile(fileOrDir);
                }
            }
        } else {
            System.out.println("No files found in directory: " + subdirectory.getAbsolutePath());
        }
    }

    private static void parseYamlFile(File yamlFile) {
        try (FileInputStream fis = new FileInputStream(yamlFile)) {
            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
            JsonNode rootNode = mapper.readTree(fis);

            if (rootNode == null) {
                System.out.println("Empty YAML file: " + yamlFile.getAbsolutePath());
                return;
            }

            JsonNode dataNode = rootNode.get("data");
            if (dataNode != null) {
                JsonNode urlNode = dataNode.get("url");
                JsonNode formatNode = dataNode.get("format");

                if (urlNode != null && formatNode != null) {
                    String url = urlNode.asText();
                    String format = formatNode.asText();
                    String file = yamlFile.getName();
                    System.out.println("File: " + yamlFile.getName());
                    System.out.println("Data URL: " + urlNode.asText());
                    System.out.println("Data Format: " + formatNode.asText());
                    System.out.println("-----------------------");

                    fileData.add(file);
                    urlData.add(url);
                    formatData.add(format);
                } else {
                    System.out.println("File: " + yamlFile.getName() + " does not contain 'url' or 'format' tags within 'data'.");
                }
            } else {
                System.out.println("File: " + yamlFile.getName() + " does not contain 'data' tag.");
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error reading file: " + yamlFile.getAbsolutePath());
        }
    }

    public static void addToCombinedXml(Document docToAdd) {
        if (combinedXmlDoc == null) {
            combinedXmlDoc = docToAdd;
        } else {
            Element rootElementToAdd = docToAdd.getDocumentElement();
            combinedXmlDoc.getDocumentElement().appendChild(combinedXmlDoc.importNode(rootElementToAdd, true));
        }
    }

    private static void analyzeFormat(ArrayList<String> fileData, ArrayList<String> urlData, ArrayList<String> formatData) throws IOException, ParserConfigurationException, TransformerException, CsvException {
        System.out.println("Начинаю парсинг !");
        for (int i = 0; i < fileData.size(); i++) {
            // Parsing CSV file and adding data to combined XML document
            if (formatData.get(i).equalsIgnoreCase("CSV")) {
                System.out.println("File: " + fileData.get(i));
                System.out.println("Data URL: " + urlData.get(i));
                System.out.println("Data Format: " + formatData.get(i));
                System.out.println("-----------------------");

                System.out.println("Введите имя отпарсированного файла (csv) ");
                String localFilename = in.nextLine();
                parsing.downloadCsv(urlData.get(i), localFilename);
                Document csvDoc = parsing.parseCsv(localFilename);
                addToCombinedXml(csvDoc);
            }

            if (formatData.get(i).equalsIgnoreCase("HTML")) {
                System.out.println("File: " + fileData.get(i));
                System.out.println("Data URL: " + urlData.get(i));
                System.out.println("Data Format: " + formatData.get(i));
                System.out.println("-----------------------");
                System.out.println("Введите имя отпарсированного файла (html) : ");
                String localFilename = in.nextLine();

                // Скачивание HTML-файла по URL
                parsingHtml.downloadHtml(urlData.get(i), localFilename);
                File htmlFile = new File(localFilename);
                Document xmlDocument = parsingHtml.parseHtml(htmlFile);
                addToCombinedXml(xmlDocument);
            }

            // Parsing XLSX file and adding data to combined XML document
            /*if (formatData.get(i).equalsIgnoreCase("XLSX")) {
                System.out.println("File: " + fileData.get(i));
                System.out.println("Data URL: " + urlData.get(i));
                System.out.println("Data Format: " + formatData.get(i));
                System.out.println("-----------------------");

                System.out.println("Введите имя отпарсированного файла: ");
                String localFilename = in.nextLine();

                parsing.downloadCsv(urlData.get(i), localFilename);

                //parsingXslx.find_url_xslx(urlData.get(i), localFilename);

                //Document xlsxDoc = ParseXLSX.parseXlsx(localFilename);
                //addToCombinedXml(xlsxDoc);
                }

             */
        }
        System.out.println("Введите название файла для сохранения (с расширением .xml): ");
        String outputXmlFilePath = in.nextLine();
        output_file.save(combinedXmlDoc, outputXmlFilePath);
    }
}
