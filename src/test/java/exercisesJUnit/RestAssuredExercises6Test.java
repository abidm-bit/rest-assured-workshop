package exercisesJUnit;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.hamcrest.Matchers.*;
import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.request;

@WireMockTest(httpPort = 9876)
public class RestAssuredExercises6Test {

    private RequestSpecification requestSpec;

    @BeforeEach
    public void createRequestSpecification() {

        requestSpec = new RequestSpecBuilder().
                setBaseUri("http://localhost").
                setPort(9876).
                setContentType(ContentType.JSON).
                build();
    }

    /*******************************************************
     * Create a new payload for a GraphQL query using a
     * HashMap and the specified query (with hardcoded ID)
     * POST this object to /graphql
     *
     * Assert that the name of the fruit is equal to "Apple"
     * Use "data.fruit.fruit_name" as the GPath
     * expression to extract the required value from the response
     *
     * Also, assert that the tree name is equal to "Malus"
     * Use "data.fruit.tree_name" as the GPath
     * expression to extract the required value from the response
     ******************************************************/

    @Test
    public void getFruitData_checkFruitAndTreeName_shouldBeAppleAndMalus() {

        String queryString = """
                {
                    fruit(id: 1) {
                        id
                        fruit_name
                        tree_name
                    }
                }
                """;
        HashMap<String,Object> graphQLQuery = new HashMap<>();
        graphQLQuery.put("query",queryString);

        given().
            spec(requestSpec).
        contentType(ContentType.JSON)
                .body(graphQLQuery).
        when().
                post("/graphql").
        then()
                .assertThat()
                .body("data.fruit.fruit_name",equalTo("Apple"))
                .body("data.fruit.tree_name",equalTo("Malus"));
    }

    /*******************************************************
     * Transform this Test into a ParameterizedTest, using
     * a CsvSource data source with three test data rows:
     * ---------------------------------
     * fruit id | fruit name | tree name
     * ---------------------------------
     *        1 |      Apple |     Malus
     *        2 |       Pear |     Pyrus
     *        3 |     Banana |      Musa
     *
     * Parameterize the test
     *
     * Create a new GraphQL query from the given query string
     * Pass in the fruit id as a variable value
     *
     * POST this object to /graphql
     *
     * Assert that the HTTP response status code is 200
     *
     * Assert that the name of the fruit is equal to the value in the data source
     * Use "data.fruit.fruit_name" as the GPath
     * expression to extract the required value from the response
     *
     * Also, assert that the tree name is equal to the value in the data source
     * Use "data.fruit.tree_name" as the GPath
     * expression to extract the required value from the response
     ******************************************************/

    @ParameterizedTest
    @CsvSource({
            "1,Apple,Malus",
            "2,Pear,Pyrus",
            "3,Banana,Musa"
    })
    public void getFruitDataById_checkFruitNameAndTreeName(int fruitId, String expectedFruitName, String expectedTreeName) {

        String queryString = """
                query GetFruit($id: ID!)
                {
                    fruit(id: $id) {
                        id
                        fruit_name
                        tree_name
                    }
                }
                """;

        HashMap<String, Object> variables = new HashMap<>();
        variables.put("id", fruitId);

        HashMap<String, Object> graphqlQuery = new HashMap<>();
        graphqlQuery.put("query", queryString);
        graphqlQuery.put("variables", variables);

        given().
            spec(requestSpec)
                .body(graphqlQuery).
        when().
                post("/graphql").
        then()
                .assertThat()
                .statusCode(ApiResponseStatus.OK.getCode())
                .and()
                .body("data.fruit.fruit_name",equalTo(expectedFruitName))
                .and()
                .body("data.fruit.tree_name",equalTo(expectedTreeName));
    }
}