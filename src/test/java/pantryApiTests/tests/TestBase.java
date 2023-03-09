package pantryApiTests.tests;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

public class TestBase {
    String myPantryId = "2f572a5a-b13f-4981-9970-93993fbb22bb";
    @BeforeAll
    public static void setUp() {

        RestAssured.baseURI = "https://getpantry.cloud/apiv1";
    }

}
