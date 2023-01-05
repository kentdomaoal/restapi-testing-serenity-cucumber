package com.project.serenity.stepdefinitions;

import io.cucumber.java.Before;
import io.cucumber.java.ParameterType;
import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import net.thucydides.core.environment.SystemEnvironmentVariables;
import net.thucydides.core.util.EnvironmentVariables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ParameterDefinitions {
    private static final Logger LOGGER = LoggerFactory.getLogger(ParameterDefinitions.class);

    @ParameterType(".*")
    public Actor actor(String actorName) {
        LOGGER.debug("ACTOR: {}",OnStage.theActorInTheSpotlight().getName());
        if(OnStage.theActorInTheSpotlight().getName() != null){
            return OnStage.theActorInTheSpotlight();
        } else{
            return OnStage.theActorCalled(actorName).whoCan(CallAnApi.at(theRestApiBaseUrl()));
        }
    }

    @Before
    public void setTheStage() {
        OnStage.setTheStage(new OnlineCast());
        OnStage.theActorCalled("actor").whoCan(CallAnApi.at(theRestApiBaseUrl()));
    }

    public static String theRestApiBaseUrl() {
        EnvironmentVariables environmentVariables = SystemEnvironmentVariables.createEnvironmentVariables();
        return  EnvironmentSpecificConfiguration.from(environmentVariables)
                .getProperty("restapi.baseurl");
    }
}
