package api;

import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class UsersApiClient {

    private static final String ENDPOINT = "/users";

    public Response getAllUsers() {
        return given().when().get(ENDPOINT);
    }

    public Response getUsersByAge(int age) {
        return given().queryParam("age", age).when().get(ENDPOINT);
    }

    public Response getUsersByGender(String gender) {
        return given().queryParam("gender", gender).when().get(ENDPOINT);
    }

    public Response simulateServerError() {
        return given().queryParam("simulateError", true).when().get(ENDPOINT);
    }
}
