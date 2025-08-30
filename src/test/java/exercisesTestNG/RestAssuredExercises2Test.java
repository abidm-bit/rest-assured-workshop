package exercisesTestNG;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.testng.annotations.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class RestAssuredExercises2Test extends Base {

    public String customerIdEndpoint = "/customer/{customerId}";

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
     * Transform these tests into a single ParameterizedTest,
     * using a CsvSource data source with three test data rows:
     * ------------------------------------
     * customer ID | first name | last name
     * ------------------------------------
     * 12212       | John       | Smith
     * 12323       | Susan      | Holmes
     * 14545       | Anna       | Grant
     *
     * Request user data for the given user IDs by sending
     * an HTTP GET to /customer/<customerID>.
     *
     * Use the test data collection created
     * above. Check that both the first name and the last name
     * for each of the customer IDs matches the expected values
     * listed in the table above
     *
     * Use the GPath expressions "firstName" and "lastName",
     * respectively, to extract the required response body elements
     ******************************************************/


    @DataProvider(name = "customerInfo")
    public Object[][] custy() {
        return new Object[][]{
                {12212, "John", "Smith"},
                {12323, "Susan", "Holmes"},
                {14545, "Anna", "Grant"}
        };
    }
    @Test(dataProvider = "customerInfo")
    public void requestData_checkNames(int customerId, String firstName, String lastName) {
        given().
            spec(Base.requestSpec).pathParam("customerId",customerId).
        when().
            get(customerIdEndpoint).
        then().
            assertThat().
            body("firstName", equalTo(firstName)).
            body("lastName", equalTo(lastName));
    }

}
