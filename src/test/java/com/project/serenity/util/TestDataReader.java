package com.project.serenity.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.bean.CsvToBeanBuilder;
import com.project.serenity.models.Pet;
import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.thucydides.core.environment.SystemEnvironmentVariables;
import net.thucydides.core.util.EnvironmentVariables;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.formula.functions.T;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class TestDataReader {

    private static String getFilePath(String filename){
        EnvironmentVariables environmentVariables = SystemEnvironmentVariables.createEnvironmentVariables();
        String testdataPath =  EnvironmentSpecificConfiguration.from(environmentVariables)
                .getProperty("testdata.path");
        return System.getProperty("user.dir") + testdataPath + filename;
    }

    public static List<Map<String,String>> fromExcel(String filename, String sheet)
            throws IOException, InvalidFormatException {
        ExcelReader reader = new ExcelReader();
        return reader.getData(getFilePath(filename), sheet);
    }

    public static <T> List<T> fromCSV(String filename, Class<T> clazz) throws FileNotFoundException {
        return new CsvToBeanBuilder(new FileReader(getFilePath(filename)))
                .withType(clazz).build().parse();
    }
    public static <T> T[] fromJSON(String filename,Class clazz) throws IOException {
        return (T[]) new ObjectMapper().readValue(new File(getFilePath(filename)), clazz);
    }

}
