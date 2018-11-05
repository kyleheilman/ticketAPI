package tickets.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import tickets.Model.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path="/vivid")
public class TicketController {
    private SQLConnector sqlConnector = new SQLConnector("jdbc:postgresql://localhost:5432/test");



    //Method to retrieve available tickets based on eventID

    @RequestMapping(path="/availableTickets", method={RequestMethod.POST}, headers="Accept=application/json")
    public ResponseEntity<EventInfo> execute(@RequestBody Event thisEvent) throws Exception {
        List<String> temp = new ArrayList<>();
        int id = thisEvent.getEventId();
        try {

            ResultSet iter = sqlConnector.executeSql("Select t.price, t.row, t.section from ticket AS t inner join event as e on t.event_id = e.event_id AND t.available>0 AND t.event_id = " + id + ";");
            if (iter != null) {
                while (iter.next()) {
                    //Adding each ticket to list
                    String toAdd = "" + iter.getString("price") + ", " + iter.getString("row") + ", " + iter.getString("section");
                    System.out.println(toAdd);
                    temp.add(toAdd);
                }
            }
        } catch (Exception e) {
            System.out.println("Unable to show available tickets");
            e.printStackTrace();
            throw e;
        }
        return new ResponseEntity<>(new EventInfo(temp), HttpStatus.OK);
    }
//posts ticket into ticket table
    //Returns the ticket information
    @RequestMapping(path="/postTicket", method={RequestMethod.POST}, headers="Accept=application/json")
    public ResponseEntity<Ticket> execute(@RequestBody Ticket thisTicket) throws Exception {
        try {
             int ticket_id = thisTicket.getTicketID();

             System.out.print(ticket_id);

             int event_id = thisTicket.getEventID();

             int available = thisTicket.getAvailable();

             String section = thisTicket.getSection();

             String row = thisTicket.getRow();

             int price = thisTicket.getPrice();
             int seller_id = thisTicket.getSellerID();

            sqlConnector.executeSql("INSERT INTO ticket(ticket_id, event_id, section, available, price, row, seller_id) VALUES (" +ticket_id + ", " + event_id + "," + "'" + section + "'" + "," + available + "," + price + "," + "'" + row  + "'" + "," + seller_id + ");");
        } catch (Exception e) {
            System.out.println("Unable to post ticket");
            e.printStackTrace();
            throw e;
        }
        return new ResponseEntity<>(thisTicket, HttpStatus.OK);
    }

//updates ticket availability to 0 based on ticket_id
    //Returns the ticket that was updated
    @RequestMapping(path="/updateTicket", method={RequestMethod.POST}, headers="Accept=application/json")
    public ResponseEntity<UpdateTicket> execute(@RequestBody UpdateTicket thisEvent) throws Exception {
        int ticket_id = 0;
        try {
            ticket_id = thisEvent.getTicketID();
            ResultSet iter = sqlConnector.executeSql("update ticket set available = 0 WHERE ticket_id = " + ticket_id + ";");
        } catch (Exception e) {
            System.out.println("Unable to update ticket");
            e.printStackTrace();
            throw e;
        }
        return new ResponseEntity<>(thisEvent, HttpStatus.OK);
    }

//retrieves the best ticket based on lowest ticket price for event

    @RequestMapping(path="/bestTicket", method={RequestMethod.POST}, headers="Accept=application/json")
    public ResponseEntity<EventInfo> execute(@RequestBody BestTicket currEvent) throws Exception {
        List<String> ticket = new ArrayList<>();
        try {
            int id = currEvent.getEventID();
            ResultSet iter = sqlConnector.executeSql("SELECT t.price, t.event_id, t.row, t.section, t.available FROM ticket as t inner join event as e on t.event_id = e.event_id WHERE price = (SELECT MIN(price) from ticket as t WHERE t.event_id = " + id + ") AND t.available > 0 AND t.event_id = " + id + ";");
            if (iter != null) {
                while (iter.next()) {
                    String toAdd = "" + iter.getString("price") + ", " + iter.getString("row") + ", " + iter.getString("section");
                    ticket.add(toAdd);
                }
            }
        } catch (Exception e) {
            System.out.println("Unable to retrieve best ticket");
            e.printStackTrace();
            throw e;
        }
        //EventInfo eventInfo =
        return new ResponseEntity<>(new EventInfo(ticket), HttpStatus.OK);
    }


}
