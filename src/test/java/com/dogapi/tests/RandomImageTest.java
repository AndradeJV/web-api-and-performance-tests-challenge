package com.dogapi.tests;

import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

/**
 * Tests for the GET /breeds/image/random endpoint.
 * Validates random image retrieval, URL format, randomness, and multiple image requests.
 */
@Epic("Dog API")
@Feature("Random Image")
@Tag("random")
@DisplayName("GET /breeds/image/random")
class RandomImageTest extends BaseTest {

    private static final String ENDPOINT = "/breeds/image/random";
    private static final String ENDPOINT_MULTIPLE = "/breeds/image/random/{count}";

    @Test
    @Severity(SeverityLevel.BLOCKER)
    @Description("Verify that the random image endpoint returns HTTP 200 and status 'success'")
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
    @Description("Verify that the 'message' field contains a valid image URL from dog.ceo")
    @DisplayName("Should return a valid image URL from images.dog.ceo")
    void shouldReturnValidImageUrl() {
        given()
                .spec(requestSpec)
        .when()
                .get(ENDPOINT)
        .then()
                .statusCode(200)
                .body("message", startsWith("https://images.dog.ceo/breeds/"));
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that the returned image URL ends with a valid image file extension")
    @DisplayName("Should return URL ending with valid image extension")
    void shouldReturnUrlWithValidExtension() {
        given()
                .spec(requestSpec)
        .when()
                .get(ENDPOINT)
        .then()
                .statusCode(200)
                .body("message", anyOf(
                        endsWith(".jpg"),
                        endsWith(".jpeg"),
                        endsWith(".png"),
                        endsWith(".gif")
                ));
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that the 'message' field is a non-empty string (not null or blank)")
    @DisplayName("Should return a non-empty message string")
    void shouldReturnNonEmptyMessage() {
        given()
                .spec(requestSpec)
        .when()
                .get(ENDPOINT)
        .then()
                .statusCode(200)
                .body("message", is(notNullValue()))
                .body("message", not(emptyOrNullString()));
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
    @Severity(SeverityLevel.MINOR)
    @Description("Verify that the endpoint responds within acceptable time limit")
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

    // ==================== Multiple Random Images ====================

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that requesting 3 random images returns exactly 3 image URLs")
    @DisplayName("Should return exactly 3 images when requesting 3")
    void shouldReturnExactNumberOfRequestedImages() {
        given()
                .spec(requestSpec)
                .pathParam("count", 3)
        .when()
                .get(ENDPOINT_MULTIPLE)
        .then()
                .statusCode(200)
                .body("status", equalTo(STATUS_SUCCESS))
                .body("message", hasSize(3));
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that requesting 1 random image via multiple endpoint returns exactly 1")
    @DisplayName("Should return exactly 1 image when requesting 1")
    void shouldReturnSingleImageWhenRequestingOne() {
        given()
                .spec(requestSpec)
                .pathParam("count", 1)
        .when()
                .get(ENDPOINT_MULTIPLE)
        .then()
                .statusCode(200)
                .body("message", hasSize(1));
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that requesting 50 (maximum) random images returns exactly 50")
    @DisplayName("Should return maximum of 50 images when requesting 50")
    void shouldReturnMaximum50Images() {
        given()
                .spec(requestSpec)
                .pathParam("count", 50)
        .when()
                .get(ENDPOINT_MULTIPLE)
        .then()
                .statusCode(200)
                .body("status", equalTo(STATUS_SUCCESS))
                .body("message", hasSize(50));
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that all URLs in a multiple random images response are valid image URLs")
    @DisplayName("Should return valid image URLs in multiple random images response")
    void shouldReturnValidUrlsInMultipleResponse() {
        given()
                .spec(requestSpec)
                .pathParam("count", 5)
        .when()
                .get(ENDPOINT_MULTIPLE)
        .then()
                .statusCode(200)
                .body("message", everyItem(startsWith("https://images.dog.ceo/breeds/")))
                .body("message", everyItem(
                        anyOf(
                                endsWith(".jpg"),
                                endsWith(".jpeg"),
                                endsWith(".png"),
                                endsWith(".gif")
                        )
                ));
    }

    // ==================== Randomness Verification ====================

    @Test
    @Severity(SeverityLevel.MINOR)
    @Description("Verify the randomness by making two consecutive requests and checking if at least one URL differs across 10 images")
    @DisplayName("Should demonstrate randomness across multiple requests")
    void shouldDemonstrateRandomness() {
        // Get first batch of 10 random images
        String firstResponse = given()
                .spec(requestSpec)
                .pathParam("count", 10)
        .when()
                .get(ENDPOINT_MULTIPLE)
        .then()
                .statusCode(200)
                .extract()
                .body().asString();

        // Get second batch of 10 random images
        String secondResponse = given()
                .spec(requestSpec)
                .pathParam("count", 10)
        .when()
                .get(ENDPOINT_MULTIPLE)
        .then()
                .statusCode(200)
                .extract()
                .body().asString();

        // With 10 images each, it's extremely unlikely both responses are identical
        org.junit.jupiter.api.Assertions.assertNotEquals(
                firstResponse, secondResponse,
                "Two consecutive requests of 10 random images should very likely differ"
        );
    }
}
