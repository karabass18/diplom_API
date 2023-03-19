package pantryApiTests.tests;

import config.PantryConfig;
import io.restassured.RestAssured;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.BeforeAll;

public class TestBase {

    PantryConfig pantryConfig = ConfigFactory.create(PantryConfig.class, System.getProperties());
    //static
    String myPantryId = pantryConfig.getPantryId() ;


    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = "https://getpantry.cloud/apiv1";
    }


}
