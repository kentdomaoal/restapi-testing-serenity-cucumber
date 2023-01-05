package com.project.serenity.stepdefinitions;

import com.project.serenity.models.Pet;
import com.project.serenity.models.Tag;
import com.project.serenity.tasks.AddRecord;
import com.project.serenity.tasks.CheckPet;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.specification.RequestSpecification;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.rest.interactions.Get;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.project.serenity.stepdefinitions.ParameterDefinitions.theRestApiBaseUrl;
import static net.serenitybdd.rest.SerenityRest.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class PetApiSteps {

    private static RequestSpecification request(){
        return given()
                .baseUri(theRestApiBaseUrl())
                .basePath("/pet")
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON);
    }

    @Given("a pet {} is available in the store")
    public void pet_is_available_in_the_store(String petCategory)
            throws IOException, InvalidFormatException {
        // serenity screenplay pattern
        Actor actor = OnStage.theActorInTheSpotlight();
        actor.attemptsTo(
                AddRecord.ofPet(petCategory)
        );
    }

    @Given("pets are available in the store")
    public void pets_are_available_in_the_store()
            throws IOException, InvalidFormatException {
        // serenity screenplay pattern
        Actor actor = OnStage.theActorInTheSpotlight();
        actor.attemptsTo(
                AddRecord.ofPet("dog"),
                AddRecord.ofPet().fromExcel("Pets.xlsx","Pets"),
                AddRecord.ofPet().fromCSV("Pets.csv"),
                AddRecord.ofPet().fromJSON("Pets.json")
        );
    }

    @When("{actor} search for {} pets")
    public void search_for_pets(Actor actor, String status) {
        request().queryParam("status", status)
                .when().get("/findByStatus");
    }

    public void searching_for_pets(Actor actor, String status) {
        // serenity screenplay pattern
        actor.attemptsTo(
                Get.resource("/pet/findByStatus").with(request -> request.queryParam("status", status))
        );
    }
    @Then("{} with id: {} should be available")
    public void pet_with_id_should_be_available(String petName, long petID, DataTable table) {
        List<Pet> pets = lastResponse().jsonPath().getList(".",Pet.class);

        // serenity screenplay pattern
        Actor actor = OnStage.theActorInTheSpotlight();
        actor.attemptsTo(CheckPet.withTheseDetails(pets, petName, petID));
    }

    @Then("following pet(s) should be available")
    public void pet_should_be_available(DataTable table) {
        List<Pet> pets = lastResponse().jsonPath().getList(".",Pet.class);
        List<Map<String, String>> rows = table.asMaps(String.class, String.class);
        for (Map<String, String> columns : rows) {
            // serenity screenplay pattern
            Actor actor = OnStage.theActorInTheSpotlight();
            actor.attemptsTo(
                    CheckPet.withTheseDetails(pets, columns.get("petName"), Long.parseLong(columns.get("petID")))
            );
        }
    }

    @When("{actor} add the tag(s) '{}' on {}'s record")
    public void add_tags_on_pet_record(Actor actor, String tag, String petName) {
        Pet pet = actor.recall("pet");
        pet.setTags(List.of(new Tag(1,tag)));
        request().body(pet, ObjectMapperType.GSON)
                .when().put();
    }
    @Then("{} process should be successful")
    public void process_should_be_successful(String process) {
        restAssuredThat(response -> response.statusCode(200));
    }
    @When("{actor} search for a pet with id {}")
    public void search_for_pet(Actor actor, int petID) {
        request().pathParam("petID",petID)
                .when().get("/{petID}");
    }
    @Then("{}'s record contains the tag '{}'")
    public void pet_record_contains_tag(String petName, String tag) {
        restAssuredThat(response -> response.body("tags.name", hasItems(tag)));
    }
    @When("{actor} update {}'s status to {}")
    public void update_pet_status_to_sold(Actor actor, String petName, String status) {
        Pet pet = actor.recall("pet");
        pet.setStatus(status);
        request().contentType("application/x-www-form-urlencoded")
                .pathParam("petID",pet.getId())
                .formParam("status",status)
                .when().post("/{petID}");
    }
    @Then("{}'s status is now {}")
    public void pet_status_is_updated(String petName, String status) {
        restAssuredThat(response -> response.body("status", equalToIgnoringCase(status)));
    }
    @When("{actor} delete {}'s record")
    public void delete_pet_record(Actor actor, String petName) {
        Pet pet = actor.recall("pet");
        request().pathParam("petID",pet.getId())
                .when().delete("/{petID}");
    }
    @Then("{}'s record should no longer be found")
    public void pet_record_should_no_longer_be_found(String petName) {
        restAssuredThat(response -> response.body("message", equalToIgnoringCase("Pet not found")));
    }

}
