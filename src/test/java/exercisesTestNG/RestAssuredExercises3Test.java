package exercisesTestNG;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class RestAssuredExercises3Test extends Base {

    String reuseToken;

    @BeforeClass
    public void setup() {
        setupServer();
    }

    @AfterClass
    public void teardown() {
        stopServer();
    }

    @BeforeMethod
    public void buildRequest() {
        createRequestSpecification();
    }

    /*******************************************************
     * Perform a GET request to /token and pass in basic
     * authentication details with username 'john' and
     * password 'demo'.
     *
     * Extract the value of the 'token' element in the
     * response into a String variable.
     *
     * Use the token to authenticate using OAuth2 when sending
     * a GET request to /secure/customer/12212
     *
     * Verify that the status code of this response is equal to HTTP 200
     ******************************************************/

    @Test
    public void getTokenUsingBasicAuth_extractFromResponse_thenReuseAsOAuthToken() {
    reuseToken =
        given().
            spec(Base.requestSpec).
                auth().preemptive().basic("john","demo").
        when().
        get("/token").
        then().extract().path("token")
            ;
        given().
            spec(Base.requestSpec).
                auth().oauth2(reuseToken).
        when().
                get("/secure/customer/12212").
        then().assertThat().statusCode(ApiResponseStatus.OK.getCode());
    }
}