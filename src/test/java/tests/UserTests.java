package tests;

import api.UsersApiClient;
import base.BaseTest;
import com.fasterxml.jackson.core.type.TypeReference;
import io.restassured.response.Response;
import listeners.ResultListener;
import models.User;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import providers.TestDataProvider;
import utils.JsonUtils;

import java.util.List;

import static org.testng.Assert.assertEquals;

@Listeners(ResultListener.class)
public class UserTests extends BaseTest {

    private final UsersApiClient usersApi = new UsersApiClient();

    @Test
    public void testGetAllUsers_Positive() throws Exception {
        Response response = usersApi.getAllUsers();
        assertEquals(response.getStatusCode(), 200);

        List<User> users = JsonUtils.objectMapper.readValue(
                response.asString(), new TypeReference<List<User>>() {});
        assertEquals(users.size(), 2);
    }

    @Test(dataProvider = "ageFilter", dataProviderClass = TestDataProvider.class)
    public void testFilterByAge_Positive(int age, int expectedStatus, String expectedName) throws Exception {
        Response response = usersApi.getUsersByAge(age);
        assertEquals(response.getStatusCode(), expectedStatus);

        List<User> users = JsonUtils.objectMapper.readValue(
                response.asString(), new TypeReference<List<User>>() {});
        assertEquals(users.get(0).getName(), expectedName);
    }

    @Test(dataProvider = "genderFilter", dataProviderClass = TestDataProvider.class)
    public void testFilterByGender_Positive(String gender, int expectedStatus, String expectedName) throws Exception {
        Response response = usersApi.getUsersByGender(gender);
        assertEquals(response.getStatusCode(), expectedStatus);

        List<User> users = JsonUtils.objectMapper.readValue(
                response.asString(), new TypeReference<List<User>>() {});
        assertEquals(users.get(0).getName(), expectedName);
    }

    @Test(dataProvider = "invalidAge", dataProviderClass = TestDataProvider.class)
    public void testInvalidAge_Negative(int age, int expectedStatus) {
        assertEquals(usersApi.getUsersByAge(age).getStatusCode(), expectedStatus);
    }

    @Test(dataProvider = "invalidGender", dataProviderClass = TestDataProvider.class)
    public void testInvalidGender_Negative(String gender, int expectedStatus) {
        assertEquals(usersApi.getUsersByGender(gender).getStatusCode(), expectedStatus);
    }

    @Test
    public void testInternalServerError_Negative() {
        assertEquals(usersApi.simulateServerError().getStatusCode(), 500);
    }
}
