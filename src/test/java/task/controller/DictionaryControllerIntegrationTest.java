package task.controller;

import io.restassured.module.mockmvc.specification.MockMvcRequestSpecification;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import task.service.DictionaryService;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;

@SpringBootTest(webEnvironment = DEFINED_PORT)
public class DictionaryControllerIntegrationTest {

    MockMvcRequestSpecification spec = given().standaloneSetup(new DictionaryController(new DictionaryService()));
    private static final String EMPTY = "[]";
    private static final String CONTAINS_CAT_AND_DOG = "[\"cat\",\"dog\"]";
    private static final String CONTAINS_CAT = "[\"cat\"]";


    @Test
    public void shouldReturnEmptyListIfNoWordsFound() {
        spec.get("/dictionary/containsV1?line=abc&ignoreCase=true")
                .then()
                .statusCode(200)
                .body(is(equalTo(EMPTY)));
    }

    @Test
    public void shouldReturnFoundWords() {
        spec.get("/dictionary/containsV1?line=catblablavladog&ignoreCase=true")
                .then()
                .statusCode(200)
                .body(is(equalTo(CONTAINS_CAT_AND_DOG)));
    }

    @Test
    public void shouldReturnFoundWordsCaseSensetive() {
        spec.get("/dictionary/containsV1?line=catblablavlaDog&ignoreCase=false")
                .then()
                .statusCode(200)
                .body(is(equalTo(CONTAINS_CAT)));
    }

    @Test
    public void shouldExecuteCaseSensetiveRequestIfFlagNotPassed() {
        spec.get("/dictionary/containsV1?line=catblablavlaDog")
                .then()
                .statusCode(200)
                .body(is(equalTo(CONTAINS_CAT)));
    }

    @Test
    public void shouldReturnEmptyListIfNoWordsFoundAhoCorasick() {
        spec.get("/dictionary/containsV2?line=abc&ignoreCase=true")
                .then()
                .statusCode(200)
                .body(is(equalTo(EMPTY)));
    }

    @Test
    public void shouldReturnFoundWordsAhoCorasick() {
        spec.get("/dictionary/containsV2?line=catblablavladog&ignoreCase=true")
                .then()
                .statusCode(200)
                .body(is(equalTo(CONTAINS_CAT_AND_DOG)));
    }

    @Test
    public void shouldReturnFoundWordsCaseSensetiveAhoCorasick() {
        spec.get("/dictionary/containsV2?line=catblablavlaDog&ignoreCase=false")
                .then()
                .statusCode(200)
                .body(is(equalTo(CONTAINS_CAT)));
    }

    @Test
    public void shouldExecuteCaseSensitiveAhoCorasickRequestIfFlagNotPassed() {
        spec.get("/dictionary/containsV2?line=catblablavlaDog")
                .then()
                .statusCode(200)
                .body(is(equalTo(CONTAINS_CAT)));
    }
}
