package org.example;

import com.opencsv.exceptions.CsvException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, ParserConfigurationException, TransformerException, CsvException {
        Scanner input = new Scanner(System.in);

        while (true) {
            System.out.println("Какой санкционный список вы хотите получить?");
            System.out.println("1. Азиатских стран (Китай, Япония, Сингапур)");
            System.out.println("2. Страны Ближнего Востока (ОАЭ, Катар)");
            System.out.println("3. Украина");
            System.out.println("4. Страны ЕС (Германия, Франция, Бельгия, Польша, Латвия)");
            System.out.println("5. Выход");
            System.out.print("Введите номер (1 - 5): ");
            int choice = input.nextInt();
            input.nextLine(); // consume the newline

            String directoryPath;
            switch (choice) {
                case 1:
                    directoryPath = "D:\\DASHAMAIN\\Asia";
                    processDirectory(directoryPath);
                    break;
                case 2:
                    directoryPath = "D:\\DASHAMAIN\\Near_East";
                    processDirectory(directoryPath);
                    exit_prog();
                    break;
                case 3:
                    directoryPath = "D:\\DASHAMAIN\\Ukraine";
                    processDirectory(directoryPath);
                    exit_prog();
                    break;
                case 4:
                    directoryPath = "D:\\DASHAMAIN\\Europe";
                    processDirectory(directoryPath);
                    exit_prog();
                    break;
                case 5:
                    exit_prog();
                    break;
                default:
                    System.out.println("Неверный ввод. Попробуйте снова.");
            }
        }
    }

    private static void processDirectory(String directoryPath) throws IOException, ParserConfigurationException, TransformerException, CsvException {
        ReadDataFunction.findUrl(directoryPath);
    }

    public static void exit_prog(){
        System.out.println("Программа завершает работу.");
        System.exit(0);
    }
}
