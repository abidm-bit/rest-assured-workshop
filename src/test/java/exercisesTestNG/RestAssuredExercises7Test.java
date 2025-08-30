package exercisesTestNG;

import dataentities.Photo;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static io.restassured.RestAssured.given;

public class RestAssuredExercises7Test extends Base {


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

    @Test
    public void fromUserId_findPhotoTitle_expectPariaturSuntEveniet() {

        /*******************************************************
         * Perform a GET to /users and extract the user id
         * that corresponds to the user with username 'Karianne'
         *
         * Hint: use extract().path() and a 'find' filter to do this.
         *
         * Store the user id in a variable of type int
         ******************************************************/

        //filter thru usernames & store an id

int reuseUserId=
            given().
                spec(requestSpec).
            when()
                    .get("/users")
                    .then()
                    .extract()
                    .path("find{it.username=='Karianne'}.id");

        /*******************************************************
         * Use a JUnit assertEquals to verify that the userId
         * is equal to 4
         ******************************************************/
            Assert.assertEquals(reuseUserId,4);

        /*******************************************************
         * Perform a GET to /albums and extract all albums that
         * are associated with the previously retrieved user id.
         *
         * Hint: use extract().path() and a 'findAll' to do this.
         *
         * Store these in a variable of type List<Integer>.
         ******************************************************/

        // filter thru ids and find all albums for a user id

ArrayList<Integer> albumList=
            given().
                spec(requestSpec).
            when()
            .get("/albums")
            .then()
            .extract()
            .path(String.format("findAll{it.userId==%d}.id", reuseUserId));
//        System.out.println(albumList);         //debug to see if i stored the album list for user id 4

        /*******************************************************
         * Use a JUnit assertEquals to verify that the list has
         * exactly 10 items (hint: use the size() method)
         ******************************************************/

Assert.assertEquals(albumList.size(),10);

        /*******************************************************
         * Perform a GET to /albums/XYZ/photos, where XYZ is the
         * id of the fifth album in the previously extracted list
         * of album IDs (hint: use get(index) on the list).
         *
         * Deserialize the list of photos returned into a variable
         * of type List<Photo>.
         *
         * Hint: see
         * https://stackoverflow.com/questions/21725093/rest-assured-deserialize-response-json-as-listpojo
         * (the accepted answer should help you solve this one).
         ******************************************************/
        List<Photo> photoList = Arrays.asList(
            given().
                spec(requestSpec)
                        .pathParam("albumId",albumList.get(reuseUserId))
                    .when()
                    .get("/albums/{albumId}/photos").as(Photo[].class));

        /*******************************************************
         * Use a JUnit assertEquals to verify that the title of
         * the 32nd photo in the list equals 'pariatur sunt eveniet'
         *
         * Hint: use the get() method to retrieve an object with a
         * specific index from a List
         ******************************************************/
        Assert.assertEquals(photoList.get(31).getTitle(),"pariatur sunt eveniet");

    }
}