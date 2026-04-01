package com.dogapi.tests;

import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

/**
 * Tests for the GET /breeds/list/all endpoint.
 * Validates breed listing, structure, known breeds, and sub-breeds.
 */
@Epic("Dog API")
@Feature("List All Breeds")
@Tag("breeds")
@DisplayName("GET /breeds/list/all")
class ListAllBreedsTest extends BaseTest {

    private static final String ENDPOINT = "/breeds/list/all";

    @Test
    @Severity(SeverityLevel.BLOCKER)
    @Description("Verify that the endpoint returns HTTP 200 and status 'success'")
    @DisplayName("Should return 200 OK with success status")
    void shouldReturnSuccessStatus() {
        given()
                .spec(requestSpec)
        .when()
                .get(ENDPOINT)
        .then()
                .statusCode(200)
                .body("status", equalTo(STATUS_SUCCESS));
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that the response Content-Type header is application/json")
    @DisplayName("Should return Content-Type application/json")
    void shouldReturnJsonContentType() {
        given()
                .spec(requestSpec)
        .when()
                .get(ENDPOINT)
        .then()
                .statusCode(200)
                .contentType("application/json");
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that the 'message' field contains a non-empty object with breed data")
    @DisplayName("Should return a non-empty breeds object in message")
    void shouldReturnNonEmptyBreedsObject() {
        given()
                .spec(requestSpec)
        .when()
                .get(ENDPOINT)
        .then()
                .statusCode(200)
                .body("message", is(notNullValue()))
                .body("message.size()", greaterThan(0));
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that well-known breeds are present in the response")
    @DisplayName("Should contain well-known breeds like labrador, bulldog, poodle")
    void shouldContainKnownBreeds() {
        given()
                .spec(requestSpec)
        .when()
                .get(ENDPOINT)
        .then()
                .statusCode(200)
                .body("message", hasKey("labrador"))
                .body("message", hasKey("bulldog"))
                .body("message", hasKey("poodle"))
                .body("message", hasKey("husky"))
                .body("message", hasKey("akita"));
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that breeds with known sub-breeds return correct sub-breed arrays")
    @DisplayName("Should return correct sub-breeds for bulldog")
    void shouldReturnCorrectSubBreedsForBulldog() {
        given()
                .spec(requestSpec)
        .when()
                .get(ENDPOINT)
        .then()
                .statusCode(200)
                .body("message.bulldog", hasItems("boston", "english", "french"))
                .body("message.bulldog.size()", equalTo(3));
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that breeds without sub-breeds return an empty array")
    @DisplayName("Should return empty sub-breeds array for labrador")
    void shouldReturnEmptySubBreedsForLabrador() {
        given()
                .spec(requestSpec)
        .when()
                .get(ENDPOINT)
        .then()
                .statusCode(200)
                .body("message.labrador", is(empty()));
    }

    @Test
    @Severity(SeverityLevel.MINOR)
    @Description("Verify that the response time is within acceptable limits")
    @DisplayName("Should respond within acceptable time limit")
    void shouldRespondWithinTimeLimit() {
        given()
                .spec(requestSpec)
        .when()
                .get(ENDPOINT)
        .then()
                .statusCode(200)
                .time(lessThan(MAX_RESPONSE_TIME_MS));
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that the response contains a significant number of breeds (100+)")
    @DisplayName("Should return at least 100 breeds")
    void shouldReturnAtLeast100Breeds() {
        given()
                .spec(requestSpec)
        .when()
                .get(ENDPOINT)
        .then()
                .statusCode(200)
                .body("message.size()", greaterThanOrEqualTo(100));
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that sub-breeds for hound include known types")
    @DisplayName("Should return correct sub-breeds for hound")
    void shouldReturnCorrectSubBreedsForHound() {
        given()
                .spec(requestSpec)
        .when()
                .get(ENDPOINT)
        .then()
                .statusCode(200)
                .body("message.hound", hasItems("afghan", "basset", "blood", "english", "ibizan", "plott", "walker"));
    }
}
