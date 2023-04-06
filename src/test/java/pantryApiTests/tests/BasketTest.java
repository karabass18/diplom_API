package pantryApiTests.tests;

import helpers.NewName;
import org.junit.jupiter.api.Test;
import pantryApiTests.models.TestingJsonModel;

import static Specs.RequestAndResponseSpec.requestSpec;
import static Specs.RequestAndResponseSpec.responseSpec;
import static com.codeborne.selenide.Selenide.sleep;
import static helpers.CustomApiListener.withCustomTemplates;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.is;
import static pantryApiTests.components.HelpersForBasketsTests.deleteBasket;

public class BasketTest extends TestBase {

    @Test
    public void addNewBasketTest() {


        sleep(2000); //Установлено из-за огрантчнгий сервиса
        //  String basketName = RandomStringUtils.random(8, true, true);
        String basketName = NewName.newName();
        TestingJsonModel testJson = new TestingJsonModel();
        testJson.setKey1("test11");

        step("Создание новый basket: " + basketName, () -> {
            given(requestSpec)
                    .body(testJson)
                    .when()
                    .post("/pantry/" + myPantryId + "/basket/" + basketName)
                    .then()
                    .spec(responseSpec)
                    .statusCode(200)
                    .body("html.body", is("Your Pantry was updated with basket: " +
                            basketName + "!"));
        });
        sleep(2000); //Установлено из-за огрантчнгий сервиса

        step("Удаление созданой на прошлом шаге basket: " + basketName, () -> {
           // deleteBasket(myPantryId,basketName);
            given(requestSpec)
                    .when()
                    .delete("/pantry/" + myPantryId + "/basket/" + basketName)
                    .then()
                    .spec(responseSpec)
                    .statusCode(200);
        });
    }

    @Test
    public void changeDataInTheBasketTest() {
        sleep(2000); //Установлено из-за огрантчнгий сервиса

        String basketName = NewName.newName();
        TestingJsonModel testJson1 = new TestingJsonModel();
        testJson1.setKey1("test11");

        TestingJsonModel testJson2 = new TestingJsonModel();
        testJson2.setKey1("test33");
        testJson2.setKey2("test44");

        step("Создание новый basket: " + basketName, () -> {
            given(requestSpec)
                    .body(testJson1)
                    .when()
                    .post("/pantry/" + myPantryId + "/basket/" + basketName)
                    .then()
                    .spec(responseSpec)
                    .statusCode(200);
        });
        sleep(2000); //Установлено из-за огрантчнгий сервиса

        step("Изменение basket их проверка: " + basketName, () -> {
            given()
                    .filter(withCustomTemplates())
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

        step("Удаление ранее созданой basket: " + basketName, () -> {
            given(requestSpec)
                    .when()
                    .delete("/pantry/" + myPantryId + "/basket/" + basketName)
                    .then()
                    .spec(responseSpec)
                    .statusCode(200);
        });
    }

    @Test
    public void getBasketInfoTest() {
        sleep(2000); //Установлено из-за огрантчнгий сервиса

        String basketName = NewName.newName();

        TestingJsonModel testJson = new TestingJsonModel();
        testJson.setKey1("test55");
        testJson.setKey2("test66");
        testJson.setKey3("test77");

        step("Создание новый basket: " + basketName, () -> {
            given(requestSpec)
                    .body(testJson)
                    .when()
                    .post("/pantry/" + myPantryId + "/basket/" + basketName)
                    .then()
                    .spec(responseSpec)
                    .statusCode(200);
        });
        sleep(2000); //Установлено из-за огрантчнгий сервиса

        step("Получаем и проверяем Json из basket: " + basketName, () -> {
            TestingJsonModel response =
                    given(requestSpec)
                            .when()
                            .get("/pantry/" + myPantryId + "/basket/" + basketName)
                            .then()
                            .spec(responseSpec)
                            .statusCode(200)
                            .extract().as(TestingJsonModel.class);
            assertThat(response).isEqualTo(testJson);
        });
        sleep(2000); //Установлено из-за огрантчнгий сервиса
        step("Удаление ранее созданой basket: " + basketName, () -> {
            given(requestSpec)
                    .when()
                    .delete("/pantry/" + myPantryId + "/basket/" + basketName)
                    .then()
                    .spec(responseSpec)
                    .statusCode(200);
        });
    }

    @Test
    public void deleteBasketTest() {
        sleep(2000); //Установлено из-за огрантчнгий сервиса
        String basketName = NewName.newName();
        TestingJsonModel testJson = new TestingJsonModel();
        testJson.setKey1("test11");

        step("Создание новой basket: " + basketName, () -> {
            given(requestSpec)
                    .body(testJson)
                    .when()
                    .post("/pantry/" + myPantryId + "/basket/" + basketName)
                    .then()
                    .spec(responseSpec)
                    .statusCode(200);
        });
        sleep(2000); //Установлено из-за огрантчнгий сервиса
        step("Удаление basket: " + basketName, () -> {
            given(requestSpec)
                    .when()
                    .delete("/pantry/" + myPantryId + "/basket/" + basketName)
                    .then()
                    .spec(responseSpec)
                    .statusCode(200)
                    .body("html.body", is(basketName + " was removed from your Pantry!"));
        });
        sleep(2000); //Установлено из-за огрантчнгий сервиса
        step(" Проверяем, что не существует basket  : " + basketName, () -> {
            given(requestSpec)
                    .when()
                    .get("/pantry/" + myPantryId + "/basket/" + basketName)
                    .then()
                    .spec(responseSpec)
                    .statusCode(400);
        });
    }
}
