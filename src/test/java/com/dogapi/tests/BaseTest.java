package com.dogapi.tests;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;

/**
 * Base test class with common RestAssured configuration.
 * All test classes should extend this to inherit the shared setup.
 */
public abstract class BaseTest {

    protected static final String BASE_URL = "https://dog.ceo/api";
    protected static final String STATUS_SUCCESS = "success";
    protected static final String STATUS_ERROR = "error";
    protected static final long MAX_RESPONSE_TIME_MS = 5000;

    protected static RequestSpecification requestSpec;

    @BeforeAll
    static void setupRestAssured() {
        RestAssured.baseURI = BASE_URL;

        requestSpec = new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .setContentType(ContentType.JSON)
                .addFilter(new AllureRestAssured())
                .log(LogDetail.URI)
                .log(LogDetail.METHOD)
                .build();
    }
}
