package pantryApiTests.tests;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

public class TestBase {

    @BeforeAll
    public static void setUp() {
        RestAssured.baseURI = "https://getpantry.cloud/apiv1";
    }
}
