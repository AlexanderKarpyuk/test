package api;

import model.Status;
import model.Ticket;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

/** Обновление тикета */
public class UpdateTicketTest extends BaseTest {

    @Test
    public void updateTicketTest() {
        Ticket ticket = BaseTest.buildNewTicket(Status.CLOSED,2);
        Ticket newTicket = createTicket(ticket);
        Ticket actual = updateTicketNegative(newTicket);
    }

    private Ticket updateTicketNegative(Ticket ticket) {
        ticket.setStatus(1);
        return given()
                .pathParam("id", ticket.getId())
                .body(ticket)
                .when()
                .patch("/api/tickets/{id}")
                .then()
                .statusCode(422)
                .extract()
                .body()
                .as(Ticket.class);
    }
}
