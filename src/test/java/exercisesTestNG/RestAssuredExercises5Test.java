package exercisesTestNG;

import dataentities.Account;
import dataentities.AccountResponse;
import dataentities.Customer;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import static io.restassured.RestAssured.given;

public class RestAssuredExercises5Test extends Base {

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
     * Create a new Account object with 'savings' as the account
     * type
     * POST this object to /customer/12212/accounts
     * Verify that the response HTTP status code is equal to 201
     ******************************************************/

    // Use a POJO to pass a test data object
    @Test
    public void postAccountObject_checkResponseHttpStatusCode_expect201() {
        Account account = new Account("savings");
        given().
            spec(requestSpec).
            body(account).
            when().post("/customer/12212/accounts").
            then().assertThat().statusCode(ApiResponseStatus.CREATED.getCode());
    }

    /*******************************************************
     * Perform an HTTP GET to /customer/12212/accounts and
     * deserialize the response into an object of type
     * AccountResponse
     * Using a TestNG assertEquals() method, verify that the
     * number of account in the response (in other words,
     * the size() of the accounts property) is equal to 3
     ******************************************************/

    @Test
    public void getAccountsForCustomer12212_deserializeIntoList_checkListSize_shouldEqual3() {
        AccountResponse accountResponse =
        given().
            spec(requestSpec).
            when()
                .get("/customer/12212/accounts").then().extract().body().as(AccountResponse.class);
        Assert.assertEquals(accountResponse.getAccounts().size(), 3);
    }

    /*******************************************************
     * Create a new Customer object by using the constructor
     * that takes a first name and last name as its parameters
     * Use a first name and a last name of your own choosing
     * POST this object to /customer
     * Deserialize the response into another object of type
     * Customer and use TestNG assertEquals() assertions to
     * check that the first name and last name returned by
     * the API are the same as those you passed into the
     * constructor of the Customer method you POSTed
     ******************************************************/

    @Test
    public void postCustomerObject_checkReturnedFirstAndLastName_expectSuppliedValues() {
        String fN = "Pakalu"; 
        String lN = "Papito";
        Customer customerInput = new Customer(fN, lN);
        Customer recorded =
        given().
            spec(requestSpec).
            body(customerInput).
            when().post("/customer").then().extract().body().as(Customer.class);
        
        Assert.assertEquals(recorded.getFirstName(), fN);
        Assert.assertEquals(recorded.getLastName(), lN);
    }
}