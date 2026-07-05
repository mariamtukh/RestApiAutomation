package base;

import com.github.tomakehurst.wiremock.client.WireMock;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeSuite;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class BaseTest {

    @BeforeSuite
    public void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;

        WireMock.configureFor("localhost", 8080);
        setupStubs();
    }

    private void setupStubs() {
        stubFor(get(urlEqualTo("/users"))
                .withQueryParam("age", absent())
                .withQueryParam("gender", absent())
                .willReturn(aResponse().withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                    [
                      {"id":1,"name":"Alice","age":30,"gender":"female"},
                      {"id":2,"name":"Bob","age":25,"gender":"male"}
                    ]""")));

        stubFor(get(urlEqualTo("/users?age=30"))
                .willReturn(aResponse().withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("[{\"id\":1,\"name\":\"Alice\",\"age\":30,\"gender\":\"female\"}]")));

        stubFor(get(urlEqualTo("/users?gender=male"))
                .willReturn(aResponse().withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("[{\"id\":2,\"name\":\"Bob\",\"age\":25,\"gender\":\"male\"}]")));

        stubFor(get(urlEqualTo("/users?age=-1"))
                .willReturn(aResponse().withStatus(400)));

        stubFor(get(urlEqualTo("/users?gender=unknown"))
                .willReturn(aResponse().withStatus(422)));

        stubFor(get(urlEqualTo("/users?simulateError=true"))
                .willReturn(aResponse().withStatus(500)));
    }
}