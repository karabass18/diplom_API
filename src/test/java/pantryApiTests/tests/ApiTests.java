package pantryApiTests.tests;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.sleep;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class ApiTests {
    String myPantryId = "2f572a5a-b13f-4981-9970-93993fbb22bb";


    @BeforeAll
    public static void setUp() {
        RestAssured.baseURI = "https://getpantry.cloud/apiv1";

    }

    String pantryName;

    @Test
    public void pantryTest() {

        step("Генерация случайного имени имени для pantry", () -> {
            pantryName = RandomStringUtils.random(8, true, true);
        });

        sleep(2000); //Установлено из-за огрантчнгий сервиса

        step("Изменения имени pantry", () -> {
            given()
                    .filter(new AllureRestAssured())
                    .contentType("application/json; charset=UTF-8")
                    .body("{\n" +
                            "\t\"name\": \"" + pantryName + "\",\n" +
                            "\t\"description\": \"my test\"\n" +
                            "}")
                    .when()
                    .put("/pantry/" + myPantryId)
                    .then()
                    .log().all()
                    .statusCode(200)
                    .body("name", is(pantryName));
        });
        sleep(2000); //Установлено из-за огрантчнгий сервиса


        step("Получение данных о pantry", () -> {
            given()
                    .filter(new AllureRestAssured())
                    .contentType("application/json; charset=UTF-8")
                    .when()
                    .get("/pantry/" + myPantryId)
                    .then()
                    .log().all()
                    .statusCode(200)
                    .body("name", is(pantryName));
        });


    }

    @Test
    public void basketTest() {
        String basketName = RandomStringUtils.random(8, true, true);
        String testJson1 = "{\"key1\": \"test11\"}";
        String testJson2 = "{\"key1\": \"test33\",\n" +
                "\"key2\": \"test44\"}";

        sleep(2000); //Установлено из-за огрантчнгий сервиса
        step("Создание новый basket: " + basketName, () -> {
            given()
                    .filter(new AllureRestAssured())
                    .contentType("application/json; charset=UTF-8")
                    .body(testJson1)
                    .when()
                    .post("/pantry/" + myPantryId + "/basket/" + basketName)
                    .then()
                    .log().all()
                    .statusCode(200)
                    .body("html.body", is("Your Pantry was updated with basket: " +
                            basketName + "!"));

        });

        sleep(2000); //Установлено из-за огрантчнгий сервиса
        step("Изменение basket: " + basketName, () -> {
            given()
                    .filter(new AllureRestAssured())
                    .contentType("application/json; charset=UTF-8")
                    .body(testJson2)
                    .when()
                    .put("/pantry/" + myPantryId + "/basket/" + basketName)
                    .then()
                    .log().all()
                    .statusCode(200)
                    .body("key1", is("test33"))
                    .body("key2", is("test44"));

        });
        sleep(2000); //Установлено из-за огрантчнгий сервиса
        step("Данные из basket: " + basketName, () -> {
            given()
                    .filter(new AllureRestAssured())
                    .contentType("application/json; charset=UTF-8")
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
                    .contentType("application/json; charset=UTF-8")
                    .when()
                    .delete("/pantry/" + myPantryId + "/basket/" + basketName)
                    .then()
                    .log().all()
                    .statusCode(200)
                    .body("html.body", is(basketName + " was removed from your Pantry!"));
        });

        sleep(2000); //Установлено из-за огрантчнгий сервиса
        step(" не существует basket  : " + basketName, () -> {
            given()
                    .filter(new AllureRestAssured())
                    .contentType("application/json; charset=UTF-8")
                    .when()
                    .get("/pantry/" + myPantryId + "/basket/" + basketName)
                    .then()
                    .log().all()
                    .statusCode(400);
        });
    }

}
