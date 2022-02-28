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
        // todo: создать тикет со статусом Closed, затем обновить тикет и проверить сообщение об ошибке (негативный сценарий)
        Ticket ticket = BaseTest.buildNewTicket(Status.CLOSED,2);
        Ticket newTicket = createTicket(ticket);
        Assert.assertEquals(ticket, newTicket);
        newTicket.setStatus(Status.OPEN.getCode());
        updateTicketNegative(newTicket);
    }

    private void updateTicketNegative(Ticket ticket) {
        // todo: отправить HTTP запрос для обновления данных тикета и сразу же проверить статус код (должен соответствовать ошибке)
        given()
                .pathParam("id", ticket.getId())
                .body(ticket)
                .when()
                .get("/api/tickets/{id}")
                .then()
                .statusCode(422);
    }
}
