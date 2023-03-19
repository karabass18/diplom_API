package pantryApiTests.tests;

import helpers.NewName;
import org.junit.jupiter.api.Test;
import pantryApiTests.models.BodyForPantryPutRequestModel;
import pantryApiTests.models.ResponseOfGetPantryModel;

import static Specs.RequestAndResponseSpec.requestSpec;
import static Specs.RequestAndResponseSpec.responseSpec;
import static com.codeborne.selenide.Selenide.sleep;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.is;

public class PantryTest extends TestBase {

    @Test
    public void changePantryName() {
        sleep(2000); //Установлено из-за огрантчнгий сервиса
        String pantryName = NewName.newName();

        BodyForPantryPutRequestModel bodyForPantryPutRequest = new BodyForPantryPutRequestModel();
        bodyForPantryPutRequest.setName(pantryName);
        bodyForPantryPutRequest.setDescription("my test");

        given(requestSpec)
                .body(bodyForPantryPutRequest)
                .when()
                .put("/pantry/" + myPantryId)
                .then()
                .spec(responseSpec)
                .statusCode(200)
                .body("name", is(pantryName));
    }

    @Test
    public void getPantryInfo() {
        sleep(2000); //Установлено из-за огрантчнгий сервиса
        ResponseOfGetPantryModel response =
                given(requestSpec)
                        .when()
                        .get("/pantry/" + myPantryId)
                        .then()
                        .spec(responseSpec)
                        .statusCode(200)
                        // .body("name", is(pantryName))
                        .extract().as(ResponseOfGetPantryModel.class);

        assertThat(response.getDescription()).isEqualTo("my test");
        assertThat(response.getPercentFull()).isBetween(0, 100);
        assertThat(response.isNotifications());

    }

}
