package com.project.serenity.tasks;

import com.project.serenity.models.Category;
import com.project.serenity.models.Pet;
import com.project.serenity.models.PetStatus;
import com.project.serenity.models.Tag;
import com.project.serenity.util.TestDataReader;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapperType;
import lombok.var;
import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Post;
import net.thucydides.core.environment.SystemEnvironmentVariables;
import net.thucydides.core.util.EnvironmentVariables;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class AddRecord {

    public static AddRecord ofPet(){
        return new AddRecord();
    }

    public static Performable ofPet(String petCategory){
        Pet pet = Pet.builder().id(1).name("Blackie").status(PetStatus.available.name())
                .category(new Category(1,petCategory)).build();

        return Task.where("Add record of pet "+petCategory,
                actor -> {
                    actor.attemptsTo(addNewPet(pet));
                    actor.remember("pet",pet);
                }
        );
    }

    public Performable fromExcel(String filename, String sheet)
            throws IOException, InvalidFormatException {
        List<Map<String,String>> testData = TestDataReader.fromExcel(filename, sheet);
        return Task.where("Add record of pet(s) from Excel",
                actor -> {
                    for (Map<String,String> row: testData) {
                        if(!row.get("petID").isEmpty()) {
                            Pet pet = Pet.builder()
                                    .id(Long.parseLong(row.get("petID")))
                                    .name(row.get("petName"))
                                    .status(row.get("status"))
                                    .category(new Category(Long.parseLong(row.get("categoryID")), row.get("categoryName")))
                                    .tag(new Tag(Long.parseLong(row.get("tagID")), row.get("tagName")))
                                    .photoUrl(row.get("photoUrl"))
                                    .build();
                            actor.attemptsTo(addNewPet(pet));
                        }
                    }
                }
        );
    }

    public Performable fromCSV(String filename)
            throws IOException, InvalidFormatException {
        List<Pet> pets = TestDataReader.fromCSV(filename, Pet.class);
        return Task.where("Add record of pet(s) from CSV",
                actor -> {
                    for (Pet pet: pets) {
                        actor.attemptsTo(addNewPet(pet));
                    }
                }
        );
    }

    public Performable fromJSON(String filename)
            throws IOException, InvalidFormatException {
        List<Pet> pets = Arrays.asList(TestDataReader.fromJSON(filename, Pet[].class));
        return Task.where("Add record of pet(s) from JSON",
                actor -> {
                    for (Pet pet: pets) {
                        actor.attemptsTo(addNewPet(pet));
                    }
                }
        );
    }

    private static Interaction addNewPet(Pet pet){
        return Post.to("/pet")
                .with(request -> request
                        .accept(ContentType.JSON)
                        .contentType(ContentType.JSON)
                        .body(pet, ObjectMapperType.GSON)
                );
    }


}
