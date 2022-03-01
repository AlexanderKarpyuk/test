package api;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import model.Status;
import model.Ticket;
import org.testng.annotations.BeforeClass;

import java.io.IOException;

import static io.restassured.RestAssured.given;

/** Абстрактный класс, содержащий общие для всех тестов методы */
public abstract class BaseTest {
    @BeforeClass
    public void prepare() {
        try {
            System.getProperties().load(ClassLoader.getSystemResourceAsStream("config.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String baseUri = System.getProperty("base.uri");
        if (baseUri == null || baseUri.isEmpty()) {
            throw new RuntimeException("В файле \"config.properties\" отсутствует значение \"base.uri\"");
        }
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.setBaseUri(baseUri);
        requestSpecBuilder.addHeader("api_key", "Mdv31");
        requestSpecBuilder.setAccept(ContentType.JSON);
        requestSpecBuilder.setContentType(ContentType.JSON);
        RestAssured.requestSpecification = requestSpecBuilder // дополнительная инструкция полного логгирования для RestAssured
                .build();
        RestAssured.filters(new ResponseLoggingFilter());
    }

    protected static Ticket buildNewTicket(Status status, int priority) {
        Ticket ticket = new Ticket();
        ticket.setPriority(priority);
        ticket.setStatus(status.getCode());
        return ticket;
    }

    protected Ticket createTicket(Ticket ticket) {
        return given()
                .body(ticket)
                .when()
                .post("/api/tickets")
                .then()
                .statusCode(201)
                .extract()
                .body()
                .as(Ticket.class);
    }
}
