package com.dogapi.tests;

import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

/**
 * Tests for the GET /breed/{breed}/images endpoint.
 * Validates breed image listing, URL format, and error handling.
 */
@Epic("Dog API")
@Feature("Breed Images")
@Tag("images")
@DisplayName("GET /breed/{breed}/images")
class BreedImagesTest extends BaseTest {

    private static final String ENDPOINT = "/breed/{breed}/images";

    @Test
    @Severity(SeverityLevel.BLOCKER)
    @Description("Verify that requesting images for a valid breed returns HTTP 200 and status 'success'")
    @DisplayName("Should return 200 OK for valid breed 'hound'")
    void shouldReturnSuccessForValidBreed() {
        given()
                .spec(requestSpec)
                .pathParam("breed", "hound")
        .when()
                .get(ENDPOINT)
        .then()
                .statusCode(200)
                .body("status", equalTo(STATUS_SUCCESS));
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that the 'message' field contains a non-empty array of image URLs")
    @DisplayName("Should return a non-empty array of images for valid breed")
    void shouldReturnNonEmptyImageArray() {
        given()
                .spec(requestSpec)
                .pathParam("breed", "hound")
        .when()
                .get(ENDPOINT)
        .then()
                .statusCode(200)
                .body("message", is(not(empty())))
                .body("message.size()", greaterThan(0));
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that all returned image URLs point to the dog.ceo image domain")
    @DisplayName("Should return URLs from images.dog.ceo domain")
    void shouldReturnUrlsFromDogCeoDomain() {
        given()
                .spec(requestSpec)
                .pathParam("breed", "labrador")
        .when()
                .get(ENDPOINT)
        .then()
                .statusCode(200)
                .body("message", everyItem(startsWith("https://images.dog.ceo/breeds/")));
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that all image URLs end with a valid image file extension")
    @DisplayName("Should return URLs ending with valid image extensions")
    void shouldReturnUrlsWithValidImageExtensions() {
        given()
                .spec(requestSpec)
                .pathParam("breed", "labrador")
        .when()
                .get(ENDPOINT)
        .then()
                .statusCode(200)
                .body("message", everyItem(
                        anyOf(
                                endsWith(".jpg"),
                                endsWith(".jpeg"),
                                endsWith(".png"),
                                endsWith(".gif")
                        )
                ));
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that image URLs for a breed contain the breed name in their path")
    @DisplayName("Should return URLs containing the breed name in path")
    void shouldReturnUrlsContainingBreedName() {
        given()
                .spec(requestSpec)
                .pathParam("breed", "labrador")
        .when()
                .get(ENDPOINT)
        .then()
                .statusCode(200)
                .body("message", everyItem(containsString("labrador")));
    }

    @ParameterizedTest(name = "Breed: {0}")
    @ValueSource(strings = {"labrador", "poodle", "husky", "akita", "beagle"})
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that multiple valid breeds return images successfully")
    @DisplayName("Should return images for multiple valid breeds")
    void shouldReturnImagesForMultipleBreeds(String breed) {
        given()
                .spec(requestSpec)
                .pathParam("breed", breed)
        .when()
                .get(ENDPOINT)
        .then()
                .statusCode(200)
                .body("status", equalTo(STATUS_SUCCESS))
                .body("message", is(not(empty())));
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that requesting images for an invalid breed returns HTTP 404 and error status")
    @DisplayName("Should return 404 for invalid breed name")
    void shouldReturn404ForInvalidBreed() {
        given()
                .spec(requestSpec)
                .pathParam("breed", "invalidbreedxyz123")
        .when()
                .get(ENDPOINT)
        .then()
                .statusCode(404)
                .body("status", equalTo(STATUS_ERROR))
                .body("message", containsString("not found"));
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that requesting images for a breed with special characters returns error")
    @DisplayName("Should handle breed name with special characters")
    void shouldHandleSpecialCharactersInBreedName() {
        given()
                .spec(requestSpec)
                .pathParam("breed", "!@#$%")
        .when()
                .get(ENDPOINT)
        .then()
                .statusCode(404)
                .body("status", equalTo(STATUS_ERROR));
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that requesting images for an empty breed name returns error")
    @DisplayName("Should return error for empty breed name")
    void shouldReturnErrorForEmptyBreedName() {
        given()
                .spec(requestSpec)
        .when()
                .get("/breed//images")
        .then()
                .statusCode(404);
    }

    @Test
    @Severity(SeverityLevel.MINOR)
    @Description("Verify that breed image endpoint responds within acceptable time limit")
    @DisplayName("Should respond within acceptable time limit")
    void shouldRespondWithinTimeLimit() {
        given()
                .spec(requestSpec)
                .pathParam("breed", "akita")
        .when()
                .get(ENDPOINT)
        .then()
                .statusCode(200)
                .time(lessThan(MAX_RESPONSE_TIME_MS));
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify the Content-Type header is application/json for breed images endpoint")
    @DisplayName("Should return Content-Type application/json")
    void shouldReturnJsonContentType() {
        given()
                .spec(requestSpec)
                .pathParam("breed", "hound")
        .when()
                .get(ENDPOINT)
        .then()
                .statusCode(200)
                .contentType("application/json");
    }
}
