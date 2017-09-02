package ru.tulin.task;

import java.io.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class FileHandler {

    public static void main(String[] args) {

        System.out.println("Введите полный путь к файлу (пример для Windows C:/yourfile.txt)");

        try (BufferedReader brConsole = new BufferedReader(new InputStreamReader(System.in));
             BufferedReader brFile = new BufferedReader(new InputStreamReader(new FileInputStream(new File(brConsole.readLine()))))) {

                Set<String> bufferSet = new HashSet<>();
                Set<String> resultSet = new HashSet<>();
                String lineFile;

                while ((lineFile = brFile.readLine()) != null) {
                    bufferSet.add(lineFile);
                }

                Iterator<String> iterator = bufferSet.iterator();

                while (iterator.hasNext()) {
                    String pair = iterator.next();
                    String pairReverse = new StringBuilder(pair).reverse().toString();

                    if (bufferSet.contains(pairReverse) && !resultSet.contains(pairReverse)) {
                        resultSet.add(pair);
                    }
                }

                for (String string : resultSet) {
                    System.out.println(String.format("%s %s", string, string.charAt(0)));
                }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}