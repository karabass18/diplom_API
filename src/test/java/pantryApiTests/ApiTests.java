package pantryApiTests;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.sleep;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;

public class ApiTests {
    String myPantryId = "2f572a5a-b13f-4981-9970-93993fbb22bb";


    @BeforeAll
    public static void setUp() {
        RestAssured.baseURI = "https://getpantry.cloud/apiv1";

    }

   /* @Test
    public void putDetailsTest() {

        given()
                .filter(new AllureRestAssured())
                .contentType("application/json")
                .body("{\n" +
                        "\t\"name\": \"My account\",\n" +
                        "\t\"description\": \"my test\"\n" +
                        "}")
                .when()
                .put("/pantry/" + myPantryId)
                .then()
                .log().all()
                .statusCode(200);

    }

    @Test
    public void getDetailsTest() {

        given()
                .filter(new AllureRestAssured())
                .contentType("application/json")
                .when()
                .get("/pantry/" + myPantryId)
                .then()
                .log().all()
                .statusCode(200);

    }*/

    @Test
    public void basketTest() {
        String basketName = "TestBasket";

        step("Изменения в pantry", () -> {
            given()
                    .filter(new AllureRestAssured())
                    .contentType("application/json")
                    .body("{\n" +
                            "\t\"name\": \"My account\",\n" +
                            "\t\"description\": \"my test\"\n" +
                            "}")
                    .when()
                    .put("/pantry/" + myPantryId)
                    .then()
                    .log().all()
                    .statusCode(200);
        });

        sleep(2000); //Установлено из-за огрантчнгий сервиса

        step("Данные из pantry", () -> {
            given()
                    .filter(new AllureRestAssured())
                    .contentType("application/json")
                    .when()
                    .get("/pantry/" + myPantryId)
                    .then()
                    .log().all()
                    .statusCode(200);
        });

        sleep(2000); //Установлено из-за огрантчнгий сервиса

        step("Создание basket: " + basketName, () -> {
            given()
                    .filter(new AllureRestAssured())
                    .contentType("application/json")
                    .body("{\n" +
                            "\t\"key1\": \"test11\",\n" +
                            "\t\"key2\": \"test22\"\n" +
                            "}")
                    .when()
                    .post("/pantry/" + myPantryId + "/basket/" + basketName)
                    .then()
                    .log().all()
                    .statusCode(200);
        });
        sleep(2000); //Установлено из-за огрантчнгий сервиса
        step("Изменение basket: " + basketName, () -> {
            given()
                    .filter(new AllureRestAssured())
                    .contentType("application/json")
                    .body("{\n" +
                            "\t\"key1\": \"test33\",\n" +
                            "\t\"key2\": \"test44\"\n" +
                            "}")
                    .when()
                    .put("/pantry/" + myPantryId + "/basket/" + basketName)
                    .then()
                    .log().all()
                    .statusCode(200);

        });
        sleep(2000); //Установлено из-за огрантчнгий сервиса
        step("Данные из basket: " + basketName, () -> {
            given()
                    .filter(new AllureRestAssured())
                    .contentType("application/json")
                    .body("{\n" +
                            "\t\"key1\": \"test33\",\n" +
                            "\t\"key2\": \"test44\"\n" +
                            "}")
                    .when()
                    .get("/pantry/" + myPantryId + "/basket/" + basketName)
                    .then()
                    .log().all()
                    .statusCode(200);
        });
        sleep(2000); //Установлено из-за огрантчнгий сервиса
        step("Удаления basket: " + basketName, () -> {
            given()
                    .filter(new AllureRestAssured())
                    .contentType("application/json")
                    .when()
                    .delete("/pantry/" + myPantryId + "/basket/" + basketName)
                    .then()
                    .log().all()
                    .statusCode(200);
        });
    }

}
