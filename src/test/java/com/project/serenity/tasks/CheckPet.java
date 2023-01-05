package com.project.serenity.tasks;

import com.project.serenity.models.Pet;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class CheckPet {

    public static Performable withTheseDetails(List<Pet> pets, String petName, long petID){
        return Task.where(String.format("Checking if pet %s with id: %o is available",petName,petID),
                actor -> {
                    for (Pet pet: pets) {
                        if(pet.getName() != null && pet.getName().equalsIgnoreCase(petName)){
                            assertThat(pet.getName(), equalTo(petName));
                            assertThat(pet.getId(), equalTo(petID));
                        }
                    }
                }
        );
    }
}
