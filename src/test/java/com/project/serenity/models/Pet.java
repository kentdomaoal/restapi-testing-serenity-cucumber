package com.project.serenity.models;

import com.opencsv.bean.CsvBindAndSplitByName;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvRecurse;
import com.project.serenity.util.TextToTag;
import lombok.*;

import java.util.LinkedList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Pet {
    @CsvBindByName(column = "petID")
    private long id;
    @CsvRecurse
    private Category category;
    @CsvBindByName(column = "petName")
    private String name;
    @Singular
    @CsvBindAndSplitByName(elementType = String.class, collectionType = LinkedList.class)
    private List<String> photoUrls;
    @Singular
    @CsvBindAndSplitByName(elementType= Tag.class, splitOn = "\\|", converter = TextToTag.class)
    private List<Tag> tags;
    @CsvBindByName
    private String status;

    public Pet(long id, String name, String status) {
        this.id = id;
        this.name = name;
        this.status = status;
    }
}
