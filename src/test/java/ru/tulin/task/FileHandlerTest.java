package ru.tulin.task;

import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.Test;

import java.io.File;
import java.net.URL;
import java.util.*;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class FileHandlerTest {

    FileHandler fileHandler = new FileHandler();

    @Test
    public void testWithoutCyclicDependenciesId() throws Exception {
        Set<String> bufferSet;
        URL url = this.getClass().getResource("/test1.txt");
        File filePath = new File(url.getFile());

        bufferSet = fileHandler.readFile(filePath);

        Set<String> resultSet = fileHandler.getAllCyclicDependenciesIds(bufferSet);

        assertThat(
                resultSet.isEmpty(),
                is(true)
        );
    }

    @Test
    public void testWithSingleDigitIdNumbers() throws Exception {
        String expectedString = "[1 2 3 1]";
        Set<String> bufferSet;
        URL url = this.getClass().getResource("/test2.txt");
        File filePath = new File(url.getFile());

        bufferSet = fileHandler.readFile(filePath);

        Set<String> resultSet = fileHandler.getAllCyclicDependenciesIds(bufferSet);

        assertEquals(expectedString, resultSet.toString());
    }

    @Test
    public void testWithTwoDigitIdNumbers() throws Exception {
        String expectedString = "[11 10 11]";
        Set<String> bufferSet;
        URL url = this.getClass().getResource("/test3.txt");
        File filePath = new File(url.getFile());

        bufferSet = fileHandler.readFile(filePath);

        Set<String> resultSet = fileHandler.getAllCyclicDependenciesIds(bufferSet);

        assertEquals(expectedString, resultSet.toString());
    }

}