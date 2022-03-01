package api;

import model.Status;
import model.Ticket;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

/** Создание и проверка тикета */
public class CreateTicketTest extends BaseTest {

    @Test
    public void createTicketTest() {
        Ticket ticket = BaseTest.buildNewTicket(Status.OPEN,2);
        Ticket newTicket = createTicket(ticket);
        Ticket actual = getTicket(newTicket.getId());
        Assert.assertEquals(newTicket.hashCode(),actual.hashCode());
    }

    protected Ticket getTicket(int id) {
        return given()
                .pathParam("id", id)
                .when()
                .get("/api/tickets/{id}")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(Ticket.class);
    }
}
