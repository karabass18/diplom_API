package pantryApiTests.tests;

import io.qameta.allure.restassured.AllureRestAssured;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.sleep;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class PantryTest extends TestBase{

    String myPantryId = "2f572a5a-b13f-4981-9970-93993fbb22bb";
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
}
