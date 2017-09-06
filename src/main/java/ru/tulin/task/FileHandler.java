package ru.tulin.task;

import java.io.*;
import java.util.*;

public class FileHandler {

    public static void main(String[] args) {
        FileHandler fileHandler = new FileHandler();
        List<String> bufferList = fileHandler.readFile(fileHandler.getFilePath());
        List<ArrayList<String>> resultList = fileHandler.getListCyclicDependencies(bufferList);
        fileHandler.printResult(resultList);
    }

    public String getFilePath () {
        String filePath = null;

        System.out.println("Enter the full path to the file (example for Windows C:/yourfile.txt)");
        try (BufferedReader brConsole = new BufferedReader(new InputStreamReader(System.in))) {
            filePath = brConsole.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return filePath;
    }

    public List<String> readFile(String filePath) {
        List<String> list = new ArrayList<>();
        String lineFile;

        if (filePath != null) {
            try (BufferedReader brFile = new BufferedReader(new InputStreamReader(new FileInputStream(new File(filePath))))) {
                while ((lineFile = brFile.readLine()) != null) {
                    list.add(lineFile);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return list;
    }

    public List<ArrayList<String>> getListCyclicDependencies(List<String> bufferList) {
        List<ArrayList<String>> resultList = new ArrayList<>();
        Set<String> set = new HashSet<>();

        for (String pair : bufferList) {
            String pairReverse = new StringBuilder(pair).reverse().toString();

            if (bufferList.contains(pairReverse) && !set.contains(pair)) {
                ArrayList<String> listPairs = new ArrayList<>();
                String leftValuePair = pair.split(" ")[0];
                String rightValuePair = pair.split(" ")[1];
                int countPair = 0;
                int countPairReverse = 0;
                int countCycles;

                listPairs.add(leftValuePair);

                for (String string : bufferList) {
                    if (string.equals(pair)) {
                        countPair++;
                    } else if (string.equals(pairReverse)){
                        countPairReverse++;
                    }
                }

                countCycles = countPair < countPairReverse ? countPair : countPairReverse;

                for (int i = 0; i < countCycles; i++) {
                    listPairs.add(rightValuePair);
                    listPairs.add(leftValuePair);
                }

                resultList.add(listPairs);
            }

            set.add(pair);
            set.add(pairReverse);
        }

        return resultList;
    }

    public void printResult (List<ArrayList<String>> resultList) {
        for (ArrayList<String> arrayList : resultList) {
            StringBuilder sb = new StringBuilder();

            for (String string : arrayList) {
                sb.append(string).append(" ");
            }

            System.out.println(sb.toString().trim());
        }
    }
}