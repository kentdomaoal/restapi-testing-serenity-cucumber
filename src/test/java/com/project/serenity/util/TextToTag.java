package com.project.serenity.util;

import com.opencsv.bean.AbstractCsvConverter;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.project.serenity.models.Tag;

public class TextToTag extends AbstractCsvConverter {
    @Override
    public Object convertToRead(String value) throws CsvDataTypeMismatchException, CsvConstraintViolationException {
        Tag tag = new Tag();
        String[] split = value.split(" ", 2);
        tag.setId(Long.parseLong(split[0]));
        tag.setName(split[1]);
        return tag;
    }
}
