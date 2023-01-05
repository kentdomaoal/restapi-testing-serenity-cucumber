package com.project.serenity.models;


import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    @CsvBindByName(column = "categoryID")
    private long id;
    @CsvBindByName(column = "categoryName")
    private String name;
}
