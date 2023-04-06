package pantryApiTests.Pages;

import pantryApiTests.models.TestingJsonModel;

import static Specs.RequestAndResponseSpec.requestSpec;
import static Specs.RequestAndResponseSpec.responseSpec;
import static com.codeborne.selenide.Selenide.sleep;
import static io.restassured.RestAssured.given;

public class BasketsTestsPage {
    public void deleteBasket(String pantryId, String basketName) {
        sleep(2000); //Установлено из-за огрантчнгий сервиса
        given(requestSpec)
                .when()
                .delete("/pantry/" + pantryId + "/basket/" + basketName)
                .then()
                .spec(responseSpec)
                .statusCode(200);
    }

    public void newBasket(String pantryId, String basketName, TestingJsonModel json) {
        sleep(2000); //Установлено из-за огрантчнгий сервиса
        given(requestSpec)
                .body(json)
                .when()
                .post("/pantry/" + pantryId + "/basket/" + basketName)
                .then()
                .spec(responseSpec)
                .statusCode(200);
    }

}
