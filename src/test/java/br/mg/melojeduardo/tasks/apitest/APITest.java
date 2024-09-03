package br.mg.melojeduardo.tasks.apitest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.CoreMatchers;
import org.junit.BeforeClass;
import org.junit.Test;

public class APITest {

    //É executado uma vez antes de todos testes, antes mesmo da classe ser instanciada, deve ser estático
    @BeforeClass
    public static void setup() {
        RestAssured.baseURI = "http://localhost:8001/tasks-backend";
    }

    @Test
    public void shouldReturnAllTasks() {
        RestAssured.given()
                .when().get("/todo")
                .then().statusCode(200);
    }

    @Test
    public void shouldAddTaskSuccessfully() {
        RestAssured.given()
                .body("{ \"task\": \"Teste via API\", \"dueDate\": \"2025-12-30\" }")
                .contentType(ContentType.JSON)
                .when().post("/todo")
                .then().statusCode(201);
    }

    @Test
    public void shouldNotAddInvalidTask() {
        RestAssured.given()
                .body("{ \"task\": \"Teste via API\", \"dueDate\": \"2010-12-30\" }")
                .contentType(ContentType.JSON)
                .when().post("/todo")
                .then().log().all().statusCode(400)
                .body("message", CoreMatchers.is("Due date must not be in past"));
    }

}