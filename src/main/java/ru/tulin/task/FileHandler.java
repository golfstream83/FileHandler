package ru.tulin.task;

import java.io.*;
import java.util.*;

public class FileHandler {

    public static void main(String[] args) {
        FileHandler fileHandler = new FileHandler();
        Set<String> bufferSet = fileHandler.readFile(fileHandler.getFilePath());
        Set<String> resultSet = fileHandler.getAllCyclicDependenciesIds(bufferSet);
        fileHandler.printResult(resultSet);
    }

    public File getFilePath () {
        String filePath = null;

        System.out.println("Enter the full path to the file (example for Windows C:/yourfile.txt)");
        try (BufferedReader brConsole = new BufferedReader(new InputStreamReader(System.in))) {
            filePath = brConsole.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new File(filePath);
    }

    public Set<String> readFile(File filePath) {
        Set<String> set = new HashSet<>();
        String lineFile;

        if (filePath != null) {
            try (BufferedReader brFile = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)))) {
                while ((lineFile = brFile.readLine()) != null) {
                    set.add(lineFile);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return set;
    }

    public Set<String> getAllCyclicDependenciesIds(Set<String> bufferSet) {
        Set<String> resultSet = new HashSet<>();
        Set<String> usedPair = new HashSet<>();

        for (String pair : bufferSet) {
            String[] array = pair.split(" ");
            String reversePair = String.format("%s %s", array[1], array[0]);

            if (!usedPair.contains(pair) || !usedPair.contains(reversePair)) {
                Set<String> usedId = new HashSet<>();
                List<String> listDependenciesId;
                StringBuilder sequence = new StringBuilder();
                String firstId = array[0];
                String secondId = array[1];

                sequence.append(pair);
                usedId.add(firstId);
                listDependenciesId = getPairsDependentId(secondId, bufferSet, usedId);

                if (!listDependenciesId.isEmpty()) {
                    String secondIdLastPair = listDependenciesId.get(listDependenciesId.size() - 1).split(" ")[1];

                    if (secondIdLastPair.equals(firstId)) {
                        for (String pairIds : listDependenciesId) {
                            String[] arrayId = pairIds.split(" ");
                            String secondIdPair = arrayId[1];
                            String reversePairIds = String.format("%s %s", arrayId[1], arrayId[0]);

                            usedPair.add(pairIds);
                            usedPair.add(reversePairIds);
                            sequence.append(" ").append(secondIdPair);
                        }
                        resultSet.add(sequence.toString());
                    }
                }
            }
        }

        return resultSet;
    }

    public List<String> getPairsDependentId(String mainId, Set<String> bufferSet, Set<String> usedId) {
        List<String> resultList = new ArrayList<>();

        for (String pair : bufferSet) {
            String[] array = pair.split(" ");
            String firstId = array[0];
            String secondId = array[1];

            if (firstId.equals(mainId) && !usedId.contains(firstId)) {
                resultList.add(pair);

                if (!usedId.contains(secondId)) {
                    usedId.add(mainId);
                    resultList.addAll(getPairsDependentId(secondId, bufferSet, usedId));
                }

                usedId.add(firstId);
                usedId.add(secondId);
            }
        }

        return resultList;
    }

    public void printResult (Set<String> resultSet) {

        if (resultSet.isEmpty()) {
            System.out.println("No cyclic dependencies");
        } else {
            for (String string : resultSet) {
                System.out.println(string);
            }
        }
    }
}