package exercisesTestNG;

import com.github.tomakehurst.wiremock.WireMockServer;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

public class Base {
    static WireMockServer wireMockServer;
    static RequestSpecification requestSpec;

    public void setupServer() {
        wireMockServer = new WireMockServer(wireMockConfig()
            .port(9876)
            .globalTemplating(true));
        wireMockServer.start();
    }

    public void stopServer() {
        wireMockServer.stop();
    }

    public void createRequestSpecification() {
        requestSpec = new RequestSpecBuilder().
                setBaseUri("http://localhost").
                setPort(9876).
                build();
    }
}
