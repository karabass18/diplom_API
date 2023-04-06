package pantryApiTests.components;

import static Specs.RequestAndResponseSpec.requestSpec;
import static Specs.RequestAndResponseSpec.responseSpec;
import static io.restassured.RestAssured.given;

public class HelpersForBasketsTests {
    public static void deleteBasket(String pantryId, String basketName){
        given(requestSpec)
                .when()
                .delete("/pantry/" + pantryId + "/basket/" + basketName)
                .then()
                .spec(responseSpec)
                .statusCode(200);
    }

}
